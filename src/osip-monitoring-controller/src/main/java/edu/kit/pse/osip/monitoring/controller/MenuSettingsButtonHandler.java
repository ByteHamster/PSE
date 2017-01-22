package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.monitoring.view.settings.SettingsViewFacade;

/**
 * Handles a click on the settings menu button in the monitoring view.
 */
public class MenuSettingsButtonHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
    /**
     * The current settings.
     */
    private ClientSettingsWrapper currentSettings;
    /**
     * The current facade to the settings view.
     */
    private SettingsViewFacade currentSettingsViewFacade;
    /**
     * Creates a new handler.
     * @param currentSettings The current settings for displaying.
     * @param currentSettingsViewFacade The current facade for the settings view.
     */
    protected MenuSettingsButtonHandler (ClientSettingsWrapper currentSettings, SettingsViewFacade currentSettingsViewFacade) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Handles a click on the settings menu button in the monitoring view.
     * @param event The occured event.
     */
    public final void handle (javafx.event.ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
