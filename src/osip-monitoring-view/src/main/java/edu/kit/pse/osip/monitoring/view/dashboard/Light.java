package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * Visualizes a traffic light.
 */
public class Light extends javafx.scene.layout.Pane implements java.util.Observer {
    /**
     * Stores the current state of all alarms: The light is enabled when at least one other alarm is enabled.
     */
    private AlarmState state = AlarmState.ALARM_ENABLED;
    /**
     * The circle for the red light.
     */
    private javafx.scene.shape.Circle redLight;
    /**
     * The circle for the green light.
     */
    private javafx.scene.shape.Circle greenLight;
    /**
     * Creates a new light.
     */
    protected Light () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the alarm state.
     * @param newState The new alarm state.
     */
    protected final void setAlarmState (AlarmState newState) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when the observed object updates.	
     * @param observable The observed object.
     * @param object The new value.
     */
    public final void update (java.util.Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
}
