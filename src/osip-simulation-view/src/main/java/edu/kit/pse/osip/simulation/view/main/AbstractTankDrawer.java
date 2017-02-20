package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 * This class visualizes a tank holding a colored liquid. It knows its position as well as the color
 * of the content. The changing part of the visualization are the fill level of the tank and, possibly,
 * the  color of the liquid.
 */
public abstract class AbstractTankDrawer extends ObjectDrawer {

    private AbstractTank tank;
    private double width;
    private double height;

    private double inBoxHorPadding;
    private double inBoxVertPadding;
    private double relInBoxWidth;
    private double relInBoxHeight;
    private double outBoxHorPadding;
    private double outBoxVertPadding;
    private double relOutBoxWidth;
    private double relOutBoxHeight;
    private double relOvalHeight;

    /**
     * Sets the position by using super(pos) and sets the tank
     * @param pos The upper left corner of the tank
     * @param tank The tank to get the attributes from
     * @param rows The number of rows in which the Tanks are ordered.
     * @param cols The number of columns in which the Tanks are ordered
     */
    public AbstractTankDrawer(Point2D pos, AbstractTank tank, int rows, int cols) {
        super(pos);
        this.tank = tank;

        width = 1.0 / cols;
        height = 1.0 / rows;

        relInBoxHeight = ViewConstants.INBOX_PERCENT / rows;
        relInBoxWidth = ViewConstants.INBOX_PERCENT / cols;
        relOutBoxHeight = ViewConstants.OUTBOX_PERCENT / rows;
        relOutBoxWidth = ViewConstants.OUTBOX_PERCENT / cols;

        inBoxHorPadding = (1 - ViewConstants.INBOX_PERCENT) / 2 / cols;
        inBoxVertPadding = (1 - ViewConstants.INBOX_PERCENT) / 2 / rows;
        outBoxHorPadding = (1 - ViewConstants.OUTBOX_PERCENT) / 2 / cols;
        outBoxVertPadding = (1 - ViewConstants.OUTBOX_PERCENT) / 2 / rows;

        relOvalHeight = ViewConstants.OVAL_PERCENT / rows;
    }
    /**
     * Sets the width of this tank
     * @param width The width of the tank
     */
    public final void setWidth(double width) {
        this.width = width;
    }

    /**
     * Gets the width of this tank
     * 
     * @return the width
     */
    public final double getWidth() {
        return width;
    }

    /**
     * Sets the height of this tank
     * @param height The height of the tank
     */
    public final void setHeight(double height) {
        this.height = height;
    }

    /**
     * Gets the height of this tank
     * 
     * @return the height
     */
    public final double getHeight() {
        return height;
    }

    /**
     * Contains the main calls necessary to draw the tank. Uses the abstract method drawSensors() for detail.
     * @param context The GraphicsContext on which the tank is drawn
     */
    @Override
    public final void draw(GraphicsContext context, long time) {
        // get the ouside values
        Canvas canvas = context.getCanvas();
        double totalWidth = canvas.getWidth();
        double totalHeight = canvas.getHeight();

        double fillLevel = tank.getFillLevel() / SimulationConstants.TANK_SIZE;
        Color fluidColor = getTankColor();

        // start the calculated values
        double outBoxXPos = (getPosition().getX() + outBoxHorPadding) * totalWidth;
        double outBoxYPos = (getPosition().getY() + outBoxVertPadding) * totalHeight;
        double outBoxWidth = relOutBoxWidth * totalWidth;
        double outBoxHeight = relOutBoxHeight * totalHeight;

        double inBoxXPos = (getPosition().getX() + inBoxHorPadding) * totalWidth;
        double inBoxYPos = (getPosition().getY() + inBoxVertPadding) * totalHeight;
        double inBoxWidth = relInBoxWidth * totalWidth;
        double inBoxHeight = relInBoxHeight * totalHeight;

        double totalOvalHeight = relOvalHeight * totalHeight;
        double topOvalYPos = inBoxYPos - (totalOvalHeight / 2);
        double botOvalYPos = inBoxYPos + inBoxHeight - (totalOvalHeight / 2);

        double fluidTopYPos = inBoxYPos + (1 - fillLevel) * inBoxHeight;
        double fluidHeight = inBoxHeight * fillLevel;

        double fluidTopOvalYPos = fluidTopYPos - totalOvalHeight / 2;

        context.setFill(Color.LIGHTGREY);
        context.setLineWidth(3);
        context.fillRect(outBoxXPos, outBoxYPos, outBoxWidth, outBoxHeight);

        context.setStroke(Color.BLACK);
        context.setFill(Color.WHITE);
        context.fillRect(inBoxXPos, inBoxYPos, inBoxWidth, inBoxHeight);
        context.strokeRect(inBoxXPos, inBoxYPos, inBoxWidth, inBoxHeight);

        context.fillOval(inBoxXPos, botOvalYPos, inBoxWidth, totalOvalHeight);
        context.strokeOval(inBoxXPos, botOvalYPos, inBoxWidth, totalOvalHeight);
        context.fillOval(inBoxXPos, topOvalYPos, inBoxWidth, totalOvalHeight);

        // Draw no fluid at all if the tank is empty
        // Drawn by setting the bottom oval, a rectangle on top and then the top oval
        if (fillLevel > 0) {
            context.setFill(fluidColor);
            context.fillOval(inBoxXPos, botOvalYPos, inBoxWidth, totalOvalHeight);
            context.fillRect(inBoxXPos, fluidTopYPos, inBoxWidth, fluidHeight);

            context.fillOval(inBoxXPos, fluidTopOvalYPos, inBoxWidth, totalOvalHeight);
            context.setLineWidth(1);
            context.strokeOval(inBoxXPos, fluidTopOvalYPos, inBoxWidth, totalOvalHeight);
        }

        context.setFill(Color.BLACK);
        //TODO: Figure out dynamic font size
        context.setFont(Font.font("Arial", 25));
        double textYPos = (getPosition().getY() + outBoxVertPadding) * totalHeight + outBoxHeight;
        String name = Translator.getInstance().getString("simulation.serverName." + tank.getName());
        context.fillText(name, outBoxXPos, textYPos);

        context.setLineWidth(3);
        context.strokeOval(inBoxXPos, topOvalYPos, inBoxWidth, totalOvalHeight);
        drawSensors(context, time);
    }

