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
     * This array defines the initial colors in the same order as the tanks are defined above.
     */
    private static Color[] initialColors = {
        new Color(1, 1, 1),
        new Color(0, 1, 0),
        new Color(1, 0, 0),
        new Color(0, 0, 1),
    };

    /**
     * Get the initial color of a tank.
     * @return The initial color of a tank.
     */
    public Color getInitialColor() {
        return initialColors[this.ordinal()].clone();
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
};
