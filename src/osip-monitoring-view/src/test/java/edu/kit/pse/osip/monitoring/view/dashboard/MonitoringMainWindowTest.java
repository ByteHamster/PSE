package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.AlarmGroup;
import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import java.util.EnumMap;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNotNull;

/**
 * Test class for the MonitoringMainWindow.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class MonitoringMainWindowTest extends ApplicationTest {
    /**
     * Saves a test instance of the MonitoringMainWindow.
     */
    private MonitoringMainWindow win;
    
    @Override
    public void start(Stage stage) throws Exception {
        EnumMap<TankSelector, AlarmGroup<ObservableBoolean, ObservableBoolean>> alarms = 
                new EnumMap<>(TankSelector.class);
        for (TankSelector tank : TankSelector.values()) {
            alarms.put(tank, new AlarmGroup<>(new ObservableBoolean(false), new ObservableBoolean(false),
                    new ObservableBoolean(false), new ObservableBoolean(false)));
        }
        win = new MonitoringMainWindow(stage, new ProductionSite(), alarms);
    }

    /**
     * Tests the default values of the MonitoringMainWindow.
     */
    @Test
    public void testDefaultValues() {
        assertNotNull(win.getMonitoringMenu());
        for (TankSelector tank : TankSelector.values()) {
            assertNotNull(win.getTank(tank));
        }
    }
    
    /**
     * Tests the constructor of the MonitoringMainWindow with null arguments.
     */
    @Test(expected = NullPointerException.class)
    public void testNullArguments() {
        new MonitoringMainWindow(null, null, null);
    }
}
