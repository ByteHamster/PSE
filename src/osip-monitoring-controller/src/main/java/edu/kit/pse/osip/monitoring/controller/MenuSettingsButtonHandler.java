package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import javafx.event.ActionEvent;

/**
 * Handles a click on the settings menu button in the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class MenuSettingsButtonHandler extends AbstractMenuSettingsButtonHandler {
    /**
     * Creates a new handler.
     * 
     * @param currentSettings The current settings for displaying.
     * @param currentSettingsViewInterface The current access point to the settings view.
     */
    protected MenuSettingsButtonHandler(ClientSettingsWrapper currentSettings,
            SettingsViewInterface currentSettingsViewInterface) {
        super(currentSettings, currentSettingsViewInterface);
    }
    
    @Override
    public void handle(ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
