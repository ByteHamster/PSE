package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    private ClientSettingsWrapper wrapperNullEntry;
    private ClientSettingsWrapper wrapperInvalidEntry;
    private File tempTestFile;
    private File tempTestFileNullEntry;
    private File tempTestFileInvalidEntry;
    private TankSelector testTank;
    
    /**
     * Set up for tests
     * @throws IOException Exceptions in set up
     */
    @Before
    public void setUp() throws IOException {
        testTank = TankSelector.valuesWithoutMix()[0];
        initWrapper();
        initNullWrapper();
        initInvalidWrapper();
    }
    
    private void initWrapper() throws IOException {
        tempTestFile = File.createTempFile("testClientSettingsTemp", ".properties");        
        PrintWriter outStream = new PrintWriter(tempTestFile);        
        outStream.println("fetchInterval=80");
        outStream.println("overflowAlarm_" + testTank + "=" + "false");
        outStream.println("underflowAlarm_" + testTank + "=" + "false");
        outStream.println("overheatingAlarm_" + testTank + "=" + "false");
        outStream.println("undercoolingAlarm_" + testTank + "=" + "false");
        outStream.println("tempDiagram_" + testTank + "=" + "false");
        outStream.println("fillLevelDiagram_" + testTank + "=" + "false");
        outStream.println("serverHostname_" + testTank + "=" + "hostNAME");
        outStream.println("serverPort_" + testTank + "=" + "4242");
        outStream.close();
        wrapper = new ClientSettingsWrapper(tempTestFile);
    }
    
    private void initNullWrapper() throws IOException {
        tempTestFileNullEntry = File.createTempFile("testClientSettingsTempNullEntry", ".properties");        
        PrintWriter outStreamNullEntry = new PrintWriter(tempTestFileNullEntry);        
        outStreamNullEntry.println("fetchIntervall=80");
        outStreamNullEntry.println("overflowAlarmm_" + testTank + "=" + "false");
        outStreamNullEntry.println("underflowAlarmm_" + testTank + "=" + "false");
        outStreamNullEntry.println("overheatingAlarmm_" + testTank + "=" + "false");
        outStreamNullEntry.println("undercoolingAlarmm_" + testTank + "=" + "false");
        outStreamNullEntry.println("tempDiagramm_" + testTank + "=" + "false");
        outStreamNullEntry.println("fillLevelDiagramm_" + testTank + "=" + "false");
        outStreamNullEntry.println("serverHostnamee_" + testTank + "=" + "hostNAME");
        outStreamNullEntry.println("serverPortt_" + testTank + "=" + "4242");
        outStreamNullEntry.close();
        wrapperNullEntry = new ClientSettingsWrapper(tempTestFileNullEntry);
    }
       
    private void initInvalidWrapper() throws IOException {
        tempTestFileInvalidEntry = File.createTempFile("testClientSettingsTempInvalidEntry", ".properties");        
        PrintWriter outStreamInvalidEntry = new PrintWriter(tempTestFileInvalidEntry);        
        outStreamInvalidEntry.println("fetchInterval=8G0AER");
        outStreamInvalidEntry.println("overflowAlarm_" + testTank + "=" + "falsE");
        outStreamInvalidEntry.println("underflowAlarm_" + testTank + "=" + "falsE");
        outStreamInvalidEntry.println("overheatingAlarm_" + testTank + "=" + "falsE");
        outStreamInvalidEntry.println("undercoolingAlarm_" + testTank + "=" + "falsE");
        outStreamInvalidEntry.println("tempDiagram_" + testTank + "=" + "falsE");
        outStreamInvalidEntry.println("fillLevelDiagram_" + testTank + "=" + "falsE");
        outStreamInvalidEntry.println("serverHostname_" + testTank + "=" + "hostNAMEZ");
        outStreamInvalidEntry.println("serverPort_" + testTank + "=" + "4L4L");
        outStreamInvalidEntry.close();
        wrapperInvalidEntry = new ClientSettingsWrapper(tempTestFileInvalidEntry);       
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
        assertTrue(wrapper.getOverflowAlarm(TankSelector.valuesWithoutMix()[0], false));
    }
    
    /**
     * Test setting temp diagram status
     */
    @Test
    public void testSetTempDiagram() {
        wrapper.setTempDiagram(TankSelector.valuesWithoutMix()[0], true);        
        assertTrue(wrapper.getTempDiagram(TankSelector.valuesWithoutMix()[0], false));
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
        assertTrue(wrapper.getUnderflowAlarm(TankSelector.valuesWithoutMix()[0], false));
    }
    
    /**
     * Test setting fill level diagram status
     */
    @Test
    public void testSetFillLevelDiagram() {
        wrapper.setFillLevelDiagram(TankSelector.valuesWithoutMix()[0], true);        
        assertTrue(wrapper.getFillLevelDiagram(TankSelector.valuesWithoutMix()[0], false));
    }
    
    /**
     * Test setting overheating alarm status
     */
    @Test
    public void testSetOverheatingAlarm() {
        wrapper.setOverheatingAlarm(TankSelector.valuesWithoutMix()[0], true);        
        assertTrue(wrapper.getOverheatingAlarm(TankSelector.valuesWithoutMix()[0], false));
    }
    
    /**
     * Test setting undercooling alarm status
     */
    @Test
    public void testSetTempLowAlarm() {
        wrapper.setUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], true);        
        assertTrue(wrapper.getUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], false));
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
        assertFalse(result);
    }
    
    /**
     * Test getting underflow alarm status
     */
    @Test
    public void testGetUnderflowAlarm() {
        boolean result = wrapper.getUnderflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(result);
    }
    
    /**
     * Test getting temp diagram status
     */
    @Test
    public void testGetTempDiagram() {
        boolean result = wrapper.getTempDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(result);
    }
    
    /**
     * Test getting fill level diagram status
     */
    @Test
    public void testGetFillLevelDiagram() {
        boolean result = wrapper.getFillLevelDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(result);
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
        assertFalse(result);
    }
    
    /**
     * Test getting undercooling alarm
     */
    @Test
    public void testGetTempLowAlarm() {
        boolean result = wrapper.getUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(result);
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
        int interval = wrapperNullEntry.getFetchInterval(1122);
        assertEquals(interval, 1122);
    }
    
    /**
     * Test getting invalid fetchInterval
     */
    @Test
    public void testInvalidFetchInterval() {
        int interval = wrapperInvalidEntry.getFetchInterval(1122);
        assertEquals(interval, 1122);
    }
    
    /**
     * Test getting null overheating alarm
     */
    @Test
    public void testNullOverheatingAlarm() {
        boolean value = wrapperNullEntry.getOverheatingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting invalid overheating alarm
     */
    @Test
    public void testInvalidOverheatingAlarm() {
        boolean value = wrapperInvalidEntry.getOverheatingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting null undercooling alarm
     */
    @Test
    public void testNullUnderCoolingAlarm() {
        boolean value = wrapperNullEntry.getUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting invalid undercooling alarm
     */
    @Test
    public void testInvalidUnderCoolingAlarm() {
        boolean value = wrapperInvalidEntry.getUndercoolingAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting null overflow alarm
     */
    @Test
    public void testNullOverflowAlarm() {
        boolean value = wrapperNullEntry.getOverflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting invalid overflow alarm
     */
    @Test
    public void testInvalidOverflowAlarm() {
        boolean value = wrapperInvalidEntry.getOverflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting null underflow alarm
     */
    @Test
    public void testNullUnderflowAlarm() {
        boolean value = wrapperNullEntry.getUnderflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting invalid underflow alarm
     */
    @Test
    public void testInvalidUnderflowAlarm() {
        boolean value = wrapperInvalidEntry.getUnderflowAlarm(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting null temperature diagramm
     */
    @Test
    public void testNullTempDiagramm() {
        boolean value = wrapperNullEntry.getTempDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting invalid temperature diagramm
     */
    @Test
    public void testInvalidTempDiagramm() {
        boolean value = wrapperInvalidEntry.getTempDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting null fill level diagramm
     */
    @Test
    public void testNullFillLevelDiagramm() {
        boolean value = wrapperNullEntry.getFillLevelDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting invalid fill level diagramm
     */
    @Test
    public void testInvalidFillLevelDiagramm() {
        boolean value = wrapperInvalidEntry.getFillLevelDiagram(TankSelector.valuesWithoutMix()[0], false);
        assertFalse(value);
    }
    
    /**
     * Test getting null hostname
     */
    @Test
    public void testNullHostname() {
        String value = wrapperNullEntry.getHostname(TankSelector.valuesWithoutMix()[0], "local");
        assertEquals(value, "local");
    }
    
    /**
     * Test getting null port
     */
    @Test
    public void testNullPort() {
        int value = wrapperNullEntry.getPort(TankSelector.valuesWithoutMix()[0], 4242);
        assertEquals(value, 4242);
    }
    
    /**
     * Test getting invalid port
     */
    @Test
    public void testInvalidPort() {
        int value = wrapperInvalidEntry.getPort(TankSelector.valuesWithoutMix()[0], 4242);
        assertEquals(value, 4242);
    }
    
}
