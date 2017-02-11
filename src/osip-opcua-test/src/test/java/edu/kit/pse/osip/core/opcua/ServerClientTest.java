package edu.kit.pse.osip.core.opcua;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CompletableFuture;

import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.Stack;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.osip.core.opcua.client.IntReceivedListener;
import edu.kit.pse.osip.core.opcua.client.UAClientException;

/**
 * Tests opc ua server and client wrapper
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ServerClientTest {
    private TestUaServerWrapper server;
    private TestUaClientWrapper client;
    private static final int PORT = 12686;

    /**
     * Sets up test environment
     * @throws Exception If test fails
     */
    @Before
    public void startup() throws Exception {
        server = new TestUaServerWrapper("test", PORT);
        client = new TestUaClientWrapper("opc.tcp://localhost:" + PORT + "/osip", "test") { };

        server.start();
        client.connectClient();
    }

    /**
     * Close server and client
     * @throws Exception If test fails
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
     * Release resources
     */
    @AfterClass
    public static void release() {
        Stack.releaseSharedResources();
    }

    /**
     * Tests if connecting to wrong server gives connection errors
     * @throws UAClientException Expected
     */
    @Test(timeout = 20000, expected = UAClientException.class)
    public void testWrongNamespace() throws UAClientException {
        client.disconnectClient();
        client = new TestUaClientWrapper("opc.tcp://localhost:" + PORT + "/osip", "not-test") { };
        client.connectClient();
    }

    /**
     * Tests if getting values form not running client gives connection errors
     * @throws UAClientException Expected
     */
    @Test(timeout = 20000, expected = UAClientException.class)
    public void testNotRunning() throws UAClientException {
        client.disconnectClient();
        client = new TestUaClientWrapper("opc.tcp://localhost:" + PORT + "/osip", "not-test") { };
        client.subscribeIntTest("testFolder/testVar1", 1000, (value) -> { });
    }

    /**
     * Tests if passing a null listener throws the right exception
     * @throws UAClientException Expected
     */
    @Test(timeout = 20000, expected = IllegalArgumentException.class)
    public void testNullListener() throws UAClientException {
        client.subscribeIntTest("testFolder/testVar1", 1000, null);
    }

    /**
     * Tests if client can receive values from the server
     * @throws Exception If something goes wrong
     */
    @Test(timeout = 20000)
    public void testReceiveValue() throws Exception  {
        CompletableFuture<Integer> received = new CompletableFuture<>();

        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        IntReceivedListener listener = (value) -> received.complete(value);

        client.subscribeIntTest("testFolder/testVar", 1000, listener);
        assertEquals(Integer.valueOf(42), received.get());
    }

    /**
     * Tests if multiple servers can be handled
     * @throws Exception If something goes wrong
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

        client.subscribeIntTest("testFolder/testVar", 1000, (value) -> received1.complete(value));
        client2.subscribeIntTest("test2Folder/test2Var", 1000, (value) -> received2.complete(value));

        assertEquals(Integer.valueOf(42), received1.get());
        assertEquals(Integer.valueOf(43), received2.get());

        client2.disconnectClient();
        server2.stop();
    }

    /**
     * Tests if client can receive multiple different values from the server
     * @throws Exception If something goes wrong
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

        client.subscribeIntTest("testFolder/testVar1", 1000, (value) -> received1.complete(value));
        client.subscribeFloatTest("testFolder/testVar2", 1000, (value) -> received2.complete(value));
        client.subscribeBooleanTest("testFolder/testVar3", 1000, (value) -> received3.complete(value));
        assertEquals(Integer.valueOf(10), received1.get());
        assertEquals(Float.valueOf(25.3f), received2.get());
        assertEquals(Boolean.valueOf(true), received3.get());
    }

    /**
     * Tests if client notices stopped server
     * @throws Exception If something goes wrong
     */
    @Test(timeout = 60000)
    public void testConnectionLost() throws Exception  {
        CompletableFuture<Integer> received = new CompletableFuture<>();
        CompletableFuture<Integer> receivedErr = new CompletableFuture<>();

        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        IntReceivedListener listener = (value) -> received.complete(value);

        client.setErrorListener((code) -> receivedErr.complete(code));
        client.subscribeIntTest("testFolder/testVar", 1000, listener);
        assertEquals(new Integer(42), received.get());
        server.stop();
        assertEquals(TestUaClientWrapper.ERROR_DISCONNECT, receivedErr.get().intValue());
    }

    /**
     * Tests if client removes subscription
     * @throws Exception If something goes wrong
     */
    @Test(timeout = 5000)
    public void testUnsubscribe() throws Exception  {
        server.addFolderTest("testFolder", "Test folder");
        server.addVariableTest("testFolder/testVar", "Variable", Identifiers.Int32);
        server.setVariableTest("testFolder/testVar", new DataValue(new Variant(42)));

        IntReceivedListener listener = (value) -> { };

        client.subscribeIntTest("testFolder/testVar", 1000, listener);
        client.unsubscribe(listener);
    }
}
