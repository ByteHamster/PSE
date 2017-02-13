package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.monitoring.controller.AbstractSettingsCancelButtonHandler;
import edu.kit.pse.osip.monitoring.controller.AbstractSettingsSaveButtonHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * The main entry point for the settings view. Within, the user can set all available parameters for his needs.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
class SettingsMainWindow {
    /**
     * The window in which the settings are presented.
     */
    private Stage window;
    
    /**
     * Pane containing tabs with the different settings possibilities.
     */
    private TabPane tabsPane;
    
    /**
     * Tab for showing all general settings.
     */
    private Tab tabGeneralSettings;
    
    /**
     * Contains the ui elements for the general settings.
     */
    private GeneralSettings generalSettingsTab;
    
    /**
     * Tab for showing all settings regarding the progressions.
     */
    private Tab tabProgressions;
    
    /**
     * Contains all ui elements for the progressions tab.
     */
    private Progressions progressionsTab;
    
    /**
     * Tab for showing all settings regarding alarms.
     */
    private Tab tabAlarms;
    
    /**
     * Contains the ui elements for the alarm settings.
     */
    private AlarmSettings alarmsTab;
    
    /**
     * The button to cancel the setting of the settings.
     */
    private Button buttonCancel;
    
    /**
     * The button to save the current settings.
     */
    private Button buttonSave;
    
    /**
     * Creates all controls for displaying and setting the settings. It shows also the window.
     * 
     * @param currentSettings The current settings used for displaying.
     */
    protected SettingsMainWindow(ClientSettingsWrapper currentSettings) {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Returns the used window.
     * 
     * @return the current used window.
     */
    protected Stage getStage() {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Returns the current used GeneralSettings object for the "General Settings" tab.
     * 
     * @return the current used GeneralSettings object.
     */
    protected GeneralSettings getGeneralSettings() {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Returns the current used Progressions object containing all controls for the "Progressions" tab.
     * 
     * @return the current used Progressions object.
     */
    protected Progressions getProgressions() {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Returns the current AlarmSettings object used for the content of the "Alarm" tab.
     * 
     * @return the current used AlarmSettings object.
     */
    protected AlarmSettings getAlarmSettings() {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Sets the handler for the cancel button in the settings view.
     * 
     * @param handler The handler for the cancel button in the settings view.
     */
    protected void setSettingsCancelButtonHandler(AbstractSettingsCancelButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Sets the handler for the save button in the settings view.
     * 
     * @param handler The handler for the save button int eh settings view.
     */
    protected void setSettingsSaveButtonHandler(AbstractSettingsSaveButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
}
