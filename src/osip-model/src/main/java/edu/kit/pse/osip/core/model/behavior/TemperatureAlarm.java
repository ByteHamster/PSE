package edu.kit.pse.osip.core.model.behavior;

import edu.kit.pse.osip.core.model.base.Tank;

/**
 * An Alarm which monitors whether the temperature breaks a given threshold.
 */
public class TemperatureAlarm extends edu.kit.pse.osip.core.model.behavior.TankAlarm<Float> {
    /**
     * Creates a new TemperatureAlarm.
     * @param tank The tank to monitor the temperature.
     * @param threshold The threshold in °K.
     * @param behavior Whether the alarm should trigger if the temperature is above or below to threshold.
     */
    public TemperatureAlarm (Tank tank, Float threshold, AlarmBehavior behavior) {
        super(tank, threshold, behavior);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the temperature of the tank
     * @return the temperature
     */
    protected final Float getNotifiedValue () {
        throw new RuntimeException("Not implemented!");
    }
}
