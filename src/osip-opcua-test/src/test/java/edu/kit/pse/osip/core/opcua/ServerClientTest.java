package edu.kit.pse.osip.core.opcua;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.osip.core.opcua.client.UAClientWrapper;

/**
 * Tests opc ua server and client wrapper
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ServerClientTest {
    TestUaServerWrapper server;
    UAClientWrapper client;
    
    /**
     * Sets up test environment
     */
    @Before
    public void setup() {
        server = new TestUaServerWrapper("test", 12686);
        client = new UAClientWrapper("opc.tcp://localhost:12686/osip", "test") { };
    }
    /**
     * Tests if client can connect to the server
     * @throws Exception If the test fails
     */
    @Test
    public void testConnect() throws Exception {
        server.start();
        client.connectClient();
    }
}
