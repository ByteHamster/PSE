package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.OSIPConstants;
import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.junit.After;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Abstract test class for the settings. It provides a ClientSettingsWrapper instance.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public abstract class SettingsTest extends ApplicationTest {
    /**
     * A temporary file with the settings.
     */
    private File tempTestFile;
    /**
     * The usable ClientSettingsWrapper instance.
     */
    protected ClientSettingsWrapper wrapper;
    
    /**
     * Setups the ClientSettingsWrapper instance.
     * 
     * @throws IOException when something goes wrong.
     */
    protected void prepareWrapper() throws IOException {
        tempTestFile = File.createTempFile("testClientSettingsTemp", ".properties");        
        PrintWriter outStream = new PrintWriter(tempTestFile);     
        outStream.println("fetchInterval=80");
        int port = OSIPConstants.DEFAULT_PORT_MIX;
        for (TankSelector tank : TankSelector.values()) {
            outStream.println("overflowAlarm_" + tank.name() + "=" + "false");
            outStream.println("underflowAlarm_" + tank.name() + "=" + "false");
            outStream.println("overheatingAlarm_" + tank.name() + "=" + "false");
            outStream.println("undercoolingAlarm_" + tank.name() + "=" + "false");
            outStream.println("tempDiagram_" + tank.name() + "=" + "false");
            outStream.println("fillLevelDiagram_" + tank.name() + "=" + "false");
            outStream.println("serverHostname_" + tank.name() + "=" + "localhost");
            outStream.println("serverPort_" + tank.name() + "=" + Integer.toString(port++));
        }
        outStream.close();
        wrapper = new ClientSettingsWrapper(tempTestFile);
    }
    
    /**
     * Cleans everything. 
     */
    @After
    public void tearDown() {
        if (tempTestFile != null) {
            tempTestFile.delete();
        }
    }
}
