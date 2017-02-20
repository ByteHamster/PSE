package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Pipe;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class visualizes the valve that is part of every pipe.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class ValveDrawer extends RotatingControlDrawer {

    private Pipe pipe;
    private double radius;
    private double relValveWidth;
    private double relValveHeight;

    /**
     * Generates a new drawer object for valves
     * @param pos The center of the drawer
     * @param pipe The pipe to which the valve is attached
     * @cols The number of columns in which the Tanks are ordered
     */
    public ValveDrawer(Point2D pos, Pipe pipe, int cols) {
        super(pos, 10);

        this.pipe = pipe;
        radius = ViewConstants.VALVE_WIDTH / cols / 2;
        relValveWidth = ViewConstants.VALVE_WIDTH / cols;
        relValveHeight = ViewConstants.VALVE_HEIGHT / cols;
    }

    /**
     * The Drawer draws itself onto the GraphicsContext at its position.
     * @param context The context that the object draws itself onto
     */
    @Override
    public final void draw(GraphicsContext context, long time) {
        // The total time is taken, not the difference to the last execution. It does not matter
        // what the start rotation is and this way there is no bookkeeping with last time
        // and difference in rotation to the last execution while achieving the same look.
        double elapsedMins = ((double) time) / (1000000000.0 * 60);
        // Convert the minutes to degrees to be turned in total
        double degrees = elapsedMins * getSpeed() * 360;

        Canvas canvas = context.getCanvas();
        double totalWidth = canvas.getWidth();
        double totalHeight = canvas.getHeight();

        double point1xPos = (getPosition().getX() - radius) * totalWidth;
        double point1yPos = (getPosition().getY() + radius) * totalHeight - 2 * ViewConstants.VALVE_LINE_WIDTH;
        double point3xPos = (getPosition().getX() + radius) * totalWidth;
        double point3yPos = point1yPos;

        double point2xPos = getPosition().getX() * totalWidth;
        double point2yPos = getPosition().getY() * totalHeight
                - (totalWidth - totalHeight) * radius - 2 * ViewConstants.VALVE_LINE_WIDTH;
        double point4xPos = point2xPos;
        double point4yPos = (getPosition().getY() + 2 * radius) * totalHeight
                + (totalWidth - totalHeight) * radius - 2 * ViewConstants.VALVE_LINE_WIDTH;

        double centerX = getPosition().getX() * totalWidth;
        double centerY = (getPosition().getY() + radius) * totalHeight - 2 * ViewConstants.VALVE_LINE_WIDTH;

        context.setStroke(Color.BLACK);
        context.setLineWidth(ViewConstants.VALVE_LINE_WIDTH);
        context.setFill(Color.WHITE);

        double valveHeight = relValveHeight * totalWidth;
        double valveWidth = relValveWidth * totalWidth;

        context.fillRect(point1xPos, point2yPos, valveWidth, valveHeight);
        context.strokeRect(point1xPos, point2yPos, valveWidth, valveHeight);

        context.strokeLine(rotateX(point1xPos, centerX, point1yPos, centerY, degrees),
                rotateY(point1xPos, centerX, point1yPos, centerY, degrees),
                rotateX(point3xPos, centerX, point3yPos, centerY, degrees),
                rotateY(point3xPos, centerX, point3yPos, centerY, degrees));
        context.strokeLine(rotateX(point2xPos, centerX, point2yPos, centerY, degrees),
                rotateY(point2xPos, centerX, point2yPos, centerY, degrees),
                rotateX(point4xPos, centerX, point4yPos, centerY, degrees),
                rotateY(point4xPos, centerX, point4yPos, centerY, degrees));
        context.strokeOval(point1xPos, point2yPos, radius * 2 * totalWidth, radius * 2 * totalWidth);

        context.setFont(Font.font("Arial", (relValveHeight - relValveWidth) * totalWidth));
        context.setFill(Color.BLUE);
        //Offset of 1 to avoid writing over the valve outline
        context.fillText(String.valueOf(pipe.getValveThreshold()), point1xPos,
                point2yPos + valveHeight - 1);
    }
}
