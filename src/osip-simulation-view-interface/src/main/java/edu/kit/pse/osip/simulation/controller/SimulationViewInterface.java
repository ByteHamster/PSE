package edu.kit.pse.osip.simulation.controller;

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
    public void setOverflowClosedHandler(OverflowClosedHandler handler);
}
