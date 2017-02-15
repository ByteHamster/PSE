package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.formatting.InvalidPortException;

/**
 * Provides abstraction from the view
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public interface SimulationSettingsInterface {
    /**
     * Gets the port number in Porttextfield of the given tank
     * @throws InvalidPortException Thrown if the current value in the text field
     * is not valid (see FormatChecker.parsePort(String port).
     * @return The port number in Porttextfield of the given tank
     * @param tank The tank to get the port from
     */
    int getPort(TankSelector tank);

    /**
     * Sets the listener that gets notified as soon as the settings change
     * @param listener The listener to be called when the settings values change
     */
    void setSettingsChangedListener(SettingsChangedListener listener);

    /**
     * Shows the window
     */
    void show();

    /**
     * Closes the window
     */
    void close();
}
