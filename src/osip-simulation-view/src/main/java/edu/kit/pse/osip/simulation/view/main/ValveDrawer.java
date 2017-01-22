package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.Pipe;
import javafx.scene.canvas.GraphicsContext;

/**
 * This class visualizes the valve that is part of every pipe.
 */
public class ValveDrawer extends edu.kit.pse.osip.simulation.view.main.RotatingControlDrawer {
    public PipeDrawer pipe;
    /**
     * Generates a new drawer object for valves
     * @param pos The center of the drawer
     * @param pipe The pipe to which the valve is attached
     */
    public ValveDrawer (Point2D pos, Pipe pipe) {
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
