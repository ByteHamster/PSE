package edu.kit.pse.osip.monitoring.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles a click on the cancel button in the settings view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public abstract class AbstractSettingsCancelButtonHandler implements EventHandler<ActionEvent> {
    /**
     * Contains the current SettingsViewInterface as access point to the settings view.
     */
    protected SettingsViewInterface currentSettingsViewInterface;
    
    /**
     * Creates a new handler.
     * 
     * @param currentSettingsViewInterface Current access point to the settings view.
     */
    protected AbstractSettingsCancelButtonHandler(SettingsViewInterface currentSettingsViewInterface) {
        this.currentSettingsViewInterface = currentSettingsViewInterface;
    }
    
    /**
     * Handles a click on the cancel button in the settings view.
     * 
     * @param event The occurred event.
     */
    public abstract void handle(ActionEvent event);
}
