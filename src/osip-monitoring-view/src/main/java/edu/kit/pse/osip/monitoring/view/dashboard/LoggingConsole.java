package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.TankAlarm;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The graphical console to show all logged events.
 * 
 * @author Martin Armbruster
 * @version 1.4
 */
class LoggingConsole extends ScrollPane implements Observer {
    /**
     * The TextFlow showing the actual logging messages.
     */
    private TextFlow flow;
    
    /**
     * Creates and initializes the console.
     */
    protected LoggingConsole() {
        flow = new TextFlow();
        // ** Solution for: ScrollPane scrolls automatically.
        // Based on: http://stackoverflow.com/questions/13156896/javafx-auto-scroll-down-scrollpane
        flow.heightProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                flow.layout();
                this.setVvalue(this.getVmax());
            });
        // **
        this.setContent(flow);
        this.setFitToWidth(true);
        this.setFitToHeight(true);
        UIOutputStream os = new UIOutputStream(this);
        System.setErr(os);
        System.setOut(os);
    }
    
    /**
     * Logs an event.
     * 
     * @param message The logging message for the occurred event.
     */
    public void log(String message) {
        flow.getChildren().add(new Text(message));
    }

    @Override
    public void update(Observable o, Object arg) {
        AlarmVisualization alarm = (AlarmVisualization) o;
        TankAlarm<?> ta = (TankAlarm<?>) arg;
        if (ta != null && ta.isAlarmTriggered()) {
            Text alarmText = new Text(
                    "[" + OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] "
                    + String.format(Translator.getInstance().getString("monitoring.logging.alarmTriggered"),
                      alarm.getAlarmName(), Translator.getInstance().getString(
                      TankSelector.TRANSLATOR_LABEL_PREFIX + ta.getTank().getTankSelector().name()).toLowerCase()));
            if (alarm.getAlarmState() == AlarmState.ALARM_DISABLED) {
                alarmText.setFill(MonitoringViewConstants.ALARM_DISABLED);
            }
            flow.getChildren().add(alarmText);
        }
    } 
}
