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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The graphical console to show all logged events.
 * 
 * @author Martin Armbruster
 * @version 1.6
 */
class LoggingConsole extends TabPane implements Observer {
    /**
     * The TextFlow showing logging messages of occurred alarms.
     */
    private TextFlow alarms;
    /**
     * The TextFlow showing the actual logging messages from System.out or System.err.
     */
    private TextFlow stdIO;
    
    /**
     * Creates and initializes the console.
     */
    protected LoggingConsole() {
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        stdIO = new TextFlow();
        alarms = new TextFlow();
        this.getTabs().addAll(createTab(Translator.getInstance().getString("monitoring.logging.tab.alarms"), alarms),
                createTab(Translator.getInstance().getString("monitoring.logging.tab.io"), stdIO));
        
        UIOutputStream os = new UIOutputStream(this);
        System.setErr(os);
        System.setOut(os);
    }
    
    /**
     * Creates a logging tab.
     * 
     * @param tabName name of the tab.
     * @param actualContent the TextFlow instance that will be placed in the tab within a ScrollPane.
     * @return the created tab.
     */
    private Tab createTab(String tabName, final TextFlow actualContent) {
        Tab tab = new Tab(tabName);
        ScrollPane pane = new ScrollPane();
        // ** Solution for: ScrollPane scrolls automatically.
        // Based on: http://stackoverflow.com/questions/13156896/javafx-auto-scroll-down-scrollpane
        actualContent.heightProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                actualContent.layout();
                pane.setVvalue(pane.getVmax());
            });
        // **
        pane.setContent(actualContent);
        pane.setFitToWidth(true);
        pane.setFitToHeight(true);
        tab.setContent(pane);
        return tab;
    }
    
    /**
     * Logs an event with a time stamp to the i/o tab.
     * 
     * @param message The logging message for the occurred event.
     */
    protected void log(String message) {
        stdIO.getChildren().add(prepareText(message));
    }
    
    /**
     * Logs an event without a time stamp to the i/o tab.
     * 
     * @param message the logging message for the occurred event.
     */
    protected void logWithoutTime(String message) {
        stdIO.getChildren().add(new Text(message));
    }
    
    /**
     * Prepares a text for logging with adding a time stamp.
     * 
     * @param text the raw message for output.
     * @return a Text instance ready for logging.
     */
    private Text prepareText(String text) {
        String time = "[" + OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] ";
        return new Text(time + text);
    }

    @Override
    public void update(Observable o, Object arg) {
        AlarmVisualization alarm = (AlarmVisualization) o;
        TankAlarm<?> ta = (TankAlarm<?>) arg;
        if (ta != null && ta.isAlarmTriggered()) {
            Text alarmText = prepareText(String.format(Translator.getInstance()
                    .getString("monitoring.logging.alarmTriggered"), alarm.getAlarmName(), Translator.getInstance()
                    .getString(TankSelector.TRANSLATOR_LABEL_PREFIX + ta.getTank().getTankSelector().name())
                    .toLowerCase()) + "\n");
            if (alarm.getAlarmState() == AlarmState.ALARM_DISABLED) {
                alarmText.setFill(MonitoringViewConstants.ALARM_DISABLED);
            }
            alarms.getChildren().add(alarmText);
        }
    } 
}
