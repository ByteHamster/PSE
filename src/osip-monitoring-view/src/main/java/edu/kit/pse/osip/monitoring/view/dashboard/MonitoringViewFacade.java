package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.monitoring.controller.MenuAboutButtonHandler;
import edu.kit.pse.osip.monitoring.controller.MenuHelpButtonHandler;
import edu.kit.pse.osip.monitoring.controller.MenuSettingsButtonHandler;
import edu.kit.pse.osip.monitoring.controller.MonitoringViewInterface;
import javafx.stage.Stage;

/**
 * Provides one single access point to the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public final class MonitoringViewFacade implements MonitoringViewInterface {
    /**
     * Saves the current used main window. All method invocations are directed to this object.
     */
    private MonitoringMainWindow mainWindow;
    
    /**
     * Shows the monitoring view to the user.
     * 
     * @param stage The stage used for displaying the controls.
     * @param currentModel The current model used to initialize the observable objects and all initial values.
     */
    public void showMonitoringView(Stage stage, ProductionSite currentModel) {
        mainWindow = new MonitoringMainWindow(stage, currentModel);
    }
    
    /**
     * Enables or disables the underflow alarm for a specified tank.
     * 
     * @param tank The tank whose underflow alarm will be enabled or disabled.
     * @param alarmEnabled true if the alarm should be enabled and false otherwise.
     */
    public void setUnderflowAlarmEnabled(TankSelector tank, boolean alarmEnabled) {
        mainWindow.getTank(tank).setUnderflowAlarmEnabled(alarmEnabled);
    }
    
    /**
     * Enables or disables the overflow alarm for a specified tank.
     * 
     * @param tank The tank whose overflow alarm will be enabled or disabled.
     * @param alarmEnabled true if the alarm should be enabled and false otherwise.
     */
    public void setOverflowAlarmEnabled(TankSelector tank, boolean alarmEnabled) {
        mainWindow.getTank(tank).setOverflowAlarmEnabled(alarmEnabled);
    }
    
    /**
     * Enables or disables the alarm when the temperature becomes too high for a specified tank.
     * 
     * @param tank The tank which alarm will be enabled or disabled.
     * @param alarmEnabled true when the alarm should be enabled. false otherwise.
     */
    public final void setTemperatureOverheatingAlarmEnabled(TankSelector tank, boolean alarmEnabled) {
        mainWindow.getTank(tank).setTemperatureOverheatingAlarmEnabled(alarmEnabled);
    }
    
    /**
     * Enables or disables the temperature alarm when it becomes too low for a specified tank.
     * 
     * @param tank The tank which alarm will be turned on or off.
     * @param alarmEnabled true when the alarm should be enabled. false otherwise.
     */
    public void setTemperatureUndercoolingAlarmEnabled(TankSelector tank, boolean alarmEnabled) {
        mainWindow.getTank(tank).setTemperatureUndercoolingAlarmEnabled(alarmEnabled);
    }
    
    /**
     * Enables or disables the logging of a fill level progression for a specified tank.
     * 
     * @param tank The tank whose fill level progression is logged or not.
     * @param progressionEnabled true if the fill level progression should be logged and false otherwise.
     */
    public void setFillLevelProgressionEnabled(TankSelector tank, boolean progressionEnabled) {
        mainWindow.getTank(tank).setFillLevelProgressionEnabled(progressionEnabled);
    }
    
    /**
     * Enables or disables the logging of the temperature progression of a specified tank.
     * 
     * @param tank The tank whose temperature progression is logged or not.
     * @param progressionEnabled true if the temperature progression should be logged and false otherwise.
     */
    public void setTemperatureProgressionEnabled(TankSelector tank, boolean progressionEnabled) {
        mainWindow.getTank(tank).setTemperatureProgressionEnabled(progressionEnabled);
    }
    
    /**
     * Sets the handler for the settings menu button.
     * 
     * @param handler The handler for the settings menu button.
     */
    public void setMenuSettingsButtonHandler(MenuSettingsButtonHandler handler) {
        mainWindow.getMonitoringMenu().setMenuSettingsButtonHandler(handler);
    }
    
    /**
     * Sets the handler for the about menu button.
     * 
     * @param handler The handler for the about menu button.
     */
    public void setMenuAboutButtonHandler(MenuAboutButtonHandler handler) {
        mainWindow.getMonitoringMenu().setMenuAboutButtonHandler(handler);
    }
    
    /**
     * Sets the handler for the help menu button.
     * 
     * @param handler The handler handles a click on the help menu button.
     */
    public void setMenuHelpButtonHandler(MenuHelpButtonHandler handler) {
        mainWindow.getMonitoringMenu().setMenuHelpButtonHandler(handler);
    }
}
