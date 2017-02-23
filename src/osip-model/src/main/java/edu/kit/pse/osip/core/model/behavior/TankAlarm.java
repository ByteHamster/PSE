package edu.kit.pse.osip.core.model.behavior;

import java.util.Observable;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.Liquid;

/**
 * An abstract class which monitors some attribute of an AbstractTank.
 * @param <T> The type of notified value
 * @author Maximilian Schwarzmann
 * @version 1.0 
 */
public abstract class TankAlarm <T extends Comparable<T>> extends java.util.Observable implements java.util.Observer {
    /**
     * tank must be protected so that all alarms  can get information from it
     */
    protected AbstractTank tank;
    private T threshold;
    private AlarmBehavior behavior;
    private boolean triggered = false;
    /**
     * liquid must be protected so that all alarms can interact with it
     */
    protected Liquid liquid;
    
    /**
     * Creates a new TankAlarm.
     * @param tank The tank which has the property to monitor.
     * @param threshold The threshold of the value.
     * @param behavior Whether the alarm should trigger if the value is bigger or smaller than the threshold.
     */
    public TankAlarm(AbstractTank tank, T threshold, AlarmBehavior behavior) {
        this.tank = tank;
        this.threshold = threshold;
        this.behavior = behavior;
        tank.addObserver(this);
        update(tank, tank.getLiquid());
    }
    /**
     * Get the value of the tank, which is monitored by the alarm. This should be overriden by subclasses.
     * @return notified value
     */
    protected abstract T getNotifiedValue();
    /**
     * The method called by the tank if something changes.
     * @param observable the observerable object.
     * @param object The changed value.
     */
    public final void update(Observable observable, Object object) {
        assert (object instanceof Liquid);
        liquid = (Liquid) object;
        if (behavior == AlarmBehavior.GREATER_THAN) {
            if (this.getNotifiedValue().compareTo(threshold) > 0 && !triggered) {
                triggered = true;
                setChanged();
            } else if (this.getNotifiedValue().compareTo(threshold) <= 0 && triggered) {
                triggered = false;
                setChanged();
            }
        } else {
            if (this.getNotifiedValue().compareTo(threshold) < 0 && !triggered) {
                triggered = true;
                setChanged();
            } else if (this.getNotifiedValue().compareTo(threshold) >= 0 && triggered) {
                triggered = false;
                setChanged();
            }
        }        
        notifyObservers(triggered);
    }
    /**
     * Return whether the alarm is active at the moment.
     * @return true if the alarm is currently triggered.
     */
    public final boolean isAlarmTriggered() {
        return triggered;
    }

    /**
     * Get the tank of the alarm
     */
    public AbstractTank getTank() {
        return tank;
    }
}
