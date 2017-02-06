package edu.kit.pse.osip.core.opcua.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.AnonymousProvider;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscriptionManager.SubscriptionListener;
import org.eclipse.milo.opcua.sdk.client.model.nodes.objects.ServerNode;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

import com.google.common.collect.Lists;

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
    private OpcUaClient client = null;
    private String serverUrl;
    private String namespace;
    private int namespaceIndex = -1;
    private final AtomicLong clientHandles = new AtomicLong(1L);
    private boolean connected = false;
    private HashMap<ReceivedListener, Long> listeners = new HashMap<>();
    
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
                .setApplicationUri("urn:edu:kit:pse:osip:client:" + namespace).setEndpoint(endpoint)
                .setIdentityProvider(new AnonymousProvider()).setRequestTimeout(Unsigned.uint(5000))
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
     * Connects the client to the server
     * @throws UAClientException If the connection fails
     */
    public final void connectClient() throws UAClientException {
        if (client == null) {
            try {
                client = createClient(serverUrl, namespace);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new UAClientException("Unable to create server");
            }
        }

        try {
            client.connect().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new UAClientException("Unable to start server");
        }

        client.getSubscriptionManager().addSubscriptionListener(new SubscriptionListener() {
            @Override
            public void onPublishFailure(UaException exception) {
                Iterator<Entry<ReceivedListener, Long>> it = listeners.entrySet().iterator();
                while (it.hasNext()) {
                    it.next().getKey().onError();
                    it.remove();
                }
            }
        });

        connected = true;
    }

    /**
     * Disconnects the client from the server
     * @throws UAClientException If the client can not be stopped
     */
    public final void disconnectClient() throws UAClientException {
        if (!connected) {
            throw new UAClientException("Not connected");
        }

        Iterator<Entry<ReceivedListener, Long>> it = listeners.entrySet().iterator();
        while (it.hasNext()) {
            try {
                doUnsubscribe(it.next().getKey());
            } catch (UAClientException e) {
                e.printStackTrace();
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
    public final void unsubscribe(ReceivedListener listener) throws UAClientException {
        if (!connected) {
            throw new UAClientException("Not connected");
        }

        if (listeners.containsKey(listener)) {
            doUnsubscribe(listener);
            listeners.remove(listener);
        }
    }

    /**
     * Unsubscribes the listener from getting refreshed. Does not remove from subscriptions list
     * @param listener The listener of the subscription to cancel
     * @throws UAClientException If unsubscription fails
     */
    private void doUnsubscribe(ReceivedListener listener) throws UAClientException {
        try {
            client.getSubscriptionManager().deleteSubscription(Unsigned.uint(listeners.get(listener))).get();
        } catch (NumberFormatException | InterruptedException | ExecutionException e) {
            // Well, this subscription is not registered on the server.
            // We don't need to remove it.
        }
    }

    /**
     * Subscribes to a float from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     * @throws UAClientException If subscription fails
     */
    protected final void subscribeFloat(String nodeName, int interval, FloatReceivedListener listener)
            throws UAClientException {
        subscribe(nodeName, interval, listener, Identifiers.Float);
    }

    /**
     * Subscribes to a boolean from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     * @throws UAClientException If subscription fails
     */
    protected final void subscribeBoolean(String nodeName, int interval, BooleanReceivedListener listener)
            throws UAClientException {
        subscribe(nodeName, interval, listener, Identifiers.Boolean);
    }

    /**
     * Subscribes to an int from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     * @throws UAClientException If subscription fails
     */
    protected final void subscribeInt(String nodeName, int interval, IntReceivedListener listener)
            throws UAClientException {
        subscribe(nodeName, interval, listener, Identifiers.Int32);
    }

    /**
     * Subscribes to a value from the server.
     * Subscribe again with same listener and other interval to change interval.
     * @param nodeName The path of the node to subscribe
     * @param interval Fetch interval in ms
     * @param listener Callback function that is called when the value was changed
     * @throws UAClientException If subscription fails
     */
    private void subscribe(String nodeName, int interval, ReceivedListener listener, NodeId varType)
            throws UAClientException {
        if (!connected) {
            throw new UAClientException("Not connected");
        }
        if (listener == null) {
            throw new IllegalArgumentException("Listener must not be null");
        }

        try {
            UaSubscription subscription = client.getSubscriptionManager().createSubscription(interval).get();

            ReadValueId readValueId = new ReadValueId(
                new NodeId(namespaceIndex, nodeName),
                AttributeId.Value.uid(), null, QualifiedName.NULL_VALUE);

            long clientHandle = clientHandles.getAndIncrement();
            MonitoringParameters parameters = new MonitoringParameters(
                Unsigned.uint(clientHandle), // Unique client handle
                (double) interval,
                null,             // filter, null means use default
                Unsigned.uint(2), // queue size
                true              // discard oldest
            );

            MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                readValueId, MonitoringMode.Reporting, parameters);
            

            listeners.put(listener, clientHandle);

            // when creating items in MonitoringMode.Reporting this callback is where each item needs to have its
            // value/event consumer hooked up. The alternative is to create the item in sampling mode, hook up the
            // consumer after the creation call completes, and then change the mode for all items to reporting.
            BiConsumer<UaMonitoredItem, Integer> onItemCreated =
                (item, id) ->
                    item.setValueConsumer((monitoredItem, value) -> {
                        boolean dataTypeMatch = value.getValue().getDataType()
                                .map(type -> type.equals(varType))
                                .orElse(false);

                        if (!dataTypeMatch) {
                            System.err.println("Data type missmatch");
                            listener.onError();
                            listeners.remove(listener);
                        } else {
                            if (varType == Identifiers.Int32) {
                                ((IntReceivedListener) listener).onReceived((int) value.getValue().getValue());
                            } else if (varType == Identifiers.Float) {
                                ((FloatReceivedListener) listener).onReceived((float) value.getValue().getValue());
                            } else if (varType == Identifiers.Boolean) {
                                ((BooleanReceivedListener) listener).onReceived((boolean) value.getValue().getValue());
                            } else {
                                System.err.println("Unknown data type");
                                listener.onError();
                                listeners.remove(listener);
                            }
                        }
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
}
