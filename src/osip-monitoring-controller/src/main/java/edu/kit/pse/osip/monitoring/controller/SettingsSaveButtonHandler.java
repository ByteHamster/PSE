package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewFacade;
import edu.kit.pse.osip.monitoring.view.settings.SettingsViewFacade;

/**
 * Handler for a click on the save button in the settings view.
 */
public class SettingsSaveButtonHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
    /**
     * Stores the access point to the monitoring view to set the user-set settings.
     */
    private MonitoringViewFacade currentMonitoringViewFacade;
    /**
     * The access point to the current settings view.
     */
    private SettingsViewFacade currentSettingsViewFacade;
    /**
     * The current settings which will be set to the user-set settings.
     */
    private ClientSettingsWrapper currentSettings;
    /**
     * Creates a new handler.
     * @param settings The settings object to save the data
     * @param currentMonitoringViewFacade Stores the access point to the monitoring view to set the user-set settings.
     * @param currentSettingsViewFacade The facade to the settings view.
     */
    protected SettingsSaveButtonHandler (ClientSettingsWrapper settings, MonitoringViewFacade currentMonitoringViewFacade, SettingsViewFacade currentSettingsViewFacade) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Handles a click on the save button in the settings view.
     * @param event The occured event.
     */
    public final void handle (javafx.event.ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
