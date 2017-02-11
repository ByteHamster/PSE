package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

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
    
    /**
     * Set up 
     * @throws Exception Exception in file loading
     */
    @Before
    public void setUp() throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("testServerSettings.properties");
        File propFile = new File(url.getPath());
        wrapper = new ServerSettingsWrapper(propFile);
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
     * test writing to file
     * @throws FileNotFoundException File is not found
     * @throws IOException Exception in IO streams
     */
    @Test
    public void testSaveSettings() throws FileNotFoundException, IOException {
        wrapper.setServerPort(TankSelector.YELLOW, 1122);
        wrapper.saveSettings();
    }
}
