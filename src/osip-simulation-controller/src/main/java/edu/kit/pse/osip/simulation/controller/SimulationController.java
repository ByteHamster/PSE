package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.io.files.ParserException;
import edu.kit.pse.osip.core.io.files.ScenarioFile;
import edu.kit.pse.osip.core.io.files.ServerSettingsWrapper;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.AlarmBehavior;
import edu.kit.pse.osip.core.model.behavior.FillAlarm;
import edu.kit.pse.osip.core.model.behavior.Scenario;
import edu.kit.pse.osip.core.model.behavior.TemperatureAlarm;
import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.simulation.view.control.SimulationControlWindow;
import edu.kit.pse.osip.simulation.view.dialogs.AboutDialog;
import edu.kit.pse.osip.simulation.view.dialogs.HelpDialog;
import edu.kit.pse.osip.simulation.view.main.SimulationMainWindow;
import edu.kit.pse.osip.simulation.view.settings.SimulationSettingsWindow;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.eclipse.milo.opcua.stack.core.UaException;

/**
 * Manages servers and controls view actions.
 * 
 * @author David Kahles
 * @version 1.0
 */
public class SimulationController extends Application {
    /**
     * The used ProductionSite.
     */
    private final ProductionSiteSimulation productionSite = new ProductionSiteSimulation();
    /**
     * Simulator for the ProductionSite.
     */
    private PhysicsSimulator simulator;

    /**
     * Contains the access point to the simulation view.
     */
    private SimulationViewInterface currentSimulationView;
    /**
     * Contains the access point to the settings view.
     */
    private SimulationSettingsInterface settingsInterface;
    /**
     * Contains the access point to the control view.
     */
    private SimulationControlInterface controlInterface;

    /**
     * Saves the container for all mixtank related objects.
     */
    private final MixTankContainer mixCont = new MixTankContainer();
    /**
     * Saves all containers for all tank related objects.
     */
    private final List<TankContainer> tanks = new LinkedList<>();

    /**
     * The current running scenario.
     */
    private Scenario currentScenario;

    /**
     * The current settings.
     */
    private ServerSettingsWrapper settingsWrapper;
    /**
     * Indicates whether an overflow occurred.
     */
    private boolean overflow = false;
    /**
     * Timer for the simulation.
     */
    private Timer stepTimer = new Timer(true);
    /**
     * Indicates whether an reset is in progress.
     */
    private boolean resetInProgress = false;

    /**
     * The threshold for an underflow alarm.
     */
    private static final float FILL_ALARM_LOWER_THRESHOLD = 0.05f;
    /**
     * The threshold for an overflow alarm.
     */
    private static final float FILL_ALARM_UPPER_THRESHOLD = 0.95f;
    /**
     * The threshold for an overheating alarm.
     */
    private static final float TEMP_ALARM_LOWER_THRESHOLD = SimulationConstants.MIN_TEMPERATURE
            + 0.05f * (SimulationConstants.MAX_TEMPERATURE - SimulationConstants.MIN_TEMPERATURE);
    /**
     * The threshold for an undercooling alarm.
     */
    private static final float TEMP_ALARM_UPPER_THRESHOLD = SimulationConstants.MIN_TEMPERATURE
            + 0.95f * (SimulationConstants.MAX_TEMPERATURE - SimulationConstants.MIN_TEMPERATURE);

    /**
     * Responsible for controlling the display windows and simulating the production.
     */
    public SimulationController() {
        File settingsLocation = new File(System.getProperty("user.home") + File.separator + ".osip");
        settingsLocation.mkdirs();
        settingsWrapper = new ServerSettingsWrapper(new File(settingsLocation, "simulation.conf"));

        simulator = new PhysicsSimulator(productionSite);
    }

    /**
     * Initializes the model.
     */
    private void initialize() {
        for (TankSelector selector : TankSelector.valuesWithoutMix()) {
            TankContainer cont = new TankContainer();
            tanks.add(cont);
            cont.tank = productionSite.getUpperTank(selector);
            cont.selector = selector;
        }
        mixCont.tank = productionSite.getMixTank();
        setupAlarms();

        if (reSetupServer()) {
            startMainLoop();
            updateServerValues();
        } else {
            Platform.runLater(() -> settingsInterface.show());
        }
    }

