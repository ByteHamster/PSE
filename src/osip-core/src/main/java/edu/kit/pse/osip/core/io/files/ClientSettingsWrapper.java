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
     * @return fetch interval in ms or -1 on error
     */
    public final int getFetchInterval() {
        String entry = properties.getProperty("fetchInterval");
        if (entry == null) {
            return -1;
        } else {
            return  Integer.parseInt(entry);
        }        
    }
    /**
     * Getter method of overflow alarm
     * @return true if alarm is enabled
     * @param tank The tank to get the value for
     */
    public final boolean getOverflowAlarm(TankSelector tank) {
        String tankString = tank.name();
        String entry = properties.getProperty("overflowAlarm_" + tankString);
        if (entry == null) {
            return null;
        }
        boolean result = Boolean.parseBoolean(entry);        
        
    }
    /**
     * Getter method of underflow alarm
     * @return true if alarm is enabled
     * @param tank The tank to get the value for
     */
    public final boolean getUnderflowAlarm(TankSelector tank) {
        String tankString = tank.name();
        return Boolean.parseBoolean(properties.getProperty("underflowAlarm_" + tankString)); 
    }
    /**
     * Getter method of temperature diagram @return true if diagram is enabled
     * @param tank The tank to get the value for
     */
    public final boolean getTempDiagram(TankSelector tank) {
        String tankString = tank.name();
        return Boolean.parseBoolean(properties.getProperty("tempDiagram_" + tankString)); 
    }
    /**
     * Getter method of fill level diagram @return true if diagram is enabled
     * @param tank The tank to get the value for
     */
    public final boolean getFillLevelDiagram(TankSelector tank) {
        String tankString = tank.name();
        return Boolean.parseBoolean(properties.getProperty("fillLevelDiagram_" + tankString)); 
    }
    /**
     * Get the hostname or IP adress
     * @return the hostname of IP adress or null on error
     * @param tank The tank to get the hostname
     */
    public final String getHostname(TankSelector tank) {
        String tankString = tank.name();
        String entry = properties.getProperty("serverHostname_" + tankString); 
        return entry;         
    }
    /**
     * get the port
     * @return the port number or -1 on error
     * @param tank The tank to get the port of
     */
    public final int getPort(TankSelector tank) {
        String tankString = tank.name();
        String entry = properties.getProperty("serverPort_" + tankString); 
        if (entry == null) {
            return -1;
        } else {
            return Integer.parseInt(entry);
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
