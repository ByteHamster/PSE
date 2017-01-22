package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.monitoring.controller.SettingsCancelButtonHandler;
import edu.kit.pse.osip.monitoring.controller.SettingsSaveButtonHandler;

/**
 * The main entry point for the settings view. Within, the user can set all available parameters for his needs.
 */
public class SettingsMainWindow {
    public AlarmSettings alarmsTab;
    public Progressions progressionsTab;
    public GeneralSettings generalSettingsTab;
    /**
     * The window in which the settings are presented.
     */
    private javafx.stage.Stage window;
    /**
     * Pane containing tabs with the different settings possibilities.
     */
    private javafx.scene.control.TabPane tabsPane;
    /**
     * Tab for showing all general settings.
     */
    private javafx.scene.control.Tab tabGeneralSettings;
    /**
     * Tab for showing all settings regarding the progressions.
     */
    private javafx.scene.control.Tab tabProgressions;
    /**
     * Tab for showing all settings regarding alarms.
     */
    private javafx.scene.control.Tab tabAlarms;
    /**
     * The button to cancel the setting of the settings.
     */
    private javafx.scene.control.Button buttonCancel;
    /**
     * The button to save the current settings.
     */
    private javafx.scene.control.Button buttonSave;
    /**
     * Creates all controls for displaying and setting the settings. It shows also the window.
     * @param currentSettings The current settings used for displaying.
     */
    protected SettingsMainWindow (ClientSettingsWrapper currentSettings) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the used window
     * @return
     * the current used window.
     */
    protected final javafx.stage.Stage getStage () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the current used GeneralSettings fobject for the "General Settings" tab.
     * @return the current used GeneralSettings object.
     */
    protected final GeneralSettings getGeneralSettings () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the current used Progressions object containing all controls for the "Progressions" tab.
     * @return the current used Progressions object.
     */
    protected final Progressions getProgressions () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the current AlarmSettings object used for the content of the "Alarm" tab.
     * @return the current used AlarmSettings object.
     */
    protected final AlarmSettings getAlarmSettings () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the cancel button in the settings view.
     * @param handler The handler for the cancel button in the settings view.
     */
    protected final void setSettingsCancelButtonHandler (SettingsCancelButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the save button in the settings view.
     * @param handler The handler for the save button int eh settings view.
     */
    protected final void setSettingsSaveButtonHandler (SettingsSaveButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
}
