package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the Pipe class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class PipeTest {
    Pipe pipe = null;

    @Before
    public void init() {
        pipe = new Pipe(5, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongCrosssection() {
        new Pipe(0 ,5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongLength() {
        new Pipe(5 ,0);
    }

    @Test
    public void testValve() {
        assertEquals(5 * SimulationConstants.SIMULATION_STEP, pipe.getMaxInput(), 0.0001);
        assertEquals(100, pipe.getValveThreshold());

        pipe.setValveThreshold((byte) 50);
        assertEquals(5 * SimulationConstants.SIMULATION_STEP * 0.5, pipe.getMaxInput(), 0.0001);
        assertEquals(50, pipe.getValveThreshold());

        pipe.setValveThreshold((byte) 100);
        assertEquals(5 * SimulationConstants.SIMULATION_STEP, pipe.getMaxInput(), 0.0001);
        assertEquals(100, pipe.getValveThreshold());
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooHighThreshold() {
        pipe.setValveThreshold((byte) 101);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooLowThreshold() {
        pipe.setValveThreshold((byte) -1);
    }

    @Test(expected = OverfullLiquidContainerException.class)
    public void putTooMuchIn() {
        float maxInput = pipe.getMaxInput();
        Liquid l = new Liquid(maxInput + 0.01f, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        pipe.putIn(l);
    }

    @Test
    public void testPutIn() {
        Liquid empty = new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        Liquid l1 = new Liquid( pipe.getMaxInput()/2, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        Liquid l2 = new Liquid( pipe.getMaxInput(), SimulationConstants.MAX_TEMPERATURE, new Color(2));
        assertEquals(empty, pipe.putIn(l1));
        assertEquals(empty, pipe.putIn(l2));
        assertEquals(empty, pipe.putIn(l1));
        assertEquals(empty, pipe.putIn(l1));
        assertEquals(empty, pipe.putIn(l2));
        assertEquals(l1, pipe.putIn(l2));
        assertEquals(l2, pipe.putIn(l2));
        assertEquals(l1, pipe.putIn(l2));
        assertEquals(l1, pipe.putIn(l2));
    }

    @Test
    public void testPutInSmallPipe() {
        pipe = new Pipe(1, 1);

        Liquid empty = new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        Liquid l1 = new Liquid( pipe.getMaxInput()/2, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        Liquid l2 = new Liquid( pipe.getMaxInput(), SimulationConstants.MAX_TEMPERATURE, new Color(2));

        assertEquals(empty, pipe.putIn(l1));
        assertEquals(l1, pipe.putIn(l2));
        assertEquals(l2, pipe.putIn(l1));
    }

    @Test(expected = NullPointerException.class)
    public void testNull() {
        pipe.putIn(null);
    }
}
