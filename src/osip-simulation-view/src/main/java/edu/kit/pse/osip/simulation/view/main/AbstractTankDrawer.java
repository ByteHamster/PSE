package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class visualizes a tank holding a colored liquid. It knows its position as well as the color of the content. The changing part of the visualization are the fill level of the tank and, possibly, the  color of the liquid.
 */
public abstract class AbstractTankDrawer extends edu.kit.pse.osip.simulation.view.main.ObjectDrawer {
    public AbstractTank tank;
    public TemperatureSensorDrawer temperatureSensor;
    public FillSensorDrawer fillSensor;
    /**
     * Sets the position by using super(pos) and sets the tank
     * @param pos The upper left corner of the tank
     * @param tank The tank to get the attributes from
     */
    public AbstractTankDrawer (Point2D pos, AbstractTank tank) {
        super(pos);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the width of this tank
     * @param width The width of the tank
     */
    public final void setWidth (double width) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets the width of this tank
     * 
     * @return the width
     */
    public final double getWidth () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the height of this tank
     * @param height The height of the tank
     */
    public final void setHeight (double height) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets the height of this tank
     * 
     * @return the height
     */
    public final double getHeight () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Contains the main calls necessary to draw the tank. Uses the abstract method drawSensors() for detail.
     * @param context 
     */
    public final void draw (GraphicsContext context) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Used by draw(). Adds some detail to the tank depending on tank type.
     * @param context 
     */
    public abstract void drawSensors (GraphicsContext context);
}
