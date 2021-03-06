package edu.kit.pse.osip.core.model.behavior;

import edu.kit.pse.osip.core.model.base.AbstractTank;

/**
 * An Alarm which monitors whether the temperature breaks a given threshold.
 * 
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class TemperatureAlarm extends TankAlarm<Float> {
    /**
     * Creates a new TemperatureAlarm.
     * 
     * @param tank The tank to monitor the temperature.
     * @param threshold The threshold in °K.
     * @param behavior Whether the alarm should trigger if the temperature is above or below to threshold.
     */
    public TemperatureAlarm(AbstractTank tank, Float threshold, AlarmBehavior behavior) {
        super(tank, threshold, behavior);
    }
    
    /**
     * Returns the temperature of the tank.
     * 
     * @return the temperature.
     */
    protected final Float getNotifiedValue() {
        return liquid.getTemperature();
    }
}
