package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.ProductionSite;

/**
 * Handles closing the overflow dialog
 *
 * @version 1.0
 * @author David Kahles
 */
public class OverflowClosedHandler {
    private ProductionSite productionSite;

    /**
     * Creates a new OverflowClosedHandler
     * @param site The production site which needs to get reseted.
     */
    public OverflowClosedHandler(ProductionSite site) {
        productionSite = site;
    }

    /**
     * Called when the overflow dialog gets closed
     */
    public void handle() {
        productionSite.reset();
    }
}
