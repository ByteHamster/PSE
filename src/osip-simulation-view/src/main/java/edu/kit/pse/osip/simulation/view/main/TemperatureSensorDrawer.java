package edu.kit.pse.osip.simulation.view.main;

import javafx.scene.canvas.GraphicsContext;

/**
 * This class visualizes a temperature sensor.
 */
public class TemperatureSensorDrawer extends edu.kit.pse.osip.simulation.view.main.ObjectDrawer {
    /**
     * Generates a new drawer for temperature sensors		
     * @param pos The center of the drawer
     */
    public TemperatureSensorDrawer (Point2D pos) {
        super(pos);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * The Drawer draws itself onto the GraphicsContext at its position.
     * @param context The context that the object draws itself onto
     */
    public final void draw (GraphicsContext context) {
        throw new RuntimeException("Not implemented!");
    }
}
