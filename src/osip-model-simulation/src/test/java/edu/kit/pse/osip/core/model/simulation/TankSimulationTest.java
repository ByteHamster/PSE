package edu.kit.pse.osip.core.model.simulation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Tests if the tanks of the simulation work
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TankSimulationTest {
    /**
     * Test putting in some liquid
     */
    @Test
    public void testPutIn() {
        Liquid content = new Liquid(100, 50, new Color(0x004000));
        Pipe pipe = new Pipe(10, 10);
        TankSimulation tank = new TankSimulation(1000, TankSelector.valuesWithoutMix()[0], content, pipe, pipe);

        tank.putIn(new Liquid(100,  150, new Color(0x800020)));

        assertEquals(200, tank.getLiquid().getAmount(), 0.0001);
        assertEquals(100, tank.getLiquid().getTemperature(), 0.0001);
        assertEquals(0x402010, tank.getLiquid().getColor());
    }

    /**
     * Test taking out some liquid
     */
    @Test
    public void testTakeOut() {
        Liquid content = new Liquid(100, 50, new Color(0x421337));
        Pipe pipe = new Pipe(10, 10);
        TankSimulation tank = new TankSimulation(1000, TankSelector.valuesWithoutMix()[0], content, pipe, pipe);

        Liquid out = tank.takeOut(33);
        assertEquals(33, out.getAmount(), 0.0001);
        assertEquals(50, out.getTemperature(), 0.0001);
        assertEquals(0x421337, out.getColor());

        out = tank.takeOut(500);
        assertEquals(67, out.getAmount(), 0.0001);
        assertEquals(50, out.getTemperature(), 0.0001);
        assertEquals(0x421337, out.getColor());
    }
}
