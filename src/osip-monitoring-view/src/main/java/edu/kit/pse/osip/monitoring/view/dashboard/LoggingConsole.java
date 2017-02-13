package edu.kit.pse.osip.monitoring.view.dashboard;

import javafx.scene.control.TextArea;

/**
 * The graphical console to show all logged events.
 * 
 * @author Martin Armbruster
 * @version 1.1
 */
class LoggingConsole extends TextArea {
    /**
     * Creates and initializes the console.
     */
    protected LoggingConsole() {
        this.setEditable(false);
        this.setWrapText(true);
    }
    
    /**
     * Logs an event.
     * 
     * @param message The logging message for the occured event.
     */
    public void log(String message) {
        this.appendText(message + "\n");
    }
}
