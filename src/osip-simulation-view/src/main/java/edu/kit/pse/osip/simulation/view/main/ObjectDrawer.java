package edu.kit.pse.osip.simulation.view.main;

/**
 * The parent class for all non-moving objects of the simulation. The only data it needs
 * is a position where to draw itself.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public abstract class ObjectDrawer implements Drawer {

    private Point2D position;

    /**
     * Initiates the ObjectDrawer, setting its position to pos.
     * @param pos Position of the element
     */
    public ObjectDrawer(Point2D pos) {
        this.position = pos;
    }

    /**
     * Returns the position of this element
     * 
     * @return The position
     */
    public final Point2D getPosition() {
        return position;
    }
}
