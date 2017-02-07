package edu.kit.pse.osip.core.model.simulation;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;

/**
 * Tests if the mix tank of the simulation works
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class MixTankSimulationTest {
    /**
     * Test putting in some liquid
     */
    @Test
    public void testPutIn() {
        Liquid content = new Liquid(100, 50, new Color(0x003000));
        Pipe outPipe = new Pipe(10, 10);
        MixTankSimulation tank = new MixTankSimulation(1000, content, outPipe);

        LinkedList<Liquid> input = new LinkedList<>();
        input.add(new Liquid(100,  75, new Color(0x000000)));
        input.add(new Liquid(100, 100, new Color(0x300000)));

        tank.putIn(input);

        assertEquals(300, tank.getLiquid().getAmount(), 0.0001);
        assertEquals(75, tank.getLiquid().getTemperature(), 0.0001);
        assertEquals(0x101000, tank.getLiquid().getColor());
    }

    /**
     * Test taking out some liquid
     */
    @Test
    public void testTakeOut() {
        Liquid content = new Liquid(100, 50, new Color(0x421337));
        Pipe outPipe = new Pipe(10, 10);
        MixTankSimulation tank = new MixTankSimulation(1000, content, outPipe);

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
