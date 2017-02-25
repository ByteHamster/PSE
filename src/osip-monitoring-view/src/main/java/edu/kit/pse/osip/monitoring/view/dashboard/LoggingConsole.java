package edu.kit.pse.osip.monitoring.view.dashboard;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The graphical console to show all logged events.
 * 
 * @author Martin Armbruster
 * @version 1.3
 */
class LoggingConsole extends ScrollPane {
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
}
