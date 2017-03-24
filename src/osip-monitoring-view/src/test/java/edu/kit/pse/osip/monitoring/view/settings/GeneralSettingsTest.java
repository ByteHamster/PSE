package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.OSIPConstants;
import edu.kit.pse.osip.core.model.base.TankSelector;
import java.io.IOException;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for the GeneralSettings.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class GeneralSettingsTest extends SettingsTest {
    @Override
    public void start(Stage arg0) throws Exception {
    }

    /**
     * Setups everything.
     * 
     * @throws IOException when something goes wrong.
     */
    @Before
    public void setUp() throws IOException {
        super.prepareWrapper();
    }
    
    /**
     * Tests the default values of the GeneralSettings.
     */
    @Test
    public void testDefaultValues() {
        GeneralSettings g = new GeneralSettings(wrapper);
        assertNotNull(g);
        assertNotNull(g.invalidHostnameProperty());
        assertEquals("localhost", g.getServerHost());
        int port = OSIPConstants.DEFAULT_PORT_MIX;
        for (TankSelector tank : TankSelector.values()) {
            assertEquals(port++, g.getServerPort(tank));
        }
        assertEquals(500, g.getUpdateInterval());
    }
    
    /**
     * Tests changes in all shown settings.
     */
    @Test
    public void testChanges() {
        GeneralSettings g = new GeneralSettings(wrapper);
        wrapper.setFetchInterval(1000);
        int port = 1024;
        for (TankSelector tank : TankSelector.values()) {
            wrapper.setServerHostname(tank, "test");
            wrapper.setServerPort(tank, port++);
        }
        g.reset(wrapper);
        assertEquals("test", g.getServerHost());
        port = 1024;
        for (TankSelector tank : TankSelector.values()) {
            assertEquals(port++, g.getServerPort(tank));
        }
        assertEquals(1000, g.getUpdateInterval());
    }
}
