package edu.kit.pse.osip.core.opcua.server;

import java.util.EnumSet;
import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.sdk.server.OpcUaServer;
import org.eclipse.milo.opcua.sdk.server.api.config.OpcUaServerConfig;
import org.eclipse.milo.opcua.sdk.server.identity.AnonymousIdentityValidator;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.application.DefaultCertificateManager;
import org.eclipse.milo.opcua.stack.core.application.DefaultCertificateValidator;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.DateTime;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.structured.BuildInfo;
import org.eclipse.milo.opcua.stack.core.util.CryptoRestrictions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * Simplifies the interface of Milo. Automatically adds a namespace and provides methods
 * to directly manage the namespace, because multiple namespaces are not needed in our case.
 * Allows to access variables based on their path instead of using the NodeId.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public abstract class UAServerWrapper {
    private UANamespaceWrapper namespace;
    private final OpcUaServer server;

    /**
     * Wraps a milo server to simplify handling
     * @param namespaceName The name of the namespace that is automatically generated
     * @param port The port to listen to
     */
    public UAServerWrapper(String namespaceName, int port) {
        server = createServer(namespaceName, port);

        String namespaceUri = "urn:edu:kit:pse:osip:namespace:" + namespaceName;
        server.getNamespaceManager().registerAndAdd(
            namespaceUri,
            idx -> {
                namespace = new UANamespaceWrapper(server, idx);
                namespace.namespaceUri = namespaceUri;
                return namespace;
            });
    }

    /**
     * Creates a new server that can be used to publish data over OPC UA
     * 
     * @return a server that can directly be started.
     * @param namespaceName The name of the default namespace
     * @param port The port to listen to
     */
    private OpcUaServer createServer(String namespaceName, int port) {
        String productUri = "urn:edu:kit:pse:osip:product:" + namespaceName;
        CryptoRestrictions.remove();
        OpcUaServerConfig serverConfig = OpcUaServerConfig.builder()
            .setApplicationUri("urn:edu:kit:pse:osip:application:" + namespaceName)
            .setApplicationName(LocalizedText.english("OSIP Tank server"))
            .setBindAddresses(Lists.newArrayList("0.0.0.0"))
            .setBindPort(12686)
            .setBuildInfo(
                new BuildInfo(
                    productUri,
                    "KinkyDonut",
                    "OSIP server",
                    OpcUaServer.SDK_VERSION,
                    "1.0", DateTime.now()))
            .setCertificateManager(new DefaultCertificateManager())
            .setCertificateValidator(new DefaultCertificateValidator(Files.createTempDir()))
            .setIdentityValidator(AnonymousIdentityValidator.INSTANCE)
            .setProductUri(productUri)
            .setServerName("OSIP server")
            .setSecurityPolicies(EnumSet.of(SecurityPolicy.None))
            .setUserTokenPolicies(
                ImmutableList.of(
                    OpcUaServerConfig.USER_TOKEN_POLICY_ANONYMOUS,
                    OpcUaServerConfig.USER_TOKEN_POLICY_USERNAME))
            .build();

        return new OpcUaServer(serverConfig);
    }

    /**
     * Starts the server
     * @throws ExecutionException If Milo has problems connecting to the remote machine
     * @throws InterruptedException If Milo has problems connecting to the remote machine
     */
    public final void start() throws InterruptedException, ExecutionException {
        server.startup().get();
    }

    /**
     * Stops the server. Can not be restarted afterwards.
     * @throws ExecutionException If Milo has problems stopping the server
     * @throws InterruptedException If Milo has problems stopping the server
     */
    public final void stop() throws InterruptedException, ExecutionException {
        server.shutdown().get();
    }

    /**
     * Adds a folder to the default namespace
     * @param path Slash separated path of the folder
     * @param displayName Name of the folder that is displayed to users
     * @throws UaException If folder can not be added
     */
    protected final void addFolder(String path, String displayName) throws UaException {
        namespace.addFolder(path, displayName);
    }

    /**
     * Adds a variable to the default namespace
     * @param path Slash separated path of the folder
     * @param displayName Name of the variable that is displayed to users
     * @param type The variable type, for example Identifiers.Float
     */
    protected final void addVariable(String path, String displayName, NodeId type) {
        namespace.addVariable(path, displayName, type);
    }

    /**
     * Sets the value of a variable in the default namespace
     * @param path Slash separated path of the folder
     * @param value Value of the variable
     */
    protected final void setVariable(String path, DataValue value) {
        namespace.updateValue(path, value);
    }
}
