package edu.kit.pse.osip.core.model.behavior;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;

public class FillAlarmTest {

    private Liquid testLiquid;
    private Tank tank;
    private FillAlarm alarm;
    
    @Before
    public void setUp() throws Exception {
//        testLiquid = new Liquid(20, 300, new Color(0.5, 0.5, 0.5));
//        tank = new Tank(200, TankSelector.valuesWithoutMix()[0], testLiquid, new Pipe(200f, 30), new Pipe(200f, 30));
//        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);
//        tank.addObserver(alarm);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAlarmIsNotTriggered() {
        testLiquid = new Liquid(20f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, new Pipe(200f, 30), new Pipe(200f, 30));
        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);
        tank.addObserver(alarm);
        tank.notifyObservers(tank.getLiquid());
        assertEquals(false, alarm.isAlarmTriggered());
    }

    @Test
    public void testAlarmIsTriggered() {
        testLiquid = new Liquid(105f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, new Pipe(200f, 30), new Pipe(200f, 30));
        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);
        tank.addObserver(alarm);
        tank.notifyObservers(tank.getLiquid());
        assertEquals(true, alarm.isAlarmTriggered());
    }
    
}
