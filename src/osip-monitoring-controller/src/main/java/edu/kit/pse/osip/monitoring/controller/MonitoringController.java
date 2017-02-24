package edu.kit.pse.osip.monitoring.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewFacade;
import edu.kit.pse.osip.monitoring.view.dialogs.AboutDialog;
import edu.kit.pse.osip.monitoring.view.dialogs.HelpDialog;
import edu.kit.pse.osip.monitoring.view.settings.SettingsViewFacade;
import javafx.application.Application;
import javafx.stage.Stage;

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
        try {
            File tempTestFile = File.createTempFile("testClientSettingsTemp", ".properties");        
            PrintWriter outStream = new PrintWriter(tempTestFile);        
            outStream.print("fetchInterval=80");
            outStream.println();
            outStream.print("overflowAlarm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
            outStream.println();
            outStream.print("underflowAlarm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
            outStream.println();
            outStream.print("overheatingAlarm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
            outStream.println();
            outStream.print("undercoolingAlarm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
            outStream.println();
            outStream.print("tempDiagram_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
            outStream.println();
            outStream.print("fillLevelDiagram_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
            outStream.println();
            outStream.print("serverHostname_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "hostNAME");
            outStream.println();
            outStream.print("serverPort_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "4242");
            outStream.println();
            outStream.close();
            currentSettings = new ClientSettingsWrapper(tempTestFile);
        } catch (IOException ioExc) {
            ioExc.printStackTrace();
      }
        currentView = new MonitoringViewFacade();
        currentSettingsView = new SettingsViewFacade(currentSettings);
    }
    
    /**
     * Initializes all necessary objects.
     */
    public void init() {
        //throw new RuntimeException("Not implemented!");
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
