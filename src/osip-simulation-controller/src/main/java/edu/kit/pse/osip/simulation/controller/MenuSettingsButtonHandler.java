package edu.kit.pse.osip.simulation.controller;

/**
 * Handles a click on the settings menu button
 *
 * @version 0.1
 * @author Niko Wilhelm
 */
public class MenuSettingsButtonHandler extends AbstractMenuSettingsButtonHandler {

    /**
     * Creates a new handler.
     * @param settingsWindow The current control window
     */
    protected MenuSettingsButtonHandler(SimulationSettingsInterface settingsWindow) {
        super(settingsWindow);
    }

    @Override
    public final void handle(javafx.event.ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
