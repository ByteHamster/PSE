package edu.kit.pse.osip.core.io.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Wrapper for server settings file where ports of tanks are stored
 * Key consists of parameterType_TankSelector
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class ServerSettingsWrapper {
    
    private File propFile;
    private Properties properties;
    
    /**
     * Constructor of ServerSettingsWrapper
     * @param propFile The file to save the settings to
     */
    public ServerSettingsWrapper(File propFile) {
        if (propFile == null) {
            throw new NullPointerException("File parameter is null");
        }

        try {
            if (!propFile.exists() && !propFile.createNewFile()) {
                throw new RuntimeException("Unable to create settings file at " + propFile.getAbsolutePath());
            }
            this.propFile = propFile;
            properties = new Properties();
            FileInputStream in = new FileInputStream(propFile);
            properties.load(in);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Impossible to access provided settings file");
        }
    }
    /**
     * Setter method of server port
     * @param tank The tank to save the value for
     * @param port The port to save
     */
    public final void setServerPort(TankSelector tank, int port) {
        String tankString = tank.name();
        String portString = String.valueOf(port);
        properties.setProperty("serverPort_" + tankString, portString);
    }
    /**
     * Getter method of server port 
     * @return The saved port or defaultPort if the tank does not exist
     * @param tank The tank to get the value for
     * @param defaultPort default output on error
     */
    public final int getServerPort(TankSelector tank, int defaultPort) {
        String port = properties.getProperty("serverPort_" + tank.name());
        if (port == null) {
            return defaultPort;
        } else {
            return Integer.parseInt(port);
        }
    }
    /**
     * Saves settings in file
     */
    public final void saveSettings() {
        try {
            FileOutputStream out = new FileOutputStream(propFile);
            properties.store(out, null);
            out.close();
        } catch (IOException ex) {
            System.err.println("Unable to save settings file. Old values will be used on next start");
            ex.printStackTrace();
        }
    }
}
