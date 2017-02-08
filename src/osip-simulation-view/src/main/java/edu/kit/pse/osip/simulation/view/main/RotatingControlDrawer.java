package edu.kit.pse.osip.simulation.view.main;

/**
 * The parent for all rotating gui elements. It needs a position and a rotational speed. It provides private methods for rotation of the shapes making up the visualization, depending on the given speed.
 */
public abstract class RotatingControlDrawer extends edu.kit.pse.osip.simulation.view.main.ObjectDrawer {

    private Point2D position;
    private int speed;

    /**
     * Sets the position by using super(pos) and the rotation speed.
     * @param pos The upper left corner
     * @param speed The speed in rpm
     */
    public RotatingControlDrawer (Point2D pos, int speed) {
        super(pos);
        this.position = pos;
        this.speed = speed;
    }
}
