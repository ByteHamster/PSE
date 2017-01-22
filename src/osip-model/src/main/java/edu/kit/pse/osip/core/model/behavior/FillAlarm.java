package edu.kit.pse.osip.core.model.behavior;

import edu.kit.pse.osip.core.model.base.Tank;

/**
 * An alarm which monitors whether the fill level breaks a given threshold.
 */
public class FillAlarm extends edu.kit.pse.osip.core.model.behavior.TankAlarm<Float> {
    /**
     * Constructs a new FillAlarm.
     * @param tank The tank to monitor.
     * @param threshold The fill level threshold in %.
     * @param behavior Whether the alarm should trigger if thefill level is above or below the threshold.
     */
    public FillAlarm (Tank tank, Float threshold, AlarmBehavior behavior) {
        super(tank, threshold, behavior);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the fill level.
     * @return the fill level.
     */
    protected final Float getNotifiedValue () {
        throw new RuntimeException("Not implemented!");
    }
}
