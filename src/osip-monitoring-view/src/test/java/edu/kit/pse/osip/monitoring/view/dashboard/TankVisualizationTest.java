package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.AlarmGroup;
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
 * Tests for the TankVisualization classes.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class TankVisualizationTest extends ApplicationTest {
    /**
     * Saves a Tank for the visualizations.
     */
    private Tank tank;
    /**
     * Saves a MixTank for the visualizations.
     */
    private MixTank mixTank;
    /**
     * Saves the alarms for the tank.
     */
    private AlarmGroup<ObservableBoolean, ObservableBoolean> alarms;
    
    @Override
    public void start(Stage stage) throws Exception {
    }
    
    /**
     * Setups all necessary objects out of the model.
     */
    @Before
    public void setUp() {
        Liquid l = new Liquid(5000, 350, new Color(1, 1, 1));
        Pipe i = new Pipe(SimulationConstants.PIPE_CROSSSECTION, SimulationConstants.PIPE_LENGTH, (byte) 50);
        Pipe o = new Pipe(SimulationConstants.PIPE_CROSSSECTION, SimulationConstants.PIPE_LENGTH, (byte) 50);
        tank = new Tank(10000, TankSelector.CYAN, l, o, i);
        mixTank = new MixTank(10000, l, o);
        alarms = new AlarmGroup<>(new ObservableBoolean(false), new ObservableBoolean(false),
                new ObservableBoolean(false), new ObservableBoolean(false));
    }
    
    /**
     * Tests the constructor of the TankVisualization with the first argument null.
     */
    @Test(expected = NullPointerException.class)
    public void testTankVisFirstNull() {
        new TankVisualization(null, alarms);
    }
    
    /**
     * Tests the constructor of the TankVisualization with the second argument null.
     */
    @Test(expected = NullPointerException.class)
    public void testTankVisSecondNull() {
        new TankVisualization(tank, null);
    }
    
    /**
     * Tests the default values of a TankVisualization.
     */
    @Test
    public void testDefaultTankVis() {
        TankVisualization tv = new TankVisualization(tank, alarms);
        assertNotNull(tv.getOverflowAlarm());
        assertNotNull(tv.getUnderflowAlarm());
        assertNotNull(tv.getTemperatureOverheatingAlarm());
        assertNotNull(tv.getTemperatureUndercoolingAlarm());
        assertFalse(tv.getOverflowAlarm().isAlarmTriggered());
        assertFalse(tv.getUnderflowAlarm().isAlarmTriggered());
        assertFalse(tv.getTemperatureOverheatingAlarm().isAlarmTriggered());
        assertFalse(tv.getTemperatureUndercoolingAlarm().isAlarmTriggered());
        assertEquals(AlarmState.ALARM_ENABLED, tv.getOverflowAlarm().getAlarmState());
        assertEquals(AlarmState.ALARM_ENABLED, tv.getUnderflowAlarm().getAlarmState());
        assertEquals(AlarmState.ALARM_ENABLED, tv.getTemperatureOverheatingAlarm().getAlarmState());
        assertEquals(AlarmState.ALARM_ENABLED, tv.getTemperatureUndercoolingAlarm().getAlarmState());
    }
    
    /**
     * Tests the activation and disabling of all alarms.
     */
    @Test
    public void testAlarmsInTankVis() {
        TankVisualization tv = new TankVisualization(tank, alarms);
        alarms.getOverflow().setValue(true);
        alarms.getUnderflow().setValue(true);
        alarms.getOverheat().setValue(true);
        alarms.getUndercool().setValue(true);
        tv.setOverflowAlarmEnabled(false);
        tv.setUnderflowAlarmEnabled(false);
        tv.setTemperatureOverheatingAlarmEnabled(false);
        tv.setTemperatureUndercoolingAlarmEnabled(false);
        assertTrue(tv.getOverflowAlarm().isAlarmTriggered());
        assertTrue(tv.getUnderflowAlarm().isAlarmTriggered());
        assertTrue(tv.getTemperatureOverheatingAlarm().isAlarmTriggered());
        assertTrue(tv.getTemperatureUndercoolingAlarm().isAlarmTriggered());
        assertEquals(AlarmState.ALARM_DISABLED, tv.getOverflowAlarm().getAlarmState());
        assertEquals(AlarmState.ALARM_DISABLED, tv.getUnderflowAlarm().getAlarmState());
        assertEquals(AlarmState.ALARM_DISABLED, tv.getTemperatureOverheatingAlarm().getAlarmState());
        assertEquals(AlarmState.ALARM_DISABLED, tv.getTemperatureUndercoolingAlarm().getAlarmState());
    }
    
    /**
     * Tests the constructor of the MixTankVisualization.
     */
    @Test
    public void testMixTankVis() {
        new MixTankVisualization(mixTank, alarms);
    }
}
