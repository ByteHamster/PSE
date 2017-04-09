package edu.kit.pse.osip.core.model.simulation;

import static org.junit.Assert.assertEquals;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if the mix tank of the simulation works.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class MixTankSimulationTest {
    /**
     * Pipe for testing.
     */
    private Pipe pipe;

    /**
     * Initializes the pipes.
     */
    @Before
    public void init() {
        pipe = new Pipe(200f, 30, (byte) 100);
    }

    /**
     * Tests putting in some liquid.
     */
    @Test
    public void testPutIn() {
        Liquid content = new Liquid(100, 350, new Color(0x003000));
        MixTankSimulation tank = new MixTankSimulation(1000, content, pipe);

        LinkedList<Liquid> input = new LinkedList<>();
        input.add(new Liquid(100, 375, new Color(0x000000)));
        input.add(new Liquid(100, 400, new Color(0x300000)));

        tank.putIn(input);
        assertEquals(new Liquid(300, 375, new Color(0x101000)), tank.getLiquid());
    }

    /**
     * Tests taking out some liquid.
     */
    @Test
    public void testTakeOut() {
        Liquid content = new Liquid(100, 350, new Color(0x421337));
        MixTankSimulation tank = new MixTankSimulation(1000, content, pipe);

        Liquid out = tank.takeOut(33);
        assertEquals(new Liquid(33, 350, new Color(0x421337)), out);
        out = tank.takeOut(500);
        assertEquals(new Liquid(67, 350, new Color(0x421337)), out);
    }
}
