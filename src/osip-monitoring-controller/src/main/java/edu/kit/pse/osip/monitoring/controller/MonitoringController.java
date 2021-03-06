package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.OSIPConstants;
import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.io.networking.RemoteMachine;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.AlarmGroup;
import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import edu.kit.pse.osip.core.opcua.client.BooleanReceivedListener;
import edu.kit.pse.osip.core.opcua.client.ErrorListener;
import edu.kit.pse.osip.core.opcua.client.FloatReceivedListener;
import edu.kit.pse.osip.core.opcua.client.IntReceivedListener;
import edu.kit.pse.osip.core.opcua.client.UAClientException;
import edu.kit.pse.osip.core.opcua.client.UAClientWrapper;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewFacade;
import edu.kit.pse.osip.monitoring.view.dialogs.AboutDialog;
import edu.kit.pse.osip.monitoring.view.dialogs.HelpDialog;
import edu.kit.pse.osip.monitoring.view.settings.SettingsViewFacade;
import java.io.File;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;

/**
 * The controller starts the monitoring view and initializes the model with the main loop.
 * It is also responsible for the communication between the view and model.
 * 
 * @author Martin Armbruster, Maximilian Schwarzmann
 * @version 1.1
 */
public final class MonitoringController extends Application {
    /**
     * The current model.
     */
    private ProductionSite productionSite;
    /**
     * Contains the current settings.
     */
    private ClientSettingsWrapper currentSettings;
    /**
     * The current entry point to the monitoring view.
     */
    private MonitoringViewInterface currentView;
    /**
     * The current entry point for the settings view.
     */
    private SettingsViewInterface currentSettingsView;
    /**
     * Contains all mixtank related objects. 
     */
    private final MixTankContainer mixCont = new MixTankContainer();
    /**
     * List of all containers with tank related objects.
     */
    private final List<TankContainer> tanks = new LinkedList<>();
    /**
     * The default host name.
     */
    private static final String DEFAULT_HOSTNAME = "localhost";
    /**
     * The internal fetch interval for the alarms.
     */
    private static final int ALARM_FETCH_INTERVAL = 50;
    
    /**
     * Creates a new controller instance.
     */
    public MonitoringController() {
        productionSite = new ProductionSite();
        File settingsLocation = new File(System.getProperty("user.home") + File.separator + ".osip");
        settingsLocation.mkdirs();
        currentSettings = new ClientSettingsWrapper(new File(settingsLocation, "monitoring.conf"));
        currentView = new MonitoringViewFacade();
        currentSettingsView = new SettingsViewFacade(currentSettings);
    }

    @Override
    public void start(Stage primaryStage) {
        for (TankSelector selector: TankSelector.valuesWithoutMix()) {
            TankContainer cont = new TankContainer();
            tanks.add(cont);
            cont.tank = productionSite.getUpperTank(selector);
            cont.selector = selector;
            cont.absTank = cont.tank;
            cont.alarmGroup = new AlarmGroup<>(new ObservableBoolean(false),
                new ObservableBoolean(false), new ObservableBoolean(false),
                new ObservableBoolean(false));
        }
        mixCont.tank = productionSite.getMixTank();
        mixCont.absTank = mixCont.tank;
        mixCont.alarmGroup = new AlarmGroup<>(new ObservableBoolean(false),
                new ObservableBoolean(false), new ObservableBoolean(false),
                new ObservableBoolean(false));

        setupUI(primaryStage);
        syncMonitoringViewAndSettingsView();
        connect();
    }

    /**
     * Generates and returns a map containing the AlarmGroups for every tank.
     * 
     * @return a map containing the AlarmGroups for every tank.
     */
    private EnumMap<TankSelector, AlarmGroup<ObservableBoolean, ObservableBoolean>> getAlarmEnumMap() {
        EnumMap<TankSelector, AlarmGroup<ObservableBoolean, ObservableBoolean>> map = new EnumMap<>(TankSelector.class);
        for (TankContainer cont: tanks) {
            map.put(cont.selector, cont.alarmGroup);
        }
        map.put(TankSelector.MIX, mixCont.alarmGroup);
        return map;
    }

