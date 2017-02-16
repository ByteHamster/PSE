package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Provides abstraction from the view and a single interface for accessing the user-set settings.
 * 
 * @author Martin Armbruster
 * @version 1.2
 */
public interface SettingsViewInterface {
    /**
     * Shows the settings view with the current settings.
     * 
     * @param currentSettings The current settings used for displaying them.
     */
    void showSettingsWindow(ClientSettingsWrapper currentSettings);
    
     /**
     * Hides the settings view.
     */
    void hideSettingsWindow();
    
    /**
     * Sets the handler for the cancel button in the settings view.
     * 
     * @param handler The handler for the cancel button in the settings view.
     */
    void setSettingsCancelButtonHandler(AbstractSettingsCancelButtonHandler handler);
    
    /**
     * Sets the handler for the save button in the settings view.
     * 
     * @param handler The handler for the save button in the settings view.
     */
    void setSettingsSaveButtonHandler(AbstractSettingsSaveButtonHandler handler);

    /**
     * Returns the host name for the servers.
     * 
     * @return the host name for the servers.
     */
    String getServerHostname();
    
    /**
     * Returns the port number for a specified server.
     * 
     * @return the port number of a specified server.
     * @param tank Selector of the tank / server whose port number will be returned.
     */
    int getServerPort(TankSelector tank);
    
    /**
     * Returns the update time interval.
     * 
     * @return the update time interval.
     */
    int getUpdateInterval();
    
    /**
     * Returns true if the underflow alarm should be enabled for a specified tank and false otherwise.
     * 
     * @return true if the underflow alarm should be enabled for the specified tank. false otherwise.
     * @param tank Tank whose value will be looked up.
     */
    boolean isUnderflowAlarmEnabled(TankSelector tank);
    
    /**
     * Returns true if the overflow alarm should be enabled for a specified tank and false otherwise.
     * 
     * @return true if the overflow alarm should be enabled for a specified tank. false otherwise.
     * @param tank The tank whose overflow alarm should be enabled or disabled.
     */
    boolean isOverflowAlarmEnabled(TankSelector tank);
    
    /**
     * Returns true if the overheating alarm for the temperature should be enabled for a specified tank
     * and false otherwise.
     * 
     * @return true if the overheating alarm for the temperature should be enabled for a specified tank
     * and false otherwise.
     * @param tank The specified tank.
     */
    boolean isTemperatureOverheatingAlarmEnabled(TankSelector tank);
    
    /**
     * Returns true if the undercooling alarm for the temperature should be enabled for a specified tank
     * and false otherwise.
     * 
     * @return true if the undercooling alarm for the temperature should be enabled for a specified tank
     * and false otherwise.
     * @param tank The specified tank.
     */
    boolean isTemperatureUndercoolingAlarmEnabled(TankSelector tank);
    
    /**
     * Returns true if the logging of the fill level progression should be enabled for a specified tank
     * and false otherwise.
     * 
     * @return true if the logging of the fill level progression should be enabled for the specified tank
     * or false otherwise.
     * @param tank The tank whose fill level progression should be enabled or disabled.
     */
    boolean isFillLevelProgressionEnabled(TankSelector tank);
    
    /**
     * Returns true if the logging of the temperature progression should be enabled for the specified tank
     * and false otherwise.
     * 
     * @return true if the logging of the temperature progression should be enabled for the specified tank.
     * false otherwise.
     * @param tank The tank whose temperature progression will be set.
     */
    boolean isTemperatureProgressionEnabled(TankSelector tank);
}
