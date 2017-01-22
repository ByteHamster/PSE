package edu.kit.pse.osip.core.opcua.server;

import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;

/**
 * Simplifies the interface of Milo. Automatically adds a namespace and provides methods to directly manage the namespace, because multiple namespaces are not needed in our case. Allows to access variables based on their path instead of using the NodeId.
 */
public abstract class UAServerWrapper {
    public UANamespaceWrapper namespace;
    /**
     * Wraps a milo server to simplify handling
     * @param namespaceName The name of the namespace that is automatically generated
     * @param port The port to listen to
     */
    public UAServerWrapper (String namespaceName, int port) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Creates a new server that can be used to publish data over OPC UA
     * 
     * @return a server that can directly be started.
     * @param namespaceName The name of the default namespace
     * @param port The port to listen to
     */
    private final OpcUaServer createServer (String namespaceName, int port) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Starts the server
     * @throws InterruptedException, ExecutionException, ConnectException If Milo has problems connecting to the remote machine
     */
    public final void start () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Stops the server. Can not be restarted afterwards.
     */
    public final void stop () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Adds a folder to the default namespace
     * @param path Slash seperated path of the folder
     * @param displayName Name of the folder that is displayed to users
     */
    protected final void addFolder (String path, String displayName) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Adds a variable to the default namespace
     * @param path Slash seperated path of the folder
     * @param displayName Name of the variable that is displayed to users
     */
    protected final void addVariable (String path, String displayName) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the value of a variable in the default namespace
     * @param path Slash seperated path of the folder
     * @param value Value of the variable
     */
    protected final void setVariable (String path, DataValue value) {
        throw new RuntimeException("Not implemented!");
    }
}
