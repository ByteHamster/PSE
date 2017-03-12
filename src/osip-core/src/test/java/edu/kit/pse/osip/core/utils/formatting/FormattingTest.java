package edu.kit.pse.osip.core.utils.formatting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit test class for FormatChecker.java
 * 
 * @author Maximilian Schwarzmann
 * @version 1.1
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
     * Tests a normal percentage.
     */
    @Test
    public void testValidPercentage() {
        String input = "55";
        assertTrue(FormatChecker.checkPercentage(input));
    }

    /**
     * Tests inputs that are not percentages.
     */
    public void testInvalidPercentages() {
        assertFalse(FormatChecker.checkPercentage("a1"));
        assertFalse(FormatChecker.checkPercentage("-1"));
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
