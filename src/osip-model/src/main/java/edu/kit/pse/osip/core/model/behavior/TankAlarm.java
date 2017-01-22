package edu.kit.pse.osip.core.model.behavior;

import java.util.Observable;

import edu.kit.pse.osip.core.model.base.AbstractTank;

/**
 * An abstract class which monitors some attribute of an AbstractTank.
 */
public abstract class TankAlarm <T> extends java.util.Observable implements java.util.Observer {
    public AbstractTank tank;
    /**
     * Creates a new TankAlarm.
     * @param tank The tank which has the property to monitor.
     * @param threshold The threshold of the value.
     * @param behavior Whether the alarm should trigger if the value is bigger or smaller than the threshold.
     */
    public TankAlarm (AbstractTank tank, T threshold, AlarmBehavior behavior) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the value of the tank, which is monitored by the alarm. This should be overriden by subclasses.
     */
    protected abstract T getNotifiedValue ();
    /**
     * The method called by the tank if something changes.
     * @param observable the observerable object.
     * @param object The changed value.
     */
    public final void update (Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Return whether the alarm is active at the moment.
     * @return true if the alarm is currently triggered.
     */
    public final boolean isAlarmTriggered () {
        throw new RuntimeException("Not implemented!");
    }
}