    /**
     * Re-setup the servers.
     * 
     * @return true if successful.
     */
    private boolean reSetupServer() {
        boolean error = false;
        Translator t = Translator.getInstance();

        for (TankSelector selector : TankSelector.values()) {
            settingsWrapper.setServerPort(selector, settingsInterface.getPort(selector));
        }
        settingsWrapper.saveSettings();

        if (hasDoublePorts()) {
            currentSimulationView.showOPCUAServerError(t.getString("simulation.settings.samePort"));
            return false;
        }

        for (TankContainer cont : tanks) {
            try {
                if (cont.server != null) {
                    cont.server.stop();
                }
            } catch (InterruptedException | ExecutionException ex) {
                currentSimulationView.showOPCUAServerError(String.format(
                    t.getString("simulation.settings.stopError") + ": " + ex.getLocalizedMessage(),
                    cont.selector.toString().toLowerCase()));
                error = true;
            }
            cont.server = null;
        }
        try {
            if (mixCont.server != null) {
                mixCont.server.stop();
            }
        } catch (InterruptedException | ExecutionException ex) {
            currentSimulationView.showOPCUAServerError(String.format(t.getString("simulation.settings.stopError")
                    + ": " + ex.getLocalizedMessage(), TankSelector.MIX.toString().toLowerCase()));
            error = true;
        }
        mixCont.server = null;

        for (TankContainer cont : tanks) {
            try {
                int port = settingsInterface.getPort(cont.selector);
                cont.server = new TankServer(port);
                cont.server.start();
            } catch (InterruptedException | ExecutionException | UaException ex) {
                currentSimulationView.showOPCUAServerError(String.format(
                    t.getString("simulation.settings.startError") + ": "
                    + ex.getLocalizedMessage(), cont.selector.toString().toLowerCase()));
                error = true;
            }
        }
        try {
            int port = settingsInterface.getPort(TankSelector.MIX);
            mixCont.server = new MixTankServer(port);
            mixCont.server.start();
        } catch (InterruptedException | ExecutionException | UaException ex) {
            currentSimulationView.showOPCUAServerError(String.format(t.getString("simulation.settings.startError")
                + ": " + ex.getLocalizedMessage(), TankSelector.MIX.toString().toLowerCase()));
            error = true;
        }
        return !error;
    }

    /**
     * Checks whether two server have two ports in common.
     * 
     * @return true when at least two servers have the same port set.
     */
    private boolean hasDoublePorts() {
        Vector<Integer> ports = new Vector<>(TankSelector.values().length);
        for (TankSelector selector : TankSelector.values()) {
            int port = settingsInterface.getPort(selector);
            if (ports.contains(port)) {
                return true;
            } else {
                ports.add(port);
            }
        }
        return false;
    }

    /**
     * Sets up the alarms for every tank.
     */
    private void setupAlarms() {
        for (TankContainer cont: tanks) {
            cont.overflowAlarm =
                new FillAlarm(cont.tank, FILL_ALARM_UPPER_THRESHOLD, AlarmBehavior.GREATER_THAN);
            cont.underflowAlarm =
                new FillAlarm(cont.tank, FILL_ALARM_LOWER_THRESHOLD, AlarmBehavior.SMALLER_THAN);
            cont.overheatAlarm =
                new TemperatureAlarm(cont.tank, TEMP_ALARM_UPPER_THRESHOLD, AlarmBehavior.GREATER_THAN);
            cont.undercoolAlarm =
                new TemperatureAlarm(cont.tank, TEMP_ALARM_LOWER_THRESHOLD, AlarmBehavior.SMALLER_THAN);
        }

        mixCont.overflowAlarm =
            new FillAlarm(mixCont.tank, FILL_ALARM_UPPER_THRESHOLD, AlarmBehavior.GREATER_THAN);
        mixCont.underflowAlarm =
            new FillAlarm(mixCont.tank, FILL_ALARM_LOWER_THRESHOLD, AlarmBehavior.SMALLER_THAN);
        mixCont.overheatAlarm =
            new TemperatureAlarm(mixCont.tank, TEMP_ALARM_UPPER_THRESHOLD, AlarmBehavior.GREATER_THAN);
        mixCont.undercoolAlarm =
            new TemperatureAlarm(mixCont.tank, TEMP_ALARM_LOWER_THRESHOLD, AlarmBehavior.SMALLER_THAN);
    }

