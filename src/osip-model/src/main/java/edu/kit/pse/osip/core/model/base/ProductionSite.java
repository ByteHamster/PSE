package edu.kit.pse.osip.core.model.base;

/**
 * Group all tanks in the production site together. This is the entrance point of the model, because you can get every tank and, through the tanks, every pipe in the production site.
 */
public class ProductionSite {
    public MixTank mixTank;
    public Tank tank;
    /**
     * Get one of the upper tanks.
     * @return the requested tank.
     * @param tank Specifies the tank.
     */
    public Tank getUpperTank (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the mixtank.
     * @return the mixtank of the production site.
     */
    public MixTank getMixTank () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Reset the whole production site to its default values: Every tank with 50% infill, valves putting the site to a stable state.
     */
    public final void reset () {
        throw new RuntimeException("Not implemented!");
    }
}
