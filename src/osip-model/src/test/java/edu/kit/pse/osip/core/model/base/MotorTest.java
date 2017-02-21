package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;
import java.util.Observable;
import java.util.Observer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the Motor class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class MotorTest implements Observer {
    private Motor motor = null;
    private boolean observed;

    /**
     * Create a new Motor for tests.
     */
    @Before
    public void initMotor() {
        motor = new Motor();
        observed = false;
    }

    /**
     * Check whether setRPM() rejects too big integers.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTooBig() {
        motor.setRPM(SimulationConstants.MAX_MOTOR_SPEED + 1);
    }

    /**
     * Check whether setRPM() rejects negative integers.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegative() {
        motor.setRPM(-1);
    }

    /**
     * Check whether setRPM() works for correct integers.
     */
    @Test
    public void testSet() {
        motor.setRPM(0);
        assertEquals(0, motor.getRPM());
        motor.setRPM(SimulationConstants.MAX_MOTOR_SPEED);
        assertEquals(SimulationConstants.MAX_MOTOR_SPEED, motor.getRPM());
    }

    /**
     * Test whether observing the motor works fine.
     */
    @Test
    public void testObserver() {
        motor.addObserver(this);
        motor.setRPM(5);
        assertTrue(observed);
    }

    @Override
    public void update(Observable observable, Object o) {
        observed = true;
    }
}
