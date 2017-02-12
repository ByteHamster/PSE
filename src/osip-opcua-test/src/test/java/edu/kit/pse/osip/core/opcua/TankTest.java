package edu.kit.pse.osip.core.opcua;

import edu.kit.pse.osip.core.io.networking.RemoteMachine;
import edu.kit.pse.osip.monitoring.controller.TankClient;
import edu.kit.pse.osip.simulation.controller.TankServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;

/**
 * Tests the tank servers and clients
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TankTest {
    private static final int PORT = 12686;
    private static final int INTERVAL = 500;
    private TankServer server;
    private TankClient client;

    /**
     * Initializes server and client
     * @throws Exception If something goes wrong
     */
    @Before
    public void setup() throws Exception {
        server = new TankServer(PORT);
        client = new TankClient(new RemoteMachine("127.0.0.1", PORT));
        server.start();
        client.connectClient();
    }

    /**
     * Closes server and client
     * @throws Exception If something goes wrong
     */
    @After
    public void cleanup() throws Exception {
        client.connectClient();
        server.stop();
    }

    /**
     * Tests of the color can be read correctly
     * @throws Exception If something goes wrong
     */
    @Test
    public void testColor() throws Exception {
        CompletableFuture<Integer> received = new CompletableFuture<>();
        server.setColor(0xff00ff);
        client.subscribeColor(INTERVAL, received::complete);
        assertEquals(new Integer(0xff00ff), received.get());
    }

    /**
     * Tests of the fill level can be read correctly
     * @throws Exception If something goes wrong
     */
    @Test
    public void testFillLevel() throws Exception {
        CompletableFuture<Float> received = new CompletableFuture<>();
        server.setFillLevel(300.5f);
        client.subscribeFillLevel(INTERVAL, received::complete);
        assertEquals(300.5, received.get(), 0.00001);
    }

    /**
     * Tests of the temperature can be read correctly
     * @throws Exception If something goes wrong
     */
    @Test
    public void testInFlowRate() throws Exception {
        CompletableFuture<Float> received = new CompletableFuture<>();
        server.setInputFlowRate(300.5f);
        client.subscribeInputFlowRate(INTERVAL, received::complete);
        assertEquals(300.5, received.get(), 0.00001);
    }
}
