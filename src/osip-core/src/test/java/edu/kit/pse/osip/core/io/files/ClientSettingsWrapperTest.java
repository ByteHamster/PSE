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
 * Test class for ClientSettingsWrapper
 * @author Maximilian Schwarzmann
 * @version 1.0
 *
 */
public class ClientSettingsWrapperTest {

    private ClientSettingsWrapper wrapper;
    URL url;
    File propertiesFile;
    
    /**
     * Set up for tests
     * @throws Exception Exceptions in set up
     */
    @Before
    public void setUp() throws Exception {
        url = Thread.currentThread().getContextClassLoader().getResource("testClientSettings.properties");
        propertiesFile = new File(url.getPath());
        wrapper = new ClientSettingsWrapper(propertiesFile);
    }
    
    /**
     * Test setting ports
     */
    @Test
    public void testSetServerPort() {
        wrapper.setServerPort(TankSelector.MAGENTA, 1042);        
        assertEquals(wrapper.getPort(TankSelector.MAGENTA), 1042);
    }

    /**
     * Test setting fetchinterval
     */
    @Test
    public void testSetFetchInterval() {
        wrapper.setFetchInterval(52);        
        assertEquals(wrapper.getFetchInterval(), 52);
    }
    
    /**
     * Test setting overflow alarm status
     */
    @Test
    public void testSetOverflowAlarm() {
        wrapper.setOverflowAlarm(TankSelector.YELLOW, true);        
        assertEquals(wrapper.getOverflowAlarm(TankSelector.YELLOW), true);
    }
    
    /**
     * Test setting temp diagram status
     */
    @Test
    public void testSetTempDiagram() {
        wrapper.setTempDiagram(TankSelector.YELLOW, true);        
        assertEquals(wrapper.getTempDiagram(TankSelector.YELLOW), true);
    }
    
    /**
     * test setting server hostname
     */
    @Test
    public void testSetServerHostname() {
        wrapper.setServerHostname(TankSelector.MAGENTA, "123.123");        
        assertEquals(wrapper.getHostname(TankSelector.MAGENTA), "123.123");
    }
    
    /**
     * Test setting underflow alarm status
     */
    @Test
    public void testSetUnderflowAlarm() {
        wrapper.setUnderflowAlarm(TankSelector.YELLOW, true);        
        assertEquals(wrapper.getUnderflowAlarm(TankSelector.YELLOW), true);
    }
    
    /**
     * Test setting fill level diagram status
     */
    @Test
    public void testSetFillLevelDiagram() {
        wrapper.setFillLevelDiagram(TankSelector.YELLOW, true);        
        assertEquals(wrapper.getFillLevelDiagram(TankSelector.YELLOW), true);
    }
    
    /**
     * Test getting fetchinterval
     */
    @Test
    public void testGetFetchInterval() {
        int result = wrapper.getFetchInterval();
        assertEquals(result, 80);
    }
    
    /**
     * Test getting overflow alarm status
     */
    @Test
    public void testGetOverflowAlarm() {
        boolean result = wrapper.getOverflowAlarm(TankSelector.YELLOW);
        assertEquals(result, false);
    }
    
    /**
     * Test getting underflow alarm status
     */
    @Test
    public void testGetUnderflowAlarm() {
        boolean result = wrapper.getUnderflowAlarm(TankSelector.YELLOW);
        assertEquals(result, false);
    }
    
    /**
     * Test getting temp diagram status
     */
    @Test
    public void testGetTempDiagram() {
        boolean result = wrapper.getTempDiagram(TankSelector.YELLOW);
        assertEquals(result, false);
    }
    
    /**
     * Test getting fill level diagram status
     */
    @Test
    public void testGetFillLevelDiagram() {
        boolean result = wrapper.getFillLevelDiagram(TankSelector.YELLOW);
        assertEquals(result, false);
    }
    
    /**
     * Test getting hostname
     */
    @Test
    public void testGetHostname() {
        String result = wrapper.getHostname(TankSelector.MAGENTA);
        assertEquals(result, "hostNAME");
    }
    
    /**
     * Test getting port
     */
    @Test
    public void testGetPort() {
        int result = wrapper.getPort(TankSelector.MAGENTA);
        assertEquals(result, 4242);
    }
    
    /**
     * Test saving to file
     * @throws FileNotFoundException Exception in file param
     * @throws IOException Exception in IO stream
     */
    @Test
    public void testSaveSettings() throws FileNotFoundException, IOException {
        wrapper.setServerPort(TankSelector.MAGENTA, 424299);
        wrapper.saveSettings();
        ClientSettingsWrapper helpWrapper = new ClientSettingsWrapper(propertiesFile);
        int helpPort = helpWrapper.getPort(TankSelector.MAGENTA);
        wrapper.setServerPort(TankSelector.MAGENTA, 4242);
        wrapper.saveSettings();
        assertEquals(helpPort, 424299);
    }
    
}
