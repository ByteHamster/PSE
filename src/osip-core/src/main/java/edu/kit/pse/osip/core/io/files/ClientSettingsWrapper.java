package edu.kit.pse.osip.core.io.files;

import java.io.File;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Wrapper for client settings file where settings of networking and monitoring are stored
 */
public class ClientSettingsWrapper {
    /**
     * Constructor of ClientSettingsWrapper
     * @param settingsFile Where to save the settings
     */
    public final void ClientSettingWrapper (File settingsFile) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Setter method of the server hostname
     * @param tank The tank to save the hostname for
     * @param hostname The server hostname
     */
    public final void setServerHostname (TankSelector tank, String hostname) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Setter method of server port
     * @param tank The tank to save the port for
     * @param portServer The port
     */
    public final void setServerPort (TankSelector tank, int portServer) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Setter method of fetch interval
     * @param intervalMs fetch interval
     */
    public final void setFetchInterval (int intervalMs) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Setter method of overflow alarm
     * @param tank The tank to save the value for
     * @param enabled true if alarm is enabled
     */
    public final void setOverflowAlarm (TankSelector tank, boolean enabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Setter method of underflow alarm
     * @param tank The tank to save the value for
     * @param enabled true if alarm is enabled
     */
    public final void setUnderflowAlarm (TankSelector tank, boolean enabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Setter method of temperature diagram
     * @param tank The tank to save the value for
     * @param enabled true if diagram is enabled
     */
    public final void setTempDiagram (TankSelector tank, boolean enabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Setter method of fill level diagram
     * @param tank The tank to save the value for
     * @param enabled true if diagram is enabled
     */
    public final void setFillLevelDiagram (TankSelector tank, boolean enabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Getter method of fetch interval
     * @return fetch interval in ms
     */
    public final int getFetchInterval () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Getter method of overflow alarm
     * @return true if alarm is enabled
     * @param tank The tank to get the value for
     */
    public final boolean getOverflowAlarm (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Getter method of underflow alarm
     * @return true if alarm is enabled
     * @param tank The tank to get the value for
     */
    public final boolean getUnderflowAlarm (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Getter method of temperature diagram @return true if diagram is enabled
     * @param tank The tank to get the value for
     */
    public final boolean getTempDiagram (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Getter method of fill level diagram @return true if diagram is enabled
     * @param tank The tank to get the value for
     */
    public final boolean getFillLevelDiagram (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the hostname or IP adress
     * @return the hostname of IP adress
     * @param tank The tank to get the hostname
     */
    public final String getHostname (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * get the port
     * @return the port number
     * @param tank The tank to get the port of
     */
    public final int getPort (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Saves settings in file
     */
    public final void saveSettings () {
        throw new RuntimeException("Not implemented!");
    }
}
