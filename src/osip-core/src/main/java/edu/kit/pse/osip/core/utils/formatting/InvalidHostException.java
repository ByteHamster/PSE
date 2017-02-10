package edu.kit.pse.osip.core.utils.formatting;

/**
 * This exception signifies that an IP or a hostname is invalid.
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class InvalidHostException extends java.lang.IllegalArgumentException {
    private static final long serialVersionUID = 6863727087861971431L;

    /**
     * Creates a new InvalidHostException
     * @param tried The value that was tried to parse        
     * @param reason The reason for the failure
     *           
     */
    public InvalidHostException(String tried, String reason) {

        super(tried + " " + reason);
    }
}
