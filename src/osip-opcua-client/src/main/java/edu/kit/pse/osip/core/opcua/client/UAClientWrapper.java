package edu.kit.pse.osip.core.opcua.client;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

/**
 * Simplifies the interface of Milo. Provides a way to easily read values by using their identifier. Allows to add subscriptions without having to deal with milo internals. Directly converts values to the 3 major types.
 */
public abstract class UAClientWrapper {
    public org.eclipse.milo.opcua.sdk.client.OpcUaClient client;
    /**
     * Wraps the milo client implementation to simplify process
     * @param serverUrl The url of the server
     * @param namespace Name of the expected default namespace. Will fail if the namespaces do not match
     */
    public UAClientWrapper (String serverUrl, String namespace) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Creates the client
     * 
     * @return a client ready to be run
     * @param serverUrl The url of the server to use
     * @param namespace The default namespace to expect
     */
    private final OpcUaClient createClient (String serverUrl, String namespace) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Connects the client to the server
     */
    public final void connectClient () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Disconnects the client from the server
     */
    public final void disconnectClient () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Unsubscribes the listener from getting refreshed
     * @param listener The listener of the subscription to cancel
     */
    public final void unsubscribe (ReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes to a float from the server. Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     */
    protected final void subscribeFloat (String nodeName, int interval, FloatReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes to a boolean from the server. Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     */
    protected final void subscribeBoolean (String nodeName, int interval, BooleanReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes to an int from the server. Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     */
    protected final void subscribeInt (String nodeName, int interval, IntReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
}
