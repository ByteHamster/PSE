package edu.kit.pse.osip.core.opcua.client;

/**
 * Listener that is called when a boolean value was loaded from the server.
 */
public interface BooleanReceivedListener extends edu.kit.pse.osip.core.opcua.client.ReceivedListener {
    /**
     * A boolean was received from the server
     * @param value The received value
     */
    public void onReceived (boolean value);
}
