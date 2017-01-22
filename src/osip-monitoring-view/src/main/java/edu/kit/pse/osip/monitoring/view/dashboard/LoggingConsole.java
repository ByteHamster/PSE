package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * The graphical console to show all logged events.
 */
public class LoggingConsole extends javafx.scene.control.ScrollPane {
    /**
     * The actual area in which all logged events will be shown.
     */
    private javafx.scene.control.TextArea console;
    /**
     * Creates and initializes the console.
     */
    protected LoggingConsole () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Logs an event.
     * @param message The logging message for the occured event.
     */
    public final void log (String message) {
        throw new RuntimeException("Not implemented!");
    }
}
