package edu.kit.pse.osip.core.model.behavior;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Test class for observable ability of TankAlarm
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class TankAlarmObservableTest {

    private Liquid testLiquid;
    private Tank tank;
    private FillAlarm alarm;
    private Pipe pipe1;
    private Pipe pipe2;
    private Color defaultColor = new Color(0.5, 0.5, 0.5);
    
   /**
    * Helper observer for tests
    * @author Maximilian Schwarzmann
    * @version 1.0
    */
    class TestObserver implements Observer {
        private boolean wasNotified = false;

        @Override
        public void update(Observable o, Object arg) {
            wasNotified = true;
        }
    
        /**
         * Getter for notified 
         * @return true if notified
         */
        public boolean wasNotified() {
            return wasNotified;
        }
    
        /**
         * resetNotified
         */
        public void resetNotified() {
            wasNotified = false;
        }
    }

    /**
     * Initialize pipes
     */
    @Before
    public void init() {
        pipe1 = new Pipe(200f, 30, (byte) 100);
        pipe2 = new Pipe(200f, 30, (byte) 100);
    }
    
    /**
     * Test if alarm gives notification on changes
     */
    @Test
    public void testObservableGreatherNotify() {
        testLiquid = new Liquid(50f, 300f, defaultColor);
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.GREATER_THAN);
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        Liquid alteredLiquid = new Liquid(150f, 300f, defaultColor);
        assertFalse(alarm.isAlarmTriggered());
        tank.setLiquid(alteredLiquid);
        assertEquals(true, tObserver.wasNotified());
        assertTrue(alarm.isAlarmTriggered());
    }

    /**
     * Test if alarm gives notification on changes
     */
    @Test
    public void testObservableSmallerNotify() {
        testLiquid = new Liquid(150f, 300f, defaultColor);
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.SMALLER_THAN);
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        Liquid alteredLiquid = new Liquid(50f, 300f, defaultColor);
        assertFalse(alarm.isAlarmTriggered());

        tank.setLiquid(alteredLiquid);
        assertEquals(true, tObserver.wasNotified());
        assertTrue(alarm.isAlarmTriggered());

        tObserver.resetNotified();
        alteredLiquid = new Liquid(150f, 300f, defaultColor);
        tank.setLiquid(alteredLiquid);
        assertEquals(true, tObserver.wasNotified());
        assertFalse(alarm.isAlarmTriggered());
    }

    /**
     * Test if the alarm gives no notification on no changes
     */
    @Test
    public void testObservableNoNotify() {
        testLiquid = new Liquid(50f, 300f, defaultColor);
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.GREATER_THAN);
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        assertFalse(alarm.isAlarmTriggered());
        Liquid alteredLiquid = new Liquid(80f, 300f, defaultColor);
        tank.setLiquid(alteredLiquid);
        assertEquals(false, tObserver.wasNotified());
        assertFalse(alarm.isAlarmTriggered());
    }
    
    /**
     * Test if alarm gives notification on changes 
     */
    @Test
    public void testObservableNotifyMultiple() {       
        Liquid testLiquidA = new Liquid(50f, 300f, defaultColor);
        Liquid testLiquidB = new Liquid(150f, 300f, defaultColor);
        Liquid testLiquidC = new Liquid(60f, 300f, defaultColor);
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquidA, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.GREATER_THAN);
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        assertEquals(false, tObserver.wasNotified());
        assertFalse(alarm.isAlarmTriggered());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidB);
        assertEquals(true, tObserver.wasNotified());
        assertTrue(alarm.isAlarmTriggered());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidC);
        assertEquals(true, tObserver.wasNotified());
        assertFalse(alarm.isAlarmTriggered());
    }

    /**
     * Test if alarm gives no notification on multiple new liquid inputs, which all trigger alarms
     * Use GREATER_THAN behavior
     */
    @Test
    public void testObservableNotifyMultipleConstantGreaterThan() {
        Liquid testLiquidA = new Liquid(50f, 300f, defaultColor);
        Liquid testLiquidB = new Liquid(150f, 300f, defaultColor);
        Liquid testLiquidC = new Liquid(160f, 300f, defaultColor);
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquidA, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.GREATER_THAN);
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        assertEquals(false, tObserver.wasNotified());
        assertFalse(alarm.isAlarmTriggered());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidB);
        assertEquals(true, tObserver.wasNotified());
        assertTrue(alarm.isAlarmTriggered());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidC);
        assertEquals(false, tObserver.wasNotified());
        assertTrue(alarm.isAlarmTriggered());
    }
    
    /**
     * Test if alarm gives no notification on multiple new liquid inputs, which all trigger alarms.
     * Use SMALLER_THAN behavior
     */
    @Test
    public void testObservableNotifyMultipleConstantSmallerThan() {
        Liquid testLiquidA = new Liquid(150f, 300f, defaultColor);
        Liquid testLiquidB = new Liquid(50f, 300f, defaultColor);
        Liquid testLiquidC = new Liquid(60f, 300f, defaultColor);
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquidA, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.SMALLER_THAN);
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        assertEquals(false, tObserver.wasNotified());
        assertFalse(alarm.isAlarmTriggered());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidB);
        assertEquals(true, tObserver.wasNotified());
        assertTrue(alarm.isAlarmTriggered());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidC);
        assertEquals(false, tObserver.wasNotified());
        assertTrue(alarm.isAlarmTriggered());
    }

    /**
     * Test update method with argument which is not a liquid
     */
    @Test(expected = AssertionError.class)
    public void testNotInstanceOfLiquidArg() {
        testLiquid = new Liquid(50f, 300f, defaultColor);
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 0.5f, AlarmBehavior.GREATER_THAN);
        alarm.update(tank, "String");
    }
    
}
