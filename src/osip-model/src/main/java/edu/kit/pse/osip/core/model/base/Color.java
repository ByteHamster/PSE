package edu.kit.pse.osip.core.model.base;

/**
 * A color in the CMY (cyan, magenta, yellow) color space.
 */
public class Color {
    /**
     * Construct a new Color object.
     * @throws IllegalArgumentException if cyan, yellow or magenta is smaller than 0 or greather than 255.
     * @param cyan The percentage of the cyan color. It needs to be between 0 and 100.
     * @param yellow The percentage of the yellow color. It needs to be between 0 and 100.
     * @param magenta The percentage of the magenta color. It needs to be between 0 and 100.
     */
    public Color (short cyan, short yellow, short magenta) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the cyan percentage.
     * @throws IllegalArgumentException if cyan is smaller than 0 or greather than 255.
     * @param cyan The percentage of the cyan color. It needs to be between 0 and 100.
     */
    public final void setCyan (short cyan) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the magenta percentage.
     * @throws IllegalArgumentException if magenta is smaller than 0 or greather than 255.
     * @param magenta The percentage of the magenta color. It needs to be between 0 and 100.
     */
    public final void setMagenta (short magenta) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the yellow percentage.
     * @throws IllegalArgumentException if yellow is smaller than 0 or greather than 255.
     * @param yellow The percentage of the yellow color. It needs to be between 0 and 100.
     */
    public final void setYellow (short yellow) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the cyan percentage.
     * @return the percentage of the cyan color.
     */
    public final short getCyan () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the magenta percentage.
     * @return the percentage of the magenta color.
     */
    public final short getMagenta () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the yellow percentage.
     * @return the percentage of the yellow color.
     */
    public final short getYellow () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the r value when converting this color to RGB.
     * @return The r value.
     */
    public final short getR () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the g value when converting this color to RGB.
     * @return The g value.
     */
    public final short getG () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the b value when converting this color to RGB.
     * @return The b value.
     */
    public final short getB () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Mix two colors.
     * @throws IllegalArgumentException if ratio is <= 0.
     * @return the new color.
     * @param color1 The first color to mix.
     * @param color2 The second color to mix.
     * @param ratio The ratio of color1/color2, e.g. "1" means that both colors have the same intensity.
     */
    public final static Color mix (Color color1, Color color2, float ratio) {
        throw new RuntimeException("Not implemented!");
    }
}
