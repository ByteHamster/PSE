package edu.kit.pse.osip.simulation.controller;

/**
 * Handles a click on the settings menu button
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public abstract class AbstractMenuSettingsButton {
    /**
     * The current simulation settings window to be shown
     */
    protected SimulationSettingsInterface settingsWindow;

    /**
     * Creates a new handler.
     * @param settingsWindow The current control window
     */
    protected AbstractMenuSettingsButton(SimulationSettingsInterface settingsWindow) {
        this.settingsWindow = settingsWindow;
    }

    /**
     * Handles a click on the settings menu button in the simulation view.
     * @param event The occurred event.
     */
    public abstract void handle(javafx.event.ActionEvent event);
}
