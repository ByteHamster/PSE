package edu.kit.pse.osip.simulation.view.settings;

import javafx.application.Platform;
import javafx.scene.control.TextField;

/**
 * This textfield is for the input of port numbers.
 * It checks its own inputs and only allows saving them if they are valid.
 *
 * @author Hans.Peter Lehmann
 * @version 1.0
 */
class PortTextField extends TextField {

    /**
     * Modified text field that only accepts valid ports
     */
    protected PortTextField() {
        setPrefWidth(80);
        setText("0");
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValidPort(newValue)) {
                setText(oldValue);
            }
        });
        focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                Platform.runLater(this::selectAll);
            }
        });
    }

    /**
     * Checks if the given string is a valid port
     * @param s The string
     * @return true if the string is a valid port
     */
    private boolean isValidPort(String s) {
        if (!s.matches("\\d+")) {
            return false;
        }
        int val = Integer.parseInt(s);
        return val < 61000 && val > 1024;
    }

    /**
     * Gets the port number in the textField.
     * @return The port number in the textField.
     */
    protected final int getPort() {
        return Integer.parseInt(getText());
    }
}
