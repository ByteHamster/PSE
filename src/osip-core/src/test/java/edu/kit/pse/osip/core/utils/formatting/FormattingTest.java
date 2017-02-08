package edu.kit.pse.osip.core.utils.formatting;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * JUnit test class for FormatChecker.java
 * @author Maximilian Schwarzmann
 *
 */
public class FormattingTest {

    String input;
    int result;

    /**
     * Test normal ports
     */
    @Test
    public void testPortNormal() {

        input = "2000";
        result = FormatChecker.checkPort(input);
        assertEquals(2000, result);
    }

    /**
     * Test big that are too big
     */
    @Test(expected = InvalidPortException.class)
    public void testPortOverflow() {

        input = "70000";
        result = FormatChecker.checkPort(input);
        assertEquals(70000, result);
    }

    /**
     * Test ports with non-digits
     */
    @Test(expected = InvalidPortException.class)
    public void testPortFormat() {

        input = "20b10";
        result = FormatChecker.checkPort(input);
        assertEquals(20000, result);
    }
    
    /**
     * Test normal percentage
     */
    @Test
    public void testPercentage() {

        input = "55";
        result = FormatChecker.checkPercentage(input);
        assertEquals(55, result);
    }

    /**
     * Test inputs that are not percentages
     */
    @Test(expected = InvalidPercentageException.class)
    public void testPercenteageFormat() {

        input = "a1";
        result = FormatChecker.checkPercentage(input);
        assertEquals(20000, result);
    }

    /**
     * Test negative percentage
     */
    @Test(expected = InvalidPercentageException.class)
    public void testPercenteageRange() {

        input = "-1";
        result = FormatChecker.checkPercentage(input);
        assertEquals(-1, result);
    }

    /**
     * Test valid ip
     */
    @Test
    public void testHostIp() {

        input = "66.249.69.000";
        FormatChecker.checkHost(input);
    }

    /**
     * Test valid port
     */
    @Test
    public void testHostPort() {

        input = "wekfpk.wekfpkp.wefkopg.00";
        FormatChecker.checkHost(input);
    }

    /**
     * Test invalid host input with invalid signs
     */
    @Test(expected = InvalidHostException.class)
    public void testHostInvalidPort() {

        input = "&/(&";
        FormatChecker.checkHost(input);
    }
}