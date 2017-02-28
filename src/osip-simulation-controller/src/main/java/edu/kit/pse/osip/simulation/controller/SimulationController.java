package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.OSIPConstants;
import edu.kit.pse.osip.core.io.files.ServerSettingsWrapper;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.AlarmBehavior;
import edu.kit.pse.osip.core.model.behavior.FillAlarm;
import edu.kit.pse.osip.core.model.behavior.TemperatureAlarm;
import edu.kit.pse.osip.simulation.view.control.SimulationControlWindow;
import edu.kit.pse.osip.simulation.view.dialogs.AboutDialog;
import edu.kit.pse.osip.simulation.view.dialogs.HelpDialog;
import edu.kit.pse.osip.simulation.view.main.SimulationMainWindow;
import edu.kit.pse.osip.simulation.view.settings.SimulationSettingsWindow;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.eclipse.milo.opcua.stack.core.UaException;

/**
 * Manages servers and controls view actions.
 * @author David Kahles
 * @version 1.0
 */
public class SimulationController extends Application {
    private final ProductionSiteSimulation productionSite = new ProductionSiteSimulation();
    private PhysicsSimulator simulator;

    private SimulationViewInterface currentSimulationView;
    private SimulationSettingsInterface settingsInterface;
    private SimulationControlInterface controlInterface;

    private final MixTankContainer mixCont = new MixTankContainer();
    private final List<TankContainer> tanks = new LinkedList<>();

    private ServerSettingsWrapper settingsWrapper;
    private boolean overflow = false;
    private Timer stepTimer = new Timer(true);

    private static final float FILL_ALARM_LOWER_THRESHOLD = 0.05f;
    private static final float FILL_ALARM_UPPER_THRESHOLD = 0.95f;
    private static final float TEMP_ALARM_LOWER_THRESHOLD = 273.15f;
    private static final float TEMP_ALARM_UPPER_THRESHOLD = 373.15f;

    /**
     * Responsible for controlling the display windows and simulating the production
     */
    public SimulationController() {
        File settingsLocation = new File(System.getProperty("user.home") + File.separator + ".osip");
        settingsLocation.mkdirs();
        settingsWrapper = new ServerSettingsWrapper(new File(settingsLocation, "monitoring.conf"));

        simulator = new PhysicsSimulator(productionSite);

        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            TankContainer cont = new TankContainer();
            tanks.add(cont);
            cont.tank = productionSite.getUpperTank(selector);
            cont.selector = selector;
        }
        mixCont.tank = productionSite.getMixTank();

        try {
            setupServer();
        } catch (UaException | InterruptedException | ExecutionException  ex) {
            System.err.println("Couldn't start OPC UA server: " + ex.getMessage());
            System.exit(1);
        }

