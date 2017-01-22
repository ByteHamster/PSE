package edu.kit.pse.osip.simulation.view.settings;

import edu.kit.pse.osip.core.utils.formatting.InvalidPortException;

/**
 * This textfield is for the input of port numbers. It checks its own inputs and only allows saving them if they are valid.
 */
public class PortTextField extends javafx.scene.control.TextField {
    /**
     * Gets the port number in the textField.
     * @throws InvalidPortException Thrown if the current value in port is not valid (see FormatChecker.parsePort(String port).
     * @return The port number in the textField.
     */
    public final int getPort () {
        throw new RuntimeException("Not implemented!");
    }
}
