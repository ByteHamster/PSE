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

    private int cols;

    /**
     * Generates a new drawer for fill sensors
     * @param pos The center of the drawer
     * @param cols The number of columns in which the tanks are ordered
     */
    public FillSensorDrawer(Point2D pos, int cols) {
        super(pos);
        this.cols = cols;
    }

    @Override
    public final void draw(GraphicsContext context, double timeDiff) {
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
