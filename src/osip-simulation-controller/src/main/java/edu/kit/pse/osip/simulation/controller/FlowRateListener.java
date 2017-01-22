package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * This Listener listens for changes in the in- and outflow slider.
 */
public interface FlowRateListener {
    /**
     * Alerts the Controller that the flow changed.
     * @param tank The tank that was updated
     */
    public void onFlowRateUpdated (TankSelector tank);
}
