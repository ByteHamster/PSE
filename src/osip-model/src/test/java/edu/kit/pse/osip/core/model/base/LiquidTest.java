package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the Liquid class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class LiquidTest {
    /**
     * Test whether the constructor accepts too low temperatures
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeTemperature() {
        new Liquid(1, SimulationConstants.MIN_TEMPERATURE - 0.01f, new Color(0, 0, 0));
    }

    /**
     * Test whether the constructor accepts a negative amount.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeAmount() {
        new Liquid(-1.1f, SimulationConstants.MIN_TEMPERATURE, new Color(0, 0, 0));
    }

    /**
     * Test whether the constructor accepts a null color.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullColor() {
        new Liquid(1, SimulationConstants.MIN_TEMPERATURE, null);
    }

    /**
     * Test the getter.
     */
    @Test
    public void testGetter() {
        Liquid liquid = new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0, 0, 0));
        assertEquals(0, liquid.getAmount(), 0.001);
        assertEquals(SimulationConstants.MIN_TEMPERATURE, liquid.getTemperature(), 0.001);
        assertEquals(new Color(0, 0, 0), liquid.getColor());
    }

    /**
     * Check whether the hashCode method works as expected.
     */
    @Test
    public void testHashcode() {
        Liquid liquid1 = new Liquid(1, SimulationConstants.MIN_TEMPERATURE, new Color(1, 1, 1));
        Liquid liquid2 = new Liquid(1, SimulationConstants.MIN_TEMPERATURE, new Color(1, 1, 1));
        assertEquals(liquid1.hashCode(), liquid2.hashCode());

        liquid2 = new Liquid(2, SimulationConstants.MIN_TEMPERATURE, new Color(1, 1, 1));
        assertNotEquals(liquid1.hashCode(), liquid2.hashCode());

        liquid2 = new Liquid(1, SimulationConstants.MAX_TEMPERATURE, new Color(1, 1, 1));
        assertNotEquals(liquid1.hashCode(), liquid2.hashCode());
    }

    /**
     * Check whether the equals method works as expected.
     */
    @Test
    public void testEquals() {
        Color color = new Color(1, 1, 1);
        Liquid liquid1 = new Liquid(1, SimulationConstants.MIN_TEMPERATURE, color);
        Liquid liquid2 = new Liquid(1, SimulationConstants.MIN_TEMPERATURE, color);
        assertTrue(liquid1.equals(liquid2));
        assertTrue(liquid2.equals(liquid1));
        assertFalse(liquid1.equals(null));
        assertFalse(liquid1.equals(new String("test")));

        liquid2 = new Liquid(2, SimulationConstants.MIN_TEMPERATURE, color);
        assertFalse(liquid1.equals(liquid2));
        assertFalse(liquid2.equals(liquid1));

        liquid2 = new Liquid(1, SimulationConstants.MAX_TEMPERATURE, color);
        assertFalse(liquid1.equals(liquid2));
        assertFalse(liquid2.equals(liquid1));
    }
}
