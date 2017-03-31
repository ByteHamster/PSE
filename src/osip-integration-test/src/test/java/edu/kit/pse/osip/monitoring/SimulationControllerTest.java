package edu.kit.pse.osip.monitoring;

import edu.kit.pse.osip.core.OSIPConstants;
import edu.kit.pse.osip.core.io.files.ServerSettingsWrapper;
import edu.kit.pse.osip.core.io.networking.RemoteMachine;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.opcua.client.UAClientException;
import edu.kit.pse.osip.monitoring.controller.MixTankClient;
import edu.kit.pse.osip.monitoring.controller.TankClient;
import edu.kit.pse.osip.simulation.controller.SimulationController;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertTrue;

/**
 * A class to test the SimulationController class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class SimulationControllerTest extends ApplicationTest {
    private static CompletableFuture<Boolean> closed = new CompletableFuture<>();
    private static SimulationController simCont;

    @Override
    public void start(Stage primaryStage) {
        simCont = new SimulationController();
        simCont.start(primaryStage);
    }

    /**
     * Start the SimulationController and check whether we can connect OPC UA Clients to it. Don't test
     * the GUI any further. Just make sure it doesn't throw an exception.
     *
     * @throws UAClientException    If connecting to the OPC UA servers fails.
     * @throws InterruptedException If sleep fails
     */
    @Test(timeout = 60000)
    public void testLaunch() throws UAClientException, InterruptedException {
        File settingsLocation = new File(System.getProperty("user.home") + File.separator + ".osip");
        settingsLocation.mkdirs();
        ServerSettingsWrapper settings = new ServerSettingsWrapper(new File(settingsLocation, "simulation.conf"));

        MixTankClient mixClient = new MixTankClient(new RemoteMachine("127.0.0.1",
            settings.getServerPort(TankSelector.MIX, OSIPConstants.DEFAULT_PORT_MIX)));
        TankClient client = new TankClient(new RemoteMachine("127.0.0.1",
            settings.getServerPort(TankSelector.valuesWithoutMix()[0], OSIPConstants.DEFAULT_PORT_MIX + 1)));

        while (true) {
            try {
                mixClient.connectClient();
                client.connectClient();
                break;
            } catch (UAClientException ex) {
                System.err.println("Exception when connecting to OPC UA server."
                    + " Waiting for some time before reconnecting");
                ex.printStackTrace();
                Thread.sleep(1000);
            }
        }
        mixClient.disconnectClient();
        client.disconnectClient();
    }

    /**
     * Check whether the application got stopped
     */
    @AfterClass
    public static void testClosed() {
        assertTrue(closed.getNow(false));
    }

    @Override
    public void stop() {
        simCont.stop();
        closed.complete(true);
    }
}
