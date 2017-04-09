package edu.kit.pse.osip.core.opcua;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.kit.pse.osip.core.opcua.client.IntReceivedListener;
import edu.kit.pse.osip.core.opcua.client.UAClientException;
import edu.kit.pse.osip.core.opcua.client.UAClientWrapper;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Tests OPC UA server and client wrapper.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ServerClientTest {
    /**
     * The server wrapper instance used for testing. 
     */
    private TestUaServerWrapper server;
    /**
     * The client wrapper instance used for testing.
     */
    private TestUaClientWrapper client;
    /**
     * The used port for testing.
     */
    private static final int PORT = 12686;

    /**
     * Sets up test environment.
     * 
     * @throws Exception If setup fails.
     */
    @Before
    public void startup() throws Exception {
        server = new TestUaServerWrapper("test", PORT);
        client = new TestUaClientWrapper("opc.tcp://localhost:" + PORT + "/osip", "test") { };

        server.start();
        client.connectClient();
    }

    /**
     * Closes server and client.
     * 
     * @throws Exception If something goes wrong.
     */
    @After
    public void shutdown() throws Exception {
        try {
            client.disconnectClient();
        } catch (UAClientException e) {
            // Not connected
        }

        server.stop();
    }

    /**
     * Tests if connecting to wrong server gives connection errors.
     * 
     * @throws UAClientException Expected.
     */
    @Test(timeout = 20000, expected = UAClientException.class)
    public void testWrongNamespace() throws UAClientException {
        client.disconnectClient();
        client = new TestUaClientWrapper("opc.tcp://localhost:" + PORT + "/osip", "not-test") { };
        client.connectClient();
    }

    /**
     * Tests if getting values form not running client gives connection errors.
     * 
     * @throws UAClientException Expected.
     */
    @Test(timeout = 20000, expected = UAClientException.class)
    public void testNotRunning() throws UAClientException {
        client.disconnectClient();
        client = new TestUaClientWrapper("opc.tcp://localhost:" + PORT + "/osip", "not-test") { };
        client.subscribeIntTest("testFolder/testVar1", 1000, (value) -> { });
    }

    /**
     * Tests if passing a null listener throws the right exception.
     * 
     * @throws UAClientException Expected.
     */
    @Test(timeout = 20000, expected = IllegalArgumentException.class)
    public void testNullListener() throws UAClientException {
        client.subscribeIntTest("testFolder/testVar1", 1000, null);
    }

    /**
     * Tests if client can subscribe to values on the server.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test(timeout = 20000)
    public void testSubscribeValue() throws Exception  {
        CompletableFuture<Integer> received = new CompletableFuture<>();

        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        client.subscribeIntTest("testFolder/testVar", 1000, received::complete);
        assertEquals(Integer.valueOf(42), received.get());
    }

    /**
     * Tests if client can read values from the server.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test(timeout = 20000)
    public void testReadValue() throws Exception  {
        CompletableFuture<Integer> received = new CompletableFuture<>();

        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        client.subscribeIntTest("testFolder/testVar", UAClientWrapper.SINGLE_READ, received::complete);
        assertEquals(Integer.valueOf(42), received.get());
    }

    /**
     * Tests if negative subscription intervals lead to an IllegalArgumentException.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSubscribeNegativeInterval() throws Exception  {
        CompletableFuture<Integer> received = new CompletableFuture<>();
        client.subscribeIntTest("testFolder/testVar", -15, received::complete);
    }

    /**
     * Tests if multiple servers can be handled.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test(timeout = 20000)
    public void testMultipleServers() throws Exception  {
        TestUaServerWrapper server2 = new TestUaServerWrapper("test", PORT + 1);
        TestUaClientWrapper client2 = new TestUaClientWrapper("opc.tcp://localhost:"
                + (PORT + 1) + "/osip", "test") { };
        server2.start();
        client2.connectClient();

        CompletableFuture<Integer> received1 = new CompletableFuture<>();
        CompletableFuture<Integer> received2 = new CompletableFuture<>();

        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        server2.addFolderTest("test2Folder", "Test folder");
        server2.addVariableTest("test2Folder/test2Var", "Variable", Identifiers.Int32);
        server2.setVariableTest("test2Folder/test2Var", new DataValue(new Variant(43)));

        client.subscribeIntTest("testFolder/testVar", 1000, received1::complete);
        client2.subscribeIntTest("test2Folder/test2Var", 1000, received2::complete);

        assertEquals(Integer.valueOf(42), received1.get());
        assertEquals(Integer.valueOf(43), received2.get());

        client2.disconnectClient();
        server2.stop();
    }

    /**
     * Tests if client can receive multiple different values from the server.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test(timeout = 20000)
    public void testReceiveDifferentValues() throws Exception  {
        CompletableFuture<Integer> received1 = new CompletableFuture<>();
        CompletableFuture<Float>   received2 = new CompletableFuture<>();
        CompletableFuture<Boolean> received3 = new CompletableFuture<>();

        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar1", "Variable 1", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar1", new DataValue(new Variant(10)));
        server.addVariableTest("testFolder/testVar2", "Variable 2", Identifiers.Float);
        server.setVariableTest("testFolder/testVar2", new DataValue(new Variant(25.3f)));
        server.addVariableTest("testFolder/testVar3", "Variable 3", Identifiers.Boolean);
        server.setVariableTest("testFolder/testVar3", new DataValue(new Variant(true)));

        client.subscribeIntTest("testFolder/testVar1", 1000, received1::complete);
        client.subscribeFloatTest("testFolder/testVar2", 1000, received2::complete);
        client.subscribeBooleanTest("testFolder/testVar3", 1000, received3::complete);
        assertEquals(Integer.valueOf(10), received1.get());
        assertEquals(Float.valueOf(25.3f), received2.get());
        assertTrue(received3.get());
    }

    /**
     * Tests if client notices stopped server.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test(timeout = 20000)
    public void testConnectionLost() throws Exception  {
        CompletableFuture<Integer> received = new CompletableFuture<>();
        CompletableFuture<Integer> receivedErr = new CompletableFuture<>();

        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        client.setErrorListener(receivedErr::complete);
        client.subscribeIntTest("testFolder/testVar", 1000, received::complete);
        assertEquals(new Integer(42), received.get());
        server.stop();
        assertEquals(TestUaClientWrapper.ERROR_DISCONNECT, receivedErr.get().intValue());
    }

    /**
     * Tests if client removes subscription.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test(timeout = 20000)
    public void testUnsubscribe() throws Exception  {
        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        IntReceivedListener listener = (value) -> { };

        client.subscribeIntTest("testFolder/testVar", 1000, listener);
        client.unsubscribe(listener);
    }

    /**
     * Tests if a long connection without changes generates a timeout.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test(timeout = TestUaClientWrapper.CONNECTION_TIMEOUT_TEST * 3)
    public void testLongConnection() throws Exception  {
        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        AtomicBoolean errorOccurred = new AtomicBoolean(false);
        client.setErrorListener(code -> errorOccurred.set(true));
        client.subscribeIntTest("testFolder/testVar", 1000, (value) -> { });

        Thread.sleep(TestUaClientWrapper.CONNECTION_TIMEOUT_TEST * 2);
        assertFalse("Long connection is not kept alive", errorOccurred.get());
    }
}
