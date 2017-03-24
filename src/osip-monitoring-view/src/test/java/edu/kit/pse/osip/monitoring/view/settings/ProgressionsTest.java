package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.model.base.TankSelector;
import java.io.IOException;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the Progressions class.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class ProgressionsTest extends SettingsTest {
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
     * Tests the default values of the Progressions.
     */
    @Test
    public void testDefaultValues() {
        Progressions p = new Progressions(wrapper);
        assertNotNull(p);
        for (TankSelector tank : TankSelector.values()) {
            assertFalse(p.isFillLevelEnabled(tank));
            assertFalse(p.isTemperatureEnabled(tank));
        }
    }
    
    /**
     * Tests the enabling of all progressions.
     */
    @Test
    public void testEnabling() {
        Progressions p = new Progressions(wrapper);
        for (TankSelector tank : TankSelector.values()) {
            wrapper.setFillLevelDiagram(tank, true);
            wrapper.setTempDiagram(tank, true);
        }
        p.reset(wrapper);
        for (TankSelector tank : TankSelector.values()) {
            assertTrue(p.isFillLevelEnabled(tank));
            assertTrue(p.isTemperatureEnabled(tank));
        }
    }
}
