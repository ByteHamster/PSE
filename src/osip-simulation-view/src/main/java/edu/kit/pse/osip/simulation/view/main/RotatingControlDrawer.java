package edu.kit.pse.osip.simulation.view.main;

/**
 * The parent for all rotating gui elements. It needs a position and a rotational speed.
 * It provides private methods for rotation of the shapes making up the visualization, depending
 * on the given speed.
 */
public abstract class RotatingControlDrawer extends ObjectDrawer {

    private int speed;

    /**
     * Sets the position by using super(pos) and the rotation speed.
     * @param pos The upper left corner
     * @param speed The speed in rpm
     */
    public RotatingControlDrawer(Point2D pos, int speed) {
        super(pos);
        this.speed = speed;
    }

    /**
     * Get the rotation speed of the RotatingControlDrawer
     * @return The current value of speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Set the rotation speed of the RotatingControlDrawer
     * @param speed The new value of speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
