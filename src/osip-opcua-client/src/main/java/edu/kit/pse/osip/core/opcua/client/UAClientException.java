package edu.kit.pse.osip.core.opcua.client;

/**
 * An error occurred while using the UAClientWrapper
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class UAClientException extends Exception {
    private static final long serialVersionUID = 8847992513923676220L;
    
    /**
     * Generates a new exception
     * @param message The reason for this exception
     */
    public UAClientException(String message) {
        super(message);
    }
}
