package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.AlarmBehavior;
import edu.kit.pse.osip.core.model.behavior.FillAlarm;
import edu.kit.pse.osip.core.model.behavior.TemperatureAlarm;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Visualizes a general tank.
 * 
 * @author Martin Armbruster
 * @version 1.7
 */
abstract class AbstractTankVisualization extends GridPane {
    /**
     * Visualization for the overflow alarm.
     */
    private AlarmVisualization overflowAlarm;
    
    /**
     * The actual overflow alarm that triggers the visualization.
     */
    private FillAlarm overflow;
    
    /**
     * Visualization for the underflow alarm.
     */
    private AlarmVisualization underflowAlarm;
    
    /**
     * The actual underflow alarm that triggers the visualization.
     */
    private FillAlarm underflow;
    
    /**
     * Alarm when the temperature becomes too high.
     */
    private AlarmVisualization temperatureOverheatingAlarm;
    
    /**
     * The actual alarm when the temperature becomes too high that triggers the visualization.
     */
    private TemperatureAlarm overheating;
    
    /**
     * Alarm when a temperature becomes too low.
     */
    private AlarmVisualization temperatureUndercoolingAlarm;
    
    /**
     * The actual alarm when the temperature becomes too low that triggers the visualization.
     */
    private TemperatureAlarm undercooling;
    
    /**
     * The layout of the row in which the alarms are.
     */
    protected HBox alarmPane;
    
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
     * 
     * @param tank this visualization is connected to that tank.
     * @throws NullPointerException when the tank is null.
     */
    protected AbstractTankVisualization(AbstractTank tank) {
        if (tank == null) {
            throw new NullPointerException("Tank is null.");
        }
        this.setVgap(MonitoringViewConstants.ELEMENTS_GAP);
        this.setHgap(MonitoringViewConstants.ELEMENTS_GAP);
        this.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        setupElements(tank);
    }
    
    /**
     * Creates and add all elements inclusive the alarms.
     * 
     * @param tank alarms and elements are connected to this tank.
     */
    private void setupElements(AbstractTank tank) {
        Label tankName = new Label(String.format(Translator.getInstance().getString("monitoring.tank.tankName"),
                Translator.getInstance().getString(TankSelector.TRANSLATOR_LABEL_PREFIX + tank.getTankSelector()
                .name())));
        overflowAlarm = new AlarmVisualization(Translator.getInstance().getString("monitoring.tank.overflowAlarm"));
        underflowAlarm = new AlarmVisualization(Translator.getInstance().getString("monitoring.tank.underflowAlarm"));
        temperatureOverheatingAlarm = new AlarmVisualization(
                Translator.getInstance().getString("monitoring.tank.temperatureOverheatingAlarm"));
        temperatureUndercoolingAlarm = new AlarmVisualization(
                Translator.getInstance().getString("monitoring.tank.temperatureUndercoolingAlarm"));
        
        VBox box = new VBox(MonitoringViewConstants.ELEMENTS_GAP);
        box.getChildren().addAll(tankName, overflowAlarm.getLayout(), underflowAlarm.getLayout(),
                temperatureOverheatingAlarm.getLayout(), temperatureUndercoolingAlarm.getLayout());
        alarmPane = new HBox(MonitoringViewConstants.ELEMENTS_GAP);
        alarmPane.getChildren().add(box);
        this.add(alarmPane, 0, 0, 2, 1);
        
        drain = new GaugeVisualization(Translator.getInstance().getString("monitoring.tank.drain"));
        fillLevel = new FillLevelVisualization();
        temperature = new TemperatureVisualization();
        progresses = new ProgressOverview(tank);
        

        overflow = new FillAlarm(tank, MonitoringViewConstants.OVERFLOW_ALARM_THRESHOLD, AlarmBehavior.GREATER_THAN);
        overflow.addObserver(overflowAlarm);
        underflow = new FillAlarm(tank, MonitoringViewConstants.UNDERFLOW_ALARM_THRESHOLD, AlarmBehavior.SMALLER_THAN);
        underflow.addObserver(underflowAlarm);
        overheating = new TemperatureAlarm(tank, MonitoringViewConstants.TEMPERATURE_OVERHEATING_THRESHOLD,
                AlarmBehavior.GREATER_THAN);
        overheating.addObserver(temperatureOverheatingAlarm);
        undercooling = new TemperatureAlarm(tank, MonitoringViewConstants.TEMPERATURE_UNDERCOOLING_THRESHOLD,
                AlarmBehavior.SMALLER_THAN);
        undercooling.addObserver(temperatureUndercoolingAlarm);
        tank.getOutPipe().addObserver(drain);
        tank.addObserver(fillLevel);
        tank.addObserver(temperature);
    }
    
    /**
     * Returns the visualization of the overflow alarm.
     * 
     * @return the visualization of the overflow alarm.
     */
    protected AlarmVisualization getOverflowAlarm() {
        return overflowAlarm;
    }
    
    /**
     * Returns the visualization of the underflow alarm.
     * 
     * @return the visualization of the underflow alarm.
     */
    protected AlarmVisualization getUnderflowAlarm() {
        return underflowAlarm;
    }
    
    /**
     * Returns the visualization of the overheating alarm.
     * 
     * @return the visualization of the overheating alarm.
     */
    protected AlarmVisualization getTemperatureOverheatingAlarm() {
        return temperatureOverheatingAlarm;
    }
    
    /**
     * Returns the visualization of the undercooling alarm.
     * 
     * @return the visualization of the undercooling alarm.
     */
    protected AlarmVisualization getTemperatureUndercoolingAlarm() {
        return temperatureUndercoolingAlarm;
    }
    
    /**
     * Sets the border color for this tank.
     * 
     * @param borderColor the border color.
     */
    protected final void setBorderColor(Color borderColor) {
        BorderStroke bStroke = new BorderStroke(borderColor, BorderStrokeStyle.SOLID, null,
                new BorderWidths(MonitoringViewConstants.TANK_BORDER_WIDTH));
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
    protected void setTemperatureProgressionEnabled(boolean progressionEnabled) {
        progresses.setTemperatureProgressEnabled(progressionEnabled);
    }
    
    /**
     * Enables or disables the logging of the fill level progression.
     * 
     * @param progressionEnabled true if the fill level progression should be logged. false otherwise.
     */
    protected void setFillLevelProgressionEnabled(boolean progressionEnabled) {
        progresses.setFillLevelProgressEnabled(progressionEnabled);
    }
    
    /**
     * Updates the progressions.
     */
    protected void updateProgressions() {
        progresses.updateProgressions();
    }
}
