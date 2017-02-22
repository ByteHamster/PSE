package edu.kit.pse.osip.simulation.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Provides abstraction from the view
 */
public interface SimulationViewInterface {

    /**
     * Draw the simulation view to the stage
     * @param primaryStage The stage that is provided by JavaFx
     */
    void start(Stage primaryStage);

    /**
     * The simulation is replaced by the OverflowOverlay.
     */
    public void showOverflow ();

    /**
     * Set a handler which gets called if the users closes the overflow dialog
     * @param handler The handler to set.
     */
    public void setOverflowClosedHandler(EventHandler<ActionEvent> handler);

    /**
     * Set a handler which gets called if the users closes the overflow dialog
     * @param handler The handler to set.
     */
    public void setHelpButtonHandler(EventHandler<ActionEvent> handler);

    /**
     * Set a handler which gets called if the users closes the overflow dialog
     * @param handler The handler to set.
     */
    public void setAboutButtonHandler(EventHandler<ActionEvent> handler);

    /**
     * Sets the handler for pressing the settings entry in the menu
     * @param settingsButtonHandler The handler to be called when the settings button is pressed
     */
    public void setSettingsButtonHandler(EventHandler<ActionEvent> settingsButtonHandler);

    /**
     * Sets the handler for pressing the control entry in the menu
     * @param controlButtonHandler The handler to execute
     */
    public void setControlButtonHandler(EventHandler<ActionEvent> controlButtonHandler);
}
