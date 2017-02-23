package edu.kit.pse.osip.core.model.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A class to test the Color class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class ColorTest {
    private final double epsilon = 0.0001;

    /**
     * Check whether the cmy constructor works correctly.
     */
    @Test
    public void testCMYConstructor() {
        Color color = new Color(0, 0, 0);
        assertEquals(0, color.getCyan(), epsilon);
        assertEquals(0, color.getMagenta(), epsilon);
        assertEquals(0, color.getYellow(), epsilon);
        color = new Color(1, 1, 1);
        assertEquals(1, color.getCyan(), epsilon);
        assertEquals(1, color.getMagenta(), epsilon);
        assertEquals(1, color.getYellow(), epsilon);
    }

    /**
     * Check whether the rgb constructor works correctly.
     */
    @Test
    public void testRGBConstructor() {
        Color color = new Color(0x7F9B22);
        assertEquals(0x7F / 255f, color.getR(), epsilon);
        assertEquals(0x9B / 255f, color.getG(), epsilon);
        assertEquals(0x22 / 255f, color.getB(), epsilon);
        assertEquals(0x7F9B22, color.getRGB());
        try {
            color = new Color(0x01FFFFFF);
            fail("Color accepts non-null first byte for rgb constructor");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Check whether the constructor rejects illegal arguments.
     */
    @Test
    public void testWrongArgumentsConstructor() {
        Color color = null;
        try {
            color = new Color(-1, 0, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            color = new Color(2, 0, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            color = new Color(0, -1, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            color = new Color(0, 2, 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            color = new Color(0, 0, -1);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            color = new Color(0, 0, 2);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Check whether the equals method works as expected.
     */
    @Test
    public void testEquals() {
        Color color1 = new Color(0.5, 0.5, 0.5);
        Color color2 = new Color(0.5, 0.5, 0.5);
        assertTrue(color1.equals(color2));
        assertTrue(color2.equals(color1));
        assertFalse(color1.equals(null));
        assertFalse(color1.equals(new String("test")));
        color2 = new Color(0.5, 0.5, 0.6);
        assertFalse(color1.equals(color2));
        assertFalse(color2.equals(color1));
    }

    /**
     * Check whether the hashCode method works as expected.
     */
    @Test
    public void testHashcode() {
        Color color1 = new Color(0.5, 0.5, 0.5);
        Color color2 = new Color(0.5, 0.5, 0.5);
        assertEquals(color1.hashCode(), color2.hashCode());
        color2 = new Color(0.5, 0.5, 0.6);
        assertNotEquals(color1.hashCode(), color2.hashCode());
    }

    /**
     * Check whether the RGB conversion works, by using an arbitrary example.
     * The RGB values were calculated by a web converter.
     */
    @Test
    public void testToRGB() {
        Color color = new Color(0.54, 0.76, 0.42);
        assertEquals(117 / 255f, color.getR(), 0.01);
        assertEquals(61 / 255f, color.getG(), 0.01);
        assertEquals(148 / 255f, color.getB(), 0.01);
    }
}
