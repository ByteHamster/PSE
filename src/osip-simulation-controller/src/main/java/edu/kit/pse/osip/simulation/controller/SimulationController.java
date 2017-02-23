package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.io.files.ServerSettingsWrapper;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.simulation.view.control.SimulationControlWindow;
import edu.kit.pse.osip.simulation.view.dialogs.AboutDialog;
import edu.kit.pse.osip.simulation.view.dialogs.HelpDialog;
import edu.kit.pse.osip.simulation.view.main.SimulationMainWindow;
import edu.kit.pse.osip.simulation.view.settings.SimulationSettingsWindow;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import javafx.stage.Stage;
import org.eclipse.milo.opcua.stack.core.UaException;

/**
 * Manages servers and controls view actions.
 * @author David Kahles
 * @version 1.0
 */
public class SimulationController extends Application implements Observer {
    private SimulationViewInterface currentSimulationView;
    private final ProductionSiteSimulation productionSite = new ProductionSiteSimulation();
    private SimulationSettingsInterface settingsInterface;
    private PhysicsSimulator simulator;
    private List<TankServer> inputServers;
    private MixTankServer mixServer;
    private ServerSettingsWrapper settingsWrapper;
    private final static int DEFAULT_PORT = 25325;
    private boolean overflow = false;
    private Timer stepTimer = new Timer(true);

    /**
     * Responsible for controlling the display windows and simulating the production
     */
    public SimulationController () {
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            productionSite.getUpperTank(selector).addObserver(this);
        }
        productionSite.getMixTank().addObserver(this);

        try {
            settingsWrapper = new ServerSettingsWrapper(new File("/Bundle.properties"));
        } catch (IOException ex) {
            System.err.println("Couldn't open settings file: " + ex.getMessage());
            System.exit(1);
        }

        initView();

        simulator = new PhysicsSimulator(productionSite);

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

    private void updateSettings() {
        for (TankSelector selector: TankSelector.values()) {
            settingsWrapper.setServerPort(selector, settingsInterface.getPort(selector));
        }
    }

    private void initView() {
        currentSimulationView = new SimulationMainWindow(productionSite);
        Stage help = new HelpDialog();
        Stage about = new AboutDialog();
        settingsInterface = new SimulationSettingsWindow(settingsWrapper);
        SimulationControlWindow control = new SimulationControlWindow(productionSite);

        currentSimulationView.setHelpButtonHandler(actionEvent -> help.show());
        currentSimulationView.setAboutButtonHandler(actionEvent -> about.show());
        currentSimulationView.setOverflowClosedHandler(actionEvent -> productionSite.reset());
        currentSimulationView.setSettingsButtonHandler(actionEvent -> settingsInterface.show());
        currentSimulationView.setControlButtonHandler(actionEvent -> control.show());

        control.setValveListener((pipe, number) -> pipe.setValveThreshold(number.byteValue()));
        control.setTemperatureListener((tankSelector, number) ->
                productionSite.setInputTemperature(tankSelector, number.byteValue()));
        control.setMotorListener(rpm -> productionSite.getMixTank().getMotor().setRPM(rpm.intValue()));
        settingsInterface.setSettingsChangedListener(this::updateSettings);
    }

    /**
     * Called bx JavaFx to start drawing the UI
     * @param primaryStage The stage to draw the main window on
     */
    public final void start (Stage primaryStage) {
        setupUI(primaryStage);
    }

    /**
     * Start loop that updates the values
     */
    public final void startMainLoop () {
        stepTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                simulator.tick();
            }
        }, 500);
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
    private final void setupUI (Stage stage) {
        currentSimulationView.start(stage);
    }

    /**
     * One of the observed objects changed its state
     * @param observable Observed object
     * @param object New value
     */
    public final void update (Observable observable, Object object) {
        AbstractTank tank = (AbstractTank) object;
        if (tank.getFillLevel() > 1) {
            overflow = true;
            currentSimulationView.showOverflow();
        }

        if (! overflow)
            return;

        /* Check whether the overflow is still present */
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            tank = productionSite.getUpperTank(selector);
            if (tank.getFillLevel() > 1) {
                return;
            }
        }

        MixTank mixTank = productionSite.getMixTank();
        if (mixTank.getFillLevel() > 1) {
            return;
        }
        overflow = false;
    }
}
