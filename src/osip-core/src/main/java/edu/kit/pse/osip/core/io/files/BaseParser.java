package edu.kit.pse.osip.core.io.files;

/**
 * Basic parser class
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class BaseParser {
    protected int currentPosition = 0;
    private final String toParse;
    
    /**
     * Constructor of BaseParser
     * @param toParse The string that should be parsed
     */
    public BaseParser(String toParse) {
        this.toParse = toParse;
    }
    /**
     * Check if there are more signs to read @return true if there are more
     */
    public final boolean available() {
        return currentPosition < toParse.length();
    }
    /**
     * Generate and throw exception (initialize with line number etc)
     * @param message The message of the exception
     * @throws ParserException Always.
     */
    protected final void die(String message) throws ParserException {
        throw new ParserException(message, 0, currentPosition);
    }
    /**
     * Look at next char and remove it
     * @return read char 
     * @throws ParserException If something goes wrong
     */
    protected final char pop() throws ParserException {
        if (available()) {
            return toParse.charAt(currentPosition++);
        } else {
            die("Unexpected EOF");
            return 0;
        }
    }
    /**
     * Look at next char
     * @return peeked char
     * @throws ParserException If something goes wrong
     */
    protected final char peek() throws ParserException {
        if (available()) {
            return toParse.charAt(currentPosition);
        } else {
            die("Unexpected EOF");
            return 0;
        }
    }
    /**
     * Read multiple symbols
     * @throws ParserException If something goes wrong
     * @param n n times read
     */
    protected final void consume(int n) throws ParserException {
        for (int i = 0; i < n; i++) {
            pop();
        }
    }
    /**
     * If peek != char then die
     * @throws ParserException If something goes wrong
     * @param c c char to check
     */
    protected final void check(char c) throws ParserException {
        if (peek() != c) {
            die("Unexpected symbol: '" + peek() + "'");
        }
        pop();
    }
    /**
     * Skip whitespaces
     * @throws ParserException If something goes wrong
     */
    public final void skipWhitespaces() throws ParserException {
        while (available() && Character.isWhitespace(peek())) {
            pop();
        }
    }
    /**
     * Skip comments
     * @throws ParserException If something goes wrong
     */
    public final void skipComments() throws ParserException {
        if (available() && peek() == '#') {
            check('#');
            while (available() && peek() != '\n') {
                pop();
            }
            check('\n');
        }
    }
}
