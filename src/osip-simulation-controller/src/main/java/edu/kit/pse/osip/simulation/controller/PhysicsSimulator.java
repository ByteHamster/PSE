package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import edu.kit.pse.osip.core.model.simulation.TankSimulation;

/**
 * Simulator for a given plant.
 */
public class PhysicsSimulator {
    public ProductionSiteSimulation productionSite;
    /**
     * Initializes a new simulator for the given plant
     * @param productionSite The production site to simulate the values on
     */
    public PhysicsSimulator (ProductionSiteSimulation productionSite) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Executes one simulation step. Needs to be called regularly.
     */
    public final void tick () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Simulates a normal tank
     * @return The outflowing liquid used to fill the MixTank
     * @param tank The tank to put the liquid in (add to fill value)
     */
    private final Liquid simulateTank (TankSimulation tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the temperature of the liquid which gets into the upper tanks
     * @param tank The tank to set the temperature
     * @param temperature The temperature
     */
    public final void setInputTemperature (TankSelector tank, float temperature) {
        throw new RuntimeException("Not implemented!");
    }
}
