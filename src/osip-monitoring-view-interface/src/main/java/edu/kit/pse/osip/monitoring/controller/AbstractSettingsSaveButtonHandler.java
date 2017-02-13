package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handler for a click on the save button in the settings view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public abstract class AbstractSettingsSaveButtonHandler implements EventHandler<ActionEvent> {
    /**
     * Stores the access point to the monitoring view to set the user-set settings.
     */
    protected MonitoringViewInterface currentMonitoringViewInterface;
    
    /**
     * The access point to the current settings view.
     */
    protected SettingsViewInterface currentSettingsViewInterface;
    
    /**
     * The current settings which will be set to the user-set settings.
     */
    protected ClientSettingsWrapper currentSettings;
    
    /**
     * Creates a new handler.
     * 
     * @param settings The settings object to save the data
     * @param currentMonitoringViewInterface Stores the access point to the monitoring view to set the user-set
     * settings.
     * @param currentSettingsViewInterface The access point to the settings view.
     */
    protected AbstractSettingsSaveButtonHandler(ClientSettingsWrapper settings,
            MonitoringViewInterface currentMonitoringViewInterface,
            SettingsViewInterface currentSettingsViewInterface) {
        this.currentSettings = settings;
        this.currentMonitoringViewInterface = currentMonitoringViewInterface;
        this.currentSettingsViewInterface = currentSettingsViewInterface;
    }
    
    /**
     * Handles a click on the save button in the settings view.
     * 
     * @param event The occurred event.
     */
    public abstract void handle(ActionEvent event);
}
