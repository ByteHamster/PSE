package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Tank;
import javafx.scene.canvas.GraphicsContext;

/**
 * The class visualizes the tanks during which liquid first enters the simulation. As they have only one input only the fill level is variable, the color remains fixed.
 */
public class TankDrawer extends edu.kit.pse.osip.simulation.view.main.AbstractTankDrawer {

    /**
     * Creates a new tank drawer
     * @param pos The upper left corner of the tank
     * @param tank The tank to get the attributes from
     */
    public TankDrawer (Point2D pos, Tank tank) {
        super(pos, tank);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Add temperature- and fillsensor visualizations to the tank.
     * @param context 
     */
    public final void drawSensors (GraphicsContext context) {
        throw new RuntimeException("Not implemented!");
    }
}
