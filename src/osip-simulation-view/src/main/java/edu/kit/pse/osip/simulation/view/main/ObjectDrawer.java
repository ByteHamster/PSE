package edu.kit.pse.osip.simulation.view.main;

import javafx.scene.canvas.GraphicsContext;

/**
 * The parent class for all non-moving objects of the simulation. The only data it needs is a position where to draw itself.
 */
public abstract class ObjectDrawer implements edu.kit.pse.osip.simulation.view.main.Drawer {
    public Point2D position;
    /**
     * Initiates the ObjectDrawer, setting its position to pos.
     * @param pos 
     */
    public ObjectDrawer (Point2D pos) {
        this.position = pos;
    }

    /**
     * Sets the position of this element
     * @param position The upper left corner
     */
    public final void setPosition (Point2D position) {
        this.position = position;
    }

    /**
     * Returns the position of this element
     * 
     * @return The position
     */
    public final Point2D getPosition () {
        return position;
    }

    /**
     * The Drawer draws itself onto the GraphicsContext at its position.
     * @param context The context that the object draws itself onto
     */
    public abstract void draw (GraphicsContext context);
}
