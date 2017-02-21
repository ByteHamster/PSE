package edu.kit.pse.osip.monitoring.controller;

import javafx.event.ActionEvent;

/**
 * Handles a click on the settings menu button in the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.3
 */
public class MenuSettingsButtonHandler extends AbstractMenuSettingsButtonHandler {
    /**
     * Creates a new handler.
     * 
     * @param currentSettingsViewInterface The current access point to the settings view.
     */
    protected MenuSettingsButtonHandler(SettingsViewInterface currentSettingsViewInterface) {
        super(currentSettingsViewInterface);
    }
    
    @Override
    public void handle(ActionEvent event) {
        currentSettingsViewInterface.showSettingsWindow();
    }
}
