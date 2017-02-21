package edu.kit.pse.osip.core.model.base;

import java.util.Arrays;

/**
 * This enumeration defines all tanks including their default color value.
 */
public enum TankSelector {
    /**
     * the mixtank
     */
    MIX,
    /**
     * The yellow tank
     */
    YELLOW,
    /**
     * The cyan colored tank
     */
    CYAN,
    /**
     * The magenta colored tank
     */
    MAGENTA;

    /**
     * Key prefix to use when retrieving a tank name from the translator
     */
    public static final String TRANSLATOR_LABEL_PREFIX = "label.tank.";

    /**
     * This array defines the initial colors in the same order as the tanks are defined above.
     */
    private static Color[] initialColors = {
        new Color(1, 1, 1),
        new Color(0, 1, 0),
        new Color(1, 0, 0),
        new Color(0, 0, 1),
    };

    /**
     * This array defines the initial temperatures in the same order as the tanks are defined above.
     */
    private static float[] initialTemperatures = {
            300,
            300,
            300,
            300,
    };

    /**
     * Get the initial color of a tank.
     * @return The initial color of a tank.
     */
    public Color getInitialColor() {
        return initialColors[this.ordinal()].clone();
    }

    /**
     * Get the initial temperature of a tank.
     * @return The initial temperature of a tank.
     */
    public float getInitialTemperature() {
        return initialTemperatures[this.ordinal()];
    }
    
    /**
     * Return an array of all tanks, except for the mixtank.
     * @return Array of all tanks, except for the mixtank.
     */
    public static TankSelector[] valuesWithoutMix() {
        TankSelector[] values = values();
        assert (values[0] == MIX);
        return Arrays.copyOfRange(values, 1, values.length);
    }
    
    /**
     * Get the number of upper (non-mix) tanks in the production site. So the total number of tanks is
     * getUpperTankCount()+1 because of the mixtank.
     * @return the number of upper tanks.
     */
    public static int getUpperTankCount() {
        return values().length - 1;
    }
}
