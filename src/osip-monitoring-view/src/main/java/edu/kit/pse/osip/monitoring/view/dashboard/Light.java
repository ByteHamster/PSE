package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.behavior.TankAlarm;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Visualizes a traffic light.
 * 
 * @author Martin Armbruster
 * @version 1.2
 */
class Light extends VBox implements Observer {
    /**
     * Stores the current state of all alarms: The light is enabled when at least one other alarm is enabled.
     */
    private AlarmState state = AlarmState.ALARM_ENABLED;
    
    /**
     * Counter for all triggered alarms. If it's above zero, at least one alarm is triggered.
     */
    private int triggeredAlarms;
    
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
    protected Light() {
        triggeredAlarms = 0;
        this.setSpacing(MonitoringViewConstants.ELEMENTS_GAP);
        this.setAlignment(Pos.TOP_RIGHT);
        redLight = new Circle();
        redLight.setStrokeWidth(MonitoringViewConstants.STROKE_WIDTH);
        this.getChildren().add(redLight);
        greenLight = new Circle();
        greenLight.setStrokeWidth(MonitoringViewConstants.STROKE_WIDTH);
        this.getChildren().add(greenLight);
        state = AlarmState.ALARM_ENABLED;
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
     * Sets the alarm state.
     * 
     * @param newState The new alarm state.
     */
    protected void setAlarmState(AlarmState newState) {
        state = newState;
        if (newState == AlarmState.ALARM_DISABLED) {
            disableWithColor();
        } else if (triggeredAlarms == 0) {
            triggerWithColor();
        } else {
            enableWithColor();
        }
    }
    
    /**
     * Called when the observed object updates.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        TankAlarm<?> alarm = (TankAlarm<?>) observable;
        if (alarm.isAlarmTriggered()) {
            triggeredAlarms++;
        } else {
            // It's assumed that the light receives a notification about an alarm that isn't triggered
            // after this alarm was triggered. 
            triggeredAlarms--;
        }
        if (state == AlarmState.ALARM_ENABLED) {
            if (triggeredAlarms == 0) {
                enableWithColor();
            } else {
                triggerWithColor();
            }
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
