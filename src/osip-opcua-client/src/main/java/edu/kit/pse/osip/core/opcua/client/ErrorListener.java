package edu.kit.pse.osip.core.opcua.client;

/**
 * Listener that is called when there is an error on the client,
 * such as a disconnect or a wrong variable type.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
@FunctionalInterface
public interface ErrorListener {
    /**
     * Method that is called when there is an error on the client,
     * such as a disconnect or a wrong variable type.
     * 
     * @param code The error code (see UAClientWrapper.ERROR_* for details).
     */
    void onError(int code);
}