        setupAlarms();
        updateServerValues();
        startMainLoop();
    }

    private void setupServer() throws UaException, ExecutionException, InterruptedException {
        int defaultPort = OSIPConstants.DEFAULT_PORT_MIX;
        mixCont.server = new MixTankServer(defaultPort++);
        mixCont.server.start();

        for (TankContainer cont : tanks) {
            cont.server = new TankServer(settingsWrapper.getServerPort(cont.selector, defaultPort++));
            cont.server.start();
        }
    }

    private void reSetupServer() {
        for (TankSelector selector: TankSelector.values()) {
            settingsWrapper.setServerPort(selector, settingsInterface.getPort(selector));
        }
        settingsWrapper.saveSettings();
        settingsInterface.close();

        int defaultPort = OSIPConstants.DEFAULT_PORT_MIX;

        MixTankServer oldMix = mixCont.server;
        try {
            mixCont.server = new MixTankServer(defaultPort++);
            mixCont.server.start();
            try {
                oldMix.stop();
            } catch (InterruptedException | ExecutionException ex) {
                System.err.println("Couldn't stop OPC UA server on port: " + ex.getMessage());
            }
        } catch (UaException | InterruptedException | ExecutionException ex) {
            System.err.println("Couldn't change OPC UA server port: " + ex.getMessage());
            mixCont.server = oldMix;
        }

        for (TankContainer cont : tanks) {
            TankServer old = cont.server;
            try {
                cont.server = new TankServer(settingsWrapper.getServerPort(cont.selector, defaultPort++));
                cont.server.start();
                try {
                    old.stop();
                } catch (InterruptedException | ExecutionException ex) {
                    System.err.println("Couldn't stop OPC UA server on port: " + ex.getMessage());
                }
            } catch (UaException | InterruptedException | ExecutionException ex) {
                System.err.println("Couldn't change OPC UA server port: " + ex.getMessage());
                cont.server = old;
            }
        }
    }

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
     * Start loop that updates the values
     */
    private void startMainLoop() {
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
     * Update values from model inside the servers
     */
    private void updateServerValues() {
        overflow = false;
        for (TankContainer cont: tanks) {
            cont.server.setInputFlowRate(cont.tank.getInPipe().getMaxInput());
            cont.server.setColor(cont.tank.getLiquid().getColor().getRGB());
            cont.server.setFillLevel(cont.tank.getLiquid().getAmount());
            cont.server.setOutputFlowRate(cont.tank.getOutPipe().getMaxInput());
            cont.server.setTemperature(cont.tank.getLiquid().getTemperature());

            cont.server.setOverflowAlarm(cont.overflowAlarm.isAlarmTriggered());
            cont.server.setUnderflowAlarm(cont.underflowAlarm.isAlarmTriggered());
            cont.server.setOverheatAlarm(cont.overheatAlarm.isAlarmTriggered());
            cont.server.setUndercoolAlarm(cont.undercoolAlarm.isAlarmTriggered());

            if (cont.tank.getFillLevel() > 1 && overflow == false) {
                overflow = true;
                showOverflow(cont.selector);
            }
        }

        mixCont.server.setMotorSpeed(mixCont.tank.getMotor().getRPM());
        mixCont.server.setColor(mixCont.tank.getLiquid().getColor().getRGB());
        mixCont.server.setFillLevel(mixCont.tank.getLiquid().getAmount());
        mixCont.server.setOutputFlowRate(mixCont.tank.getOutPipe().getMaxInput());
        mixCont.server.setTemperature(mixCont.tank.getLiquid().getTemperature());

        mixCont.server.setOverflowAlarm(mixCont.overflowAlarm.isAlarmTriggered());
        mixCont.server.setUnderflowAlarm(mixCont.underflowAlarm.isAlarmTriggered());
        mixCont.server.setOverheatAlarm(mixCont.overheatAlarm.isAlarmTriggered());
        mixCont.server.setUndercoolAlarm(mixCont.undercoolAlarm.isAlarmTriggered());

        if (mixCont.tank.getFillLevel() > 1 && overflow == false) {
            overflow = true;
            showOverflow(TankSelector.MIX);
        }
    }

    private void showOverflow(TankSelector selector) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentSimulationView.showOverflow(TankSelector.MIX);
            }
        });
    }

    /**
     * Called bx JavaFx to start drawing the UI
     * @param primaryStage The stage to draw the main window on
     */
    public void start(Stage primaryStage) {
        currentSimulationView = new SimulationMainWindow(productionSite);
        currentSimulationView.start(primaryStage);
        setupView();
    }

    private void setupView() {
        Stage help = new HelpDialog();
        Stage about = new AboutDialog();
        settingsInterface = new SimulationSettingsWindow(settingsWrapper);
        controlInterface = new SimulationControlWindow(productionSite);

        currentSimulationView.setOverflowClosedHandler(actionEvent -> productionSite.reset());
        currentSimulationView.setSettingsButtonHandler(actionEvent -> settingsInterface.show());
        currentSimulationView.setControlButtonHandler(actionEvent -> controlInterface.show());
        currentSimulationView.setAboutButtonHandler(actionEvent -> about.show());
        currentSimulationView.setHelpButtonHandler(actionEvent -> help.show());

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
        settingsInterface.setSettingsChangedListener(actionEvent -> reSetupServer());
    }
    /**
     * Called when the last window is closed
     */
    public void stop() {
        stepTimer.cancel();
        System.out.println("Stopped simulation thread");
        try {
            for (TankContainer cont : tanks) {
                cont.server.stop();
                cont.server = null;
            }
            mixCont.server.stop();
            mixCont.server = null;
        } catch (InterruptedException | ExecutionException ex) {
            System.err.println("Couldn't stop OPC UA servers, continuing: " + ex.getMessage());
        }
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
