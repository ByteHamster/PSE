package edu.kit.pse.osip.core.model.simulation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
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
    private Pipe pipe1;
    private Pipe pipe2;

    /**
     * Initialize the pipes.
     */
    @Before
    public void init() {
        pipe1 = new Pipe(200f, 30, (byte) 100);
        pipe2 = new Pipe(200f, 30, (byte) 100);
    }

    /**
     * Test putting in some liquid
     */
    @Test
    public void testPutIn() {
        Liquid content = new Liquid(100, 350, new Color(0x004000));
        TankSimulation tank = new TankSimulation(1000,
                TankSelector.valuesWithoutMix()[0], content, pipe1, pipe2);

        tank.putIn(new Liquid(100,  310, new Color(0x800020)));
        assertEquals(new Liquid(200, 330, new Color(0x402010)), tank.getLiquid());
    }

    /**
     * Test taking out some liquid
     */
    @Test
    public void testTakeOut() {
        Liquid content = new Liquid(100, 350, new Color(0x421337));
        TankSimulation tank = new TankSimulation(1000,
                TankSelector.valuesWithoutMix()[0], content, pipe1, pipe2);

        Liquid out = tank.takeOut(33);
        assertEquals(new Liquid(33, 350, new Color(0x421337)), out);
        out = tank.takeOut(500);
        assertEquals(new Liquid(67, 350, new Color(0x421337)), out);
    }
}
