package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.behavior.TankAlarm;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Visualizes an alarm with a name and the current state.
 * 
 * @author Martin Armbruster
 * @version 1.1
 */
class AlarmVisualization extends HBox implements Observer {
    /**
     * Saves the current alarm state.
     */
    private AlarmState currentState;
    
    /**
     * Label containing the name of the alarm.
     */
    private Label alarmName;
    
    /**
     * The circle represents the state of the alarm.
     */
    private Circle alarmState;
    
    /**
     * Creates and initializes a new alarm visualization.
     * 
     * @param alarmName The name of the alarm.
     */
    protected AlarmVisualization(String alarmName) {
        this.alarmName = new Label(alarmName);
        alarmState = new Circle();
        alarmState.setStroke(Color.BLACK);
        alarmState.setStrokeWidth(MonitoringViewConstants.STROKE_WIDTH);
        alarmState.setFill(MonitoringViewConstants.ALARM_ENABLED);
        currentState = AlarmState.ALARM_ENABLED;
        this.setSpacing(MonitoringViewConstants.ELEMENTS_GAP);
        this.getChildren().addAll(alarmState, this.alarmName);
    }
    
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double radius = this.getHeight() / 2.0;
        alarmState.setLayoutX(radius);
        alarmState.setLayoutY(radius);        
        alarmState.setRadius(radius);
    }
    
    /**
     * Sets the current state of the alarm.
     * 
     * @param newState The new state of the alarm.
     */
    protected void setAlarmState(AlarmState newState) {
        currentState = newState;
        if (newState == AlarmState.ALARM_ENABLED) {
            alarmState.setFill(MonitoringViewConstants.ALARM_ENABLED);
        } else if (newState == AlarmState.ALARM_DISABLED) {
            alarmState.setFill(MonitoringViewConstants.ALARM_DISABLED);
        }
    }
    
    /**
     * Called when the observed object is updated.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        if (currentState == AlarmState.ALARM_ENABLED) {
            TankAlarm<?> actualAlarm = (TankAlarm<?>) observable;
            if (actualAlarm.isAlarmTriggered()) {
                alarmState.setFill(MonitoringViewConstants.ALARM_TRIGGERED);
            } else {
                alarmState.setFill(MonitoringViewConstants.ALARM_ENABLED);
            }
        }
    }
}
