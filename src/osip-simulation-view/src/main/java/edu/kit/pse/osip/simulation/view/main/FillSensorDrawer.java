package edu.kit.pse.osip.simulation.view.main;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class visualizes a fill level sensor.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class FillSensorDrawer extends ObjectDrawer {

    private int rows;
    private int cols;

    /**
     * Generates a new drawer for fill sensors
     * @param pos The center of the drawer
     * @param rows The number of rows in which the tanks are ordered
     * @param cols The number of columns in which the tanks are ordered
     */
    public FillSensorDrawer(Point2D pos, int rows, int cols) {
        super(pos);
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * The Drawer draws itself onto the GraphicsContext at its position.
     * @param context The context that the object draws itself onto
     * @param time
     */
    @Override
    public final void draw(GraphicsContext context, double time) {
        context.setFill(Color.BLACK);
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);

        Canvas canvas = context.getCanvas();
        double totalWidth = canvas.getWidth();
        double totalHeight = canvas.getHeight();

        double compWidth = totalWidth / cols;

        double rectXPos = (getPosition().getX() + 1.0 / 4.0 * ViewConstants.FILLSENSOR_WIDTH / cols) * totalWidth;
        double rectYPos = (getPosition().getY()  * totalHeight
                - 1.0 / 2.0 * ViewConstants.FILLSENSOR_HEIGHT * compWidth);
        double rectWidth = compWidth * ViewConstants.FILLSENSOR_WIDTH / 2.0;
        double rectHeight = compWidth * ViewConstants.FILLSENSOR_HEIGHT;

        double leftEndX = (getPosition().getX() - 1 / 4.0 * ViewConstants.FILLSENSOR_WIDTH / cols) * totalWidth;

        // Draw a rectangle and a line going into the tank
        context.fillRect(rectXPos, rectYPos, rectWidth, rectHeight);
        context.strokeLine(leftEndX, getPosition().getY() * totalHeight,
                rectXPos, getPosition().getY() * totalHeight);

    }
}
