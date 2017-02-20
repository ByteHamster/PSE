package edu.kit.pse.osip.simulation.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Handles a click on the help menu button in the simulation view.
 *
 * @author Niko Wilhelm
 * @version 1.0
 */
public abstract class AbstractMenuHelpButtonHandler implements EventHandler<ActionEvent> {

    /**
     * Handles a click on the help menu button in the simulation view.
     * @param event The ocurred event.
     */
    public abstract void handle(ActionEvent event);
}