    private Color getTankColor() {
        edu.kit.pse.osip.core.model.base.Color tankColor = tank.getLiquid().getColor();
        double red = tankColor.getR();
        double green = tankColor.getG();
        double blue = tankColor.getB();
        return Color.color(red, green, blue);
    }

    /**
     * Used by draw(). Adds some detail to the tank depending on tank type.
     * @param context The GraphicsContext on which the sensors are drawn
     * @param time The time passed
     */
    public abstract void drawSensors(GraphicsContext context, long time);

    /**
     * Gets the point where pipes can attach to the bottom of the tank. This point lies in the
     * middle of the tank horizontally and and the lowest point of the bottom oval vertically
     * @return The Point2D sitting at the bottom middle of the tank.
     */
    public Point2D getPipeStartPoint() {
        double xPos = getPosition().getX() + inBoxHorPadding + relInBoxWidth / 2;
        double yPos = getPosition().getY() + inBoxVertPadding + relInBoxHeight + relOvalHeight / 2;
        return new Point2D(xPos, yPos);
    }

    /**
     * Gets the point number pointnum at the top of the tank, assuming pointCount points
     * are wanted. All points are evenly spaced.
     * @param pointNum The number of the point that is wanted.
     * @param pointCount The number of pipes that have to fit onto the Tank
     * @return The Point2D that sits at the top of the tank at (pointNum/ (tankCount + 1))
     */
    public Point2D getPipeEndPoint(int pointNum, int pointCount) {
        double xPos = getPosition().getX() + inBoxHorPadding
                + (relInBoxWidth * pointNum / (pointCount + 1));
        double yPos = getPosition().getY() + inBoxVertPadding;
        return new Point2D(xPos, yPos);
    }

    /**
     * Gets the Point2D where a new pipe, marking an input into the production, starts at a Tank.
     * This Point2D marks the top middle of the outer box.
     * @return The Point2D sitting at the top middle of the tank.
     */
    public Point2D getPipeTopEntry() {
        double xPos = getPosition().getX() + inBoxHorPadding + relInBoxWidth / 2;
        double yPos = getPosition().getY() + outBoxVertPadding;
        return new Point2D(xPos, yPos);
    }

    /**
     * Gets the Point2D where a pipe, marking an output of the production, leaves the simulation after
     * coming from the tank..
     * This Point2D marks the bottom middle of the outer box.
     * @return The Point2D sitting at the bottom middle of the tank compartment
     */
    public Point2D getPipeBotExit() {
        double xPos = getPosition().getX() + inBoxHorPadding + relInBoxWidth / 2;
        double yPos = getPosition().getY() + outBoxVertPadding + relOutBoxHeight;
        return new Point2D(xPos, yPos);
    }
}
