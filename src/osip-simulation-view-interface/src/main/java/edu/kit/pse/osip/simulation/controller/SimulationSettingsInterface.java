package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Provides abstraction from the view
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public interface SimulationSettingsInterface {
    /**
     * Gets the port number in Porttextfield of the given tank
     * @return The port number in Porttextfield of the given tank
     * @param tank The tank to get the port from
     */
    int getPort(TankSelector tank);

    /**
     * Sets the listener that gets notified as soon as the settings change
     * @param listener The listener to be called when the settings values change
     */
    void setSettingsChangedListener(EventHandler<ActionEvent> listener);

    /**
     * Shows the window
     */
    void show();

    /**
     * Closes the window
     */
    void close();

}
