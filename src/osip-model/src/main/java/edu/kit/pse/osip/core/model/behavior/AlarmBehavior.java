package edu.kit.pse.osip.core.model.behavior;

/**
 * This specifies whether an alarm is triggered if the value is below or greater than the threshold.
 * @author Maximilian Schwarzmann
 * @version 1.0
 */
public enum AlarmBehavior {
    /**
     * Alarm is triggered when the observed value gets greater than the given value.
     */
    GREATER_THAN,
    /**
     * Alarm is triggered when the observed value gets smaller than the given value.
     */
    SMALLER_THAN
}
