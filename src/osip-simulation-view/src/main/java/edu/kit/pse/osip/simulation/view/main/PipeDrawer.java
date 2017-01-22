package edu.kit.pse.osip.simulation.view.main;

import javafx.scene.canvas.GraphicsContext;

/**
 * This class visualizes a pipe connecting two tanks. It is specified by the waypoints during which the pipe leads. It needs at least two waypoints to exist.
 */
public class PipeDrawer implements edu.kit.pse.osip.simulation.view.main.Drawer {
    public Point2D position;
    /**
     * Create a new pipe along the waypoints 1 to n.
     * @param waypoints The points that the pipe goes along
     */
    public PipeDrawer (Point2D[] waypoints) {
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
