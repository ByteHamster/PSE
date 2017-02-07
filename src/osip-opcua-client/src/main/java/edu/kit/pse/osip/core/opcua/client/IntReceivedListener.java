package edu.kit.pse.osip.core.opcua.client;

/**
 * Listener that is called when an integer value was loaded from the server.
 */
public interface IntReceivedListener extends edu.kit.pse.osip.core.opcua.client.ReceivedListener {
    /**
     * An int was received from the server
     * @param value The received value
     */
    public void onReceived (int value);
}