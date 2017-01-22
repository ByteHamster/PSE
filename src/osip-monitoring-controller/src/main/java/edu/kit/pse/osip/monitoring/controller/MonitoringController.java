package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.ProductionSite;

/**
 * The controller starts the monitoring view and initializes the model with the main loop. It is also responsible for the communication between the view and model.
 */
public class MonitoringController extends javafx.application.Application {
    /**
     * The current model.
     */
    private ProductionSite currentProductionSite;
    /**
     * Contains the current settings.
     */
    private ClientSettingsWrapper currentSettings;
    /**
     * The current entry point to the monitoring view.
     */
    private MonitoringViewInterface currentView;
    /**
     * Creates a new controller instance.
     */
    protected final void Controller () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Initializes all necessary objects.
     */
    public final void init () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when JavaFX creates and starts the application.
     * @param primaryStage The stage used for displaying all controls.
     */
    public final void start (javafx.stage.Stage primaryStage) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when the last window is closed.
     */
    public final void stop () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Initialize the UI
     * @param stage The javafx stage
     */
    private final void setupUI (javafx.stage.Stage stage) {
        throw new RuntimeException("Not implemented!");
    }
}
