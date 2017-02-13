package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import javafx.event.ActionEvent;

/**
 * Handler for a click on the save button in the settings view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class SettingsSaveButtonHandler extends AbstractSettingsSaveButtonHandler {
    /**
     * Creates a new handler.
     * 
     * @param settings The settings object to save the data
     * @param currentMonitoringViewInterface Stores the access point to the monitoring view to set the user-set
     * settings.
     * @param currentSettingsViewInterface The access point to the settings view.
     */
    protected SettingsSaveButtonHandler(ClientSettingsWrapper settings,
            MonitoringViewInterface currentMonitoringViewInterface,
            SettingsViewInterface currentSettingsViewInterface) {
        super(settings, currentMonitoringViewInterface, currentSettingsViewInterface);
    }

    @Override
    public void handle(ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
