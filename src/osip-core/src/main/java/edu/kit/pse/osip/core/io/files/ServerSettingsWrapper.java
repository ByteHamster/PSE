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
    private Properties props;
    
    /**
     * Constructor of ServerSettingsWrapper
     * @param propFile The file to save the settings to
     * @throws IOException 
     * @throws FileNotFoundException
     */
    public ServerSettingsWrapper(File propFile) throws IOException, FileNotFoundException {
        if (propFile == null) {
            throw new NullPointerException();
        }
        this.propFile = propFile;
        props = new Properties();
        FileInputStream in = new FileInputStream(propFile);
        props.load(in);
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
        props.setProperty(portString, tankString);
    }
    /**
     * Getter method of server port @return The saved port
     * @param tank The tank to get the value for
     */
    public final int getServerPort(TankSelector tank) {
        String port = props.getProperty(tank.name());
        return Integer.parseInt(port);
    }
    /**
     * Saves settings in file
     * @throws IOException
     * @throws FileNotFoundException
     */
    public final void saveSettings() throws FileNotFoundException, IOException{
        FileOutputStream out = new FileOutputStream(propFile);
        props.store(out, "");
        out.close();
    }
}
