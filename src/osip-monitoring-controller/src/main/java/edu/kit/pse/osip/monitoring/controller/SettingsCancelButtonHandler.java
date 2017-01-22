package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.monitoring.view.settings.SettingsViewFacade;

/**
 * Handles a click on the cancel button in the settings view.
 */
public class SettingsCancelButtonHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {
    /**
     * Contains the current SettingsViewFacade as access point to the settings view.
     */
    private SettingsViewFacade currentSettingsViewFacade;
    /**
     * Creates a new handler.
     * @param currentSettingsViewFacade The current facade to access the settings view.
     */
    protected SettingsCancelButtonHandler (SettingsViewFacade currentSettingsViewFacade) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Handles a click on the cancel button in the settings view.
     * @param event The occured event.
     */
    public final void handle (javafx.event.ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
