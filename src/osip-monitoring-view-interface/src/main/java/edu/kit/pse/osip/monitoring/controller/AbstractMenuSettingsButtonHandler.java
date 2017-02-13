package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles a click on the settings menu button in the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public abstract class AbstractMenuSettingsButtonHandler implements EventHandler<ActionEvent> {
    /**
     * The current settings.
     */
    protected ClientSettingsWrapper currentSettings;
    
    /**
     * The current access point to the settings view.
     */
    protected SettingsViewInterface currentSettingsViewInterface;
    
    /**
     * Creates a new handler.
     * 
     * @param currentSettings The current settings for displaying.
     * @param currentSettingsViewInterface The current access point to the settings view.
     */
    protected AbstractMenuSettingsButtonHandler(ClientSettingsWrapper currentSettings,
            SettingsViewInterface currentSettingsViewInterface) {
        this.currentSettings = currentSettings;
        this.currentSettingsViewInterface = currentSettingsViewInterface;
    }
    
    /**
     * Handles a click on the settings menu button in the monitoring view.
     * 
     * @param event The occurred event.
     */
    public abstract void handle(ActionEvent event);
}
