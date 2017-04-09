package edu.kit.pse.osip.core.model.behavior;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for TemperatureAlarm.
 * 
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class TemperatureAlarmTest {
    /**
     * Liquid used for testing.
     */
    private Liquid testLiquid;
    /**
     * The tank used for testing the alarms.
     */
    private Tank tank;
    /**
     * Tested alarms.
     */
    private TemperatureAlarm alarm;
    /**
     * Outgoing pipe for testing.
     */
    private Pipe pipe1;
    /**
     * Incoming pipe for testing.
     */
    private Pipe pipe2;

    /**
     * Initializes pipes.
     */
    @Before
    public void init() {
        pipe1 = new Pipe(200f, 30, (byte) 100);
        pipe2 = new Pipe(200f, 30, (byte) 100);
    }
    
    /**
     * Tests configuration where alarm is not triggered.
     */
    @Test
    public void testAlarmIsNotTriggered() {
        testLiquid = new Liquid(20f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new TemperatureAlarm(tank, 400f, AlarmBehavior.GREATER_THAN);
        assertFalse(alarm.isAlarmTriggered());
    }

    /**
     * Tests configuration where alarm is triggered.
     */
    @Test
    public void testAlarmIsTriggered() {
        testLiquid = new Liquid(20f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new TemperatureAlarm(tank, 200f, AlarmBehavior.GREATER_THAN);
        assertTrue(alarm.isAlarmTriggered());
    }
}
