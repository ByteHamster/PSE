package edu.kit.pse.osip.core.opcua.server;

import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the server wrapper
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class UAServerWrapperTest {
    private UAServerWrapper server;

    /**
     * Initialize server
     */
    @Before
    public void setup() {
        server = new UAServerWrapper("test-server", 12686) { };
    }

    /**
     * Tests if the server can be started and stopped without crashing
     * @throws Exception If something goes wrong
     */
    @Test
    public void startStopServer() throws Exception {
        server.start();
        server.stop();
    }

    /**
     * Tests if values inside the server can be set
     * @throws Exception If something goes wrong
     */
    @Test
    public void setVariable() throws Exception {
        server.start();
        server.addFolder("testfolder", "My folder");
        server.addVariable("testfolder/testvar", "My variable", Identifiers.Float);
        server.setVariable("testfolder/testvar", new DataValue(new Variant(10)));
        server.stop();
    }

    /**
     * Tests if multiple folders can be added
     * @throws Exception If something goes wrong
     */
    @Test
    public void addMultipleFolders() throws Exception {
        server.start();
        server.addFolder("folder1", "My folder 1");
        server.addFolder("folder1/folder2", "My folder 2");
        server.addVariable("folder1/testvar", "My variable", Identifiers.Float);
        server.addVariable("folder1/folder2/testvar", "My variable", Identifiers.Float);
        server.stop();
    }
}
