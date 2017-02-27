package edu.kit.pse.osip.core.model.behavior;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Test class for FillAlarm
 * @author Maximilian Schwarzmann
 * @version 1.0
 *
 */
public class FillAlarmTest {

    private Liquid testLiquid;
    private Tank tank;
    private FillAlarm alarm;
    private Pipe pipe1;
    private Pipe pipe2;

    /**
     * Initialize the pipes.
     */
    @Before
    public void init() {
        pipe1 = new Pipe(200f, 30, (byte) 100);
        pipe2 = new Pipe(200f, 30, (byte) 100);
    }
    
    /**
     * Test configuration where alarm is not triggered
     */
    @Test
    public void testAlarmIsNotTriggered() {
        testLiquid = new Liquid(20f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.GREATER_THAN);
        assertEquals(false, alarm.isAlarmTriggered());
    }

    /**
     * Test configuration where the alarm is triggered
     */
    @Test
    public void testAlarmIsTriggered() {
        testLiquid = new Liquid(101f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.GREATER_THAN);
        assertEquals(true, alarm.isAlarmTriggered());
    }
    
    /**
     * Test configuration with underflow situation
     */
    @Test
    public void testUnderFlowAlarm() {
        testLiquid = new Liquid(90f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.SMALLER_THAN);
        assertEquals(true, alarm.isAlarmTriggered());
    }
    
    
}