    /**
     * Initializes the UI.
     * 
     * @param stage The JavaFX stage.
     */
    private void setupUI(Stage stage) {
        currentView.showMonitoringView(stage, productionSite, getAlarmEnumMap());
        currentView.setMenuSettingsButtonHandler(event -> currentSettingsView.showSettingsWindow());
        HelpDialog helpDialog = new HelpDialog();
        currentView.setMenuHelpButtonHandler(event -> helpDialog.show());
        AboutDialog aboutDialog = new AboutDialog();
        currentView.setMenuAboutButtonHandler(event -> aboutDialog.show());
        currentSettingsView.setSettingsCancelButtonHandler(event -> currentSettingsView.hideSettingsWindow());
        currentSettingsView.setSettingsSaveButtonHandler(event -> {
            syncMonitoringViewAndSettingsView();
            handleSaveButton();
        });
    
        stage.setOnHiding((event) -> {
            helpDialog.hide();
            aboutDialog.hide();
            currentSettingsView.hideSettingsWindow();
        });     
    }

    @Override
    public void stop() {
        System.out.println("Stopped monitoring thread");

        // So the second tank does not receive a disconnect
        // while the first is currently disconnecting
        for (TankContainer cont : tanks) {
            cont.client.setErrorListener(null);
        }
        mixCont.client.setErrorListener(null);

        for (TankContainer cont : tanks) {
            try {
                cont.client.disconnectClient();
            } catch (UAClientException e) {
                System.err.println(e.getLocalizedMessage());
            }
            cont.client = null;
        }
        try {
            mixCont.client.disconnectClient();
        } catch (UAClientException e) {
            System.err.println(e.getLocalizedMessage());
        }
        mixCont.client = null;
        AbstractTankClient.releaseSharedResources();
    }

    /**
     * Initializes and connects the clients.
     */
    private void connect() {
        currentView.setProgressIndicatorVisible(true);
        new Thread(() -> {
            if (reSetupClients()) {
                clientSubscribe(currentSettings.getFetchInterval(MonitoringViewConstants.DEFAULT_UPDATE_INTERVAL),
                        ALARM_FETCH_INTERVAL);
            } else {
                Platform.runLater(() -> {
                    disableProgressions();
                    currentSettingsView.showCanNotConnectAlert();
                });
            }
            currentView.setProgressIndicatorVisible(false);
        }).start();
    }

    /**
     * Changes server connections.
     * 
     * @return true if switching servers is successful.
     */
    private boolean reSetupClients() {
        int defaultPort = OSIPConstants.DEFAULT_PORT_MIX;
        String hostname;
        int port;

        try {
            if (mixCont.client != null) {
                mixCont.client.disconnectClient();
                mixCont.client = null;
            }
            for (TankContainer cont: tanks) {
                if (cont.client != null) {
                    cont.client.disconnectClient();
                    cont.client = null;
                }
            }
        } catch (UAClientException e) {
            System.err.println(e.getLocalizedMessage());
        }

        hostname = currentSettings.getHostname(mixCont.tank.getTankSelector(), DEFAULT_HOSTNAME);
        port = currentSettings.getPort(mixCont.tank.getTankSelector(), defaultPort++);
        mixCont.client = new MixTankClient(new RemoteMachine(hostname, port));

        for (TankContainer cont: tanks) {
            hostname = currentSettings.getHostname(cont.tank.getTankSelector(), DEFAULT_HOSTNAME);
            port = currentSettings.getPort(cont.tank.getTankSelector(), defaultPort++);
            cont.client = new TankClient(new RemoteMachine(hostname, port));
        }

        try {
            mixCont.client.connectClient();
            for (TankContainer cont : tanks) {
                cont.client.connectClient();
            }
            return true;
        } catch (UAClientException e) {
            System.err.println(e.getLocalizedMessage());
            return false;
        }
    }
    
