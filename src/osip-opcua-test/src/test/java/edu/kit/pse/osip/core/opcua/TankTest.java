package edu.kit.pse.osip.core.opcua;

import edu.kit.pse.osip.core.io.networking.RemoteMachine;
import edu.kit.pse.osip.monitoring.controller.TankClient;
import edu.kit.pse.osip.simulation.controller.TankServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the tank servers and clients.
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TankTest {
    /**
     * The used port for testing.
     */
    private static final int PORT = 12686;
    /**
     * The used interval for testing.
     */
    private static final int INTERVAL = 500;
    /**
     * The TankServer instance used for testing.
     */
    private TankServer server;
    /**
     * The TankClient instance used for testing.
     */
    private TankClient client;

    /**
     * Initializes server and client.
     * 
     * @throws Exception If something goes wrong.
     */
    @Before
    public void setup() throws Exception {
        server = new TankServer(PORT);
        client = new TankClient(new RemoteMachine("127.0.0.1", PORT));
        server.start();
        client.connectClient();
    }

    /**
     * Closes server and client.
     * 
     * @throws Exception If something goes wrong.
     */
    @After
    public void cleanup() throws Exception {
        client.disconnectClient();
        server.stop();
    }

    /**
     * Tests if the color can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testColor() throws Exception {
        final int color = 0xff00ff;
        CompletableFuture<Integer> received = new CompletableFuture<>();
        server.setColor(color);
        client.subscribeColor(INTERVAL, received::complete);
        assertEquals(new Integer(color), received.get());
    }

    /**
     * Tests if the fill level can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testFillLevel() throws Exception {
        final float level = 300.5f;
        CompletableFuture<Float> received = new CompletableFuture<>();
        server.setFillLevel(level);
        client.subscribeFillLevel(INTERVAL, received::complete);
        assertEquals(level, received.get(), 0.00001);
    }

    /**
     * Tests if the input flow rate can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testInFlowRate() throws Exception {
        final float rate = 123.523f;
        CompletableFuture<Float> received = new CompletableFuture<>();
        server.setInputFlowRate(rate);
        client.subscribeInputFlowRate(INTERVAL, received::complete);
        assertEquals(rate, received.get(), 0.00001);
    }

    /**
     * Tests if the output flow rate can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testOutFlowRate() throws Exception {
        final float rate = 125.523f;
        CompletableFuture<Float> received = new CompletableFuture<>();
        server.setOutputFlowRate(rate);
        client.subscribeOutputFlowRate(INTERVAL, received::complete);
        assertEquals(rate, received.get(), 0.00001);
    }

    /**
     * Tests if the temperature can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testTemperature() throws Exception {
        final float temp = 325.523f;
        CompletableFuture<Float> received = new CompletableFuture<>();
        server.setTemperature(temp);
        client.subscribeTemperature(INTERVAL, received::complete);
        assertEquals(temp, received.get(), 0.00001);
    }

    /**
     * Tests if the undercooling alarm can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testAlarmUndercool() throws Exception {
        CompletableFuture<Boolean> received = new CompletableFuture<>();
        server.setUndercoolAlarm(true);
        client.subscribeUndercoolSensor(INTERVAL, received::complete);
        assertTrue(received.get());
    }

    /**
     * Tests if the overheating alarm can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testAlarmOverheat() throws Exception {
        CompletableFuture<Boolean> received = new CompletableFuture<>();
        server.setOverheatAlarm(false);
        client.subscribeOverheatSensor(INTERVAL, received::complete);
        assertFalse(received.get());
    }

    /**
     * Tests if the overflow alarm can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testAlarmOverflow() throws Exception {
        CompletableFuture<Boolean> received = new CompletableFuture<>();
        server.setOverflowAlarm(false);
        client.subscribeOverflowSensor(INTERVAL, received::complete);
        assertFalse(received.get());
    }

    /**
     * Tests if the underflow alarm can be read correctly.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testAlarmUnderflow() throws Exception {
        CompletableFuture<Boolean> received = new CompletableFuture<>();
        server.setUnderflowAlarm(true);
        client.subscribeUnderflowSensor(INTERVAL, received::complete);
        assertTrue(received.get());
    }
}
