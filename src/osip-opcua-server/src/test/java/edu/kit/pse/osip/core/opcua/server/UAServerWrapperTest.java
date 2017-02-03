package edu.kit.pse.osip.core.opcua.server;

import java.util.concurrent.ExecutionException;

import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
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
        server = new UAServerWrapper("test-server", 8080) { };
    }

    /**
     * Tests if the server can be started and stopped without crashing
     * @throws ExecutionException If something goes wrong
     * @throws InterruptedException If something goes wrong
     */
    @Test
    public void startStopServer() throws InterruptedException, ExecutionException {
        server.start();
        server.stop();
    }

    /**
     * Tests if values inside the server can be set
     * @throws ExecutionException If something goes wrong
     * @throws InterruptedException If something goes wrong
     * @throws UaException If something goes wrong
     */
    @Test
    public void setVariable() throws InterruptedException, ExecutionException, UaException {
        server.start();
        server.addFolder("testfolder", "My folder");
        server.addVariable("testfolder/testvar", "My variable", Identifiers.Float);
        server.setVariable("testfolder/testvar", new DataValue(new Variant(10)));
        server.stop();
    }
}
