package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.formatting.InvalidPortException;

/**
 * Provides abstraction from the view
 */
public interface SimulationSettingsWindow {
    /**
     * Gets he current value of the jitter-scrollbar.
     * @return The current value of the jitter-scrollbar.
     */
    public int getJitter ();
    /**
     * Gets the port number in Porttextfield id.
     * @throws InvalidPortException Thrown if the current value in port is not valid (see FormatChecker.parsePort(String port).
     * @return The port number in Porttextfield id.
     * @param tank 
     */
    public int getPort (TankSelector tank);
    /**
     * Sets the listener that gets notified as soon as the settings change
     * @param listener 
     */
    public void setSettingsChangedListener (SettingsChangedListener listener);
}
