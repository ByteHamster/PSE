package edu.kit.pse.osip.core.opcua.server;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.milo.opcua.sdk.core.AccessLevel;
import org.eclipse.milo.opcua.sdk.core.Reference;
import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.api.AccessContext;
import org.eclipse.milo.opcua.sdk.server.api.DataItem;
import org.eclipse.milo.opcua.sdk.server.api.MonitoredItem;
import org.eclipse.milo.opcua.sdk.server.api.Namespace;
import org.eclipse.milo.opcua.sdk.server.nodes.AttributeContext;
import org.eclipse.milo.opcua.sdk.server.nodes.ServerNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaFolderNode;
import org.eclipse.milo.opcua.sdk.server.nodes.UaVariableNode;
import org.eclipse.milo.opcua.sdk.server.util.SubscriptionModel;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UShort;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.eclipse.milo.opcua.stack.core.types.structured.WriteValue;
import org.eclipse.milo.opcua.stack.core.util.FutureUtils;

import com.google.common.collect.Lists;

/**
 * Implements a namespace needed by Milo.
 * Contains methods to add folders and variables from outside to make it easier
 * to create a server (by using inheritance). Methods are called by UAServerWrapper on its default namespace.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class UANamespaceWrapper implements Namespace {
    private final SubscriptionModel subscriptionModel;
    private final UShort namespaceIndex;
    private final OpcUaServer server;
    private final HashMap<String, UaVariableNode> variableNodes = new HashMap<>();
    private final HashMap<String, UaFolderNode> folderNodes = new HashMap<>();

    protected String namespaceUri;

    /**
     * Creates a new namespace to simply manage a milo namespace
     * @param server The server that this namespace belongs to
     * @param namespaceIndex The index of this namespace
     */
    protected UANamespaceWrapper (OpcUaServer server, UShort namespaceIndex) {
        this.server = server;
        this.namespaceIndex = namespaceIndex;
        this.subscriptionModel = new SubscriptionModel(server, this);
    }

    /**
     * Add a folder to the server
     * @param path The path of the folder to add
     * @param displayName Name that is displayed to the user
     * @throws UaException If folder can not be added
     */
    protected final void addFolder (String path, String displayName) throws UaException {
        NodeId folderNodeId = new NodeId(namespaceIndex, path);
        UaFolderNode folderNode = new UaFolderNode(server.getNodeMap(), folderNodeId,
                new QualifiedName(namespaceIndex, path), LocalizedText.english(displayName));
        server.getNodeMap().addNode(folderNode);        
        server.getUaNamespace().addReference(Identifiers.ObjectsFolder, Identifiers.Organizes, true,
                folderNodeId.expanded(), NodeClass.Object);
        folderNodes.put(path, folderNode);
    }

    /**
     * Add a variable to the server
     * @param path The path of the variable to add
     * @param displayName Name that is displayed to the user
     * @param type The variable type, for example Identifiers.Float
     */
    protected final void addVariable (String path, String displayName, NodeId type) {
        UaVariableNode newNode = new UaVariableNode.UaVariableNodeBuilder(server.getNodeMap())
            .setNodeId(new NodeId(namespaceIndex, path))
            .setAccessLevel(Unsigned.ubyte(AccessLevel.getMask(AccessLevel.READ_WRITE)))
            .setBrowseName(new QualifiedName(namespaceIndex, displayName))
            .setDisplayName(LocalizedText.english(displayName)).setDataType(type)
            .setTypeDefinition(Identifiers.BaseDataVariableType).build();
        server.getNodeMap().addNode(newNode);
        variableNodes.put(path, newNode);

        String folderPath = path.substring(0, path.lastIndexOf("/"));
        folderNodes.get(folderPath).addOrganizes(newNode);
    }

    /**
     * Update the value of a variable
     * @param path The path of the variable to update
     * @param value Name that is displayed to the user
     */
    protected final void updateValue (String path, DataValue value) {
        variableNodes.get(path).setValue(value);
    }

    /**
     * Needed by milo: Returns the namespace index of this namespace
     * 
     * @return The index of this namespace
     */
    public final UShort getNamespaceIndex () {
        return namespaceIndex;
    }

    /**
     * Needed by milo. Allows browsing the nodes inside this namespace
     * 
     * @return a list of references tto nodes on the server
     * @param context The context to write the values back
     * @param nodeId The id of the element to bowse
     */
    public final CompletableFuture<List<Reference>> browse (AccessContext context, NodeId nodeId) {
        ServerNode node = server.getNodeMap().get(nodeId);

        if (node != null) {
            return CompletableFuture.completedFuture(node.getReferences());
        } else {
            return FutureUtils.failedFuture(new UaException(StatusCodes.Bad_NodeIdUnknown));
        }
    }

    /**
     * Needed by milo. Allows reading a value
     * @param context The context to write the value back
     * @param maxAge Maximum age of the values to return
     * @param timestamps The value from which time to return
     * @param readValueIds The ids of the values that should be read
     */
    public final void read (ReadContext context, Double maxAge, TimestampsToReturn timestamps, List<ReadValueId> readValueIds) {
        List<DataValue> results = Lists.newArrayListWithCapacity(readValueIds.size());

        for (ReadValueId readValueId : readValueIds) {
            ServerNode node = server.getNodeMap().get(readValueId.getNodeId());
            if (node != null) {
                DataValue value = node.readAttribute(new AttributeContext(context), readValueId.getAttributeId());
                results.add(value);
            } else {
                results.add(new DataValue(StatusCodes.Bad_NodeIdUnknown));
            }
        }

        context.complete(results);
    }

    /**
     * Needed by milo. Writes values to the current server
     * @param context The calling context
     * @param values The values to write
     */
    public final void write (WriteContext context, List<WriteValue> values) {
        List<StatusCode> results = Lists.newArrayListWithCapacity(values.size());

        for (WriteValue writeValue : values) {
            ServerNode node = server.getNodeMap().get(writeValue.getNodeId());
            if (node != null) {
                try {
                    node.writeAttribute(new AttributeContext(context), writeValue.getAttributeId(),
                            writeValue.getValue(), writeValue.getIndexRange());
                    results.add(StatusCode.GOOD);
                } catch (UaException e) {
                    e.printStackTrace();
                    results.add(e.getStatusCode());
                }
            } else {
                results.add(new StatusCode(StatusCodes.Bad_NodeIdUnknown));
            }
        }

        context.complete(results);
    }

    /**
     * Needed by milo. Gets called when the data inside the server are created
     * @param dataItems The created items
     */
    public final void onDataItemsCreated (List<DataItem> dataItems) {
        subscriptionModel.onDataItemsCreated(dataItems);
    }

    /**
     * Needed by milo. Called when the data inside the server is modified
     * @param dataItems The modified data items
     */
    public final void onDataItemsModified (List<DataItem> dataItems) {
        subscriptionModel.onDataItemsModified(dataItems);
    }

    /**
     * Needed by milo. Called when items inside the server are deleted
     * @param dataItems The deleted items
     */
    public final void onDataItemsDeleted (List<DataItem> dataItems) {
        subscriptionModel.onDataItemsDeleted(dataItems);
    }

    /**
     * Needed by milo. Called when the method changes how the client gets its data
     * @param monitoredItems The changed items
     */
    public final void onMonitoringModeChanged (List<MonitoredItem> monitoredItems) {
        subscriptionModel.onMonitoringModeChanged(monitoredItems);
    }

    /**
     * Returns the identifier for this namespace
     * @return the identifying string
     */
    public final String getNamespaceUri () {
        return namespaceUri;
    }
}
