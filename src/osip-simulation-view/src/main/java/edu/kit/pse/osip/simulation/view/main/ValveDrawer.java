package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Pipe;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class visualizes the valve that is part of every pipe.
 *
 * @version 1.1
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
     * @param rows The number of rows in which the Tanks are ordered
     */
    public ValveDrawer(Point2D pos, Pipe pipe, int rows) {
        super(pos, pipe.getValveThreshold());

        this.pipe = pipe;
        radius = ViewConstants.VALVE_WIDTH / rows / 2;
        relValveWidth = ViewConstants.VALVE_WIDTH / rows;
        relValveHeight = ViewConstants.VALVE_HEIGHT / rows;
    }

    @Override
    public final void draw(GraphicsContext context, double timeDiff) {
        setSpeed(pipe.getValveThreshold());

        if (pipe.isLiquidEntering()) {
            updateDegrees(timeDiff, 0.5);
        }

        Canvas canvas = context.getCanvas();
        double totalWidth = canvas.getWidth();
        double totalHeight = canvas.getHeight();

        double valveHeight = relValveHeight * totalHeight;
        double valveWidth = relValveWidth * totalHeight;

        double valveTopLeftX = (getPosition().getX() - radius) * totalWidth
                - (totalHeight - totalWidth) * radius;
        double valveTopLeftY = getPosition().getY() * totalHeight;

        double point1xPos = (getPosition().getX() - radius) * totalWidth
                - (totalHeight - totalWidth) * radius + ViewConstants.VALVE_CIRCLE_DIST * valveWidth;
        double point1yPos = (getPosition().getY() + radius) * totalHeight;
        double point3xPos = point1xPos + valveWidth - 2 * ViewConstants.VALVE_CIRCLE_DIST * valveWidth;
        double point3yPos = point1yPos;
        double point2xPos = getPosition().getX() * totalWidth;
        double point2yPos = getPosition().getY() * totalHeight + ViewConstants.VALVE_CIRCLE_DIST * valveWidth;
        double point4xPos = point2xPos;
        //Width, not height, as we only want to increase by the circle diameter
        double point4yPos = point2yPos + valveWidth - 2 * ViewConstants.VALVE_CIRCLE_DIST * valveWidth;

        double centerX = getPosition().getX() * totalWidth;
        double centerY = (getPosition().getY() + radius) * totalHeight;

        context.setStroke(Color.BLACK);
        context.setLineWidth(ViewConstants.VALVE_LINE_WIDTH);
        context.setFill(Color.WHITE);

        context.fillRect(valveTopLeftX, valveTopLeftY, valveWidth, valveHeight);
        context.strokeRect(valveTopLeftX, valveTopLeftY, valveWidth, valveHeight);

        context.strokeLine(rotateX(point1xPos, centerX, point1yPos, centerY, getDegrees()),
                rotateY(point1xPos, centerX, point1yPos, centerY, getDegrees()),
                rotateX(point3xPos, centerX, point3yPos, centerY, getDegrees()),
                rotateY(point3xPos, centerX, point3yPos, centerY, getDegrees()));
        context.strokeLine(rotateX(point2xPos, centerX, point2yPos, centerY, getDegrees()),
                rotateY(point2xPos, centerX, point2yPos, centerY, getDegrees()),
                rotateX(point4xPos, centerX, point4yPos, centerY, getDegrees()),
                rotateY(point4xPos, centerX, point4yPos, centerY, getDegrees()));
        context.strokeOval(point1xPos, point2yPos,
                radius * 2 * totalHeight - 2 * ViewConstants.VALVE_CIRCLE_DIST * valveWidth,
                radius * 2 * totalHeight - 2 * ViewConstants.VALVE_CIRCLE_DIST * valveWidth);

        context.setFont(Font.font("Arial", (relValveHeight - relValveWidth) * totalHeight * 0.95));
        context.setFill(Color.BLUE);
        //Offset of 1 to avoid writing over the valve outline
        context.fillText(String.valueOf(pipe.getValveThreshold()), valveTopLeftX + 1,
                valveTopLeftY + valveHeight - 2);
    }
}
