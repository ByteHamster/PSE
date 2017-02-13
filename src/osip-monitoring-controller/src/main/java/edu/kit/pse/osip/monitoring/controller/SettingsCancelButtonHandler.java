package edu.kit.pse.osip.monitoring.controller;

import javafx.event.ActionEvent;

/**
 * Handles a click on the cancel button in the settings view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class SettingsCancelButtonHandler extends AbstractSettingsCancelButtonHandler {
    /**
     * Creates a new handler.
     * 
     * @param currentSettingsViewInterface Current access point to the settings view.
     */
    protected SettingsCancelButtonHandler(SettingsViewInterface currentSettingsViewInterface) {
        super(currentSettingsViewInterface);
    }
    
    @Override
    public void handle(ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
