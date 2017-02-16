package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.simulation.view.control.SimulationControlWindow;
import javafx.event.ActionEvent;

/**
 * Handles a click on the control menu button
 *
 * @version 0.1
 * @author Niko Wilhelm
 */
public class MenuControlButtonHandler extends AbstractMenuControlButton {

    /**
     * Creates a new handler.
     * @param controlWindow The current control window
     */
    protected MenuControlButtonHandler(SimulationControlWindow controlWindow) {
        super(controlWindow);
    }

    /**
     * Handles a click on the settings menu button in the monitoring view.
     * @param event The occured event.
     */
    @Override
    public final void handle(ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
