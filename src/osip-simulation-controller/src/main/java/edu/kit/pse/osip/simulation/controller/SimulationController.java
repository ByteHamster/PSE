package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.io.files.ServerSettingsWrapper;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.simulation.view.main.SimulationMainWindow;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import java.util.concurrent.ExecutionException;
import org.eclipse.milo.opcua.stack.core.UaException;

/**
 * Manages servers and controls view actions.
 * @author David Kahles
 * @version 1.0
 */
public class SimulationController extends javafx.application.Application implements java.util.Observer {
    private SimulationViewInterface currentSimulationView;
    private ProductionSiteSimulation productionSite;
    private PhysicsSimulator simulator;
    private List<TankServer> inputServers;
    private MixTankServer mixServer;
    private ServerSettingsWrapper settingsWrapper;
    private final static int DEFAULT_PORT = 253625;
    private boolean overflow = false;

    /**
     * Responsible for controlling the display windows and simulating the production
     */
    public SimulationController () {
        productionSite = new ProductionSiteSimulation();
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            productionSite.getUpperTank(selector).addObserver(this);
        }
        productionSite.getMixTank().addObserver(this);

        currentSimulationView = new SimulationMainWindow(productionSite);

        currentSimulationView.setOverflowClosedHandler(new OverflowClosedHandler(productionSite));
        simulator = new PhysicsSimulator(productionSite);

        try {
            settingsWrapper = new ServerSettingsWrapper(new File("/Bundle.properties"));
        } catch (IOException ex) {
            System.err.println("Couldn't open settings file: " + ex.getMessage());
            System.exit(1);
        }

        int defaultPort = DEFAULT_PORT;
        try {
            for (TankSelector selector: TankSelector.valuesWithoutMix()) {
                inputServers.add(new TankServer(settingsWrapper.getServerPort(selector, defaultPort++)));
            }
            mixServer = new MixTankServer(defaultPort);
        } catch (UaException ex) {
            System.err.println("Couldn't start OPC UA server on port " + (defaultPort - 1) + " : " + ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * Called bx JavaFx to start drawing the UI
     * @param primaryStage The stage to draw the main window on
     */
    public final void start (javafx.stage.Stage primaryStage) {
        setupUI(primaryStage);
    }

    /**
     * Show windows and start loop that updates the values
     */
    public final void startMainLoop () {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Update values from model inside the servers
     */
    private final void updateServerValues () {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Check if threre is an alarm that needs to be set in the servers
     */
    private final void checkAlarms () {
        throw new RuntimeException("Not implemented!");
    }

    /**
     * Called when the last window is closed
     */
    public final void stop () {
        try {
            for (TankServer tank : inputServers) {
                tank.stop();
            }
            mixServer.stop();
        } catch (InterruptedException | ExecutionException ex) {
            System.err.println("Couldn't stop OPC UA servers: " + ex.getMessage());
        }
    }

    /**
     * Initialize the UI
     * @param stage The javafx stage
     */
    private final void setupUI (javafx.stage.Stage stage) {
        currentSimulationView.start(stage);
    }

    /**
     * One of the observed objects changed its state
     * @param observable Observed object
     * @param object New value
     */
    public final void update (Observable observable, Object object) {
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            Tank tank = productionSite.getUpperTank(selector);
            if (tank.getFillLevel() > 1) {
                overflow = true;
                currentSimulationView.showOverflow();
                return;
            }
        }
        MixTank mixTank = productionSite.getMixTank();
        if (mixTank.getFillLevel() > 1) {
            overflow = true;
            currentSimulationView.showOverflow();
            return;
        }
        overflow = false;
    }
}
