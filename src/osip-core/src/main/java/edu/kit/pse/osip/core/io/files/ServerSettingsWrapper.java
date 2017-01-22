package edu.kit.pse.osip.core.io.files;

import java.io.File;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Wrapper for server settings file where ports of tanks are stored
 */
public class ServerSettingsWrapper {
    /**
     * Constructor of ServerSettingsWrapper
     * @param settingsFile The file to save the settings to
     */
    public ServerSettingsWrapper (File settingsFile) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Setter method of server port
     * @param tank The tank to save the value for
     * @param port The port to save
     */
    public final void setServerPort (TankSelector tank, int port) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Getter method of server port @return The saved port
     * @param tank The tank to get the value for
     */
    public final int getServerPort (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Saves settings in file
     */
    public final void saveSettings () {
        throw new RuntimeException("Not implemented!");
    }
}
