package edu.kit.pse.osip.core.io.files;

/**
 * Basic parser class: LL(1) parser.
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class BaseParser {
    /**
     * Saves the current position within the string being parsed.
     */
    protected int currentPosition = 0;
    /**
     * Saves the current parsed string.
     */
    private final String toParse;
    /**
     * Saves the current line within the string being parsed.
     */
    private int currentLine = 0;
    
    /**
     * Constructor of BaseParser.
     * 
     * @param toParse The string that should be parsed.
     */
    public BaseParser(String toParse) {
        this.toParse = toParse;
    }
    /**
     * Checks if there are more signs to read.
     * 
     * @return true if there are more.
     */
    public final boolean available() {
        return currentPosition < toParse.length();
    }
    /**
     * Generates and throws exception (initializes with line number etc.).
     * 
     * @param message The message of the exception.
     * @throws ParserException Always.
     */
    protected final void die(String message) throws ParserException {
        String line = toParse.split("\n")[currentLine];
        throw new ParserException(message + " - " + line, currentLine + 1, currentPosition);
    }
    /**
     * Looks at next char and removes it.
     * 
     * @return read char.
     * @throws ParserException If something goes wrong.
     */
    protected final char pop() throws ParserException {
        if (available()) {
            if (toParse.charAt(currentPosition) == '\n') {
                currentLine++;
            }
            return toParse.charAt(currentPosition++);
        } else {
            die("Unexpected EOF");
            return 0;
        }
    }
    /**
     * Looks at next char.
     * 
     * @return peeked char.
     * @throws ParserException If something goes wrong.
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
     * Reads multiple symbols.
     * 
     * @param n times to read.
     * @throws ParserException If something goes wrong.
     */
    protected final void consume(int n) throws ParserException {
        for (int i = 0; i < n; i++) {
            pop();
        }
    }
    /**
     * If peek != char then die.
     * 
     * @param c char to check.
     * @throws ParserException If something goes wrong.
     */
    protected final void check(char c) throws ParserException {
        if (peek() != c) {
            die("Unexpected symbol: '" + peek() + "'");
        }
        pop();
    }
    /**
     * Skips white spaces.
     * 
     * @throws ParserException If something goes wrong.
     */
    public final void skipWhitespaces() throws ParserException {
        while (available() && Character.isWhitespace(peek())) {
            pop();
        }
    }
    /**
     * Skips comments.
     * 
     * @throws ParserException If something goes wrong.
     */
    public final void skipComments() throws ParserException {
        if (available() && peek() == '#') {
            check('#');
            while (available() && peek() != '\n' && peek() != '\r') {
                pop();
            }
            if (available()) {
                pop();
            }
        }
    }
}
