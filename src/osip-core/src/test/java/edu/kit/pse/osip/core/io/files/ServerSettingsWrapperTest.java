package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
    private URL url;
    private File propertiesFile;
    private File tempTestFile;
    
    /**
     * Set up 
     * @throws Exception Exception in file loading or IO errors
     */
    @Before
    public void setUp() throws Exception {
        url = Thread.currentThread().getContextClassLoader().getResource("testServerSettings.properties");
        propertiesFile = new File(url.getPath());
        File resourceDir = propertiesFile.getParentFile();
        tempTestFile = File.createTempFile("testServerSettingsTemp", ".properties", resourceDir);
        
        PrintWriter outStream = new PrintWriter(tempTestFile);
        for (int i = 0; i < 2; i++) {
            outStream.print("serverPort_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "1000");
            outStream.println();
        }
        outStream.close();
        wrapper = new ServerSettingsWrapper(tempTestFile);
    }

    /**
     * Test setting server ports
     */
    @Test
    public void testSetServerPort() {
        wrapper.setServerPort(TankSelector.valuesWithoutMix()[0], 1042);        
        assertEquals(wrapper.getServerPort(TankSelector.valuesWithoutMix()[0], -1), 1042);
    }

    /**
     * Test getting server ports
     */
    @Test
    public void testGetServerPort() {
        int result = wrapper.getServerPort(TankSelector.valuesWithoutMix()[0], -1);
        assertEquals(result, 1000);
    }

    /**
     * Test writing to file
     * @throws FileNotFoundException File is not found
     * @throws IOException Exception in IO streams
     */
    @Test
    public void testSaveSettings() throws FileNotFoundException, IOException {
        wrapper.setServerPort(TankSelector.valuesWithoutMix()[0], 1122);
        wrapper.saveSettings();
        wrapper = new ServerSettingsWrapper(tempTestFile);
        assertEquals(wrapper.getServerPort(TankSelector.valuesWithoutMix()[0], -1), 1122);
    }

    /**
     * Test getting tank that is not in properties file
     */
    @Test
    public void testGetServerPortNull() {
        int result = wrapper.getServerPort(TankSelector.valuesWithoutMix()[1], -1);
        assertEquals(result, -1);
    }
    
    /**
     * Reset file
     * @throws FileNotFoundException File is not found
     * @throws IOException Exception in IO streams
     */
    @After
    public void tearDown() throws FileNotFoundException, IOException {
        tempTestFile.delete();
    }        
}
