package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Tank;
import javafx.scene.canvas.GraphicsContext;

/**
 * The class visualizes the tanks during which liquid first enters the simulation.
 * As they have only one input only the fill level is variable, the color remains fixed.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class TankDrawer extends AbstractTankDrawer {
    /**
     * The upper fill sensor.
     */
    private FillSensorDrawer topSensor;
    /**
     * The lower fill sensor.
     */
    private FillSensorDrawer botSensor;
    /**
     * The temperature sensor.
     */
    private TemperatureSensorDrawer tempSensor;

    /**
     * Creates a new tank drawer.
     * 
     * @param pos The upper left corner of the tank.
     * @param tank The tank to get the attributes from.
     * @param rows The number of rows in which the Tanks are ordered.
     * @param cols The number of columns in which the Tanks are ordered.
     */
    public TankDrawer(Point2D pos, Tank tank, int rows, int cols) {
        super(pos, tank, rows, cols);

        double inBoxHorPadding = (1 - ViewConstants.INBOX_WIDTH) / 2 / cols;
        double relInBoxWidth = ViewConstants.INBOX_WIDTH / cols;
        double inBoxVertPadding = (1 - ViewConstants.INBOX_HEIGHT) / 2 / rows;
        double relInBoxHeight = ViewConstants.INBOX_HEIGHT / rows;

        double topXPos = getPosition().getX() + inBoxHorPadding + relInBoxWidth;
        double topYPos = getPosition().getY() + inBoxVertPadding + 0.1 * relInBoxHeight;
        Point2D topPos = new Point2D(topXPos, topYPos);
        topSensor = new FillSensorDrawer(topPos, cols);

        double botYPos = getPosition().getY() + inBoxVertPadding + 0.92 * relInBoxHeight;
        Point2D botPos = new Point2D(topXPos, botYPos);
        botSensor = new FillSensorDrawer(botPos, cols);

        double tempX = getPosition().getX() + inBoxHorPadding;
        double tempY = getPosition().getY() + inBoxVertPadding + relInBoxHeight;
        Point2D tempPos = new Point2D(tempX, tempY);
        tempSensor = new TemperatureSensorDrawer(tempPos, tank, rows, cols);
    }

    /**
     * Adds temperature and fill sensor visualizations to the tank.
     */
    @Override
    public final void drawSensors(GraphicsContext context, double timeDiff) {
        topSensor.draw(context, timeDiff);
        botSensor.draw(context, timeDiff);
        tempSensor.draw(context, timeDiff);
    }
}
