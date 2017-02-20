package edu.kit.pse.osip.core.opcua;

import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import edu.kit.pse.osip.core.opcua.server.UAServerWrapper;

/**
 * For test purposes, makes protected methods public
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TestUaServerWrapper extends UAServerWrapper {
    /**
     * Allows public access to UAServerWrapper
     * @param namespaceName Same as for addVariable
     * @param port Same as for addVariable
     */
    public TestUaServerWrapper(String namespaceName, int port) {
        super(namespaceName, port);
    }

    /**
     * Allows public access to addVariable
     * @param path Same as for addVariable
     * @param displayName Same as for addVariable
     * @param type Same as for addVariable
     * @throws UaException If the variable can not be added
     */
    public void addVariableTest(String path, String displayName, NodeId type) throws UaException {
        this.addVariable(path, displayName, type);
    }

    /**
     * Allows public access to setVariable
     * @param path Same as for setVariable
     * @param value Same as for setVariable
     */
    public void setVariableTest(String path, DataValue value) {
        this.setVariable(path, value);
    }

    /**
     * Allows public access to addFolder
     * @param path Same as for addFolder
     * @param displayName Same as for addFolder
     * @throws UaException If the folder can not be added
     */
    public void addFolderTest(String path, String displayName) throws UaException {
        this.addFolder(path, displayName);
    }
}
