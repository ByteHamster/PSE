package edu.kit.pse.osip.core.io.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Wrapper for server settings file where ports of tanks are stored
 */
public class ServerSettingsWrapper {
    
    private File propFile;
    private Properties properties;
    
    /**
     * Constructor of ServerSettingsWrapper
     * @param propFile The file to save the settings to
     * @throws IOException  Exception in FileInputStream
     * @throws FileNotFoundException Exception in file param
     */
    public ServerSettingsWrapper(File propFile) throws IOException, FileNotFoundException  {
        if (propFile == null) {
            throw new NullPointerException();
        }
        this.propFile = propFile;
        properties = new Properties();
        FileInputStream in = new FileInputStream(propFile);
        properties.load(in);
        in.close();
    }
    /**
     * Setter method of server port
     * @param tank The tank to save the value for
     * @param port The port to save
     */
    public final void setServerPort(TankSelector tank, int port) {
        String portString = tank.name();
        String tankString = String.valueOf(port);
        properties.setProperty(portString, tankString);
    }
    /**
     * Getter method of server port @return The saved port
     * @param tank The tank to get the value for
     */
    public final int getServerPort(TankSelector tank) {
        String port = properties.getProperty(tank.name());
        return Integer.parseInt(port);
    }
    /**
     * Saves settings in file
     * @throws IOException Exception in FileOutputStream
     * @throws FileNotFoundException Exception in file param
     */
    public final void saveSettings() throws FileNotFoundException, IOException {
        FileOutputStream out = new FileOutputStream(propFile);
        properties.store(out, null);
        out.close();
    }
}
