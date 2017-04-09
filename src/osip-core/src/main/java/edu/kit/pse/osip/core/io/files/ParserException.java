package edu.kit.pse.osip.core.io.files;

/**
 * Exception class for exceptions in parsing.
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ParserException extends Exception {
    private static final long serialVersionUID = -3194028981516295714L;

    /**
     * Constructor of ParserException.
     * 
     * @param msg Message.
     * @param line The line in which the exception occurred.
     * @param character The character within the line where the error occurred.
     */
    public ParserException(String msg, int line, int character) {
        super("Error in line " + line + ", char " + character + ": " + msg);
    }
}
