package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Visualizes an alarm with a name and the current state.
 * 
 * @author Martin Armbruster
 * @version 1.7
 */
class AlarmVisualization extends Observable implements Observer {
    /**
     * Saves the current alarm state.
     */
    private AlarmState currentState;
    
    /**
     * Saves if the alarm is currently triggered or not. This is used when the the alarm state changes back to enabled.
     */
    private ObservableBoolean alarm;

    private TankSelector selector;
    
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
     * @param alarm The boolean alarm
     * @param selector the TankSelector of the alarms tank
     */
    protected AlarmVisualization(String alarmName, ObservableBoolean alarm, TankSelector selector) {
        if (alarm == null || alarmName == null || selector == null) {
            throw new NullPointerException("The AlarmVisualization arguments must nut be null!");
        }
        this.alarm = alarm;
        alarm.addObserver(this);
        this.selector = selector;
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
     * Returns the triggered state of the represented alarm regardless of an enabled or disabled alarm.
     * 
     * @return true if this alarm is triggered. false otherwise.
     */
    protected boolean isAlarmTriggered() {
        return alarm.getValue();
    }
    
    /**
     * Sets the current state of the alarm.
     * 
     * @param newState The new state of the alarm.
     */
    protected void setAlarmState(AlarmState newState) {
        currentState = newState;
        if (newState == AlarmState.ALARM_ENABLED) {
            if (alarm.getValue()) {
                alarmState.setFill(MonitoringViewConstants.ALARM_TRIGGERED);
            } else {
                alarmState.setFill(MonitoringViewConstants.ALARM_ENABLED);
            }
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
        super.setChanged();
        super.notifyObservers(selector);
        if (currentState == AlarmState.ALARM_DISABLED) {
            return;
        }
        if (alarm.getValue()) {
            Platform.runLater(() -> {
                alarmState.setFill(MonitoringViewConstants.ALARM_TRIGGERED);
                alarmState.setFill(MonitoringViewConstants.ALARM_TRIGGERED);
                Alert user = new Alert(AlertType.INFORMATION);
                user.setTitle(Translator.getInstance().getString("monitoring.alarmDialog.header"));
                user.setHeaderText(Translator.getInstance().getString("monitoring.alarmDialog.header"));
                user.setContentText(String.format(Translator.getInstance().getString("monitoring.alarmDialog.content"),
                        getAlarmName(), Translator.getInstance().getString(TankSelector.TRANSLATOR_LABEL_PREFIX
                                + selector.name()).toLowerCase()));
                user.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                user.show();
            });
        } else {
            Platform.runLater(() -> alarmState.setFill(MonitoringViewConstants.ALARM_ENABLED));
        }
    }
}
