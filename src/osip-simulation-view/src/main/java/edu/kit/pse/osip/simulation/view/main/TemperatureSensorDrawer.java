package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class visualizes a temperature sensor.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class TemperatureSensorDrawer extends ObjectDrawer {
    /**
     * Number of rows in which the tanks are ordered.
     */
    private int rows;
    /**
     * Number of columns in which the tanks are ordered.
     */
    private int cols;
    /**
     * The tank the temperture sensor is assigned to.
     */
    private AbstractTank tank;

    /**
     * Generates a new drawer for temperature sensors.
     * 
     * @param pos The center of the drawer.
     * @param tank The tank to get the attributes from.
     * @param rows The number of rows in which the Tanks are ordered.
     * @param cols The number of columns in which the Tanks are ordered.
     */
    public TemperatureSensorDrawer(Point2D pos, AbstractTank tank, int rows, int cols) {
        super(pos);
        this.tank = tank;
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public final void draw(GraphicsContext context, double timeDiff) {
        Canvas canvas = context.getCanvas();

        double temp = (tank.getLiquid().getTemperature() - SimulationConstants.MIN_TEMPERATURE)
                / (SimulationConstants.MAX_TEMPERATURE - SimulationConstants.MIN_TEMPERATURE);

        double totalHeight = canvas.getHeight();
        double totalWidth = canvas.getWidth();

        context.setFill(Color.WHITE);
        context.setStroke(Color.BLACK);
        context.setLineWidth(2);

        double padding = ((ViewConstants.TEMP_WIDTH / 2) / cols) * totalWidth;
        double outerTopLeftX = (getPosition().getX() - ViewConstants.TEMP_WIDTH / cols) * totalWidth - padding;
        double outerTopLeftY = (getPosition().getY() - ViewConstants.TEMP_HEIGHT / rows) * totalHeight;
        double outerWidth = (ViewConstants.TEMP_WIDTH / cols) * totalWidth;
        double outerHeight = (ViewConstants.TEMP_HEIGHT / rows) * totalHeight;

        double innerTopLeftX = (getPosition().getX() - (ViewConstants.TEMP_WIDTH - 0.025) / cols) * totalWidth
                - padding;
        double innerTopLeftY = (getPosition().getY() - (ViewConstants.TEMP_HEIGHT - 0.015) / rows) * totalHeight;
        double innerWidth = ((ViewConstants.TEMP_WIDTH - 0.05) / cols) * totalWidth;
        double innerHeight = ((ViewConstants.TEMP_HEIGHT - 0.03) / rows) * totalHeight;
        double circleWidth = ((ViewConstants.TEMP_WIDTH - 0.03) / cols) * totalWidth;
        double circleY = innerTopLeftY + innerHeight - circleWidth;
        double circleX = (getPosition().getX() - (ViewConstants.TEMP_WIDTH - 0.015) / cols) * totalWidth - padding;

        context.fillRect(outerTopLeftX, outerTopLeftY, outerWidth, outerHeight);
        context.strokeRect(outerTopLeftX, outerTopLeftY, outerWidth, outerHeight);

        context.strokeLine(outerTopLeftX + outerWidth, innerTopLeftY + 0.5 * outerHeight,
                outerTopLeftX + outerWidth + 2 * padding, innerTopLeftY + 0.5 * outerHeight);

        context.setFill(Color.LIGHTGRAY);
        context.setLineWidth(0);
        context.fillRect(innerTopLeftX, innerTopLeftY, innerWidth, innerHeight);

        double tempHeight = (((ViewConstants.TEMP_HEIGHT - 0.03) / rows) * totalHeight - circleWidth) * temp;
        double tempTopLeftX = (getPosition().getX() - (ViewConstants.TEMP_WIDTH - 0.025) / cols) * totalWidth - padding;
        double tempTopLeftY = innerTopLeftY + innerHeight - tempHeight - circleWidth;
        double tempWidth = ((ViewConstants.TEMP_WIDTH - 0.05) / cols) * totalWidth;

        context.setFill(Color.RED);
        context.fillOval(circleX, circleY, circleWidth, circleWidth);
        context.fillRect(tempTopLeftX, tempTopLeftY + tempHeight, tempWidth, circleWidth / 2);
        context.fillRect(tempTopLeftX, tempTopLeftY, tempWidth, tempHeight);
    }
}