    /**
     * Handles a click on the save button in the settings window.
     */
    private void handleSaveButton() {
        for (TankSelector tank: TankSelector.values()) {
            currentSettings.setFetchInterval(currentSettingsView.getUpdateInterval());
            currentSettings.setFillLevelDiagram(tank, currentSettingsView.isFillLevelProgressionEnabled(tank));
            currentSettings.setOverflowAlarm(tank, currentSettingsView.isOverflowAlarmEnabled(tank));
            currentSettings.setOverheatingAlarm(
                    tank, currentSettingsView.isTemperatureOverheatingAlarmEnabled(tank));
            currentSettings.setServerHostname(tank, currentSettingsView.getServerHostname());
            currentSettings.setServerPort(tank, currentSettingsView.getServerPort(tank));
            currentSettings.setTempDiagram(tank, currentSettingsView.isTemperatureProgressionEnabled(tank));
            currentSettings.setUndercoolingAlarm(
                    tank, currentSettingsView.isTemperatureUndercoolingAlarmEnabled(tank));
            currentSettings.setUnderflowAlarm(tank, currentSettingsView.isUnderflowAlarmEnabled(tank));        
        }
        currentSettings.saveSettings();
        currentSettingsView.hideSettingsWindow();

        connect();
    }
    
    /**
     * The settings are applied to the monitoring view.
     */
    private void syncMonitoringViewAndSettingsView() {
        for (TankSelector tank: TankSelector.values()) {
            currentView.setFillLevelProgressionEnabled(tank,
                    currentSettingsView.isFillLevelProgressionEnabled(tank));
            currentView.setOverflowAlarmEnabled(tank,
                    currentSettingsView.isOverflowAlarmEnabled(tank));
            currentView.setTemperatureOverheatingAlarmEnabled(tank,
                    currentSettingsView.isTemperatureOverheatingAlarmEnabled(tank));
            currentView.setTemperatureProgressionEnabled(tank,
                    currentSettingsView.isTemperatureProgressionEnabled(tank));
            currentView.setTemperatureUndercoolingAlarmEnabled(tank,
                    currentSettingsView.isTemperatureUndercoolingAlarmEnabled(tank));
            currentView.setUnderflowAlarmEnabled(tank,
                    currentSettingsView.isUnderflowAlarmEnabled(tank)); 
        }
    }
    
    /**
     * Disables all progressions in case there is no connection.
     */
    private void disableProgressions() {
        for (TankSelector tank : TankSelector.values()) {
            currentView.setFillLevelProgressionEnabled(tank, false);
            currentView.setTemperatureProgressionEnabled(tank, false);
        }
    }
    
