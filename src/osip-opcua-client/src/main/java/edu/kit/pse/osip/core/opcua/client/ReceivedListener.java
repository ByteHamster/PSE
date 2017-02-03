package edu.kit.pse.osip.core.opcua.client;

/**
 * Listener that is called when a value was received from the server.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public interface ReceivedListener {
    /**
     * An error occurred while trying to fetch data from the server
     */
    public void onError();
}
