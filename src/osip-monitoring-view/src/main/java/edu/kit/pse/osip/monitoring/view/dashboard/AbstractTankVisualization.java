package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Visualizes a general tank.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
abstract class AbstractTankVisualization extends GridPane {
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
    protected AlarmVisualization temperatureOverheatingAlarm;
    
    /**
     * Alarm when a temperature becomes too low.
     */
    protected AlarmVisualization temperatureUndercoolingAlarm;
    
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
     * Constructor for sub classes. It creates all through this class available visualizations
     * except the progresses and adds the alarms.
     */
    protected AbstractTankVisualization() {
        this.setVgap(MonitoringViewConstants.ELEMENTS_GAP);
        this.setHgap(MonitoringViewConstants.ELEMENTS_GAP);
        this.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        createAndAddElements();
    }
    
    /**
     * Creates and add all elements.
     */
    private void createAndAddElements() {
        overflowAlarm = new AlarmVisualization(Translator.getInstance().getString("monitoring.tank.overflowAlarm"));
        add(overflowAlarm, 0, 0, 2, 1);
        underflowAlarm = new AlarmVisualization(Translator.getInstance().getString("monitoring.tank.underflowAlarm"));
        add(underflowAlarm, 0, 1, 2, 1);
        temperatureOverheatingAlarm = new AlarmVisualization(
                Translator.getInstance().getString("monitoring.tank.temperatureOverheatingAlarm"));
        add(temperatureOverheatingAlarm, 0, 2, 2, 1);
        temperatureUndercoolingAlarm = new AlarmVisualization(
                Translator.getInstance().getString("monitoring.tank.temperatureUndercoolingAlarm"));
        add(temperatureUndercoolingAlarm, 0, 3, 2, 1);
        drain = new GaugeVisualization(Translator.getInstance().getString("monitoring.tank.drain"));
        fillLevel = new FillLevelVisualization();
        temperature = new TemperatureVisualization();
    }
    
    /**
     * Sets the border color for this tank.
     * 
     * @param borderColor the border color.
     */
    protected final void setBorderColor(Color borderColor) {
        BorderStroke bStroke = new BorderStroke(borderColor, BorderStrokeStyle.SOLID, null,
                new BorderWidths(MonitoringViewConstants.ELEMENTS_GAP));
        Border newBorder = new Border(new BorderStroke[]{bStroke});
        this.setBorder(newBorder);
    }
    
    /**
     * Enables or disables the overflow alarm of this tank.
     * 
     * @param alarmEnabled true if the overflow alarm should be enabled. false otherwise.
     */
    protected final void setOverflowAlarmEnabled(boolean alarmEnabled) {
        setAlarmEnabled(overflowAlarm, alarmEnabled);
    }
    
    /**
     * Enables or disables the underflow alarm for this tank.
     * 
     * @param alarmEnabled true if the overflow alarm should be enabled. false otherwise.
     */
    protected final void setUnderflowAlarmEnabled(boolean alarmEnabled) {
        setAlarmEnabled(underflowAlarm, alarmEnabled);
    }
    
    /**
     * Sets the overheating alarm for the temperature enabled or disabled.
     * 
     * @param alarmEnabled true when the alarm should be enabled. false otherwise.
     */
    protected final void setTemperatureOverheatingAlarmEnabled(boolean alarmEnabled) {
        setAlarmEnabled(temperatureOverheatingAlarm, alarmEnabled);
    }
    
    /**
     * Sets the undercooling alarm for the temperature enabled or disabled.
     * 
     * @param alarmEnabled true when the alarm should be enabled and false otherwise.
     */
    protected final void setTemperatureUndercoolingAlarmEnabled(boolean alarmEnabled) {
        setAlarmEnabled(temperatureUndercoolingAlarm, alarmEnabled);
    }
    
    /**
     * Enables or disables an alarm in general.
     * 
     * @param alarm the alarm which will be enabled or disabled.
     * @param alarmEnabled true when the alarm should be enabled and false otherwise.
     */
    private void setAlarmEnabled(AlarmVisualization alarm, boolean alarmEnabled) {
        if (alarmEnabled) {
            alarm.setAlarmState(AlarmState.ALARM_ENABLED);
        } else {
            alarm.setAlarmState(AlarmState.ALARM_DISABLED);
        }
    }
    
    /**
     * Enables or disables the logging of the temperature progression.
     * 
     * @param progressionEnabled true if the temperature should be logged.
     *        false otherwise.
     */
    protected final void setTemperatureProgressionEnabled(boolean progressionEnabled) {
        progresses.setTemperatureProgressEnabled(progressionEnabled);
    }
    
    /**
     * Enables or disables the logging of the fill level progression.
     * 
     * @param progressionEnabled true if the fill level progression should be logged. false otherwise.
     */
    protected final void setFillLevelProgressionEnabled(boolean progressionEnabled) {
        progresses.setFillLevelProgressEnabled(progressionEnabled);
    }
}
