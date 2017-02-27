package edu.kit.pse.osip.simulation.view.settings;

import edu.kit.pse.osip.core.utils.formatting.FormatChecker;
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
        setText("0");
        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!FormatChecker.isValidPort(newValue)) {
                setStyle("-fx-control-inner-background: #ff9d9d");
            } else {
                setStyle("-fx-control-inner-background: #ffffff");
            }
        });
        focusedProperty().addListener((arg0, oldPropertyValue, newPropertyValue) -> {
            if (newPropertyValue) {
                Platform.runLater(this::selectAll);
            }
        });
    }


    /**
     * Gets the port number in the textField.
     * @return The port number in the textField.
     */
    protected final int getPort() {
        return Integer.parseInt(getText());
    }
}
