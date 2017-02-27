package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewFacade;
import edu.kit.pse.osip.monitoring.view.dialogs.AboutDialog;
import edu.kit.pse.osip.monitoring.view.dialogs.HelpDialog;
import edu.kit.pse.osip.monitoring.view.settings.SettingsViewFacade;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

/**
 * The controller starts the monitoring view and initializes the model with the main loop.
 * It is also responsible for the communication between the view and model.
 * 
 * @author Martin Armbruster
 * @version 1.1
 */
public final class MonitoringController extends Application {
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
     * The current entry point for the settings view.
     */
    private SettingsViewInterface currentSettingsView;
    
    /**
     * Creates a new controller instance.
     */
    public MonitoringController() {
        currentProductionSite = new ProductionSite();
        File settingsLocation = new File(System.getProperty("user.home") + File.separator + ".osip");
        settingsLocation.mkdirs();
        currentSettings = new ClientSettingsWrapper(new File(settingsLocation, "monitoring.conf"));
        currentView = new MonitoringViewFacade();
        currentSettingsView = new SettingsViewFacade(currentSettings);
    }
    
    /**
     * Initializes all necessary objects.
     */
    public void init() {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Called when JavaFX creates and starts the application.
     * 
     * @param primaryStage The stage used for displaying all controls.
     */
    public void start(Stage primaryStage) {
        setupUI(primaryStage);
    }
    
    /**
     * Initializes the UI.
     * 
     * @param stage The JavaFX stage.
     */
    private void setupUI(Stage stage) {
        currentView.showMonitoringView(stage, currentProductionSite);
        currentView.setMenuSettingsButtonHandler(event -> currentSettingsView.showSettingsWindow());
        HelpDialog helpDialog = new HelpDialog();
        currentView.setMenuHelpButtonHandler(event -> helpDialog.show());
        AboutDialog aboutDialog = new AboutDialog();
        currentView.setMenuAboutButtonHandler(event -> aboutDialog.show());
        currentSettingsView.setSettingsCancelButtonHandler(event -> currentSettingsView.hideSettingsWindow());
//        currentSettingsView.setSettingsSaveButtonHandler(new SettingsSaveButtonHandler(currentSettings,
//                currentView, currentSettingsView));
    }

    /**
     * Called when the last window is closed.
     */
    public void stop() {
        throw new RuntimeException("Not implemented!");
    }
}
