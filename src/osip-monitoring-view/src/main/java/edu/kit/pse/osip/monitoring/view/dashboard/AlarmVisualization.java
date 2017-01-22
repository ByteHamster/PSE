package edu.kit.pse.osip.monitoring.view.dashboard;

public class AlarmVisualization implements java.util.Observer {
    /**
     * Label containing the name of the alarm.
     */
    private javafx.scene.control.Label alarmName;
    /**
     * The circle represents the state of the alarm.
     */
    private javafx.scene.shape.Circle alarmState;
    /**
     * Creates and initializes a new alarm visualization.
     * @param alarmName The name of the alarm.
     */
    protected AlarmVisualization (String alarmName) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the current state of the alarm.
     * @param newState The new state of the alarm.
     */
    protected final void setAlarmState (AlarmState newState) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when the observed object is updated.
     * @param observable The observed object.
     * @param object The new value.
     */
    public final void update (java.util.Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
}
