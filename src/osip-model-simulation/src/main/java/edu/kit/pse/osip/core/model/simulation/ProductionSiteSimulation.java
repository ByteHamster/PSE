package edu.kit.pse.osip.core.model.simulation;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * This is a ProductionSite which returns the simulated tanks of the package "simulation"
 * instead of the non-simulated tanks of package "base".
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ProductionSiteSimulation extends edu.kit.pse.osip.core.model.base.ProductionSite {
    /**
     * Get a tank which is extended to be simulatable.
     * @return the requested tank that is simulatable.
     * @param tank Specifies the tank.
     */
    public TankSimulation getUpperTank(TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the mixtank which is extended to be simulatable.
     * @return the requested mixtank that is simulatable.
     */
    public MixTankSimulation getMixTank() {
        throw new RuntimeException("Not implemented!");
    }
}
