package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the extended parser class.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ExtendedParserTest {
    /**
     * Tests if numbers can be read.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test
    public void testNumber() throws ParserException {
        ExtendedParser parser = new ExtendedParser("0");
        assertEquals(0f, parser.readNumber(), 0.0001);
        
        parser = new ExtendedParser("424242");
        assertEquals(424242f, parser.readNumber(), 0.0001);
        
        parser = new ExtendedParser("0.05");
        assertEquals(0.05f, parser.readNumber(), 0.0001);

        parser = new ExtendedParser("45.67");
        assertEquals(45.67f, parser.readNumber(), 0.0001);
    }
    
    /**
     * Tests if factors can be read.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test
    public void testFactor() throws ParserException {
        ExtendedParser parser = new ExtendedParser("-5");
        assertEquals(-5f, parser.readFactor(), 0.0001);
        
        parser = new ExtendedParser("--3");
        assertEquals(3f, parser.readFactor(), 0.0001);
        
        parser = new ExtendedParser("10");
        assertEquals(10f, parser.readFactor(), 0.0001);
        
        parser = new ExtendedParser("varname");
        parser.variables.put("varname", 42f);
        assertEquals(42f, parser.readFactor(), 0.0001);

        parser = new ExtendedParser("-varname");
        parser.variables.put("varname", 42f);
        assertEquals(-42f, parser.readFactor(), 0.0001);
    }
    
    /**
     * Tests invalid numbers.
     */
    @Test
    public void testInvalidNumbers() {        
        try {
            new ExtendedParser("a").readNumber();
            fail("Expected invalid number error");
        } catch (ParserException e) {
            // Expected
        }
        
        try {
            new ExtendedParser("-").readNumber();
            fail("Expected invalid number error");
        } catch (ParserException e) {
            // Expected
        }
        
        try {
            new ExtendedParser("0.").readNumber();
            fail("Expected invalid number error");
        } catch (ParserException e) {
            // Expected
        }
        
        try {
            new ExtendedParser(".0").readNumber();
            fail("Expected invalid number error");
        } catch (ParserException e) {
            // Expected
        }
    }
    
    /**
     * Tests if terms can be read.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test
    public void testTerm() throws ParserException {
        ExtendedParser parser = new ExtendedParser("5 * 5");
        assertEquals(25f, parser.readTerm(), 0.0001);
        
        parser = new ExtendedParser("1 * -1 * +3");
        assertEquals(-3f, parser.readTerm(), 0.0001);
        
        parser = new ExtendedParser("10");
        assertEquals(10f, parser.readTerm(), 0.0001);
        
        parser = new ExtendedParser("3 / -varname");
        parser.variables.put("varname", 42f);
        assertEquals(3f / -42f, parser.readTerm(), 0.0001);

        parser = new ExtendedParser("varname / varname");
        parser.variables.put("varname", 42f);
        assertEquals(1f, parser.readTerm(), 0.0001);

        parser = new ExtendedParser("var_name * 2");
        parser.variables.put("var_name", 42f);
        assertEquals(84f, parser.readTerm(), 0.0001);
    }
    
    
    /**
     * Tests if expressions can be read.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test
    public void testExpression() throws ParserException {
        ExtendedParser parser = new ExtendedParser("5 * (2+3)");
        assertEquals(25f, parser.readExpression(), 0.0001);

        parser = new ExtendedParser(" (2)+((( (( ( ((7)))*1 ))) + 1)) * 3");
        assertEquals(26f, parser.readExpression(), 0.0001);
        
        parser = new ExtendedParser("1 + 2 * 3 + 3");
        assertEquals(10f, parser.readExpression(), 0.0001);
    }
    
    /**
     * Tests invalid terms.
     */
    @Test
    public void testInvalidTerms() {        
        try {
            new ExtendedParser("))()()").readTerm();
            fail("Expected invalid term error");
        } catch (ParserException e) {
            // Expected
        }
        
        try {
            new ExtendedParser("(())").readTerm();
            fail("Expected invalid term error");
        } catch (ParserException e) {
            // Expected
        }
        
        try {
            new ExtendedParser("((a))").readTerm();
            fail("Expected invalid term error");
        } catch (ParserException e) {
            // Expected
        }
        
        try {
            new ExtendedParser("-*").readTerm();
            fail("Expected invalid term error");
        } catch (ParserException e) {
            // Expected
        }
        
        try {
            new ExtendedParser("-").readTerm();
            fail("Expected invalid term error");
        } catch (ParserException e) {
            // Expected
        }
        
        try {
            new ExtendedParser("*").readTerm();
            fail("Expected invalid term error");
        } catch (ParserException e) {
            // Expected
        }
    }
}
