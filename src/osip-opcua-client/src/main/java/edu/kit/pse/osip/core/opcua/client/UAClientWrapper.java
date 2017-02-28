package edu.kit.pse.osip.core.opcua.client;

import com.google.common.collect.Lists;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.SessionActivityListener;
import org.eclipse.milo.opcua.sdk.client.api.UaSession;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.sdk.client.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscriptionManager.SubscriptionListener;
import org.eclipse.milo.opcua.sdk.client.model.nodes.objects.ServerNode;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.Stack;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

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
    /**
     * The client disconnected from the server. Can only be received if there is at least one subscription.
     */
    public static final int ERROR_DISCONNECT = 1;
    /**
     * The data type found on the server does not match the one you requested
     */
    public static final int ERROR_DATA_TYPE_MISMATCH = 2;
    /**
     * The server returned an unsupported data type
     */
    public static final int ERROR_DATA_TYPE_UNSUPPORTED = 3;
    /**
     * The interval parameter for a single immediate read of subscribed data
     */
    public static final int SINGLE_READ = -1;
    /**
     * Maximum size of the subscription queue
     */
    private static final UInteger SUBSCRIPTION_QUEUE_SIZE = Unsigned.uint(1);
    /**
     * Timeout when connecting
     */
    protected static final int CONNECTION_TIMEOUT = 5000;

    private OpcUaClient client = null;
    private String serverUrl;
    private String namespace;
    private int namespaceIndex = -1;
    private final AtomicLong clientHandles = new AtomicLong(1L);
    private boolean connected = false;
    private HashMap<ReceivedListener, UInteger> listeners = new HashMap<>();
    private ErrorListener errorListener;
    
    /**
     * Wraps the milo client implementation to simplify process
     * @param serverUrl The url of the server. Something like opc.tcp://localhost:12686/example
     * @param namespace Name of the expected default namespace. Will fail if the namespaces do not match
     */
    public UAClientWrapper(String serverUrl, String namespace) {
        this.serverUrl = serverUrl;
        this.namespace = namespace;
    }

    /**
     * Creates the client
     * 
     * @return a client ready to be run
     * @param serverUrl The url of the server to use
     * @param namespace The default namespace to expect
     * @throws ExecutionException If creation of the client fails
     * @throws InterruptedException If creation of the client fails
     * @throws UAClientException If creation of the client fails
     */
    private OpcUaClient createClient(String serverUrl, String namespace)
            throws InterruptedException, ExecutionException, UAClientException {
        SecurityPolicy securityPolicy = SecurityPolicy.None;

        EndpointDescription[] endpoints = UaTcpStackClient.getEndpoints(serverUrl).get();

        EndpointDescription endpoint = Arrays.stream(endpoints)
                .filter(e -> e.getSecurityPolicyUri().equals(securityPolicy.getSecurityPolicyUri())).findFirst()
                .orElseThrow(() -> new RuntimeException("No desired endpoints returned"));

        OpcUaClientConfig config = OpcUaClientConfig.builder()
                .setApplicationName(LocalizedText.english("OSIP client"))
                .setApplicationUri("urn:edu:kit:pse:osip:client:" + namespace)
                .setEndpoint(endpoint)
                .setIdentityProvider(new AnonymousProvider())
                .setRequestTimeout(Unsigned.uint(CONNECTION_TIMEOUT))
                .setSessionTimeout(Unsigned.uint(CONNECTION_TIMEOUT))
                .build();

        OpcUaClient client = new OpcUaClient(config);

        ServerNode serverNode = client.getAddressSpace().getObjectNode(Identifiers.Server, ServerNode.class).get();
        String[] namespaceArray = serverNode.getNamespaceArray().get();
        for (int i = 0; i < namespaceArray.length; i++) {
            if (namespaceArray[i].equals("urn:edu:kit:pse:osip:namespace:" + namespace)) {
                namespaceIndex = i;
                return client;
            }
        }

        throw new UAClientException("Requested namespace not found");
    }

    /**
     * Sets the listener to be called on error states
     * @param listener The listener
     */
    public void setErrorListener(ErrorListener listener) {
        errorListener = listener;
    }

    /**
     * Connects the client to the server
     * @throws UAClientException If the connection fails
     */
    public void connectClient() throws UAClientException {
        if (client == null) {
            try {
                client = createClient(serverUrl, namespace);
            } catch (InterruptedException | ExecutionException e) {
                throw new UAClientException("Unable to create server: " + e.getMessage());
            }
        }

        try {
            client.connect().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new UAClientException("Unable to start server");
        }

        // To prevent connection timeout if values on server do not change for a long time
        doSubscribe(Identifiers.Server_ServerStatus_CurrentTime, CONNECTION_TIMEOUT / 2,
                (IntReceivedListener) value -> { }, Identifiers.DateTime);

        client.getSubscriptionManager().addSubscriptionListener(new SubscriptionListener() {
            @Override
            public void onPublishFailure(UaException exception) {
                if (errorListener != null) {
                    errorListener.onError(ERROR_DISCONNECT);
                }
            }
        });
        client.addSessionActivityListener(new SessionActivityListener() {
            @Override
            public void onSessionInactive(UaSession session) {
                if (errorListener != null) {
                    errorListener.onError(ERROR_DISCONNECT);
                }
            }
        });

        connected = true;
    }

    /**
     * Disconnects the client from the server
     * @throws UAClientException If the client can not be stopped
     */
    public void disconnectClient() throws UAClientException {
        if (!connected) {
            throw new UAClientException("Not connected");
        }

        Iterator<Entry<ReceivedListener, UInteger>> it = listeners.entrySet().iterator();
        while (it.hasNext()) {
            try {
                doUnsubscribe(it.next().getKey());
            } catch (UAClientException e) {
                System.err.println("Unsubscribing OPC UA Listener failed. Continuing.");
                // We want to disconnect the client. It unsubscribing fails, just ignore it.
            }
            it.remove();
        }

        try {
            client.disconnect().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new UAClientException("Unable to disconnect from server");
        }
    }

    /**
     * Unsubscribes the listener from getting refreshed
     * @param listener The listener of the subscription to cancel
     * @throws UAClientException If unsubscription fails
     */
    public void unsubscribe(ReceivedListener listener) throws UAClientException {
        if (!connected) {
            throw new UAClientException("Not connected");
        }

        if (listeners.containsKey(listener)) {
            doUnsubscribe(listener);
            listeners.remove(listener);
        } else {
            throw new UAClientException("Listener not subscribed.");
        }
    }

    /**
     * Unsubscribes the listener from getting refreshed. Does not remove from subscriptions list
     * @param listener The listener of the subscription to cancel
     * @throws UAClientException If unsubscription fails
     */
    private void doUnsubscribe(ReceivedListener listener) throws UAClientException {
        try {
            client.getSubscriptionManager().deleteSubscription(listeners.get(listener)).get();
        } catch (NumberFormatException | InterruptedException | ExecutionException e) {
            throw new UAClientException("Unsubscribing OPC UA Listener failed.");
        }
    }

    /**
     * Subscribes to a float from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe or read
     * @param interval Fetch interval in ms. Single, immediate read when providing -1.
     * @param listener Callback function that is called when the value was changed
     * @throws UAClientException If subscription fails
     */
    protected void subscribeFloat(String nodeName, int interval, FloatReceivedListener listener)
            throws UAClientException {
        subscribeOrRead(nodeName, interval, listener, Identifiers.Float);
    }

    /**
     * Subscribes to a boolean from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe or read
     * @param interval Fetch interval in ms. Single, immediate read when providing -1.
     * @param listener Callback function that is called when the value was changed
     * @throws UAClientException If subscription fails
     */
    protected void subscribeBoolean(String nodeName, int interval, BooleanReceivedListener listener)
            throws UAClientException {
        subscribeOrRead(nodeName, interval, listener, Identifiers.Boolean);
    }

    /**
     * Subscribes to an int from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe or read
     * @param interval Fetch interval in ms. Single, immediate read when providing -1.
     * @param listener Callback function that is called when the value was changed
     * @throws UAClientException If subscription fails
     */
    protected void subscribeInt(String nodeName, int interval, IntReceivedListener listener)
            throws UAClientException {
        subscribeOrRead(nodeName, interval, listener, Identifiers.Int32);
    }

    /**
     * Subscribes to a value from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe or read
     * @param interval Fetch interval in ms. Single, immediate read when providing -1.
     * @param listener Callback function that is called when the value was changed
     * @param varType The type of the variable to be read
     * @throws UAClientException If subscription fails
     */
    private void subscribeOrRead(String nodeName, int interval, ReceivedListener listener, NodeId varType)
            throws UAClientException {
        if (!connected) {
            throw new UAClientException("Not connected");
        }

        if (listener == null) {
            throw new IllegalArgumentException("Listener must not be null");
        }

        if (interval == SINGLE_READ) {
            doRead(nodeName, listener, varType);
        } else if (interval > 0) {
            if (listeners.containsKey(listener)) {
                unsubscribe(listener); // Allows changing the interval
            }

            doSubscribe(new NodeId(namespaceIndex, nodeName), interval, listener, varType);
        } else {
            throw new IllegalArgumentException("Interval must be > 0 or -1");
        }
    }

    /**
     * Asynchronously reads a value from the server, creating a new thread
     * @param nodeName Name of the value to read
     * @param listener The listener to be called as soon as the value is received
     * @param varType The type of the variable to be read
     */
    private void doRead(String nodeName, ReceivedListener listener, NodeId varType) {
        new Thread(() -> {
            try {
                VariableNode readNode = client.getAddressSpace()
                        .getVariableNode(new NodeId(namespaceIndex, nodeName)).get();
                DataValue value = readNode.readValue().get();
                callReceivedListener(value, listener, varType);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Subscribes to a value from the server.
     * @param nodeId The node to subscribe
     * @param interval Fetch interval in ms. Single, immediate read when providing -1.
     * @param listener Callback function that is called when the value was changed
     * @param varType The type of the variable to be read
     * @throws UAClientException If subscription fails
     */
    private void doSubscribe(NodeId nodeId, int interval, ReceivedListener listener, NodeId varType)
            throws UAClientException {
        try {
            UaSubscription subscription = client.getSubscriptionManager().createSubscription(interval).get();

            ReadValueId readValueId = new ReadValueId(
                nodeId,
                AttributeId.Value.uid(), null, QualifiedName.NULL_VALUE);

            MonitoringParameters parameters = new MonitoringParameters(
                Unsigned.uint(clientHandles.getAndIncrement()), // Unique client handle
                (double) interval,
                null,                  // filter, null means use default
                SUBSCRIPTION_QUEUE_SIZE,
                true            // discard oldest
            );

            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                readValueId, MonitoringMode.Reporting, parameters);

            listeners.put(listener, subscription.getSubscriptionId());

            BiConsumer<UaMonitoredItem, Integer> onItemCreated =
                (item, id) ->
                    item.setValueConsumer((monitoredItem, value) -> {
                        callReceivedListener(value, listener, varType);
                    });

            List<UaMonitoredItem> items = subscription.createMonitoredItems(
                TimestampsToReturn.Both,
                Lists.newArrayList(request),
                onItemCreated
            ).get();

            for (UaMonitoredItem item : items) {
                if (!item.getStatusCode().isGood()) {
                    throw new UAClientException("Failed to create monitored item. Status " + item.getStatusCode());
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new UAClientException("Unable to subscribe");
        }
    }

    /**
     * Calls the correct function of the given receivedListener
     * @param value The value to be sent to the listener
     * @param listener The listener. Listener type must match varType
     * @param varType The type of the variable to send
     */
    private void callReceivedListener(DataValue value, ReceivedListener listener, NodeId varType) {
        boolean dataTypeMatch = value.getValue().getDataType()
                .map(type -> type.equals(varType))
                .orElse(false);

        if (!dataTypeMatch) {
            if (errorListener != null) {
                errorListener.onError(ERROR_DATA_TYPE_MISMATCH);
            }
            listeners.remove(listener);
        } else {
            if (varType == Identifiers.Int32) {
                ((IntReceivedListener) listener).onReceived((int) value.getValue().getValue());
            } else if (varType == Identifiers.Float) {
                ((FloatReceivedListener) listener).onReceived((float) value.getValue().getValue());
            } else if (varType == Identifiers.Boolean) {
                ((BooleanReceivedListener) listener).onReceived((boolean) value.getValue().getValue());
            } else if (varType != Identifiers.DateTime) {
                // Ignore DateTime. Used for the keep alive signal

                if (errorListener != null) {
                    errorListener.onError(ERROR_DATA_TYPE_UNSUPPORTED);
                }
                listeners.remove(listener);
            }
        }
    }

    /**
     * Releases all resources used by milo. It is impossible to start clients afterwards.
     */
    public static void releaseSharedResources() {
        Stack.releaseSharedResources();
    }
}
