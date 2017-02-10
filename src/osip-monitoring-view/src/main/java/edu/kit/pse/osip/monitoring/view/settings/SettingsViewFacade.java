package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.monitoring.controller.SettingsCancelButtonHandler;
import edu.kit.pse.osip.monitoring.controller.SettingsSaveButtonHandler;

/**
 * Provides a single access point to the user-set settings.
 */
public class SettingsViewFacade implements edu.kit.pse.osip.monitoring.controller.SettingsViewInterface {
    /**
     * The current settings view containg the user-set settings.
     */
    private SettingsMainWindow currentSettingsWindow;
    /**
     * Shows the settings view with the current settings.
     * @param currentSettings The current settings used for displaying them.
     */
    public final void showSettingsWindow (ClientSettingsWrapper currentSettings) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the cancel button in the settings view.
     * @param handler The handler for the cancel button in the settings view.
     */
    public final void setSettingsCancelButtonHandler (SettingsCancelButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the handler for the save button in the settings view.
     * @param handler The handler for the save button in the settings view.
     */
    public final void setSettingsSaveButtonHandler (SettingsSaveButtonHandler handler) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Hides the settings view.
     */
    public final void hideSettingsWindow () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the host name for the servers.
     * @return the host name for the servers.
     */
    public final String getServerHostname () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the port number for a specified server.
     * @return
     * the port number of a specified server.
     * @param serverNumber Number of the server whose port number will be returned.
     */
    public final int getServerPort (int serverNumber) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the update time interval.
     * @return the update time interval.
     */
    public final int getTime () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the underflow alarm should be enabled for a specified tank and false otherwise.
     * @return
     * true if the underflow alarm should be enabled for the specified tank. false otherwise.
     * @param tank Tank whose value will be looked up.
     */
    public final boolean isUnderflowAlarmEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the overflow alarm should be enabled for a specified tank and false otherwise.
     * @return
     * true if the overflow alarm should be enabled for a specified tank. false otherwise.
     * @param tank The tank whose overflow alarm should be enabled or disabled.
     */
    public final boolean isOverflowAlarmEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the overflow alarm for the temperature should be enabled for a specified tank and false otherwise.
     * @return true if the overflow alarm for the temperature should be enabled for a specified tank and false otherwise.
     * @param tank The specified tank.
     */
    public final boolean isTemperatureOverheatingAlarmEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the underflow alarm for the temperature should be enabled for a specified tank and false otherwise.
     * @return true if the underflow alarm for the temperature should be enabled for a specified tank and false otherwise.
     * @param tank The specified tank.
     */
    public final boolean isTemperatureUndercoolingAlarmEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the logging of the fill level progression should be enabled for a specified tank and false otherwise.
     * @return
     * true if the logging of the fill level progression should be enabled for the specified tank or false otherwise.
     * @param tank The tank whose fill level progression should be enabled or disabled.
     */
    public final boolean isFillLevelProgressionEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the logging of the temperature progression should be enabled for the specified tank and false otherwise.
     * @return
     * true if the logging of the temperature progression should be enabled for the specified tank. false otherwise.
     * @param tank The tank whose temperature progression will be set.
     */
    public final boolean isTemperatureProgressionEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
}
