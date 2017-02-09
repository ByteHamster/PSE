package edu.kit.pse.osip.core.utils.formatting;

/**
 * This exception signifies that a port was invalid.
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class InvalidPortException extends java.lang.IllegalArgumentException {
    private static final long serialVersionUID = -4059815426263854286L;

    /**
     * Creates a new InvalidPortException
     * @param tried The string that was tried
     * @param reason The reason why parsing went wrong
     */
    public InvalidPortException(String tried, String reason) {
        
        super(tried + " " + reason);
    }
}
