package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the extended parser class
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ExtendedParserTest {
    /**
     * Tests numbers can be read
     * @throws ParserException If something goes wrong
     */
    @Test
    public void testNumber() throws ParserException {
        ExtendedParser parser = new ExtendedParser("0");
        TestUtils.assertFloat(parser.readNumber(), 0f);
        
        parser = new ExtendedParser("424242");
        TestUtils.assertFloat(parser.readNumber(), 424242f);
        
        parser = new ExtendedParser("0.05");
        TestUtils.assertFloat(parser.readNumber(), 0.05f);

        parser = new ExtendedParser("45.67");
        TestUtils.assertFloat(parser.readNumber(), 45.67f);
    }
    
    /**
     * Tests factors can be read
     * @throws ParserException If something goes wrong
     */
    @Test
    public void testFactor() throws ParserException {
        ExtendedParser parser = new ExtendedParser("-5");
        TestUtils.assertFloat(parser.readFactor(), -5f);
        
        parser = new ExtendedParser("--3");
        TestUtils.assertFloat(parser.readFactor(), 3f);
        
        parser = new ExtendedParser("10");
        TestUtils.assertFloat(parser.readFactor(), 10f);
        
        parser = new ExtendedParser("varname");
        parser.variables.put("varname", 42f);
        TestUtils.assertFloat(parser.readFactor(), 42f);

        parser = new ExtendedParser("-varname");
        parser.variables.put("varname", 42f);
        TestUtils.assertFloat(parser.readFactor(), -42f);
    }
    
    /**
     * Test invalid numbers
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
     * Tests terms can be read
     * @throws ParserException If something goes wrong
     */
    @Test
    public void testTerm() throws ParserException {
        ExtendedParser parser = new ExtendedParser("5 * 5");
        TestUtils.assertFloat(parser.readTerm(), 25f);
        
        parser = new ExtendedParser("1 * -1 * +3");
        TestUtils.assertFloat(parser.readTerm(), -3f);
        
        parser = new ExtendedParser("10");
        TestUtils.assertFloat(parser.readTerm(), 10f);
        
        parser = new ExtendedParser("3 / -varname");
        parser.variables.put("varname", 42f);
        TestUtils.assertFloat(parser.readTerm(), 3f / -42f);

        parser = new ExtendedParser("varname / varname");
        parser.variables.put("varname", 42f);
        TestUtils.assertFloat(parser.readTerm(), 1f);

        parser = new ExtendedParser("var_name * 2");
        parser.variables.put("var_name", 42f);
        TestUtils.assertFloat(parser.readTerm(), 84f);
    }
    
    
    /**
     * Tests expressions can be read
     * @throws ParserException If something goes wrong
     */
    @Test
    public void testExpression() throws ParserException {
        ExtendedParser parser = new ExtendedParser("5 * (2+3)");
        TestUtils.assertFloat(parser.readExpression(), 25f);

        parser = new ExtendedParser(" (2)+((( (( ( ((7)))*1 ))) + 1)) * 3");
        TestUtils.assertFloat(parser.readExpression(), 26f);
        
        parser = new ExtendedParser("1 + 2 * 3 + 3");
        TestUtils.assertFloat(parser.readExpression(), 10f);
    }
    
    /**
     * Test invalid terms
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
