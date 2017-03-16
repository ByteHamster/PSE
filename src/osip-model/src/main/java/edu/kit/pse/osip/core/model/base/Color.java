package edu.kit.pse.osip.core.model.base;

/**
 * A color in the CMY (cyan, magenta, yellow) color space.
 * @author David Kahles
 * @version 1.0
 */
public class Color {
    private double cyan;
    private double yellow;
    private double magenta;

    /**
     * Construct a new Color object.
     *
     * @param cyan    The portion of the cyan color. It needs to be between 0 and 1.
     * @param yellow  The portion of the yellow color. It needs to be between 0 and 1.
     * @param magenta The portion of the magenta color. It needs to be between 0 and 1.
     * @throws IllegalArgumentException if cyan, yellow or magenta is smaller than 0 or greater than 1.
     */
    public Color(double cyan, double magenta, double yellow) {
        if (cyan < 0 || cyan > 1 || yellow < 0 || yellow > 1 || magenta < 0 || magenta > 1) {
            throw new IllegalArgumentException("Color pigment contents need to be in range from 0 to 1");
        }

        this.cyan = cyan;
        this.yellow = yellow;
        this.magenta = magenta;
    }

    /**
     * Construct a new Color object.
     * @param rgb an integer containing the red component in bits 16-23, the green component in bits 8-15
     *            and the blue component in bits 0-7. Bits 24-32 need to be zero.
     * @throws IllegalArgumentException if the fourth byte is not zero.

     */
    public Color(int rgb) {
        if ((rgb & 0xFF000000) != 0) {
            throw new IllegalArgumentException("The fourth byte of the rgb argument needs to be zero");
        }

        cyan =  1 - (rgb >> 16) / 255f;
        magenta = 1 - ((rgb >> 8) & 0xFF) / 255f;
        yellow = 1 - (rgb & 0xFF) / 255f;
    }

    /**
     * Get the cyan percentage.
     *
     * @return the percentage of the cyan color.
     */
    public final double getCyan() {
        return cyan;
    }

    /**
     * Get the magenta percentage.
     *
     * @return the percentage of the magenta color.
     */
    public final double getMagenta() {
        return magenta;
    }

    /**
     * Get the yellow percentage.
     *
     * @return the percentage of the yellow color.
     */
    public final double getYellow() {
        return yellow;
    }

    /**
     * Returns the r value when converting this color to RGB.
     *
     * @return The r value.
     */
    public final double getR() {
        return (1 - cyan);
    }

    /**
     * Returns the g value when converting this color to RGB.
     *
     * @return The g value.
     */
    public final double getG() {
        return (1 - magenta);
    }

    /**
     * Returns the b value when converting this color to RGB.
     *
     * @return The b value.
     */
    public final double getB() {
        return (1 - yellow);
    }

    /**
     * Get the rgb color in one integer.
     * @return an integer containing the red component in bits 16-23, the green component in bits 8-15
     *            and the blue component in bits 0-7. Bits 24-32 are zero.
     */
    public int getRGB() {
        int r = (int) Math.round(getB() * 255);
        r |= (int) Math.round(getR() * 255) << 16;
        r |= (int) Math.round(getG() * 255) << 8;
        return r;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Color)) {
            return false;
        }
        Color otherColor = (Color) other;
        return (Math.abs(otherColor.cyan - cyan) < 0.000001 && Math.abs(otherColor.magenta - magenta) < 0.000001
            && Math.abs(otherColor.yellow - yellow) < 0.000001);
    }

    @Override
    public int hashCode() {
        int result = 17; /* prime number! */
        result = 31 * result + (int) Math.round(cyan * 10000);
        result = 31 * result + (int) Math.round(magenta * 10000);
        result = 31 * result + (int) Math.round(yellow * 10000);
        return result;
    }
}
