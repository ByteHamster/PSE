package edu.kit.pse.osip.simulation.controller;

/**
 * Handles a click on the settings menu button
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public abstract class MenuSettingsButtonInterface {
    /**
     * The current simulation settings window to be shown
     */
    protected SimulationSettingsInterface settingsWindow;

    /**
     * Creates a new handler.
     * @param settingsWindow The current control window
     */
    protected MenuSettingsButtonInterface(SimulationSettingsInterface settingsWindow) {
        this.settingsWindow = settingsWindow;
    }

    /**
     * Handles a click on the settings menu button in the monitoring view.
     * @param event The occured event.
     */
    public abstract void handle(javafx.event.ActionEvent event);
}
