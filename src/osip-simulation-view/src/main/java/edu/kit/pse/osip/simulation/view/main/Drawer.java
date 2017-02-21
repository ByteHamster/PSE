package edu.kit.pse.osip.simulation.view.main;

import javafx.scene.canvas.GraphicsContext;

/**
 * The interface from which all other GUI elements originate. It specifies the draw() method with
 * which the element draws itself onto the screen according to its implementation.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public interface Drawer {
    /**
     * The Drawer draws itself onto the GraphicsContext at its position.
     * @param context The context that the object draws itself onto
     * @param time The current time in nanoseconds as supplied by ApplicationTimer
     */
    void draw(GraphicsContext context, double time);
}
