package edu.kit.pse.osip.core.opcua.client;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.Stack;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

/**
 * Simplifies the interface of Milo.
 * Provides a way to easily read values by using their identifier.
 * Allows to add subscriptions without having to deal with milo internals.
 * Directly converts values to the 3 major types.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public abstract class UAClientWrapper {
    private final OpcUaClient client;
    
    /**
     * Wraps the milo client implementation to simplify process
     * @param serverUrl The url of the server
     * @param namespace Name of the expected default namespace. Will fail if the namespaces do not match
     */
    public UAClientWrapper(String serverUrl, String namespace) {
        try {
            client = createClient(serverUrl, namespace);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to create client");
        }
    }

    /**
     * Creates the client
     * 
     * @return a client ready to be run
     * @param serverUrl The url of the server to use. Something like opc.tcp://localhost:12686/example
     * @param namespace The default namespace to expect
     * @throws ExecutionException If creation of the client fails
     * @throws InterruptedException If creation of the client fails
     */
    private OpcUaClient createClient(String serverUrl, String namespace)
            throws InterruptedException, ExecutionException {
        SecurityPolicy securityPolicy = SecurityPolicy.None;

        EndpointDescription[] endpoints = UaTcpStackClient.getEndpoints(serverUrl).get();

        EndpointDescription endpoint = Arrays.stream(endpoints)
                .filter(e -> e.getSecurityPolicyUri().equals(securityPolicy.getSecurityPolicyUri())).findFirst()
                .orElseThrow(() -> new RuntimeException("No desired endpoints returned"));

        OpcUaClientConfig config = OpcUaClientConfig.builder()
                .setApplicationName(LocalizedText.english("OSIP client"))
                .setApplicationUri("urn:edu:kit:pse:osip:client:application:" + namespace).setEndpoint(endpoint)
                .setIdentityProvider(new AnonymousProvider()).setRequestTimeout(Unsigned.uint(5000))
                .build();

        return new OpcUaClient(config);
    }

    /**
     * Connects the client to the server
     * @throws ExecutionException If the connection fails
     * @throws InterruptedException If the connection fails
     */
    public final void connectClient() throws InterruptedException, ExecutionException {
        client.connect().get();
    }

    /**
     * Disconnects the client from the server
     * @throws ExecutionException If the client can not be stopped
     * @throws InterruptedException If the client can not be stopped
     */
    public final void disconnectClient() throws InterruptedException, ExecutionException {
        client.disconnect().get();
        Stack.releaseSharedResources();
    }

    /**
     * Unsubscribes the listener from getting refreshed
     * @param listener The listener of the subscription to cancel
     */
    public final void unsubscribe(ReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Subscribes to a float from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     */
    protected final void subscribeFloat(String nodeName, int interval, FloatReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Subscribes to a boolean from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     */
    protected final void subscribeBoolean(String nodeName, int interval, BooleanReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Subscribes to an int from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     */
    protected final void subscribeInt(String nodeName, int interval, IntReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
}
