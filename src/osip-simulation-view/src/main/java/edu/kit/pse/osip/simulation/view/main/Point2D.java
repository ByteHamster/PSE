package edu.kit.pse.osip.simulation.view.main;

/**
 * This class represents a simple Point in 2D space. It is a simple wrapper for the data and does not support arithmetic operations on points.
 */
public class Point2D {

    private double x;
    private double y;

    /**
     * Creates a new point on a 2D surface
     * @param x 
     * @param y 
     */
    public Point2D (double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the value of x.
     * @return The value of x.
     */
    public final double getX () {
        return x;
    }

    /**
     * Gets the value of y.
     * @return The value of y.
     */
    public final double getY () {
        return y;
    }

    /**
     * Sets the value of x.
     * @param x The x coordinate
     */
    public final void setX (double x) {
        this.x = x;
    }

    /**
     * Sets the value of y.
     * @param y The y coordinate
     */
    public final void setY (double y) {
        this.y = y;
    }
}