    /**
     * Subscribes all values of the tanks.
     * 
     * @param intervalRegular the fetch interval for regular values.
     * @param intervalAlarm the fetch interval for the alarms.
     */
    private void clientSubscribe(int intervalRegular, int intervalAlarm) {        
        if (mixCont.client != null) {
            try {
                mixCont.client.subscribeColor(intervalRegular, mixCont.colorListener);
                mixCont.client.subscribeFillLevel(intervalRegular, mixCont.fillLevelListener);
                mixCont.client.subscribeMotorSpeed(intervalRegular, mixCont.motorSpeedListener);
                mixCont.client.subscribeOutputFlowRate(intervalRegular, mixCont.outputFlowRateListener);
                mixCont.client.subscribeOverflowSensor(intervalAlarm, mixCont.overflowSensorListener);
                mixCont.client.subscribeOverheatSensor(intervalAlarm, mixCont.overheatSensorListener);
                mixCont.client.subscribeTemperature(intervalRegular, mixCont.temperatureListener);
                mixCont.client.subscribeUndercoolSensor(intervalAlarm, mixCont.undercoolSensorListener);
                mixCont.client.subscribeUnderflowSensor(intervalAlarm, mixCont.underflowSensorListener);
            } catch (UAClientException e) {
                System.err.println("Could not subscribe property: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
            mixCont.client.setErrorListener(mixCont.errorListener);
        }
        for (TankContainer cont: tanks) {
            if (cont.client != null) {
                try {
                    cont.client.subscribeColor(intervalRegular, cont.colorListener);
                    cont.client.subscribeFillLevel(intervalRegular, cont.fillLevelListener);
                    cont.client.subscribeInputFlowRate(intervalRegular, cont.inputFlowRateListener);
                    cont.client.subscribeOutputFlowRate(intervalRegular, cont.outputFlowRateListener);
                    cont.client.subscribeOverflowSensor(intervalAlarm, cont.overflowSensorListener);
                    cont.client.subscribeOverheatSensor(intervalAlarm, cont.overheatSensorListener);
                    cont.client.subscribeTemperature(intervalRegular, cont.temperatureListener);
                    cont.client.subscribeUndercoolSensor(intervalAlarm, cont.undercoolSensorListener);
                    cont.client.subscribeUnderflowSensor(intervalAlarm, cont.underflowSensorListener);
                } catch (UAClientException e) {
                    System.err.println("Could not subscribe property: " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
                cont.client.setErrorListener(cont.errorListener);
            }
        }
    }
    
    /**
     * Container for the OPC UA clients and subscribed value listeners.
     * 
     * @author Maximilian Schwarzmann
     * @version 1.0
     */
    private class Container {
        TankSelector selector;
        AbstractTank absTank;
        AlarmGroup<ObservableBoolean, ObservableBoolean> alarmGroup;
        
        IntReceivedListener colorListener = value ->
                absTank.setLiquid(new Liquid(absTank.getLiquid().getAmount(),
                        absTank.getLiquid().getTemperature(), new Color(value)));

        FloatReceivedListener fillLevelListener = value ->
                absTank.setLiquid(new Liquid(value,
                        absTank.getLiquid().getTemperature(), absTank.getLiquid().getColor()));

        FloatReceivedListener outputFlowRateListener = value ->
                absTank.getOutPipe().setValveThreshold((byte) value);

        BooleanReceivedListener overflowSensorListener = value -> alarmGroup.getOverflow().setValue(value);

        FloatReceivedListener temperatureListener = value ->
                absTank.setLiquid(new Liquid(absTank.getLiquid().getAmount(), value, absTank.getLiquid().getColor()));

        BooleanReceivedListener overheatSensorListener = value -> alarmGroup.getOverheat().setValue(value);

        BooleanReceivedListener undercoolSensorListener = value -> alarmGroup.getUndercool().setValue(value);

        BooleanReceivedListener underflowSensorListener = value -> alarmGroup.getUnderflow().setValue(value);

        ErrorListener errorListener = new ErrorListener() {
            public void onError(int code) {
                switch(code) {
                    case UAClientWrapper.ERROR_DISCONNECT:
                        System.err.println("The client disconnected from the server!");
                        Platform.runLater(() -> {
                            disableProgressions();
                            currentSettingsView.showDisconnectAlert();
                        });
                        break;
                    case UAClientWrapper.ERROR_DATA_TYPE_MISMATCH:
                        System.err.println("The data type found on the server does not match the one you requested!");
                        break;
                    case UAClientWrapper.ERROR_DATA_TYPE_UNSUPPORTED:
                        System.err.println("The server returned an unsupported data type!");
                        break;
                    default: 
                        System.err.println("Unknown OPC UA error occurred!");
                        break;
                }
            }
        };
    }
    
    private class TankContainer extends Container {
        private Tank tank;
        private TankClient client;

        FloatReceivedListener inputFlowRateListener = value -> tank.getInPipe().setValveThreshold((byte) value);
    }
    
    private class MixTankContainer extends Container {
        private MixTank tank;
        private MixTankClient client;

        IntReceivedListener motorSpeedListener = value -> mixCont.tank.getMotor().setRPM(value);
    }
}
