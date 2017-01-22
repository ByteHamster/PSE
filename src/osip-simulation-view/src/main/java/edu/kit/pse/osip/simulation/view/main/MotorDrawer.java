package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Motor;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class visualizes the mixing motor in the MixTank.
 */
public class MotorDrawer extends edu.kit.pse.osip.simulation.view.main.RotatingControlDrawer {
    /**
     * Generates a new drawer object for motors
     * @param pos The center of the drawer
     * @param motor The motor to draw
     */
    public MotorDrawer (Point2D pos, Motor motor) {
        super(pos, 0);
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
