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
    @Test
    public void testInvalidPercentages() {
        assertFalse(FormatChecker.checkPercentage("a1"));
        assertFalse(FormatChecker.checkPercentage("-1"));
    }

    /**
     * Tests valid IP address.
     */
    @Test
    public void testHostIp() {
        assertTrue(FormatChecker.checkHost("66.249.69.000"));
    }

    /**
     * Tests invalid host names.
     */
    @Test
    public void testInvalidHostName() {
        assertFalse(FormatChecker.checkHost("\\"));
        assertFalse(FormatChecker.checkHost("&/(&"));
    }
}
