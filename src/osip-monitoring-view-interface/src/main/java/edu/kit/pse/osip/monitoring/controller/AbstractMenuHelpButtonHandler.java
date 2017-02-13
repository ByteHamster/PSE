package edu.kit.pse.osip.monitoring.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles a click on the help menu button in the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public abstract class AbstractMenuHelpButtonHandler implements EventHandler<ActionEvent> {
    /**
     * Handles a click on the help menu button in the monitoring view.
     * 
     * @param event The occurred event.
     */
    public abstract void handle(ActionEvent event);
}
