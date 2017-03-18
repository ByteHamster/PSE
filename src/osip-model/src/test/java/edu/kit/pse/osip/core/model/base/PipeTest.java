package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;
import java.util.Observable;
import java.util.Observer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the Pipe class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class PipeTest implements Observer {
    private Pipe pipe = null;
    private boolean changed;

    /**
     * Initialize the pipe and the changed boolean.
     */
    @Before
    public void init() {
        pipe = new Pipe(5, 5, (byte) 100);
        changed = false;
    }

    /**
     * Test whether a zero crosssection triggers an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void wrongCrosssection() {
        new Pipe(0, 5, (byte) 100);
    }

    /**
     * Check whether a zero length triggers an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void wrongLength() {
        new Pipe(5, 0, (byte) 100);
    }

    /**
     * Check whether the valve works as expected
     */
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

    /**
     * Test whether the pipe rejects to high valve settings.
     */
    @Test(expected = IllegalArgumentException.class)
    public void tooHighThreshold() {
        pipe.setValveThreshold((byte) 101);
    }

    /**
     * Check whether the pipe rejects to low valve settings
     */
    @Test(expected = IllegalArgumentException.class)
    public void tooLowThreshold() {
        pipe.setValveThreshold((byte) -1);
    }

    /**
     * Check whether the pipe rejects to much liquid.
     */
    @Test(expected = OverfullLiquidContainerException.class)
    public void putTooMuchIn() {
        float maxInput = pipe.getMaxInput();
        Liquid l = new Liquid(maxInput + 0.01f, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        pipe.putIn(l);
    }

    /**
     * Check whether put in return the correct liquids after some time-
     */
    @Test
    public void testPutIn() {
        Liquid empty = new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        Liquid l1 = new Liquid(pipe.getMaxInput() / 2, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        Liquid l2 = new Liquid(pipe.getMaxInput(), SimulationConstants.MAX_TEMPERATURE, new Color(2));
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

    /**
     * Check whether putIn() also works for a pipe with a length of one.
     */
    @Test
    public void testPutInSmallPipe() {
        pipe = new Pipe(1, 1, (byte) 100);

        Liquid empty = new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        Liquid l1 = new Liquid(pipe.getMaxInput() / 2, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        Liquid l2 = new Liquid(pipe.getMaxInput(), SimulationConstants.MAX_TEMPERATURE, new Color(2));

        assertEquals(empty, pipe.putIn(l1));
        assertEquals(l1, pipe.putIn(l2));
        assertEquals(l2, pipe.putIn(l1));
    }

    /**
     * Check whether a null-liquid gets rejected
     */
    @Test(expected = NullPointerException.class)
    public void testNull() {
        pipe.putIn(null);
    }

    /**
     * Check that every change to the pipe triggers the observers.
     */
    @Test
    public void testObserver() {
        pipe.addObserver(this);
        pipe.putIn(new Liquid(1, SimulationConstants.MAX_TEMPERATURE, new Color(0)));
        assertTrue(changed);
        changed = false;
        pipe.setValveThreshold((byte) 23);
        assertTrue(changed);
    }

    /**
     * Check if isLiquidEntering works correctly.
     */
    @Test
    public void testIsLiquidEntering() {
        assertFalse(pipe.isLiquidEntering());
        pipe.putIn(new Liquid(pipe.getMaxInput(), SimulationConstants.MIN_TEMPERATURE, new Color(0)));
        assertTrue(pipe.isLiquidEntering());
        pipe.putIn(new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0)));
        assertFalse(pipe.isLiquidEntering());
    }

    @Override
    public void update(Observable observable, Object o) {
        changed = true;
    }
}
