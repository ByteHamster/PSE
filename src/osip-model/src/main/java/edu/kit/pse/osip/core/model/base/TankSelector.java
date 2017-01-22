package edu.kit.pse.osip.core.model.base;

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
     * Get the initial color of a tank.
     * @return The initial color of a tank.
     */
    public Color getInitialColor () {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Return an array of all tanks, except for the mixtank.
     * @return Array of all tanks, except for the mixtank.
     */
    public TankSelector[] valuesWithoutMix () {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Get the number of upper (non-mix) tanks in the production site. So the total number of tanks is
     * getUpperTankCount()+1 because of the mixtank.
     * @return the number of upper tanks.
     */
    public int getUpperTankCount () {
        throw new RuntimeException("Not implemented!");
    }
};
