package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.kit.pse.osip.core.model.base.TankSelector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for ClientSettingsWrapper.
 * 
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class ClientSettingsWrapperTest {
    /**
     * Test wrapper with valid settings.
     */
    private ClientSettingsWrapper wrapper;
    /**
     * Test wrapper for testing null entries.
     */
    private ClientSettingsWrapper wrapperNullEntry;
    /**
     * Test wrapper with invalid settings.
     */
    private ClientSettingsWrapper wrapperInvalidEntry;
    /**
     * Temporary file for wrapper.
     */
    private File tempTestFile;
    /**
     * Temporary file for wrapperNullEntry.
     */
    private File tempTestFileNullEntry;
    /**
     * Temporary file for wrapperInvalidEntry.
     */
    private File tempTestFileInvalidEntry;
    /**
     * Tank used for testing.
     */
    private TankSelector testTank;
    
    /**
     * Setup for tests.
     * 
     * @throws IOException Exceptions in setup.
     */
    @Before
    public void setUp() throws IOException {
        testTank = TankSelector.valuesWithoutMix()[0];
        initWrapper();
        initNullWrapper();
        initInvalidWrapper();
    }
    
    /**
     * Initializes a wrapper with valid settings.
     * 
     * @throws IOException when an IO error happens.
     */
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
    
    /**
     * Initializes a wrapper for testing null values.
     * 
     * @throws IOException when an IO error happens.
     */
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

    /**
     * Initializes a wrapper with invalid settings.
     *  
     * @throws IOException when an IO error happens.
     */
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
     * Deletes used test temp files.
     */
    @After
    public void tearDown() {
        tempTestFile.delete();
        tempTestFileNullEntry.delete();
        tempTestFileInvalidEntry.delete();
    }
    
    /**
     * Tests setting ports.
     */
    @Test
    public void testSetServerPort() {
        wrapper.setServerPort(testTank, 1042);        
        assertEquals(1042, wrapper.getPort(testTank, -1));
    }

    /**
     * Tests setting fetch interval.
     */
    @Test
    public void testSetFetchInterval() {
        wrapper.setFetchInterval(52);        
        assertEquals(52, wrapper.getFetchInterval(-1));
    }
    
    /**
     * Tests setting overflow alarm status.
     */
    @Test
    public void testSetOverflowAlarm() {
        wrapper.setOverflowAlarm(testTank, true);        
        assertTrue(wrapper.getOverflowAlarm(testTank, false));
    }
    
    /**
     * Tests setting temp diagram status.
     */
    @Test
    public void testSetTempDiagram() {
        wrapper.setTempDiagram(testTank, true);        
        assertTrue(wrapper.getTempDiagram(testTank, false));
    }
    
    /**
     * Tests setting server host name.
     */
    @Test
    public void testSetServerHostname() {
        wrapper.setServerHostname(testTank, "123.123");        
        assertEquals("123.123", wrapper.getHostname(testTank, "ERROR"));
    }
    
    /**
     * Tests setting underflow alarm status.
     */
    @Test
    public void testSetUnderflowAlarm() {
        wrapper.setUnderflowAlarm(testTank, true);        
        assertTrue(wrapper.getUnderflowAlarm(testTank, false));
    }
    
    /**
     * Tests setting fill level diagram status.
     */
    @Test
    public void testSetFillLevelDiagram() {
        wrapper.setFillLevelDiagram(testTank, true);        
        assertTrue(wrapper.getFillLevelDiagram(testTank, false));
    }
    
    /**
     * Tests setting overheating alarm status.
     */
    @Test
    public void testSetOverheatingAlarm() {
        wrapper.setOverheatingAlarm(testTank, true);        
        assertTrue(wrapper.getOverheatingAlarm(testTank, false));
    }
    
    /**
     * Tests setting undercooling alarm status.
     */
    @Test
    public void testSetTempLowAlarm() {
        wrapper.setUndercoolingAlarm(testTank, true);        
        assertTrue(wrapper.getUndercoolingAlarm(testTank, false));
    }
    
    /**
     * Tests getting fetch interval.
     */
    @Test
    public void testGetFetchInterval() {
        int result = wrapper.getFetchInterval(-1);
        assertEquals(80, result);
    }
    
    /**
     * Tests getting overflow alarm status.
     */
    @Test
    public void testGetOverflowAlarm() {
        boolean result = wrapper.getOverflowAlarm(testTank, false);
        assertFalse(result);
    }
    
    /**
     * Tests getting underflow alarm status.
     */
    @Test
    public void testGetUnderflowAlarm() {
        boolean result = wrapper.getUnderflowAlarm(testTank, false);
        assertFalse(result);
    }
    
    /**
     * Tests getting temp diagram status.
     */
    @Test
    public void testGetTempDiagram() {
        boolean result = wrapper.getTempDiagram(testTank, false);
        assertFalse(result);
    }
    
    /**
     * Tests getting fill level diagram status.
     */
    @Test
    public void testGetFillLevelDiagram() {
        boolean result = wrapper.getFillLevelDiagram(testTank, false);
        assertFalse(result);
    }
    
    /**
     * Tests getting host name.
     */
    @Test
    public void testGetHostname() {
        String result = wrapper.getHostname(testTank, "ERROR");
        assertEquals("hostNAME", result);
    }
    
    /**
     * Tests getting port.
     */
    @Test
    public void testGetPort() {
        int result = wrapper.getPort(testTank, -1);
        assertEquals(4242, result);
    }
    
    /**
     * Tests getting overheating alarm.
     */
    @Test
    public void testGetOverheatingAlarm() {
        boolean result = wrapper.getOverheatingAlarm(testTank, false);
        assertFalse(result);
    }
    
    /**
     * Tests getting undercooling alarm.
     */
    @Test
    public void testGetTempLowAlarm() {
        boolean result = wrapper.getUndercoolingAlarm(testTank, false);
        assertFalse(result);
    }
    
    /**
     * Tests getter for non existent entry.
     */
    @Test
    public void testNonExistentTankPort() {
        int result = wrapper.getPort(TankSelector.valuesWithoutMix()[1], -1);
        assertEquals(-1, result);
    }
    
    /**
     * Tests saving to file.
     * 
     * @throws IOException Exception in IO stream.
     */
    @Test
    public void testSaveSettings() throws IOException {
        wrapper.setServerPort(testTank, 424299);
        wrapper.saveSettings();
        ClientSettingsWrapper helpWrapper = new ClientSettingsWrapper(tempTestFile);
        int helpPort = helpWrapper.getPort(testTank, -1);
        assertEquals(424299, helpPort);
    }
    
    /**
     * Tests constructor with null argument.
     */
    @Test(expected = NullPointerException.class) 
    public void testNullArgConstructor() {
        ClientSettingsWrapper wrapper = new ClientSettingsWrapper(null);
    }
    
    /**
     * Tests getting null fetchInterval.
     */
    @Test
    public void testNullFetchInterval() {
        int interval = wrapperNullEntry.getFetchInterval(1122);
        assertEquals(1122, interval);
    }
    
    /**
     * Tests getting invalid fetchInterval.
     */
    @Test
    public void testInvalidFetchInterval() {
        int interval = wrapperInvalidEntry.getFetchInterval(1122);
        assertEquals(1122, interval);
    }
    
    /**
     * Tests getting null overheating alarm.
     */
    @Test
    public void testNullOverheatingAlarm() {
        boolean value = wrapperNullEntry.getOverheatingAlarm(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting null overheating alarm.
     */
    @Test
    public void testNullOverheatingAlarmAlternativeDefault() {
        boolean value = wrapperNullEntry.getOverheatingAlarm(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting invalid overheating alarm.
     */
    @Test
    public void testInvalidOverheatingAlarm() {
        boolean value = wrapperInvalidEntry.getOverheatingAlarm(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting invalid overheating alarm.
     */
    @Test
    public void testInvalidOverheatingAlarmAlternativeDefault() {
        boolean value = wrapperInvalidEntry.getOverheatingAlarm(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting null undercooling alarm.
     */
    @Test
    public void testNullUnderCoolingAlarm() {
        boolean value = wrapperNullEntry.getUndercoolingAlarm(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting null undercooling alarm.
     */
    @Test
    public void testNullUnderCoolingAlarmAlternativeDefault() {
        boolean value = wrapperNullEntry.getUndercoolingAlarm(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting invalid undercooling alarm.
     */
    @Test
    public void testInvalidUnderCoolingAlarm() {
        boolean value = wrapperInvalidEntry.getUndercoolingAlarm(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting invalid undercooling alarm.
     */
    @Test
    public void testInvalidUnderCoolingAlarmAlternativeDefault() {
        boolean value = wrapperInvalidEntry.getUndercoolingAlarm(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting null overflow alarm.
     */
    @Test
    public void testNullOverflowAlarm() {
        boolean value = wrapperNullEntry.getOverflowAlarm(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting null overflow alarm.
     */
    @Test
    public void testNullOverflowAlarmAlternativeDefault() {
        boolean value = wrapperNullEntry.getOverflowAlarm(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting invalid overflow alarm.
     */
    @Test
    public void testInvalidOverflowAlarm() {
        boolean value = wrapperInvalidEntry.getOverflowAlarm(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting invalid overflow alarm.
     */
    @Test
    public void testInvalidOverflowAlarmAlternativeDefault() {
        boolean value = wrapperInvalidEntry.getOverflowAlarm(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting null underflow alarm.
     */
    @Test
    public void testNullUnderflowAlarm() {
        boolean value = wrapperNullEntry.getUnderflowAlarm(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting null underflow alarm.
     */
    @Test
    public void testNullUnderflowAlarmAlternativeDefault() {
        boolean value = wrapperNullEntry.getUnderflowAlarm(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting invalid underflow alarm.
     */
    @Test
    public void testInvalidUnderflowAlarm() {
        boolean value = wrapperInvalidEntry.getUnderflowAlarm(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting invalid underflow alarm.
     */
    @Test
    public void testInvalidUnderflowAlarmAlternativeDefault() {
        boolean value = wrapperInvalidEntry.getUnderflowAlarm(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting null temperature diagram.
     */
    @Test
    public void testNullTempDiagramm() {
        boolean value = wrapperNullEntry.getTempDiagram(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting null temperature diagram.
     */
    @Test
    public void testNullTempDiagrammAlternativeDefault() {
        boolean value = wrapperNullEntry.getTempDiagram(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting invalid temperature diagram.
     */
    @Test
    public void testInvalidTempDiagramm() {
        boolean value = wrapperInvalidEntry.getTempDiagram(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting invalid temperature diagram.
     */
    @Test
    public void testInvalidTempDiagrammAlternativeDefault() {
        boolean value = wrapperInvalidEntry.getTempDiagram(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting null fill level diagram.
     */
    @Test
    public void testNullFillLevelDiagramm() {
        boolean value = wrapperNullEntry.getFillLevelDiagram(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting null fill level diagram.
     */
    @Test
    public void testNullFillLevelDiagrammAlternativeDefault() {
        boolean value = wrapperNullEntry.getFillLevelDiagram(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting invalid fill level diagram.
     */
    @Test
    public void testInvalidFillLevelDiagramm() {
        boolean value = wrapperInvalidEntry.getFillLevelDiagram(testTank, false);
        assertFalse(value);
    }
    
    /**
     * Tests getting invalid fill level diagram.
     */
    @Test
    public void testInvalidFillLevelDiagrammAlternativeDefault() {
        boolean value = wrapperInvalidEntry.getFillLevelDiagram(testTank, true);
        assertTrue(value);
    }
    
    /**
     * Tests getting null host name.
     */
    @Test
    public void testNullHostname() {
        String value = wrapperNullEntry.getHostname(testTank, "local");
        assertEquals("local", value);
    }
    
    /**
     * Tests getting null port.
     */
    @Test
    public void testNullPort() {
        int value = wrapperNullEntry.getPort(testTank, 4243);
        assertEquals(4243, value);
    }
    
    /**
     * Tests getting invalid port.
     */
    @Test
    public void testInvalidPort() {
        int value = wrapperInvalidEntry.getPort(testTank, 4243);
        assertEquals(4243, value);
    }
}
