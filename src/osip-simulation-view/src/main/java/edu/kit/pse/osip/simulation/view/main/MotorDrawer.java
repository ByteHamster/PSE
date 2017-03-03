package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Motor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class visualizes the mixing motor in the MixTank.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class MotorDrawer extends RotatingControlDrawer {

    private Point2D position;
    private Motor motor;
    private double relRadius;

    /**
     * Generates a new drawer object for motors
     * @param pos The center of the drawer
     * @param motor The motor to draw
     * @param cols The number of colons in which the tanks are ordered
     */
    public MotorDrawer(Point2D pos, Motor motor, int cols) {
        super(pos, motor.getRPM());
        this.motor = motor;
        this.position = pos;

        relRadius = ViewConstants.MOTOR_RADIUS / cols;
    }

    /**
     * The Drawer draws itself onto the GraphicsContext at its position.
     * @param context The context that the object draws itself onto
     * @param time
     */
    @Override
    public final void draw(GraphicsContext context, double time) {
        setSpeed(motor.getRPM());

        updateDegrees(time, ViewConstants.MOTOR_SPEED_FACTOR);

        Canvas canvas = context.getCanvas();
        double totalWidth = canvas.getWidth();
        double totalHeight = canvas.getHeight();

        // Points 1 and 3 make up the first line inside the motor.
        double point1xPos = (position.getX() - relRadius) * totalWidth;
        double point1yPos = position.getY() * totalHeight - 2 * ViewConstants.MOTOR_LINE_WIDTH;
        double point3xPos = (position.getX() + relRadius) * totalWidth;
        double point3yPos = point1yPos;

        // Points 2 and 4 make up the second line inside the motor.
        double point2xPos = position.getX() * totalWidth;
        double point2yPos = (position.getY() - relRadius) * totalHeight
                - (totalWidth - totalHeight) * relRadius - 2 * ViewConstants.MOTOR_LINE_WIDTH;
        double point4xPos = point2xPos;
        double point4yPos = (position.getY() + relRadius) * totalHeight
                + (totalWidth - totalHeight) * relRadius - 2 * ViewConstants.MOTOR_LINE_WIDTH;

        // The absolute center values of the motor.
        double centerX = position.getX() * totalWidth;
        double centerY = position.getY() * totalHeight - 2 * ViewConstants.MOTOR_LINE_WIDTH;

        //First draw the motor in white, slightly thicker than the result is supposed to be.
        //This leads to a white outline and visibility in all cases.
        context.setStroke(Color.WHITE);
        context.setLineWidth(ViewConstants.MOTOR_LINE_WIDTH + 2);
        context.strokeLine(rotateX(point1xPos, centerX, point1yPos, centerY, getDegrees()),
                rotateY(point1xPos, centerX, point1yPos, centerY, getDegrees()),
                rotateX(point3xPos, centerX, point3yPos, centerY, getDegrees()),
                rotateY(point3xPos, centerX, point3yPos, centerY, getDegrees()));
        context.strokeLine(rotateX(point2xPos, centerX, point2yPos, centerY, getDegrees()),
                rotateY(point2xPos, centerX, point2yPos, centerY, getDegrees()),
                rotateX(point4xPos, centerX, point4yPos, centerY, getDegrees()),
                rotateY(point4xPos, centerX, point4yPos, centerY, getDegrees()));

        context.strokeOval(point1xPos, point2yPos, relRadius * 2 * totalWidth, relRadius * 2 * totalWidth);

        context.setStroke(Color.BLACK);
        context.setLineWidth(ViewConstants.MOTOR_LINE_WIDTH);

        // Draw the two rotated lines.
        context.strokeLine(rotateX(point1xPos, centerX, point1yPos, centerY, getDegrees()),
                rotateY(point1xPos, centerX, point1yPos, centerY, getDegrees()),
                rotateX(point3xPos, centerX, point3yPos, centerY, getDegrees()),
                rotateY(point3xPos, centerX, point3yPos, centerY, getDegrees()));
        context.strokeLine(rotateX(point2xPos, centerX, point2yPos, centerY, getDegrees()),
                rotateY(point2xPos, centerX, point2yPos, centerY, getDegrees()),
                rotateX(point4xPos, centerX, point4yPos, centerY, getDegrees()),
                rotateY(point4xPos, centerX, point4yPos, centerY, getDegrees()));

        // There would be not much sense in rotating a circle.
        context.strokeOval(point1xPos, point2yPos, relRadius * 2 * totalWidth, relRadius * 2 * totalWidth);

    }

}
