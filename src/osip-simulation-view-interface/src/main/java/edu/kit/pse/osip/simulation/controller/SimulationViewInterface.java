package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * Provides abstraction from the view.
 * 
 * @author Niko Wilhelm
 * @version 1.0
 */
public interface SimulationViewInterface {
    /**
     * Draws the simulation view to the stage.
     * 
     * @param primaryStage The stage that is provided by JavaFX.
     */
    void start(Stage primaryStage);

    /**
     * The simulation is replaced by the OverflowOverlay.
     * 
     * @param tank The overflowing tank.
     */
    void showOverflow(AbstractTank tank);

    /**
     * Sets a handler which gets called if the users closes the overflow dialog.
     * 
     * @param handler The handler to set.
     */
    void setHelpButtonHandler(EventHandler<ActionEvent> handler);

    /**
     * Sets the handler for pressing the about entry in the menu.
     * 
     * @param handler The handler to be called when the about button is pressed.
     */
    void setAboutButtonHandler(EventHandler<ActionEvent> handler);

    /**
     * Sets the handler for pressing the settings entry in the menu.
     * 
     * @param settingsButtonHandler The handler to be called when the settings button is pressed.
     */
    void setSettingsButtonHandler(EventHandler<ActionEvent> settingsButtonHandler);

    /**
     * Sets the handler for pressing the control entry in the menu.
     * 
     * @param controlButtonHandler The handler to execute.
     */
    void setControlButtonHandler(EventHandler<ActionEvent> controlButtonHandler);

    /**
     * Sets the listener to start a scenario.
     * 
     * @param listener The listener called if the user starts a scenario.
     *                 The parameter is the path to the scenario file.
     */
    void setScenarioStartListener(Consumer<String> listener);

    /**
     * Sets the listener to reset the simulation.
     * 
     * @param listener The listener to be executed.
     */
    void setResetListener(EventHandler<ActionEvent> listener);

    /**
     * Sets the handler called if the scenario gets stopped by the user.
     * 
     * @param listener The handler function.
     */
    void setScenarioStopListener(Runnable listener);

    /**
     * Shows an error about the scenario.
     * 
     * @param error The error message.
     */
    void showScenarioError(String error);

    /**
     * Tells the view the scenario has finished (either stopped by the user, finished or if it has an error).
     */
    void scenarioFinished();

    /**
     * Tells the view the scenario has started.
     */
    void scenarioStarted();

    /**
     * Shows an OPC UA Server error dialog.
     * 
     * @param message The error message to show.
     */
    void showOPCUAServerError(String message);

    /**
     * Shows and hides the progress indicator.
     * 
     * @param visible If the indicator should be visible.
     */
    void setProgressIndicatorVisible(boolean visible);
}
