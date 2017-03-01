package edu.kit.pse.osip.simulation.view.main;

/**
 * This class represents a simple Point in 2D space. It is a simple wrapper for the
 * data and does not support arithmetic operations on points.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public class Point2D {

    private double x;
    private double y;

    /**
     * Creates a new point on a 2D surface
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the value of x.
     * @return The value of x.
     */
    public final double getX() {
        return x;
    }

    /**
     * Gets the value of y.
     * @return The value of y.
     */
    public final double getY() {
        return y;
    }

}
