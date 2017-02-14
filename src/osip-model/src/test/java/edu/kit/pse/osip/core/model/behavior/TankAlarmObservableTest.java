package edu.kit.pse.osip.core.model.behavior;

import static org.junit.Assert.assertEquals;

import java.util.Observable;
import java.util.Observer;

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
    
   /**
    * Helper observer for tests
    * @author Maximilian Schwarzmann
    * @version 1.0
    */
    class TestObserver implements Observer {
        private boolean notified = false;
        @Override
        public void update(Observable o, Object arg) {
            notified = true;                
        }            
    
        /**
         * Getter for notified 
         * @return true if notified
         */
        public boolean getNotified() {
            return notified;
        }
    }    
    
    /**
     * Test if alarm gives notification on changes
     */
    @Test
    public void testObservableNotify() {       
        testLiquid = new Liquid(150f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, new Pipe(200f, 30), new Pipe(200f, 30));
        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);        
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        tank.addObserver(alarm);
        tank.setLiquid(testLiquid);
        assertEquals(true, tObserver.getNotified());
        
    }

    /**
     * Test if the alarm gives no notification on no changes
     */
    @Test
    public void testObservableNoNotify() {
        testLiquid = new Liquid(50f, 300f, new Color(0.5, 0.5, 0.5));
        tank = new Tank(200f, TankSelector.valuesWithoutMix()[0], testLiquid, new Pipe(200f, 30), new Pipe(200f, 30));
        alarm = new FillAlarm(tank, 50f, AlarmBehavior.GREATER_THAN);        
        TestObserver tObserver = new TestObserver();
        alarm.addObserver(tObserver);
        tank.addObserver(alarm);
        tank.setLiquid(testLiquid);
        assertEquals(false, tObserver.getNotified());
    }    
}
