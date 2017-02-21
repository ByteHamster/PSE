package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.simulation.MixTankSimulation;
import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import edu.kit.pse.osip.core.model.simulation.TankSimulation;
import java.util.LinkedList;

/**
 * Simulator for a given plant.
 * @author David Kahles
 * @version 1.0
 */
public class PhysicsSimulator {
    private ProductionSiteSimulation productionSite;

    /**
     * Initializes a new simulator for the given plant
     * @param productionSite The production site to simulate the values on
     */
    public PhysicsSimulator (ProductionSiteSimulation productionSite) {
        this.productionSite = productionSite;
    }

    /**
     * Executes one simulation step. Needs to be called regularly.
     */
    public final void tick () {
        MixTankSimulation mixTank = productionSite.getMixTank();
        LinkedList<Liquid> mixInput = new LinkedList<>();

        for(TankSelector selector: TankSelector.valuesWithoutMix()) {
            TankSimulation tank = productionSite.getUpperTank(selector);
            mixInput.add(simulateTank(tank));
        }

        mixTank.putIn(mixInput);
        mixTank.takeOut(mixTank.getOutPipe().getMaxInput());
    }

    /**
     * Simulates a normal tank
     * @return The outflowing liquid used to fill the MixTank
     * @param tank The tank to put the liquid in (add to fill value)
     */
    private final Liquid simulateTank (TankSimulation tank) {
        Pipe inPipe = tank.getInPipe();
        Pipe outPipe = tank.getOutPipe();
        TankSelector selector = tank.getTankSelector();

        Liquid inLiquid = new Liquid(inPipe.getMaxInput(), productionSite.getInputTemperature(selector),
                selector.getInitialColor());
        tank.putIn(inPipe.putIn(inLiquid));
        return tank.takeOut(outPipe.getMaxInput());
    }

    /**
     * Set the temperature of the liquid which gets into the upper tanks
     * @param tank The tank to set the temperature
     * @param temperature The temperature
     */
    public final void setInputTemperature (TankSelector tank, float temperature) {
        productionSite.setInputTemperature(tank, temperature);
    }
}
