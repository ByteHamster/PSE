package edu.kit.pse.osip.core.opcua.client;

/**
 * Listener that is called when a value was received from the server.
 */
public interface ReceivedListener {
    /**
     * An error occured while trying to fetch data from the server
     */
    public void onError ();
}
