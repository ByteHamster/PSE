package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Test class for ServerSettingsWrapper
 * @author Maximilian Schwarzmann
 * @version 1.0
 *
 */
public class ServerSettingsWrapperTest {

    private ServerSettingsWrapper wrapper;
    URL url;
    File propertiesFile;
    
    /**
     * Set up 
     * @throws Exception Exception in file loading
     */
    @Before
    public void setUp() throws Exception {
        url = Thread.currentThread().getContextClassLoader().getResource("testServerSettings.properties");
        propertiesFile = new File(url.getPath());
        wrapper = new ServerSettingsWrapper(propertiesFile);
    }

    /**
     * Test setting server ports
     */
    @Test
    public void testSetServerPort() {
        wrapper.setServerPort(TankSelector.MAGENTA, 1042);        
        assertEquals(wrapper.getServerPort(TankSelector.MAGENTA), 1042);
    }

    /**
     * Test getting server ports
     */
    @Test
    public void testGetServerPort() {
        int result = wrapper.getServerPort(TankSelector.MAGENTA);
        assertEquals(result, 1000);
    }

    /**
     * Test writing to file
     * @throws FileNotFoundException File is not found
     * @throws IOException Exception in IO streams
     */
    @Test
    public void testSaveSettings() throws FileNotFoundException, IOException {
        wrapper.setServerPort(TankSelector.YELLOW, 1122);
        wrapper.saveSettings();
        wrapper = new ServerSettingsWrapper(propertiesFile);
        assertEquals(wrapper.getServerPort(TankSelector.YELLOW), 1122);
    }

    /**
     * Test getting tank that is not in properties file
     */
    @Test(expected = NumberFormatException.class)
    public void testGetServerPortNull() {
        wrapper.getServerPort(TankSelector.MIX);
    }
    
    /**
     * Reset file
     * @throws FileNotFoundException File is not found
     * @throws IOException Exception in IO streams
     */
    @After
    public void tearDown() throws FileNotFoundException, IOException {
        wrapper.setServerPort(TankSelector.YELLOW, 1100);
        wrapper.setServerPort(TankSelector.MAGENTA, 1000);
        wrapper.saveSettings();
    }        
}
