package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the AlarmVisualization.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class AlarmVisualizationTest extends ApplicationTest {
    /**
     * Saves the name of the tested alarm.
     */
    private String alarmName;
    /**
     * Saves the actual used alarm.
     */
    private ObservableBoolean alarm;
    /**
     * Saves the tank the alarm is assigned to.
     */
    private TankSelector tank;
    
    @Override
    public void start(Stage arg0) throws Exception {
    }
    
    /**
     * Setups all necessary objects.
     */
    @Before
    public void setUp() {
        alarmName = "TestAlarm";
        alarm = new ObservableBoolean(false);
        tank = TankSelector.MIX;
    }
    
    /**
     * Tests the constructor where the first argument is null.
     */
    @Test(expected = NullPointerException.class)
    public void testFirstArgumentNull() {
        new AlarmVisualization(null, alarm, tank);
    }
    
    /**
     * Tests the constructor where the second argument is null.
     */
    @Test(expected = NullPointerException.class)
    public void testSecondArgumentNull() {
        new AlarmVisualization(alarmName, null, tank);
    }
    
    /**
     * Tests the constructor where the third argument is null.
     */
    @Test(expected = NullPointerException.class)
    public void testThirdArgumentNull() {
        new AlarmVisualization(alarmName, alarm, null);
    }
    
    /**
     * Tests the default values after creating a new AlarmVisualization instance.
     */
    @Test
    public void testDefaultValues() {
        AlarmVisualization av = new AlarmVisualization(alarmName, alarm, tank);
        assertEquals(AlarmState.ALARM_ENABLED, av.getAlarmState());
        assertFalse(av.isAlarmTriggered());
        assertEquals(alarmName, av.getAlarmName());
        assertNotNull(av.getLayout());
    }
    
    /**
     * Tests different state changes.
     */
    @Test
    public void testStateChanges() {
        AlarmVisualization av = new AlarmVisualization(alarmName, alarm, tank);
        av.setAlarmState(AlarmState.ALARM_DISABLED);
        assertEquals(AlarmState.ALARM_DISABLED, av.getAlarmState());
        assertFalse(av.isAlarmTriggered());
        alarm.setValue(true);
        assertEquals(AlarmState.ALARM_DISABLED, av.getAlarmState());
        assertTrue(av.isAlarmTriggered());
        av.setAlarmState(AlarmState.ALARM_ENABLED);
        assertEquals(AlarmState.ALARM_ENABLED, av.getAlarmState());
        assertTrue(av.isAlarmTriggered());
        alarm.setValue(false);
        assertEquals(AlarmState.ALARM_ENABLED, av.getAlarmState());
        assertFalse(av.isAlarmTriggered());
    }
}
