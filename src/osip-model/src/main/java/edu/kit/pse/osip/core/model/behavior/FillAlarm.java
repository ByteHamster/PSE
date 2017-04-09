package edu.kit.pse.osip.core.model.behavior;

import edu.kit.pse.osip.core.model.base.AbstractTank;

/**
 * An alarm which monitors whether the fill level breaks a given threshold.
 * 
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public class FillAlarm extends TankAlarm<Float> {
    /**
     * Constructs a new FillAlarm.
     * 
     * @param tank The tank to monitor.
     * @param threshold The fill level threshold in % between 0 and 1.
     * @param behavior Whether the alarm should trigger if the fill level is above or below the threshold.
     */
    public FillAlarm(AbstractTank tank, Float threshold, AlarmBehavior behavior) {
        super(tank, threshold, behavior);
    }
    
    /**
     * Returns the fill level.
     * 
     * @return the fill level.
     */
    @Override
    protected final Float getNotifiedValue() {
        return getTank().getFillLevel();
    }
}
