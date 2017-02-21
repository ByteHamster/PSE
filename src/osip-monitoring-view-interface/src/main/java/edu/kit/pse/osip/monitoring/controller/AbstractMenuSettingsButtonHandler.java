package edu.kit.pse.osip.monitoring.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles a click on the settings menu button in the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.1
 */
public abstract class AbstractMenuSettingsButtonHandler implements EventHandler<ActionEvent> {
    /**
     * The current access point to the settings view.
     */
    protected SettingsViewInterface currentSettingsViewInterface;
    
    /**
     * Creates a new handler.
     * 
     * @param currentSettingsViewInterface The current access point to the settings view.
     */
    protected AbstractMenuSettingsButtonHandler(SettingsViewInterface currentSettingsViewInterface) {
        this.currentSettingsViewInterface = currentSettingsViewInterface;
    }
    
    /**
     * Handles a click on the settings menu button in the monitoring view.
     * 
     * @param event The occurred event.
     */
    public abstract void handle(ActionEvent event);
}
