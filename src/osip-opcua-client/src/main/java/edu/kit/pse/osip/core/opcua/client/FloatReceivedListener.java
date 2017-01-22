package edu.kit.pse.osip.core.opcua.client;

/**
 * Listener that is called when a float value was loaded from the server.
 */
public interface FloatReceivedListener extends edu.kit.pse.osip.core.opcua.client.ReceivedListener {
    /**
     * A float was received from the server
     * @param value The received value
     */
    public void onReceived (float value);
}
