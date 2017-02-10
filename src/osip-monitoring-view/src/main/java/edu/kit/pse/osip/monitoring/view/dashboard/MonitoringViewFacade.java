package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.monitoring.controller.MenuAboutButtonHandler;
import edu.kit.pse.osip.monitoring.controller.MenuHelpButtonHandler;
import edu.kit.pse.osip.monitoring.controller.MenuSettingsButtonHandler;

/**
 * Provides one single access point to the monitoring view.
 */
public class MonitoringViewFacade implements edu.kit.pse.osip.monitoring.controller.MonitoringViewInterface {
    public MonitoringMainWindow mainWindow;
    /**
     * Shows the monitoring view to the user.
     * @param stage The stage used for displaying the controls.
     */
    public final void showMonitoringView (javafx.stage.Stage stage) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the underflow alarm for a specified tank.
     * @param tank The tank whose underflow alarm will be enabled or disabled.
     * @param alarmEnabled true if the alarm should be enabled and false otherwise.
     */
    public final void setUnderflowAlarmEnabled (TankSelector tank, boolean alarmEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the overflow alarm for a specified tank.
     * @param tank The tank whose overflow alarm will be enabled or disabled.
     * @param alarmEnabled true if the alarm should be enabled and false otherwise.
     */
    public final void setOverflowAlarmEnabled (TankSelector tank, boolean alarmEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the alarm when the temperature becomes too high for a specified tank.
     * @param tank The tank which alarm will be enabled or disabled.
     * @param alarmEnabled true when the alarm should be enabled. false otherwise.
     */
    public final void setTemperatureOverheatingAlarmEnabled (TankSelector tank, boolean alarmEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the temperature alarm when it becomes too low for a specified tank.
     * @param tank The tank which alarm will be turned on or off.
     * @param alarmEnabled true when the alarm should be enabled. false otherwise.
     */
    public final void setTemperatureUndercoolingAlarmEnabled (TankSelector tank, boolean alarmEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the logging of a fill level progression for a specified tank.
     * @param tank The tank whose fill level progression is logged or not.
     * @param progressionEnabled true if the fill level progression should be logged and false otherwise.
     */
    public final void setFillLevelProgressionEnabled (TankSelector tank, boolean progressionEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the logging of the temperature progression of a specified tank.
     * @param tank The tank whose temperature progression is logged or not.
     * @param progressionEnabled true if the temperature progression should be logged and false otherwise.
     */
    public final void setTemperatureProgressionEnabled (TankSelector tank, boolean progressionEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the settings menu button.
     * @param handler The handler for the settings menu button.
     */
    public final void setMenuSettingsButtonHandler (MenuSettingsButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the about menu button.
     * @param handler The handler for the about menu button.
     */
    public final void setMenuAboutButtonHandler (MenuAboutButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the help menu button.
     * @param handler The handler handles a click on the help menu button.
     */
    public final void setMenuHelpButtonHandler (MenuHelpButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
}
