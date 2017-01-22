package edu.kit.pse.osip.simulation.view.main;

import javafx.scene.canvas.GraphicsContext;

/**
 * The interface from which all other GUI elements originate. It specifies the draw() method with which the element draws itself onto the screen according to its implementation.
 */
public abstract interface Drawer {
    /**
     * The Drawer draws itself onto the GraphicsContext at its position.
     * @param context The context that the object draws itself onto
     */
    public void draw (GraphicsContext context);
}
