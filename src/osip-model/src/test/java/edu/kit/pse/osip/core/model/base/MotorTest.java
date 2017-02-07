package edu.kit.pse.osip.core.model.base;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the Motor class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class MotorTest {
    Motor motor = null;

    /**
     * Create a new Motor for tests.
     */
    @Before
    public void initMotor() {
        motor = new Motor();
    }

    /**
     * Check whether setRPM() rejects too big integers.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testTooBig() {
        motor.setRPM(3001);
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
        motor.setRPM(3000);
        assertEquals(3000, motor.getRPM());
    }
}
