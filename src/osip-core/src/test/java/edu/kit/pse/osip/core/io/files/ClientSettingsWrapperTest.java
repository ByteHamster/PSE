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
 * Test class for ClientSettingsWrapper
 * @author Maximilian Schwarzmann
 * @version 1.0
 *
 */
public class ClientSettingsWrapperTest {

    private ClientSettingsWrapper wrapper;
    private ClientSettingsWrapper wrapperFalse;
    private File tempTestFile;
    private File tempTestFileFalse;
    
    /**
     * Set up for tests
     * @throws Exception Exceptions in set up
     */
    @Before
    public void setUp() throws Exception {
        tempTestFile = File.createTempFile("testClientSettingsTemp", ".properties");        
        PrintWriter outStream = new PrintWriter(tempTestFile);        
        outStream.print("fetchInterval=80");
        outStream.println();
        outStream.print("overflowAlarm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("underflowAlarm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("overheatingAlarm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("undercoolingAlarm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("tempDiagram_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("fillLevelDiagram_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("serverHostname_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "hostNAME");
        outStream.println();
        outStream.print("serverPort_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "4242");
        outStream.println();
        outStream.close();
        wrapper = new ClientSettingsWrapper(tempTestFile);
    
        tempTestFileFalse = File.createTempFile("testClientSettingsTempFalse", ".properties");        
        PrintWriter outStreamFalse = new PrintWriter(tempTestFileFalse);        
        outStream.print("fetchIntervall=80");
        outStream.println();
        outStream.print("overflowAlarmm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("underflowAlarmm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("overheatingAlarmm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("undercoolingAlarmm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("tempDiagramm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("fillLevelDiagramm_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "false");
        outStream.println();
        outStream.print("serverHostnamee_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "hostNAME");
        outStream.println();
        outStream.print("serverPortt_" + (TankSelector.valuesWithoutMix()[0]) + "=" + "4242");
        outStream.println();
        outStream.close();
        wrapperFalse = new ClientSettingsWrapper(tempTestFileFalse);
    
    }
    
    /**
     * Delete used test temp file
     */
    @After
    public void tearDown() {
        tempTestFile.delete();
    }
    
    /**
     * Test setting ports
     */
    @Test
    public void testSetServerPort() {
        wrapper.setServerPort(TankSelector.valuesWithoutMix()[0], 1042);        
        assertEquals(wrapper.getPort(TankSelector.valuesWithoutMix()[0], -1), 1042);
    }

    /**
     * Test setting fetchinterval
     */
    @Test
    public void testSetFetchInterval() {
        wrapper.setFetchInterval(52);        
        assertEquals(wrapper.getFetchInterval(-1), 52);
    }
    
    /**
     * Test setting overflow alarm status
     */
    @Test
    public void testSetOverflowAlarm() {
        wrapper.setOverflowAlarm(TankSelector.valuesWithoutMix()[0], true);        
        assertEquals(wrapper.getOverflowAlarm(TankSelector.valuesWithoutMix()[0], false), true);
    }
    
    /**
     * Test setting temp diagram status
     */
    @Test
    public void testSetTempDiagram() {
        wrapper.setTempDiagram(TankSelector.valuesWithoutMix()[0], true);        
        assertEquals(wrapper.getTempDiagram(TankSelector.valuesWithoutMix()[0], false), true);
    }
    
    /**
     * test setting server hostname
     */
    @Test
    public void testSetServerHostname() {
        wrapper.setServerHostname(TankSelector.valuesWithoutMix()[0], "123.123");        
        assertEquals(wrapper.getHostname(TankSelector.valuesWithoutMix()[0], "ERROR"), "123.123");
    }
    
    /**
     * Test setting underflow alarm status
     */
    @Test
    public void testSetUnderflowAlarm() {
        wrapper.setUnderflowAlarm(TankSelector.valuesWithoutMix()[0], true);        
        assertEquals(wrapper.getUnderflowAlarm(TankSelector.valuesWithoutMix()[0], false), true);
    }
    
    /**
     * Test setting fill level diagram status
     */
    @Test
    public void testSetFillLevelDiagram() {
        wrapper.setFillLevelDiagram(TankSelector.valuesWithoutMix()[0], true);        
        assertEquals(wrapper.getFillLevelDiagram(TankSelector.valuesWithoutMix()[0], false), true);
    }
    
    /**
     * Test setting overheating alarm status
     */
    @Test
    public void testSetOverheatingAlarm() {
        wrapper.setOverheatingAlarm(TankSelector.valuesWithoutMix()[0], true);        
        assertEquals(wrapper.getOverheatingAlarm(TankSelector.valuesWithoutMix()[0], false), true);
    }
    
    /**
     * Test setting undercooling alarm status
     */
    @Test
    public void testSetTempLowAlarm() {
        wrapper.setUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], true);        
        assertEquals(wrapper.getUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], false), true);
    }
    
    /**
     * Test getting fetchinterval
     */
    @Test
    public void testGetFetchInterval() {
        int result = wrapper.getFetchInterval(-1);
        assertEquals(result, 80);
    }
    
    /**
     * Test getting overflow alarm status
     */
    @Test
    public void testGetOverflowAlarm() {
        boolean result = wrapper.getOverflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(result, false);
    }
    
    /**
     * Test getting underflow alarm status
     */
    @Test
    public void testGetUnderflowAlarm() {
        boolean result = wrapper.getUnderflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(result, false);
    }
    
    /**
     * Test getting temp diagram status
     */
    @Test
    public void testGetTempDiagram() {
        boolean result = wrapper.getTempDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(result, false);
    }
    
    /**
     * Test getting fill level diagram status
     */
    @Test
    public void testGetFillLevelDiagram() {
        boolean result = wrapper.getFillLevelDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(result, false);
    }
    
    /**
     * Test getting hostname
     */
    @Test
    public void testGetHostname() {
        String result = wrapper.getHostname(TankSelector.valuesWithoutMix()[0], "ERROR");
        assertEquals(result, "hostNAME");
    }
    
    /**
     * Test getting port
     */
    @Test
    public void testGetPort() {
        int result = wrapper.getPort(TankSelector.valuesWithoutMix()[0], -1);
        assertEquals(result, 4242);
    }
    
    /**
     * Test getting overheating alarm
     */
    @Test
    public void testGetOverheatingAlarm() {
        boolean result = wrapper.getOverheatingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(result, false);
    }
    
    /**
     * Test getting undercooling alarm
     */
    @Test
    public void testGetTempLowAlarm() {
        boolean result = wrapper.getUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(result, false);
    }
    
    /**
     * Test getter for non existent entry
     */
    @Test
    public void testNonExistentTankPort() {
        int result = wrapper.getPort(TankSelector.valuesWithoutMix()[1], -1);
        assertEquals(result, -1);
    }
    
    /**
     * Test saving to file
     * @throws IOException Exception in IO stream
     */
    @Test
    public void testSaveSettings() throws IOException {
        wrapper.setServerPort(TankSelector.valuesWithoutMix()[0], 424299);
        wrapper.saveSettings();
        ClientSettingsWrapper helpWrapper = new ClientSettingsWrapper(tempTestFile);
        int helpPort = helpWrapper.getPort(TankSelector.valuesWithoutMix()[0], -1);
        assertEquals(helpPort, 424299);
    }
    
    /**
     * Test constructor with null argument.
     */
    @Test(expected = NullPointerException.class) 
    public void testNullArgConstructor() {
        ClientSettingsWrapper wrapper = new ClientSettingsWrapper(null);
    }
    
    /**
     * Test getting null fetchInterval
     */
    @Test
    public void testNullFetchInterval() {
        int interval = wrapperFalse.getFetchInterval(1122);
        assertEquals(interval, 1122);
    }
    
    /**
     * Test getting null overheating alarm
     */
    @Test
    public void testNullOverheatingAlarm() {
        boolean value = wrapperFalse.getOverheatingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(value, false);
    }
    
    /**
     * Test getting null undercooling alarm
     */
    @Test
    public void testNullUnderCoolingAlarm() {
        boolean value = wrapperFalse.getUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(value, false);
    }
    
    /**
     * Test getting null overflow alarm
     */
    @Test
    public void testNullOverflowAlarm() {
        boolean value = wrapperFalse.getOverflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(value, false);
    }
    
    /**
     * Test getting null underflow alarm
     */
    @Test
    public void testNullUnderflowAlarm() {
        boolean value = wrapperFalse.getUnderflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(value, false);
    }
    
    /**
     * Test getting null temperature diagramm
     */
    @Test
    public void testNullTempDiagramm() {
        boolean value = wrapperFalse.getTempDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(value, false);
    }
    
    /**
     * Test getting null fill level diagramm
     */
    @Test
    public void testNullFillLevelDiagramm() {
        boolean value = wrapperFalse.getFillLevelDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertEquals(value, false);
    }
    
    /**
     * Test getting null hostname
     */
    @Test
    public void testNullHostname() {
        String value = wrapperFalse.getHostname(TankSelector.valuesWithoutMix()[0], "local");
        assertEquals(value, "local");
    }
    
    /**
     * Test getting null port
     */
    @Test
    public void testNullPort() {
        int value = wrapperFalse.getPort(TankSelector.valuesWithoutMix()[0], 4242);
        assertEquals(value, 4242);
    }
    
}
