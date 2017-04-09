package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;
import java.util.Observable;
import java.util.Observer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the TankSelector class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class MixTankTest implements Observer {
    /**
     * Liquid used for testing.
     */
    private Liquid liquid = new Liquid(1, SimulationConstants.MIN_TEMPERATURE, new Color(0));
    /**
     * Pipe used for testing.
     */
    private Pipe outPipe = new Pipe(5, 5, (byte) 100);
    /**
     * Tracks the working of the observer.
     */
    private boolean changed;

    /**
     * Reset changed.
     */
    @Before
    public void init() {
        changed = false;
    }

    /**
     * Tests whether a too small capacity throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testWrongCapacity() {
        new MixTank(0, liquid, outPipe);
    }

    /**
     * Tests whether a null liquid throws an exception.
     */
    @Test(expected = NullPointerException.class)
    public void testNullLiquid() {
        new MixTank(2, null, outPipe);
    }

    /**
     * Tests whether a null outPipe throws an exception.
     */
    @Test(expected = NullPointerException.class)
    public void testNullInpipe() {
        new MixTank(2, liquid, null);
    }

    /**
     * Tests whether getOutPipe() works correctly.
     */
    @Test
    public void testPipes() {
        MixTank tank = new MixTank(5, liquid, outPipe);
        assertEquals(outPipe, tank.getOutPipe());
    }

    /**
     * Tests whether getMotor() works correctly.
     */
    @Test
    public void testMotor() {
        MixTank tank = new MixTank(5, liquid, outPipe);
        assertNotNull(tank.getMotor());
    }

    /**
     * Tests whether getting and setting the liquid and getFillLevel() works correctly.
     */
    @Test
    public void testLiquid() {
        MixTank tank = new MixTank(5, liquid, outPipe);
        assertEquals(liquid, tank.getLiquid());
        assertEquals(1 / 5f, tank.getFillLevel(), 0.001);

        /* Overfill tank */
        tank.setLiquid(new Liquid(20, SimulationConstants.MAX_TEMPERATURE, new Color(1)));
        assertEquals(new Liquid(20, SimulationConstants.MAX_TEMPERATURE, new Color(1)), tank.getLiquid());
        assertEquals(4, tank.getFillLevel(), 0.001);
    }

    /**
     * Tests whether setting a null liquid works correctly.
     */
    @Test(expected = NullPointerException.class)
    public void testSetNullLiquid() {
        MixTank tank = new MixTank(5, liquid, outPipe);
        tank.setLiquid(null);
    }

    @Override
    public void update(Observable observable, Object o) {
        changed = true;
        assertTrue(o instanceof Liquid);
    }

    /**
     * Tests whether observing works correctly.
     */
    @Test
    public void testObserver() {
        MixTank tank = new MixTank(5, liquid, outPipe);
        tank.addObserver(this);
        assertFalse(changed);
        tank.setLiquid(new Liquid(20, SimulationConstants.MAX_TEMPERATURE, new Color(1)));
        assertTrue(changed);
    }
}
