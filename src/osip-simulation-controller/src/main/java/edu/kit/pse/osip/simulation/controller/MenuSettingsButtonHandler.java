package edu.kit.pse.osip.simulation.controller;

/**
 * Handles a click on the settings menu button
 */
public class MenuSettingsButtonHandler extends AbstractMenuSettingsButton {
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
