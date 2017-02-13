package edu.kit.pse.osip.core.io.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Wrapper for client settings file where settings of networking and monitoring are stored
 * Keys consist of parameterType_TankSelector
 * @author Maximlian Schwarzmann
 * @version 1.0
 */
public class ClientSettingsWrapper {
    
    private File settingsFile;
    private Properties properties;
    
    /**
     * Constructor of ClientSettingsWrapper
     * @param settingsFile Where to save the settings
     * @throws IOException Exception in IO stream
     * @throws FileNotFoundException Exception in file param
     */        
    public ClientSettingsWrapper(File settingsFile) throws IOException, FileNotFoundException {
        if (settingsFile == null) {
            throw new NullPointerException("File parameter is null");
        }
        this.settingsFile = settingsFile;
        properties = new Properties();
        FileInputStream in = new FileInputStream(settingsFile);
        properties.load(in);
        in.close();
    }
    /**
     * Setter method of the server hostname
     * @param tank The tank to save the hostname for
     * @param hostname The server hostname
     */
    public final void setServerHostname(TankSelector tank, String hostname) {
        String tankString = tank.name();
        properties.setProperty("serverHostname_" + tankString, hostname);
    }
    /**
     * Setter method of server port
     * @param tank The tank to save the port for
     * @param portServer The port
     */
    public final void setServerPort(TankSelector tank, int portServer) {
        String tankString = tank.name();
        String portString = String.valueOf(portServer);
        properties.setProperty("serverPort_" + tankString, portString);
    }
    /**
     * Setter method of fetch interval
     * @param intervalMs fetch interval
     */
    public final void setFetchInterval(int intervalMs) {
        String intervallString = String.valueOf(intervalMs);
        properties.setProperty("fetchInterval", intervallString);
    }
    /**
     * Setter method of overflow alarm
     * @param tank The tank to save the value for
     * @param enabled true if alarm is enabled
     */
    public final void setOverflowAlarm(TankSelector tank, boolean enabled) {
        String tankString = tank.name();
        String enabledString = String.valueOf(enabled);
        properties.setProperty("overflowAlarm_" + tankString, enabledString);
    }
    /**
     * Setter method of underflow alarm
     * @param tank The tank to save the value for
     * @param enabled true if alarm is enabled
     */
    public final void setUnderflowAlarm(TankSelector tank, boolean enabled) {
        String tankString = tank.name();
        String enabledString = String.valueOf(enabled);
        properties.setProperty("underflowAlarm_" + tankString, enabledString);
    }
    /**
     * Setter method of temperature diagram
     * @param tank The tank to save the value for
     * @param enabled true if diagram is enabled
     */
    public final void setTempDiagram(TankSelector tank, boolean enabled) {
        String tankString = tank.name();
        String enabledString = String.valueOf(enabled);
        properties.setProperty("tempDiagram_" + tankString, enabledString);
    }
    /**
     * Setter method of fill level diagram
     * @param tank The tank to save the value for
     * @param enabled true if diagram is enabled
     */
    public final void setFillLevelDiagram(TankSelector tank, boolean enabled) {
        String tankString = tank.name();
        String enabledString = String.valueOf(enabled);
        properties.setProperty("fillLevelDiagram_" + tankString, enabledString);
    }
    /**
     * Getter method of fetch interval
     * @return fetch interval in ms or or default on error
     * @param defaultValue value on error
     */
    public final int getFetchInterval(int defaultValue) {
        String entry = properties.getProperty("fetchInterval");
        if (entry == null) {
            return defaultValue;
        }
        try {
            return  Integer.parseInt(entry);               
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    /**
     * Getter method of overflow alarm
     * @return true if alarm is enabled or default on error
     * @param tank The tank to get the value for
     * @param defaultValue value on error
     */
    public final boolean getOverflowAlarm(TankSelector tank, boolean defaultValue) {
        String tankString = tank.name();
        String entry = properties.getProperty("overflowAlarm_" + tankString);
        if (entry == null || (!entry.equals("true") && !entry.equals("false"))) {
            return defaultValue;
        }
        return Boolean.parseBoolean(entry);        
    }
    /**
     * Getter method of underflow alarm
     * @return true if alarm is enabled or default on error
     * @param tank The tank to get the value for
     * @param defaultValue value on error
     */
    public final boolean getUnderflowAlarm(TankSelector tank, boolean defaultValue) {
        String tankString = tank.name();
        String entry = properties.getProperty("underflowAlarm_" + tankString);
        if (entry == null || (!entry.equals("true") && !entry.equals("false"))) {
            return defaultValue;
        }
        return Boolean.parseBoolean(entry); 
    
    }
    /**
     * Getter method of temperature diagram
     * @return true if diagram is enabled or default on error
     * @param tank The tank to get the value for
     * @param defaultValue value on error
     */
    public final boolean getTempDiagram(TankSelector tank, boolean defaultValue) {
        String tankString = tank.name();
        String entry = properties.getProperty("tempDiagram_" + tankString);
        if (entry == null || (!entry.equals("true") && !entry.equals("false"))) {
            return defaultValue;
        }
        return Boolean.parseBoolean(entry); 
    }
    /**
     * Getter method of fill level diagram
     * @return true if diagram is enabled or default on error
     * @param tank The tank to get the value for
     * @param defaultValue value on error
     */
    public final boolean getFillLevelDiagram(TankSelector tank, boolean defaultValue) {
        String tankString = tank.name();
        String entry = properties.getProperty("fillLevelDiagram_" + tankString);
        if (entry == null || (!entry.equals("true") && !entry.equals("false"))) {
            return defaultValue;
        }
        return Boolean.parseBoolean(entry); 
    }
    /**
     * Get the hostname or IP adress
     * @return the hostname of IP adress or default on error
     * @param tank The tank to get the hostname
     * @param defaultValue value on error
     */
    public final String getHostname(TankSelector tank, String defaultValue) {
        String tankString = tank.name();
        String entry = properties.getProperty("serverHostname_" + tankString); 
        if (entry == null) {
            return defaultValue;
        }
        return entry;         
    }
    /**
     * get the port
     * @return the port number or default on error
     * @param tank The tank to get the port of
     * @param defaultValue value on error
     */
    public final int getPort(TankSelector tank, int defaultValue) {
        String tankString = tank.name();
        String entry = properties.getProperty("serverPort_" + tankString); 
        if (entry == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(entry);      
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    /**
     * Saves settings in file
     * @throws IOException Exception in IO stream
     * @throws FileNotFoundException Exception in file param
     */
    public final void saveSettings() throws IOException, FileNotFoundException {
        FileOutputStream out = new FileOutputStream(settingsFile);
        properties.store(out, null);
        out.close();
    }
}
