package edu.kit.pse.osip.core.model.behavior;

import static org.junit.Assert.assertEquals;

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
 * Test class for observerable ability of TankAlarm
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class TankAlarmObservableTest {

    private Liquid testLiquid;
    private Tank tank;
    private FillAlarm alarm;
    private Pipe pipe1;
    private Pipe pipe2;
    
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
     * Intialize pipes
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
    public void testObservableNotify() {       
        testLiquid = new Liquid(150f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);        
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        Liquid alteredLiquid = new Liquid(50f, 300f, new Color(0.5, 0.5, 0.5));
        tank.setLiquid(alteredLiquid);
        assertEquals(true, tObserver.wasNotified());        
    }

    /**
     * Test if the alarm gives no notification on no changes
     */
    @Test
    public void testObservableNoNotify() {
        testLiquid = new Liquid(50f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, pipe1, pipe2);
        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);        
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        Liquid alteredLiquid = new Liquid(80f, 300f, new Color(0.5, 0.5, 0.5));
        tank.setLiquid(alteredLiquid);
        assertEquals(false, tObserver.wasNotified());
    }    

    /**
     * Test if alarm gives notification on changes 
     */
    @Test
    public void testObservableNotifyMultiple() {       
        Liquid testLiquidA = new Liquid(50f, 300f, new Color(0.5, 0.5, 0.5));
        Liquid testLiquidB = new Liquid(150f, 300f, new Color(0.5, 0.5, 0.5));
        Liquid testLiquidC = new Liquid(60f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquidA, pipe1, pipe2);
        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);        
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        assertEquals(false, tObserver.wasNotified());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidB);
        assertEquals(true, tObserver.wasNotified());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidC);
        assertEquals(true, tObserver.wasNotified());        
    }

    /**
     * Test if alarm gives no nofification on multiple new liquid inputs, which all trigger alarms
     */
    @Test
    public void testObservbleNotifyMultipleConstant() {
        Liquid testLiquidA = new Liquid(50f, 300f, new Color(0.5, 0.5, 0.5));
        Liquid testLiquidB = new Liquid(150f, 300f, new Color(0.5, 0.5, 0.5));
        Liquid testLiquidC = new Liquid(160f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquidA, pipe1, pipe2);
        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);        
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        assertEquals(false, tObserver.wasNotified());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidB);
        assertEquals(true, tObserver.wasNotified());
        tObserver.resetNotified();
        tank.setLiquid(testLiquidC);
        assertEquals(false, tObserver.wasNotified());       
    }

}
