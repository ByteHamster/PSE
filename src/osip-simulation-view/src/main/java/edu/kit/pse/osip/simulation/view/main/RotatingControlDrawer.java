package edu.kit.pse.osip.simulation.view.main;

/**
 * The parent for all rotating gui elements. It needs a position and a rotational speed.
 * It provides private methods for rotation of the shapes making up the visualization, depending
 * on the given speed.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public abstract class RotatingControlDrawer extends ObjectDrawer {

    private int speed;
    private double degrees;

    /**
     * Sets the position by using super(pos) and the rotation speed.
     * @param pos The upper left corner
     * @param speed The speed in rpm
     */
    public RotatingControlDrawer(Point2D pos, int speed) {
        super(pos);
        this.speed = speed;
        degrees = 0;
    }

    /**
     * Gets the value of degrees
     * @return The value of degrees
     */
    public double getDegrees() {
        return degrees;
    }

    /**
     * Updates the total amount of degrees turned according to the time passed and norms it to
     * the interval [0, 360)
     * @param timeDiff The time passed since the last update
     * @param speedFactor Fraction of the speed that is actually to be displayed for aesthetic reasons
     */
    public void updateDegrees(double timeDiff, double speedFactor) {
        degrees = degrees + (timeDiff * (speed * speedFactor) * 360);
        degrees = degrees % 360;
    }

    /**
     * Set the rotation speed of the RotatingControlDrawer
     * @param speed The new value of speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * This rotates the x coordinate of the point described by (xPos,yPos) around the point
     * (centerX, centerY) by degree degrees.
     * @param xPos The x coordinate of the point to be rotated
     * @param yPos The y coordinate of the point to be rotated
     * @param centerX The x coordinate of the point around which is rotated
     * @param centerY The y coordinate of the point around which is rotated
     * @param degrees The number of degrees by which should be rotated. This parameter is expected to be in
     *                degrees, not radians.
     * @return The x coordinate of the point (xPos, yPos) rotated around (centerX, centerY) by degrees degrees
     */
    protected static double rotateX(double xPos, double centerX, double yPos, double centerY, double degrees) {
        degrees = degrees % 360;
        double angle = degrees * (Math.PI / 180);
        return Math.cos(angle) * (xPos - centerX) - Math.sin(angle) * (yPos - centerY) + centerX;
    }

    /**
     * This rotates the y coordinate of the point described by (xPos,yPos) around the point
     * (centerX, centerY) by degree degrees.
     * @param xPos The x coordinate of the point to be rotated
     * @param yPos The y coordinate of the point to be rotated
     * @param centerX The x coordinate of the point around which is rotated
     * @param centerY The y coordinate of the point around which is rotated
     * @param degrees The number of degrees by which should be rotated. This parameter is expected to be in
     *                degrees, not radians.
     * @return The y coordinate of the point (xPos, yPos) rotated around (centerX, centerY) by degrees degrees
     */
    protected static double rotateY(double xPos, double centerX, double yPos, double centerY, double degrees) {
        degrees = degrees % 360;
        double angle = degrees * (Math.PI / 180);
        return Math.sin(angle) * (xPos - centerX) + Math.cos(angle) * (yPos - centerY) + centerY;
    }

}
