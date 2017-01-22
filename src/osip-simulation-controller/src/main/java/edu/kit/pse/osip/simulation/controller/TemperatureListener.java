package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * This Listener listens for changes in the temperature slider.
 */
public interface TemperatureListener {
    /**
     * Alerts the Controller that the temperature changed.
     * @param tank The tank that was updated
     */
    public void onTempUpdated (TankSelector tank);
}
