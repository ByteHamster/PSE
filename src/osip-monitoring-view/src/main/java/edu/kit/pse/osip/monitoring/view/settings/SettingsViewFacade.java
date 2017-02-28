package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.controller.SettingsViewInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

/**
 * Provides a single access point to the user-set settings.
 * 
 * @author Martin Armbruster
 * @version 1.3
 */
public class SettingsViewFacade implements SettingsViewInterface {
    /**
     * The current settings view containing the user-set settings.
     */
    private SettingsMainWindow currentSettingsWindow;
    private Alert disconnectAlert;
    private Alert cannotConnectAlert;
    
    /**
     * Creates a new facade for accessing the settings in the settings view.
     * 
     * @param currentSettings The current settings used to initialize the settings window.
     */
    public SettingsViewFacade(ClientSettingsWrapper currentSettings) {
        currentSettingsWindow = new SettingsMainWindow(currentSettings);

        Translator t = Translator.getInstance();
        disconnectAlert = new Alert(Alert.AlertType.ERROR);
        disconnectAlert.setTitle(t.getString("monitoring.settings.disconnectalert.title"));
        disconnectAlert.setHeaderText(null);
        disconnectAlert.setContentText(t.getString("monitoring.settings.disconnectalert.text"));

        cannotConnectAlert = new Alert(Alert.AlertType.ERROR);
        cannotConnectAlert.setTitle(t.getString("monitoring.settings.cannotconnectalert.title"));
        cannotConnectAlert.setHeaderText(null);
        cannotConnectAlert.setContentText(t.getString("monitoring.settings.cannotconnectalert.text"));

    }
    
    @Override
    public void showSettingsWindow() {
        currentSettingsWindow.getStage().show();
    }
    
    @Override
    public void showDisconnectAlert() {
        showSettingsWindow();
        currentSettingsWindow.setCancelButtonDisabled(true);
        disconnectAlert.show();
    }
    
    @Override
    public void showCanNotConnectAlert() {
        showSettingsWindow();
        currentSettingsWindow.setCancelButtonDisabled(true);
        cannotConnectAlert.show();
    }
    
    @Override
    public void setSettingsCancelButtonHandler(EventHandler<ActionEvent> handler) {
        currentSettingsWindow.setSettingsCancelButtonHandler(handler);
    }
    
    @Override
    public void setSettingsSaveButtonHandler(EventHandler<ActionEvent> handler) {
        currentSettingsWindow.setSettingsSaveButtonHandler(handler);
    }
    
    @Override
    public void hideSettingsWindow() {
        currentSettingsWindow.getStage().hide();
        currentSettingsWindow.setCancelButtonDisabled(false);
    }
    
    @Override
    public String getServerHostname() {
        return currentSettingsWindow.getGeneralSettings().getServerHost();
    }
    
    @Override
    public int getServerPort(TankSelector tank) {
        return currentSettingsWindow.getGeneralSettings().getServerPort(tank);
    }
    
    @Override
    public int getUpdateInterval() {
        return currentSettingsWindow.getGeneralSettings().getUpdateInterval();
    }
    
    @Override
    public boolean isUnderflowAlarmEnabled(TankSelector tank) {
        return currentSettingsWindow.getAlarmSettings().isUnderflowEnabled(tank);
    }
    
    @Override
    public boolean isOverflowAlarmEnabled(TankSelector tank) {
        return currentSettingsWindow.getAlarmSettings().isOverflowEnabled(tank);
    }
    
    @Override
    public boolean isTemperatureOverheatingAlarmEnabled(TankSelector tank) {
        return currentSettingsWindow.getAlarmSettings().isTemperatureOverheatingEnabled(tank);
    }
    
    @Override
    public boolean isTemperatureUndercoolingAlarmEnabled(TankSelector tank) {
        return currentSettingsWindow.getAlarmSettings().isTemperatureUndercoolingEnabled(tank);
    }
    
    @Override
    public boolean isFillLevelProgressionEnabled(TankSelector tank) {
        return currentSettingsWindow.getProgressions().isFillLevelEnabled(tank);
    }
    
    @Override
    public boolean isTemperatureProgressionEnabled(TankSelector tank) {
        return currentSettingsWindow.getProgressions().isTemperatureEnabled(tank);
    }
}
