package edu.kit.pse.osip.simulation.controller;

/**
 * Provides abstraction from the view
 */
public interface SimulationViewInterface {
    /**
     * Draw the simulation view to the stage
     * @param primaryStage The stage that is provided by JavaFx
     */
    public void start (javafx.stage.Stage primaryStage);
    /**
     * The simulation is replaced by the OverflowOverlay.
     */
    public void showOverflow ();
}
