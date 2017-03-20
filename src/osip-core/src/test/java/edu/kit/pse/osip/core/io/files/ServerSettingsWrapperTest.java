package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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
    private File tempTestFile;
    
    /**
     * Set up 
     * @throws Exception Exception in file loading or IO errors
     */
    @Before
    public void setUp() throws Exception {
        tempTestFile = File.createTempFile("testServerSettingsTemp", ".properties");        
        PrintWriter outStream = new PrintWriter(tempTestFile);        
        outStream.print("serverPort_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "1000");
        outStream.println();
        outStream.close();
        wrapper = new ServerSettingsWrapper(tempTestFile);
    }

    /**
     * Test setting server ports
     */
    @Test
    public void testSetServerPort() {
        wrapper.setServerPort(TankSelector.valuesWithoutMix()[0], 1042);        
        assertEquals(1042, wrapper.getServerPort(TankSelector.valuesWithoutMix()[0], -1));
    }

    /**
     * Test getting server ports
     */
    @Test
    public void testGetServerPort() {
        int result = wrapper.getServerPort(TankSelector.valuesWithoutMix()[0], -1);
        assertEquals(1000, result);
    }

    /**
     * Test writing to file
     * @throws IOException Exception in IO streams
     */
    @Test
    public void testSaveSettings() throws IOException {
        wrapper.setServerPort(TankSelector.valuesWithoutMix()[0], 1122);
        wrapper.saveSettings();
        wrapper = new ServerSettingsWrapper(tempTestFile);
        assertEquals(1122, wrapper.getServerPort(TankSelector.valuesWithoutMix()[0], -1));
    }

    /**
     * Test getting tank that is not in properties file
     */
    @Test
    public void testGetServerPortNull() {
        int result = wrapper.getServerPort(TankSelector.valuesWithoutMix()[1], -1);
        assertEquals(-1, result);
    }
    
    /**
     * Test null argument in constructor
     */
    @Test(expected = NullPointerException.class)
    public void testNullArgConstructor() {
        ServerSettingsWrapper serverWrapper = new ServerSettingsWrapper(null);
    }
    
    /**
     * Reset file
     * @throws IOException Exception in IO streams
     */
    @After
    public void tearDown() throws IOException {
        tempTestFile.delete();
    }        
}
