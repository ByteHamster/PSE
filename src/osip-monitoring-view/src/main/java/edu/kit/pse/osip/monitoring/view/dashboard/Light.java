package edu.kit.pse.osip.monitoring.view.dashboard;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Visualizes a traffic light.
 * 
 * @author Martin Armbruster
 * @version 1.4
 */
class Light extends VBox implements Observer {
    /**
     * Number of all available alarms.
     */
    private int numberAlarms;
    
    /**
     * List of all triggered alarms. If its count is above zero, at least one alarm is triggered.
     */
    private ArrayList<AlarmVisualization> triggeredAlarms;
    
    /**
     * List of all disabled alarms. If its count is equals to the number of available alarms, all alarms are disabled. 
     */
    private ArrayList<AlarmVisualization> disabledAlarms;
    
    /**
     * The circle for the red light.
     */
    private Circle redLight;
    
    /**
     * The circle for the green light.
     */
    private Circle greenLight;
    
    /**
     * Creates a new light.
     */
    protected Light() {
        triggeredAlarms = new ArrayList<>();
        disabledAlarms = new ArrayList<>();
        this.setSpacing(MonitoringViewConstants.ELEMENTS_GAP);
        this.setAlignment(Pos.TOP_RIGHT);
        redLight = new Circle();
        redLight.setStrokeWidth(MonitoringViewConstants.STROKE_WIDTH);
        this.getChildren().add(redLight);
        greenLight = new Circle();
        greenLight.setStrokeWidth(MonitoringViewConstants.STROKE_WIDTH);
        this.getChildren().add(greenLight);
        enableWithColor();
    }
    
    @Override
    protected void layoutChildren() {
        double radius = Math.min(this.getWidth(), this.getHeight() / 2 - MonitoringViewConstants.ELEMENTS_GAP) / 2;
        redLight.setRadius(radius);
        greenLight.setRadius(radius);
        super.layoutChildren();
    }
    
    /**
     * Sets the number of all alarms.
     * 
     * @param count the number of all alarms.
     */
    protected void setAlarmCount(int count) {
        numberAlarms = count;
    }
    
    @Override
    public void update(Observable observable, Object object) {
        AlarmVisualization alarm = (AlarmVisualization) observable;       
        triggeredAlarms.remove(alarm);
        disabledAlarms.remove(alarm);
        if (alarm.getAlarmState() == AlarmState.ALARM_DISABLED) {
            disabledAlarms.add(alarm);
        } else if (alarm.isAlarmTriggered()) {
            triggeredAlarms.add(alarm);
        }
        if (disabledAlarms.size() == numberAlarms) {
            Platform.runLater(this::disableWithColor);
        } else if (triggeredAlarms.size() > 0) {
            Platform.runLater(this::triggerWithColor);
        } else {
            Platform.runLater(this::enableWithColor);
        }
    }
    
    /**
     * Disables the light visually with changing the colors.
     */
    private void disableWithColor() {
        redLight.setStroke(MonitoringViewConstants.ALARM_DISABLED);
        redLight.setFill(Color.TRANSPARENT);
        greenLight.setStroke(MonitoringViewConstants.ALARM_DISABLED);
        greenLight.setFill(Color.TRANSPARENT);
    }
    
    /**
     * Enables the light visually with changing the colors.
     */
    private void enableWithColor() {
        redLight.setStroke(MonitoringViewConstants.ALARM_TRIGGERED);
        redLight.setFill(Color.TRANSPARENT);
        greenLight.setStroke(MonitoringViewConstants.ALARM_ENABLED);
        greenLight.setFill(MonitoringViewConstants.ALARM_ENABLED);
    }
    
    /**
     * Shows visually that at least one alarm is triggered.
     */
    private void triggerWithColor() {
        redLight.setStroke(MonitoringViewConstants.ALARM_TRIGGERED);
        redLight.setFill(MonitoringViewConstants.ALARM_TRIGGERED);
        greenLight.setStroke(MonitoringViewConstants.ALARM_ENABLED);
        greenLight.setFill(Color.TRANSPARENT);
    }
}
