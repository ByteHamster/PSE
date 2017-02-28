package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.TankAlarm;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Visualizes an alarm with a name and the current state.
 * 
 * @author Martin Armbruster
 * @version 1.6
 */
class AlarmVisualization extends Observable implements Observer {
    /**
     * Saves the current alarm state.
     */
    private AlarmState currentState;
    
    /**
     * The layout for this visualizations.
     */
    private HBox layout;
    
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
        layout = new HBox(MonitoringViewConstants.ELEMENTS_GAP) {
            @Override
            protected void layoutChildren() {
                double radius = this.getHeight() / 2.0;       
                alarmState.setRadius(radius);
                super.layoutChildren();
            }
        };
        layout.setPrefWidth(MonitoringViewConstants.PREF_HEIGHT_FOR_BARS * 1.2);
        layout.getChildren().addAll(alarmState, this.alarmName);
    }
    
    /**
     * Returns the layout for this visualization.
     * 
     * @return the layout for this visualization.
     */
    protected HBox getLayout() {
        return layout;
    }
    
    /**
     * Returns the name of the represented alarm.
     * 
     * @return the name of the represented alarm.
     */
    protected String getAlarmName() {
        return alarmName.getText();
    }
    
    /**
     * Returns the current state of the alarm.
     * 
     * @return the current state of the alarm.
     */
    protected AlarmState getAlarmState() {
        return currentState;
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
        super.setChanged();
        super.notifyObservers(null);
    }
    
    /**
     * Called when the observed object is updated.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        TankAlarm<?> actualAlarm = (TankAlarm<?>) observable;
        super.setChanged();
        super.notifyObservers(actualAlarm);
        if (currentState == AlarmState.ALARM_DISABLED) {
            return;
        }
        if (actualAlarm.isAlarmTriggered()) {
            alarmState.setFill(MonitoringViewConstants.ALARM_TRIGGERED);
            Alert user = new Alert(AlertType.INFORMATION);
            user.setTitle(Translator.getInstance().getString("monitoring.alarmDialog.header"));
            user.setHeaderText(Translator.getInstance().getString("monitoring.alarmDialog.header"));
            user.setContentText(String.format(Translator.getInstance().getString("monitoring.alarmDialog.content"),
                    getAlarmName(), Translator.getInstance().getString(TankSelector.TRANSLATOR_LABEL_PREFIX
                            + actualAlarm.getTank().getTankSelector().name()).toLowerCase()));
            user.show();
        } else {
            alarmState.setFill(MonitoringViewConstants.ALARM_ENABLED);
        }
    }
}
