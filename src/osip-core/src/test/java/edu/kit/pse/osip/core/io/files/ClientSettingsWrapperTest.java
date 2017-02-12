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
 * Test class for ClientSettingsWrapper
 * @author Maximilian Schwarzmann
 * @version 1.0
 *
 */
public class ClientSettingsWrapperTest {

    private ClientSettingsWrapper wrapper;
    URL url;
    File propertiesFile;
    
    @Before
    public void setUp() throws Exception {
        url = Thread.currentThread().getContextClassLoader().getResource("testClientSettings.properties");
        propertiesFile = new File(url.getPath());
        wrapper = new ClientSettingsWrapper(propertiesFile);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetServerPort() {
        wrapper.setServerPort(TankSelector.MAGENTA, 1042);        
        assertEquals(wrapper.getPort(TankSelector.MAGENTA), 1042);
    }

    @Test
    public void testSetFetchInterval() {
        wrapper.setFetchInterval(52);        
        assertEquals(wrapper.getFetchInterval(), 52);
    }
    
    @Test
    public void testSetOverflowAlarm() {
        wrapper.setOverflowAlarm(TankSelector.YELLOW, true);        
        assertEquals(wrapper.getOverflowAlarm(TankSelector.YELLOW), true);
    }
    
    @Test
    public void testSetTempDiagram() {
        wrapper.setTempDiagram(TankSelector.YELLOW, true);        
        assertEquals(wrapper.getTempDiagram(TankSelector.YELLOW), true);
    }
    
    @Test
    public void testSetServerHostname() {
        wrapper.setServerHostname(TankSelector.MAGENTA, "123.123");        
        assertEquals(wrapper.getHostname(TankSelector.MAGENTA), "123.123");
    }
    
    @Test
    public void testSetUnderflowAlarm() {
        wrapper.setUnderflowAlarm(TankSelector.YELLOW, true);        
        assertEquals(wrapper.getUnderflowAlarm(TankSelector.YELLOW), true);
    }
    
    @Test
    public void testSetFillLevelDiagram() {
        wrapper.setFillLevelDiagram(TankSelector.YELLOW, true);        
        assertEquals(wrapper.getFillLevelDiagram(TankSelector.YELLOW), true);
    }
    
    @Test
    public void testGetFetchInterval() {
        int result = wrapper.getFetchInterval();
        assertEquals(result, 80);
    }
    
    @Test
    public void testGetOverflowAlarm() {
        boolean result = wrapper.getOverflowAlarm(TankSelector.YELLOW);
        assertEquals(result, false);
    }
    
    @Test
    public void testGetUnderFlowAlarm() {
        boolean result = wrapper.getUnderflowAlarm(TankSelector.YELLOW);
        assertEquals(result, false);
    }
    
    @Test
    public void testGetTempDiagram() {
        boolean result = wrapper.getTempDiagram(TankSelector.YELLOW);
        assertEquals(result, false);
    }
    
    @Test
    public void testGetFillLevelDiagram() {
        boolean result = wrapper.getFillLevelDiagram(TankSelector.YELLOW);
        assertEquals(result, false);
    }
    
    @Test
    public void testGetHostname() {
        String result = wrapper.getHostname(TankSelector.MAGENTA);
        assertEquals(result, "hostNAME");
    }
    
    @Test
    public void testGetPort() {
        int result = wrapper.getPort(TankSelector.MAGENTA);
        assertEquals(result, 4242);
    }
    
    @Test
    public void testSaveSettings() throws FileNotFoundException, IOException {
        wrapper.setServerPort(TankSelector.MAGENTA, 424299);
        wrapper.saveSettings();
        wrapper = new ClientSettingsWrapper(propertiesFile);
        assertEquals(wrapper.getPort(TankSelector.YELLOW), 424299);
    }
    
}
