package edu.kit.pse.osip.core.utils.formatting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * JUnit test class for FormatChecker.java
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class FormattingTest {

    /**
     * Test normal ports
     */
    @Test
    public void testPortNormal() {
        assertTrue(FormatChecker.isValidPort("2000"));
    }

    /**
     * Test big that are too big
     */
    @Test
    public void testPortOverflow() {
        assertFalse(FormatChecker.isValidPort("70000"));
    }

    /**
     * Test ports with non-digits
     */
    @Test
    public void testPortFormat() {
        assertFalse(FormatChecker.isValidPort("20b10"));
    }
    
    /**
     * Test normal percentage
     */
    @Test
    public void testPercentage() {
        String input = "55";
        int result = FormatChecker.checkPercentage(input);
        assertEquals(55, result);
    }

    /**
     * Test inputs that are not percentages
     */
    @Test(expected = InvalidPercentageException.class)
    public void testPercentageFormat() {
        String input = "a1";
        FormatChecker.checkPercentage(input);
    }

    /**
     * Test negative percentage
     */
    @Test(expected = InvalidPercentageException.class)
    public void testPercentageRange() {
        String input = "-1";
        FormatChecker.checkPercentage(input);
    }

    /**
     * Test valid ip
     */
    @Test
    public void testHostIp() {
        String input = "66.249.69.000";
        FormatChecker.checkHost(input);
    }

    /**
     * Test valid port
     */
    @Test (expected = InvalidHostException.class)
    public void testHostName() {
        String input = "\\";
        FormatChecker.checkHost(input);
    }

    /**
     * Test invalid host input with invalid signs
     */
    @Test(expected = InvalidHostException.class)
    public void testHostInvalidHostName() {
        String input = "&/(&";
        FormatChecker.checkHost(input);
    }
}
