package edu.kit.pse.osip.core.io.files;

/**
 * Basic parser class 
 */
public class BaseParser {
    /**
     * Constructor of BaseParser
     * @param toParse The string that should be parsed
     */
    public BaseParser (String toParse) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Check if there are more signs to read @return true if there are more
     */
    public final boolean available () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Generate and throw exception (initialize with line number etc)
     * @throws ParserException Always.
     */
    protected final void die () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Look at next char and remove it
     * @return read char 
     * @throws ParserException If something goes wrong
     */
    protected final char pop () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Look at next char
     * @return peeked char
     * @throws ParserException If something goes wrong
     */
    protected final char peek () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Read multiple symbols
     * @throws ParserException If something goes wrong
     * @param n n times read
     */
    protected final void consume (int n) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * If peek != char then die
     * @throws ParserException If something goes wrong
     * @param c c char to check
     */
    protected final void check (char c) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Skip whitespaces
     * @throws ParserException If something goes wrong
     */
    public final void skipWhitespaces () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Skip comments
     * @throws ParserException If something goes wrong
     */
    public final void skipComments () {
        throw new RuntimeException("Not implemented!");
    }
}
