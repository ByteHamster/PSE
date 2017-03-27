package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.model.base.TankSelector;
import java.io.IOException;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the AlarmSettings.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class AlarmSettingsTest extends SettingsTest {
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
     * Tests the default values of the AlarmSettings.
     */
    @Test
    public void testDefaultValues() {
        AlarmSettings alarms = new AlarmSettings(wrapper);
        for (TankSelector tank : TankSelector.values()) {
            assertFalse(alarms.isOverflowEnabled(tank));
            assertFalse(alarms.isUnderflowEnabled(tank));
            assertFalse(alarms.isTemperatureOverheatingEnabled(tank));
            assertFalse(alarms.isTemperatureUndercoolingEnabled(tank));
        }
    }
    
    /**
     * Tests the enabling of all alarms.
     */
    @Test
    public void testEnabling() {
        AlarmSettings alarms = new AlarmSettings(wrapper);
        for (TankSelector tank : TankSelector.values()) {
            wrapper.setOverflowAlarm(tank, true);
            wrapper.setUnderflowAlarm(tank, true);
            wrapper.setOverheatingAlarm(tank, true);
            wrapper.setUndercoolingAlarm(tank, true);
        }
        alarms.reset(wrapper);
        for (TankSelector tank : TankSelector.values()) {
            assertTrue(alarms.isOverflowEnabled(tank));
            assertTrue(alarms.isUnderflowEnabled(tank));
            assertTrue(alarms.isTemperatureOverheatingEnabled(tank));
            assertTrue(alarms.isTemperatureUndercoolingEnabled(tank));
        }
    }
}
