package edu.kit.pse.osip.simulation.controller;

/**
 * Handles a click on the settings menu button
 */
public class MenuSettingsButtonHandler extends MenuSettingsButtonInterface {
    /**
     * Creates a new handler.
     * @param settingsWindow The current control window
     */
    protected MenuSettingsButtonHandler(SimulationSettingsInterface settingsWindow) {
        super(settingsWindow);
    }

    /**
     * Handles a click on the settings menu button in the monitoring view.
     * @param event The occured event.
     */
    public final void handle(javafx.event.ActionEvent event) {
        throw new RuntimeException("Not implemented!");
    }
}
