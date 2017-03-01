package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;
import java.util.function.Consumer;
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
    void start(Stage primaryStage, SimulationControlInterface controlWindow);

    /**
     * The simulation is replaced by the OverflowOverlay.
     * @param selector The overflowing tank.
     */
    void showOverflow(TankSelector selector);

    /**
     * Set a handler which gets called if the users closes the overflow dialog
     * @param handler The handler to set.
     */
    void setOverflowClosedHandler(EventHandler<ActionEvent> handler);

    /**
     * Set a handler which gets called if the users closes the overflow dialog
     * @param handler The handler to set.
     */
    void setHelpButtonHandler(EventHandler<ActionEvent> handler);

    /**
     * Set a handler which gets called if the users closes the overflow dialog
     * @param handler The handler to set.
     */
    void setAboutButtonHandler(EventHandler<ActionEvent> handler);

    /**
     * Sets the handler for pressing the settings entry in the menu
     * @param settingsButtonHandler The handler to be called when the settings button is pressed
     */
    void setSettingsButtonHandler(EventHandler<ActionEvent> settingsButtonHandler);

    /**
     * Sets the handler for pressing the control entry in the menu
     * @param controlButtonHandler The handler to execute
     */
    void setControlButtonHandler(EventHandler<ActionEvent> controlButtonHandler);

    /**
     * Sets the listener to start a scenario.
     * @param listener The listener called if the user starts a scenario.
     *                 The parameter is the path tot he scenario file.
     */
    void setScenarioStartListener(Consumer<String> listener);

    /**
     * Sets the handler called if the scenario gets stopped by the user.
     * @param listener The handler function.
     */
    void setScenarioStopListener(Runnable listener);

    /**
     * Show an error about the scenario
     * @param error The error message.
     */
    void showScenarioError(String error);

    /**
     * Tell the view the scenario is finished (either stopped by the user, finished or if it has an error).
     */
    void scenarioFinished();
}
