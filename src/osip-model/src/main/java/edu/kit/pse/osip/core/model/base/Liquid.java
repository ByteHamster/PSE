package edu.kit.pse.osip.core.model.base;

/**
 * A readonly data type for liquids. This is the liquid which flows through the tanks and pipes. It has attributes for the color, the temperature and the amount.
 */
public class Liquid {
    /**
     * Construct a new Liquid object.
     * @param amount The amout in cm³.
     * @param temperature The temperature in °K.
     * @param color The color of the liquid.
     */
    public Liquid (float amount, float temperature, Color color) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the color of the Liquid.
     * @return the color.
     */
    public final Color getColor () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the temperature of the liquid.
     * @return the temperature.
     */
    public final float getTemperature () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the amount of the liquid.
     * @return the amount.
     */
    public final float getAmount () {
        throw new RuntimeException("Not implemented!");
    }
}
