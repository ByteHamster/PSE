package edu.kit.pse.osip.core.opcua.client;

/**
 * Listener that is called when an integer value was loaded from the server.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
@FunctionalInterface
public interface IntReceivedListener extends ReceivedListener {
    /**
     * An int was received from the server
     * @param value The received value
     */
    void onReceived(int value);
}
