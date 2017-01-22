package edu.kit.pse.osip.core.model.behavior;

import edu.kit.pse.osip.core.model.base.ProductionSite;

/**
 * A sequence of automated changes to parameters of the simulation. It is used to have changes in the simulation (so it looks more interesting) without the need to adjust parameters all the time.
 */
public class Scenario extends java.util.Observable {
    /**
     * Add a command to the Scenario. It gets executed after the last command or pause.
     * @param runnable The command to run.
     */
    public final void appendRunnable (java.util.function.Consumer<ProductionSite> runnable) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Add a pause after the last command or last pause.
     * @param length The length of the pause in ms.
     */
    public final void addPause (int length) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Start the Scenario.
     * @throws IllegalStateException if the production site isn't set (@see setProductionSite).
     */
    public final void startScenario () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Stop the Scenario.
     */
    public final void cancelScenario () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Report whether the Scenario is currently running.
     * @return true if the Scenario is running, false otherwise.
     */
    public final boolean isRunning () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set a production site which is needed to execute the runnables. This needs to be done before starting the scenario.
     * @param productionSite The production site which gets set.
     */
    public final void setProductionSite (ProductionSite productionSite) {
        throw new RuntimeException("Not implemented!");
    }
}
