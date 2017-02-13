package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.MixTank;
import javafx.scene.canvas.GraphicsContext;

/**
 * The class visualizes a tank that is connected to several inputs. Due to this the fill level as
 * well as the color of the contained liquid might change with time.
 */
public class MixTankDrawer extends edu.kit.pse.osip.simulation.view.main.AbstractTankDrawer {

    private FillSensorDrawer topSensor;
    private FillSensorDrawer botSensor;
    private TemperatureSensorDrawer tempSensor;
    private MotorDrawer motor;
    /**
     * Creates a new MixTankDrawer
     * @param pos The upper left corner of the tank
     * @param tank The tank to get the attributes from
     * @param rows The number of rows in which the Tanks are ordered.
     * @param cols The number of columns in which the Tanks are ordered
     */
    public MixTankDrawer(Point2D pos, MixTank tank, int rows, int cols) {
        super(pos, tank, rows, cols);

        double inBoxHorPadding = (1 - ViewConstants.INBOX_PERCENT) / 2 / cols;
        double relInBoxWidth = ViewConstants.INBOX_PERCENT / cols;
        double inBoxVertPadding = (1 - ViewConstants.INBOX_PERCENT) / 2 / rows;
        double relInBoxHeight = ViewConstants.INBOX_PERCENT / rows;

        double topXPos = getPosition().getX() + inBoxHorPadding + relInBoxWidth;
        double topYPos = getPosition().getY() + inBoxVertPadding + 0.1 * relInBoxHeight;
        Point2D topPos = new Point2D(topXPos, topYPos);
        topSensor = new FillSensorDrawer(topPos, rows, cols);

        double botYPos = getPosition().getY() + inBoxVertPadding + 0.92 * relInBoxHeight;
        Point2D botPos = new Point2D(topXPos, botYPos);
        botSensor = new FillSensorDrawer(botPos, rows, cols);

        double tempX = getPosition().getX() + inBoxHorPadding;
        double tempY = getPosition().getY() + inBoxVertPadding + relInBoxHeight;
        Point2D tempPos = new Point2D(tempX, tempY);
        tempSensor = new TemperatureSensorDrawer(tempPos, tank, rows, cols);

        double motorX = getPosition().getX() + 0.5 / cols;
        double motorY = getPosition().getY() + inBoxVertPadding + relInBoxHeight - 0.1 / cols;
        Point2D motorPos = new Point2D(motorX, motorY);
        motor = new MotorDrawer(motorPos, tank.getMotor(), cols);
    }
    /**
     * Add Temperature- and Fillsensors as well as a motor visualization to the tank.
     * @param context The GraphicsContext on which the sensors are drawn.
     */
    @Override
    public final void drawSensors(GraphicsContext context, long time) {
        topSensor.draw(context, time);
        botSensor.draw(context, time);
        tempSensor.draw(context, time);
        motor.draw(context, time);
    }
}
