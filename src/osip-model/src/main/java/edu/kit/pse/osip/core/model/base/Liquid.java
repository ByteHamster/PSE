package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;

/**
 * A readonly data type for liquids. This is the liquid which flows through the tanks and pipes. It has attributes
 * for the color, the temperature and the amount.
 * @author David Kahles
 * @version 1.0
 */
public class Liquid {
    private float amount;
    private float temperature;
    private Color color;

    /**
     * Construct a new Liquid object.
     *
     * @throws IllegalArgumentException if the amount is smaller than zero, the temperature is smaller than
     * SimulationConstants.MIN_TEMPERATURE or greater than SimulationConstants.MAX_TEMPERATURE or the color is null.
     * @param amount      The amount in cm³.
     * @param temperature The temperature in °K.
     * @param color       The color of the liquid.
     */
    public Liquid(float amount, float temperature, Color color) {
        if (amount < 0) {
            throw new IllegalArgumentException("The amount of a liquid needs to be >= 0");
        }
        if (temperature + 0.0001 < SimulationConstants.MIN_TEMPERATURE) {
            throw new IllegalArgumentException("The temperature of a liquid in Kelvin needs to be >= "
                + SimulationConstants.MIN_TEMPERATURE);
        }
        if (temperature - 0.0001 > SimulationConstants.MAX_TEMPERATURE) {
            throw new IllegalArgumentException("The temperature of a liquid in Kelvin needs to be <= "
                + SimulationConstants.MAX_TEMPERATURE);
        }
        if (color == null) {
            throw new IllegalArgumentException("The color of a liquid can't be null");
        }

        this.amount = amount;
        this.temperature = temperature;
        this.color = color;
    }

    /**
     * Get the color of the Liquid.
     *
     * @return the color.
     */
    public final Color getColor() {
        return color;
    }

    /**
     * Get the temperature of the liquid.
     *
     * @return the temperature.
     */
    public final float getTemperature() {
        return temperature;
    }

    /**
     * Get the amount of the liquid.
     *
     * @return the amount.
     */
    public final float getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Liquid)) {
            return false;
        }
        Liquid otherLiquid = (Liquid) other;
        return (Math.abs(otherLiquid.amount - amount) < 0.000001
            && Math.abs(otherLiquid.temperature - temperature) < 0.000001 && otherLiquid.color.equals(color));
    }

    @Override
    public int hashCode() {
        int result = 17; /* prime number! */
        result = 31 * result + (int) (amount * 100); /* *100 to get a different hashcode if the amount differs only
                                                        after the decimal point */
        result = 31 * result + (int) (temperature * 100);
        result = 31 * result + color.hashCode();
        return result;
    }
}
