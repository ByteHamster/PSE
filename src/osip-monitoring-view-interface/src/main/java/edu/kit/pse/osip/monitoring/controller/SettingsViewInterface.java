package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Provides abstraction from the view and a single interface for accessing the user-set settings.
 */
public interface SettingsViewInterface {
    /**
     * Shows the settings view with the current settings.
     * @param currentSettings The current settings used for displaying them.
     */
    public void showSettingsWindow (ClientSettingsWrapper currentSettings);
    /**
     * Sets the handler for the cancel button in the settings view.
     * @param handler The handler for the cancel button in the settings view.
     */
    public void setSettingsCancelButtonHandler (SettingsCancelButtonHandler handler);
    /**
     * Sets the handler for the save button in the settings view.
     * @param handler The handler for the save button in the settings view.
     */
    public void setSettingsSaveButtonHandler (SettingsSaveButtonHandler handler);
    /**
     * Hides the settings view.
     */
    public void hideSettingsWindow ();
    /**
     * Returns the host name for the servers.
     * @return the host name for the servers.
     */
    public String getServerHostname ();
    /**
     * Returns the port number for a specified server.
     * @return
     * the port number of a specified server.
     * @param serverNumber Number of the server whose port number will be returned.
     */
    public int getServerPort (int serverNumber);
    /**
     * Returns the update time interval.
     * @return the update time interval.
     */
    public int getTime ();
    /**
     * Returns true if the underflow alarm should be enabled for a specified tank and false otherwise.
     * @return
     * true if the underflow alarm should be enabled for the specified tank. false otherwise.
     * @param tank Tank whose value will be looked up.
     */
    public boolean isUnderflowAlarmEnabled (TankSelector tank);
    /**
     * Returns true if the overflow alarm should be enabled for a specified tank and false otherwise.
     * @return
     * true if the overflow alarm should be enabled for a specified tank. false otherwise.
     * @param tank The tank whose overflow alarm should be enabled or disabled.
     */
    public boolean isOverflowAlarmEnabled (TankSelector tank);
    /**
     * Returns true if the overflow alarm for the temperature should be enabled for a specified tank and false otherwise.
     * @return true if the overflow alarm for the temperature should be enabled for a specified tank and false otherwise.
     * @param tank The specified tank.
     */
    public boolean isTemperatureOverflowAlarmEnabled (TankSelector tank);
    /**
     * Returns true if the underflow alarm for the temperature should be enabled for a specified tank and false otherwise.
     * @return true if the underflow alarm for the temperature should be enabled for a specified tank and false otherwise.
     * @param tank The specified tank.
     */
    public boolean isTemperatureUnderflowAlarmEnabled (TankSelector tank);
    /**
     * Returns true if the logging of the fill level progression should be enabled for a specified tank and false otherwise.
     * @return
     * true if the logging of the fill level progression should be enabled for the specified tank or false otherwise.
     * @param tank The tank whose fill level progression should be enabled or disabled.
     */
    public boolean isFillLevelProgressionEnabled (TankSelector tank);
    /**
     * Returns true if the logging of the temperature progression should be enabled for the specified tank and false otherwise.
     * @return
     * true if the logging of the temperature progression should be enabled for the specified tank. false otherwise.
     * @param tank The tank whose temperature progression will be set.
     */
    public boolean isTemperatureProgressionEnabled (TankSelector tank);
}
