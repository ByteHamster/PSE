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
 * A class to test the Tank class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class TankTest implements Observer {
    /**
     * Liquid used for testing.
     */
    private Liquid liquid = new Liquid(1, SimulationConstants.MIN_TEMPERATURE, new Color(0));
    /**
     * Pipe for testing the incoming pipe.
     */
    private Pipe inPipe = new Pipe(5, 5, (byte) 100);
    /**
     * Pipe for testing the outgoing pipe.
     */
    private Pipe outPipe = new Pipe(5, 5, (byte) 100);
    /**
     * Indicates whether the observer works.
     */
    private boolean changed;

    /**
     * Resets changed.
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
        new Tank(0, TankSelector.CYAN, liquid, outPipe, inPipe);
    }

    /**
     * Tests whether a null TankSelector throws an exception.
     */
    @Test(expected = NullPointerException.class)
    public void testNullSelector() {
        new Tank(2, null, liquid, outPipe, inPipe);
    }

    /**
     * Tests whether a null liquid throws an exception.
     */
    @Test(expected = NullPointerException.class)
    public void testNullLiquid() {
        new Tank(2, TankSelector.CYAN, null, outPipe, inPipe);
    }

    /**
     * Tests whether a null outPipe throws an exception.
     */
    @Test(expected = NullPointerException.class)
    public void testNullInpipe() {
        new Tank(2, TankSelector.CYAN, liquid, null, inPipe);
    }

    /**
     * Tests whether the same in- and outPipe throws an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSamePipes() {
        new Tank(2, TankSelector.CYAN, liquid, outPipe, outPipe);
    }

    /**
     * Tests whether a null inPipe throws an exception.
     */
    @Test(expected = NullPointerException.class)
    public void testNullOutpipe() {
        new Tank(2, TankSelector.CYAN, liquid, outPipe, null);
    }

    /**
     * Tests whether getInPipe() and getOutPipe() work correctly.
     */
    @Test
    public void testPipes() {
        Tank tank = new Tank(5, TankSelector.CYAN, liquid, outPipe, inPipe);
        assertEquals(inPipe, tank.getInPipe());
        assertEquals(outPipe, tank.getOutPipe());
    }

    /**
     * Tests whether getting and setting the liquid and getFillLevel() works correctly.
     */
    @Test
    public void testLiquid() {
        Tank tank = new Tank(5, TankSelector.CYAN, liquid, outPipe, inPipe);
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
        Tank tank = new Tank(5, TankSelector.CYAN, liquid, outPipe, inPipe);
        tank.setLiquid(null);
    }

    /**
     * Tests whether getting the TankSelector works.
     */
    @Test
    public void testTankSelector() {
        Tank tank = new Tank(5, TankSelector.CYAN, liquid, outPipe, inPipe);
        assertEquals(TankSelector.CYAN, tank.getTankSelector());
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
        Tank tank = new Tank(5, TankSelector.CYAN, liquid, outPipe, inPipe);
        tank.addObserver(this);
        assertFalse(changed);
        tank.setLiquid(new Liquid(20, SimulationConstants.MAX_TEMPERATURE, new Color(1)));
        assertTrue(changed);
    }
}
