package edu.kit.pse.osip.core.opcua.server;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.milo.opcua.sdk.core.Reference;
import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.api.AccessContext;
import org.eclipse.milo.opcua.sdk.server.api.DataItem;
import org.eclipse.milo.opcua.sdk.server.api.MonitoredItem;
import org.eclipse.milo.opcua.sdk.server.util.SubscriptionModel;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.eclipse.milo.opcua.stack.core.types.structured.WriteValue;

/**
 * Implements a namespace needed by Milo. Contains methods to add folders and variables from outside to make it easier to create a server (by using inheritance). Methods are called by UAServerWrapper on its default namespace.
 */
public class UANamespaceWrapper implements org.eclipse.milo.opcua.sdk.server.api.Namespace {
    private SubscriptionModel subscriptionModel;
    /**
     * The opc ua server to use
     */
    private OpcUaServer server;
    /**
     * Creates a new namespace to simply manage a milo namespace
     * @param server The server that this namespace belongs to
     * @param namespaceIndex The index of this namespace
     */
    protected UANamespaceWrapper (OpcUaServer server, UShort namespaceIndex) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Add a folder to the server
     * @param path The path of the folder to add
     * @param displayName Name that is displayed to the user
     */
    protected final void addFolder (String path, String displayName) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Add a variable to the server
     * @param path The path of the variable to add
     * @param displayName Name that is displayed to the user
     */
    protected final void addVariable (String path, String displayName) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Update the value of a variable
     * @param path The path of the variable to update
     * @param value Name that is displayed to the user
     */
    protected final void updateValue (String path, DataValue value) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Needed by milo: Returns the namespace index of this namespace
     * 
     * @return The index of this namespace
     */
    public final UShort getNamespaceIndex () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Needed by milo. Allows browsing the nodes inside this namespace
     * 
     * @return a list of references tto nodes on the server
     * @param context The context to write the values back
     * @param nodeId The id of the element to bowse
     */
    public final CompletableFuture<List<Reference>> browse (AccessContext context, NodeId nodeId) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Needed by milo. Allows reading a value
     * @param context The context to write the value back
     * @param maxAge Maximum age of the values to return
     * @param timestamps The value from which time to return
     * @param readValueIds The ids of the values that should be read
     */
    public final void read (ReadContext context, Double maxAge, TimestampsToReturn timestamps, List<ReadValueId> readValueIds) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Needed by milo. Writes values to the current server
     * @param context The calling context
     * @param values The values to write
     */
    public final void write (WriteContext context, List<WriteValue> values) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Needed by milo. Gets called when the data inside the server are created
     * @param dataItems The created items
     */
    public final void onDataItemsCreated (List<DataItem> dataItems) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Needed by milo. Called when the data inside the server is modified
     * @param dataItems The modified data items
     */
    public final void onDataItemsModified (List<DataItem> dataItems) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Needed by milo. Called when items inside the server are deleted
     * @param dataItems The deleted items
     */
    public final void onDataItemsDeleted (List<DataItem> dataItems) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Needed by milo. Called when the method changes how the client gets its data
     * @param monitoredItems The changed items
     */
    public final void onMonitoringModeChanged (List<MonitoredItem> monitoredItems) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the identifier for this namespace
     * @return the identifying string
     */
    public final String getNamespaceUri () {
        throw new RuntimeException("Not implemented!");
    }
}