    /**
     * Starts loop that updates the values.
     */
    private void startMainLoop() {
        if (stepTimer != null) {
            stepTimer.cancel();
        }
        stepTimer = new Timer(true);
        stepTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!overflow) {
                    simulator.tick();
                    updateServerValues();
                }
            }
        }, 0, 100);
    }

    /**
     * Updates values from model inside the servers.
     */
    private void updateServerValues() {
        for (TankContainer cont : tanks) {
            if (cont.server == null) {
                continue;
            }
            cont.server.setInputFlowRate(cont.tank.getInPipe().getValveThreshold());
            cont.server.setColor(cont.tank.getLiquid().getColor().getRGB());
            cont.server.setFillLevel(cont.tank.getLiquid().getAmount());
            cont.server.setOutputFlowRate(cont.tank.getOutPipe().getValveThreshold());
            cont.server.setTemperature(cont.tank.getLiquid().getTemperature());

            cont.server.setOverflowAlarm(cont.overflowAlarm.isAlarmTriggered());
            cont.server.setUnderflowAlarm(cont.underflowAlarm.isAlarmTriggered());
            cont.server.setOverheatAlarm(cont.overheatAlarm.isAlarmTriggered());
            cont.server.setUndercoolAlarm(cont.undercoolAlarm.isAlarmTriggered());

            if (cont.tank.getFillLevel() > 1 && !overflow && !resetInProgress) {
                overflow = true;
                showOverflow(cont.tank);
            }
        }

        if (mixCont.server == null) {
            return;
        }
        mixCont.server.setMotorSpeed(mixCont.tank.getMotor().getRPM());
        mixCont.server.setColor(mixCont.tank.getLiquid().getColor().getRGB());
        mixCont.server.setFillLevel(mixCont.tank.getLiquid().getAmount());
        mixCont.server.setOutputFlowRate(mixCont.tank.getOutPipe().getValveThreshold());
        mixCont.server.setTemperature(mixCont.tank.getLiquid().getTemperature());

        mixCont.server.setOverflowAlarm(mixCont.overflowAlarm.isAlarmTriggered());
        mixCont.server.setUnderflowAlarm(mixCont.underflowAlarm.isAlarmTriggered());
        mixCont.server.setOverheatAlarm(mixCont.overheatAlarm.isAlarmTriggered());
        mixCont.server.setUndercoolAlarm(mixCont.undercoolAlarm.isAlarmTriggered());

        if (mixCont.tank.getFillLevel() > 1 && !overflow && !resetInProgress) {
            overflow = true;
            showOverflow(mixCont.tank);
        }
    }

    /**
     * Shows the overflow dialog.
     * 
     * @param tank the tank where the overflow occurred.
     */
    private void showOverflow(AbstractTank tank) {
        controlInterface.setControlsDisabled(true);
        Platform.runLater(() -> currentSimulationView.showOverflow(tank));
        if (currentScenario != null) {
            currentScenario.cancelScenario();
            currentSimulationView.scenarioFinished();
        }
    }

    /**
     * Called bx JavaFX to start drawing the UI.
     * 
     * @param primaryStage The stage to draw the main window on.
     */
    public void start(Stage primaryStage) {
        controlInterface = new SimulationControlWindow(productionSite);

        currentSimulationView = new SimulationMainWindow(productionSite);
        currentSimulationView.start(primaryStage);
        setupView(primaryStage);

        currentSimulationView.setProgressIndicatorVisible(true);
        new Thread(() -> {
            initialize();
            Platform.runLater(() -> currentSimulationView.setProgressIndicatorVisible(false));
        }).start();
    }

    /**
     * Sets up the view.
     * 
     * @param primaryStage the stage on which the view is shown.
     */
    private void setupView(Stage primaryStage) {
        Stage help = new HelpDialog();
        Stage about = new AboutDialog();
        settingsInterface = new SimulationSettingsWindow(settingsWrapper);

        primaryStage.setOnHiding((event) -> {
            help.hide();
            about.hide();
            settingsInterface.close();
            controlInterface.close();
        });

        currentSimulationView.setSettingsButtonHandler(actionEvent -> settingsInterface.show());
        currentSimulationView.setControlButtonHandler(actionEvent -> controlInterface.show());
        currentSimulationView.setAboutButtonHandler(actionEvent -> about.show());
        currentSimulationView.setHelpButtonHandler(actionEvent -> help.show());

        currentSimulationView.setScenarioStartListener(this::startScenario);
        currentSimulationView.setScenarioStopListener(() -> {
            if (currentScenario != null) {
                currentScenario.cancelScenario();
                currentSimulationView.scenarioFinished();
                controlInterface.setControlsDisabled(false);
            }
        });

        currentSimulationView.setResetListener((event) -> {
            if (currentScenario != null) {
                currentScenario.cancelScenario();
                currentSimulationView.scenarioFinished();
                controlInterface.setControlsDisabled(false);
            }

            startMainLoop();

            resetInProgress = true;
            productionSite.reset();
            overflow = false;
            resetInProgress = false;
            controlInterface.setControlsDisabled(false);
        });

        controlInterface.setValveListener((pipe, number) -> {
            pipe.setValveThreshold(number);
            updateServerValues();
        });
        controlInterface.setTemperatureListener((tankSelector, number) -> {
            simulator.setInputTemperature(tankSelector, number);
            updateServerValues();
        });
        controlInterface.setMotorListener(rpm -> {
            productionSite.getMixTank().getMotor().setRPM(rpm);
            updateServerValues();
        });
        settingsInterface.setSettingsChangedListener(actionEvent -> {
            currentSimulationView.setProgressIndicatorVisible(true);
            new Thread(() -> {
                Platform.runLater(() -> settingsInterface.close());
                if (!reSetupServer()) {
                    Platform.runLater(() -> settingsInterface.show());
                    currentSimulationView.setProgressIndicatorVisible(false);
                    return;
                }
                startMainLoop();
                currentSimulationView.setProgressIndicatorVisible(false);
            }).start();
        });
    }

    /**
     * Starts a scenario.
     * 
     * @param file path to the scenario file.
     */
    private void startScenario(String file) {
        try {
            ScenarioFile scenarioFile = new ScenarioFile(file);
            currentScenario = scenarioFile.getScenario();
        } catch (ParserException | IOException ex) {
            currentSimulationView.showScenarioError(ex.getLocalizedMessage());
            currentSimulationView.scenarioFinished();
            ex.printStackTrace();
            return;
        }
        currentSimulationView.scenarioStarted();
        currentScenario.setProductionSite(productionSite);
        currentScenario.setScenarioFinishedListener(() -> {
            currentSimulationView.scenarioFinished();
            controlInterface.setControlsDisabled(false);
        });
        controlInterface.setControlsDisabled(true);
        resetInProgress = true;
        productionSite.reset();
        resetInProgress = false;
        currentScenario.startScenario();
    }

    /**
     * Called when the last window is closed.
     */
    public void stop() {
        stepTimer.cancel();
        System.out.println("Stopped simulation thread");
        try {
            for (TankContainer cont : tanks) {
                if (cont.server == null) {
                    continue;
                }
                cont.server.stop();
                cont.server = null;
            }
            if (mixCont.server != null) {
                mixCont.server.stop();
                mixCont.server = null;
            }
        } catch (InterruptedException | ExecutionException ex) {
            System.err.println("Couldn't stop OPC UA servers, continuing: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        AbstractTankServer.releaseSharedResources();
    }

    /**
     * Groups all tank related attributes together.
     */
    private class TankContainer {
        private TankSelector selector;
        private Tank tank;
        private TankServer server;

        private FillAlarm overflowAlarm;
        private FillAlarm underflowAlarm;
        private TemperatureAlarm overheatAlarm;
        private TemperatureAlarm undercoolAlarm;
    }

    /**
     * Groups all mixtank related attributes together.
     */
    private class MixTankContainer {
        private MixTank tank;
        private MixTankServer server;

        private FillAlarm overflowAlarm;
        private FillAlarm underflowAlarm;
        private TemperatureAlarm overheatAlarm;
        private TemperatureAlarm undercoolAlarm;
    }
}
