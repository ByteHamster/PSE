package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the basic parser class
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class BaseParserTest {
    /**
     * Tests if the parser throws an exception when calling die()
     * @throws ParserException expected
     */
    @Test(expected = ParserException.class)
    public void testDie() throws ParserException {
        new BaseParser("").die("Something went wrong");
    }

    /**
     * Tests if peek and pop work as expected
     * @throws ParserException If something goes wrong
     */
    @Test
    public void testPeekPop() throws ParserException {
        BaseParser parser = new BaseParser("abc");
        assertEquals(parser.peek(), 'a');
        assertEquals(parser.pop(), 'a');
        assertEquals(parser.pop(), 'b');
        assertEquals(parser.peek(), 'c');
        assertEquals(parser.peek(), 'c');
        assertTrue(parser.available());
        assertEquals(parser.pop(), 'c');
        assertFalse(parser.available());
    }

    /**
     * Pop without data being available
     * @throws ParserException Expected
     */
    @Test(expected = ParserException.class)
    public void testPopException() throws ParserException {
        BaseParser parser = new BaseParser("a");
        parser.pop();
        parser.pop();
    }

    /**
     * Test consuming and checking
     * @throws ParserException If something goes wrong
     */
    @Test
    public void testConsumeAndCheck() throws ParserException {
        BaseParser parser = new BaseParser("abcde");
        parser.consume(3);
        assertEquals(parser.pop(), 'd');
        parser.check('e');
    }

    /**
     * Test failed check()
     * @throws ParserException If something goes wrong
     */
    @Test(expected = ParserException.class)
    public void testCheckFailed() throws ParserException {
        BaseParser parser = new BaseParser("abcde");
        parser.consume(3);
        parser.check('y');
    }

    /**
     * Test whitespaces and comments
     * @throws ParserException If something goes wrong
     */
    @Test
    public void testWhitespaceComments() throws ParserException {
        BaseParser parser = new BaseParser("    a \t\n\t b# comment (/\"§&!)) \nc");
        parser.skipWhitespaces();
        parser.check('a');
        parser.skipWhitespaces();
        parser.check('b');
        parser.skipComments();
        parser.check('c');
    }

    /**
     * Test if commands create EOF errors
     */
    @Test
    public void testEOF() {
        BaseParser parser = new BaseParser("");

        try {
            parser.pop();
            fail("Expected EOF error");
        } catch (ParserException e) {
            // Expected
        }

        try {
            parser.peek();
            fail("Expected EOF error");
        } catch (ParserException e) {
            // Expected
        }
    }
}
