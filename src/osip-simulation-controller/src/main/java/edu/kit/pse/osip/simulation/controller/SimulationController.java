package edu.kit.pse.osip.simulation.controller;

import java.util.Observable;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.simulation.ProductionSiteSimulation;
import edu.kit.pse.osip.simulation.view.main.SimulationMainWindow;

/**
 * Manages servers and controls view actions.
 */
public class SimulationController extends javafx.application.Application implements java.util.Observer {    
    private SimulationViewInterface currentSimulationView;   
    private ProductionSiteSimulation productionSite;   
    private PhysicsSimulator simulator;   
    private TankServer inputServer;   
    private MixTankServer mixServer;
    /**
     * Responsible for controlling the display windows and simulating the production
     */
    public SimulationController() {
        throw new RuntimeException("Not implemented!");
        
    }
    /**
     * Called bx JavaFx to start drawing the UI
     * @param primaryStage The stage to draw the main window on
     */
    public final void start(javafx.stage.Stage primaryStage) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Show windows and start loop that updates the values
     */
    public final void startMainLoop() {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Update values from model inside the servers
     */
    private void updateServerValues() {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Check if threre is an alarm that needs to be set in the servers
     */
    private void checkAlarms() {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when the last window is closed
     */
    public final void stop() {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Initialize the UI
     * @param stage The javafx stage
     */
    private void setupUI(javafx.stage.Stage stage) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * One of the observed objects changed its state
     * @param observable Observed object
     * @param object New value
     */
    public final void update(Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
}
