package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Provides abstraction from the UI and a single interface to configure the monitoring view.
 */
public interface MonitoringViewInterface {
    /**
     * Shows the monitoring view to the user.
     * @param stage The stage used for displaying controls.
     */
    public void showMonitoringView (javafx.stage.Stage stage);
    /**
     * Enables or disables the underflow alarm for a specified tank.
     * @param tank The tank whose underflow alarm will be enabled or disabled.
     * @param alarmEnabled true if the alarm should be enabled and false otherwise.
     */
    public void setUnderflowAlarmEnabled (TankSelector tank, boolean alarmEnabled);
    /**
     * Enables or disables the overflow alarm for a specified tank.
     * @param tank The tank whose overflow alarm will be enabled or disabled.
     * @param alarmEnabled true if the alarm should be enabled and false otherwise.
     */
    public void setOverflowAlarmEnabled (TankSelector tank, boolean alarmEnabled);
    /**
     * Enables or disables the alarm when the temperature becomes too high for a specified tank.
     * @param tank The tank which alarm will be turned off or on.
     * @param alarmEnabled true when the alarm should be enabled. false otherwise.
     */
    public void setTemperatureOverflowAlarmEnabled (TankSelector tank, boolean alarmEnabled);
    /**
     * Enables or disables the temperature alarm when it becomes too low for a specified tank.
     * @param tank The tank which alarm will be turned off or on.
     * @param alarmEnabled true when the alarm should be enabled. false otherwise.
     */
    public void setTemperatureUnderflowAlarmEnabled (TankSelector tank, boolean alarmEnabled);
    /**
     * Enables or disables the logging of a fill level progression for a specified tank.
     * @param tank The tank whose fill level progression is logged or not.
     * @param progressionEnabled true if the fill level progression should be logged and false otherwise.
     */
    public void setFillLevelProgressionEnabled (TankSelector tank, boolean progressionEnabled);
    /**
     * Enables or disables the logging of the temperature progression of a specified tank.
     * @param tank The tank whose temperature progression is logged or not.
     * @param progressionEnabled true if the temperature progression should be logged and false otherwise.
     */
    public void setTemperatureProgressionEnabled (TankSelector tank, boolean progressionEnabled);
    /**
     * Sets the handler for the settings menu button.
     * @param handler The handler for the settings menu button.
     */
    public void setMenuSettingsButtonHandler (MenuSettingsButtonHandler handler);
    /**
     * Sets the handler for the about menu button.
     * @param handler The handler for the about menu button.
     */
    public void setMenuAboutButtonHandler (MenuAboutButtonHandler handler);
    /**
     * Sets the handler for the help menu button.
     * @param handler The handler handles a click on the help menu button.
     */
    public void setMenuHelpButtonHandler (MenuHelpButtonHandler handler);
}
