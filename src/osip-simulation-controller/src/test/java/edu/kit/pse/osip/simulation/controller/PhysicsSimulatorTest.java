package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the PhysicsSimulator class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class PhysicsSimulatorTest {
    private ProductionSiteSimulation site;
    private PhysicsSimulator sim;

    /**
     * Initialize site and sim.
     */
    @Before
    public void init() {
        site = new ProductionSiteSimulation();
        sim = new PhysicsSimulator(site);
    }

    /**
     * Check whether tick() works with the default ProductionSite and produces a stable state.
     */
    @Test
    public void testTickDefault() {
        float[] liquidBefore = new float[TankSelector.values().length];
        int i = 0;
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            liquidBefore[i++] = site.getUpperTank(selector).getLiquid().getAmount();
        }
        liquidBefore[i] = site.getMixTank().getLiquid().getAmount();

        float temp = SimulationConstants.MIN_TEMPERATURE;
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            site.setInputTemperature(selector, temp);
            Liquid old = site.getUpperTank(selector).getLiquid();
            site.getUpperTank(selector).setLiquid(new Liquid(old.getAmount(), temp, old.getColor()));
        }
        Liquid old = site.getMixTank().getLiquid();
        site.getMixTank().setLiquid(new Liquid(old.getAmount(), temp, old.getColor()));

        sim.tick();
        sim.tick();
        sim.tick();
        sim.tick();
        sim.tick();

        i = 0;
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            float pipeInput = site.getUpperTank(selector).getOutPipe().getMaxInput();
            System.out.println(selector.toString());
            assertEquals(liquidBefore[i++] - SimulationConstants.PIPE_LENGTH * pipeInput,
                site.getUpperTank(selector).getLiquid().getAmount(), 0.0001);
            assertEquals(site.getInputTemperature(selector), temp, 0.0001);
        }

        float pipeInput = site.getMixTank().getOutPipe().getMaxInput();
        assertEquals(liquidBefore[i] - SimulationConstants.PIPE_LENGTH * pipeInput,
            site.getMixTank().getLiquid().getAmount(), 0.0001);
        assertEquals(temp, site.getMixTank().getLiquid().getTemperature(), 0.0001);
    }

    /**
     * Check whether setting the input temperatures works correctly.
     */
    @Test
    public void testSetInputTemperature() {
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            sim.setInputTemperature(selector, SimulationConstants.MIN_TEMPERATURE);
            assertEquals(SimulationConstants.MIN_TEMPERATURE, site.getInputTemperature(selector), 0.001);
        }
    }
}
