package edu.kit.pse.osip.simulation.controller;

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
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import javafx.application.Application;
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
    private final static int DEFAULT_PORT = 12686;
    private boolean overflow = false;
    private Timer stepTimer = new Timer(true);

    /**
     * Responsible for controlling the display windows and simulating the production
     */
    public SimulationController () {
        try {
            settingsWrapper = new ServerSettingsWrapper(new File("file"));
        } catch (IOException ex) {
            System.err.println("Couldn't open settings file: " + ex.getMessage());
            System.exit(1);
        }

        simulator = new PhysicsSimulator(productionSite);

        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            TankContainer cont = new TankContainer();
            tanks.add(cont);
            cont.tank = productionSite.getUpperTank(selector);
            cont.selector = selector;
        }
        mixCont.tank = productionSite.getMixTank();
        mixCont.selector = TankSelector.MIX;

        try {
            setupServer();
        } catch (UaException ex) {
            System.err.println("Couldn't start OPC UA server: " + ex.getMessage());
            System.exit(1);
        }

        setupAlarms();
        updateServerValues();
        startMainLoop();
    }

    private void setupAlarms() {
        for (TankContainer cont: tanks) {
            cont.overflowAlarm = new FillAlarm(cont.tank, 95f, AlarmBehavior.GREATER_THAN);
            cont.underflowAlarm = new FillAlarm(cont.tank, 5f, AlarmBehavior.SMALLER_THAN);
            cont.overheatAlarm = new TemperatureAlarm(cont.tank, 5000f, AlarmBehavior.GREATER_THAN);
            cont.undercoolAlarm = new TemperatureAlarm(cont.tank, 200f, AlarmBehavior.SMALLER_THAN);
        }

        mixCont.overflowAlarm = new FillAlarm(mixCont.tank, 95f, AlarmBehavior.GREATER_THAN);
        mixCont.underflowAlarm = new FillAlarm(mixCont.tank, 5f, AlarmBehavior.SMALLER_THAN);
        mixCont.overheatAlarm = new TemperatureAlarm(mixCont.tank, 5000f, AlarmBehavior.GREATER_THAN);
        mixCont.undercoolAlarm = new TemperatureAlarm(mixCont.tank, 200f, AlarmBehavior.SMALLER_THAN);
    }

    private void setupServer() throws UaException {
        int defaultPort = DEFAULT_PORT;
        for (TankContainer cont : tanks) {
            cont.server = new TankServer(settingsWrapper.getServerPort(cont.selector, defaultPort++));
        }
        mixCont.server = new MixTankServer(defaultPort);
    }

    private void reSetupServer() {
        int defaultPort = DEFAULT_PORT;
        for (TankContainer cont : tanks) {
            TankServer old = cont.server;
            try {
                cont.server = new TankServer(settingsWrapper.getServerPort(cont.selector, defaultPort++));
            } catch (UaException ex) {
                System.err.println("Couldn't change OPC UA server port: " + ex.getMessage());
                cont.server = old;
            }
        }
        MixTankServer old = mixCont.server;
        try {
            mixCont.server = new MixTankServer(defaultPort);
        } catch (UaException ex) {
            System.err.println("Couldn't change OPC UA server port: " + ex.getMessage());
            mixCont.server = old;
        }
    }

    private void setupView() {
        Stage help = new HelpDialog();
        Stage about = new AboutDialog();
        settingsInterface = new SimulationSettingsWindow(settingsWrapper);
        controlInterface = new SimulationControlWindow(productionSite);

        currentSimulationView.setOverflowClosedHandler(actionEvent -> productionSite.reset());
        currentSimulationView.setSettingsButtonHandler(actionEvent -> settingsInterface.show());
        currentSimulationView.setControlButtonHandler(actionEvent -> controlInterface.show());

        controlInterface.setValveListener((pipe, number) -> pipe.setValveThreshold(number.byteValue()));
        controlInterface.setTemperatureListener((tankSelector, number) ->
                productionSite.setInputTemperature(tankSelector, number.byteValue()));
        controlInterface.setMotorListener(rpm -> productionSite.getMixTank().getMotor().setRPM(rpm.intValue()));
        settingsInterface.setSettingsChangedListener(this::reSetupServer);
    }

    /**
     * Called bx JavaFx to start drawing the UI
     * @param primaryStage The stage to draw the main window on
     */
    public final void start (Stage primaryStage) {
        currentSimulationView = new SimulationMainWindow(productionSite);
        currentSimulationView.start(primaryStage);
        setupView();
    }

    /**
     * Called when the last window is closed
     */
    public final void stop () {
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
     * Start loop that updates the values
     */
    public final void startMainLoop () {
        stepTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                simulator.tick();
                updateServerValues();
            }
        }, 0, 100);
    }

    /**
     * Update values from model inside the servers
     */
    private final void updateServerValues () {
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

            if (cont.tank.getFillLevel() > 1) {
                overflow = true;
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

        if (mixCont.tank.getFillLevel() > 1) {
            overflow = true;
        }
    }

    /**
     * Some classes to group all tank related attributes together.
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

    private class MixTankContainer {
        private TankSelector selector;
        private MixTank tank;
        private MixTankServer server;

        private FillAlarm overflowAlarm;
        private FillAlarm underflowAlarm;
        private TemperatureAlarm overheatAlarm;
        private TemperatureAlarm undercoolAlarm;
    }
}
