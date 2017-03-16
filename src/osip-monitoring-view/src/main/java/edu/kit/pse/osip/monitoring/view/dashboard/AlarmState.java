package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * Defines all possible states of an alarm in the monitoring view.
 */
public enum AlarmState {
    /**
     * The alarm is enabled and can receive notifications.
     */
    ALARM_ENABLED,
    /**
     * The alarm is disabled and receives no de- or activation.
     */
    ALARM_DISABLED
}
