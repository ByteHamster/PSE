package edu.kit.pse.osip.core.utils.formatting;

import static org.junit.Assert.assertEquals;
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
        String input = "2000";
        int result = FormatChecker.checkPort(input);
        assertEquals(2000, result);
    }

    /**
     * Test big that are too big
     */
    @Test(expected = InvalidPortException.class)
    public void testPortOverflow() {
        String input = "70000";
        FormatChecker.checkPort(input);
    }

    /**
     * Test ports with non-digits
     */
    @Test(expected = InvalidPortException.class)
    public void testPortFormat() {
        String input = "20b10";
        FormatChecker.checkPort(input);
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
        //String input = "wekfpk.wekfpkp.wefkopg.00";
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
