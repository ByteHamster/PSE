package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * Visualises a general tank.
 */
public abstract class AbstractTankVisualization extends javafx.scene.layout.Pane {
    /**
     * Visualization for the overflow alarm.
     */
    protected AlarmVisualization overflowAlarm;
    /**
     * Visualization for the underflow alarm.
     */
    protected AlarmVisualization underflowAlarm;
    /**
     * Alarm when the temperature becomes too high.
     */
    protected AlarmVisualization temperatureOverflowAlarm;
    /**
     * Alarm when a temperature becomes too low.
     */
    protected AlarmVisualization temperatureUnderflowAlarm;
    /**
     * Visualization for the drain pipe.
     */
    protected GaugeVisualization drain;
    /**
     * Visualization of the fill level.
     */
    protected FillLevelVisualization fillLevel;
    /**
     * Visualization of the temperature.
     */
    protected TemperatureVisualization temperature;
    /**
     * Visualization of the progressions.
     */
    protected ProgressOverview progresses;
    /**
     * Enables or disables the overflow alarm of this tank.
     * @param alarmEnabled true if the overflow alarm should be enabled. false otherwise.
     */
    protected final void setOverflowAlarmEnabled (boolean alarmEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the underflow alarm for this tank.
     * @param alarmEnabled true if the overflow alarm should be enabled. false otherwise.
     */
    protected final void setUnderflowAlarmEnabled (boolean alarmEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the overflow alarm for the temperature enabled or disabled.
     * @param alarmEnabled true when the alarm should be enabled. false otherwise.
     */
    protected final void setTemperatureOverflowAlarmEnabled (boolean alarmEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the underflow alarm for the temperature enabled or disabled.
     * @param alarmEnabled true when the alarm should be enabled and false otherwise.
     */
    protected final void setTemperatureUnderflowAlarmEnabled (boolean alarmEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the logging of the temperature progression.
     * @param progressionEnabled true if the temperature should be logged.
     *        false otherwise.
     */
    protected final void setTemperatureProgressionEnabled (boolean progressionEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the logging of the fill level progression.
     * @param progressionEnabled true if the fill level progression should be logged. false otherwise.
     */
    protected final void setFillLevelProgressionEnabled (boolean progressionEnabled) {
        throw new RuntimeException("Not implemented!");
    }
}
