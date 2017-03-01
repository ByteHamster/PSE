package edu.kit.pse.osip.core.model.behavior;

import edu.kit.pse.osip.core.model.base.ProductionSite;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.Thread.sleep;

/**
 * A sequence of automated changes to parameters of the simulation.
 * It is used to have changes in the simulation (so it looks more interesting)
 * without the need to adjust parameters all the time.
 *
 * @author David Kahles
 * @version 1.0
 */
public class Scenario extends java.util.Observable implements Runnable {
    private List<ThrowingConsumer<ProductionSite>> commands = new LinkedList<>();
    private Thread thread;
    private ProductionSite productionSite;
    private boolean stop = false;
    private Runnable finishedListener;

    @Override
    public void run() {
        assert (null != productionSite);
        for (ThrowingConsumer<ProductionSite> c: commands) {
            try {
                c.accept(productionSite);
            } catch (InterruptedException ex) {
                System.err.println("Scenario: sleep command failed: " + ex.getMessage());
            }
            if (stop) {
                return;
            }
        }
        if (finishedListener != null) {
            finishedListener.run();
        }
    }

    @FunctionalInterface
    private interface ThrowingConsumer<T> {
        void accept(T t) throws InterruptedException;
    }

    /**
     * Add a command to the Scenario. It gets executed after the last command or pause.
     * @param runnable The command to run.
     */
    public void appendRunnable(Consumer<ProductionSite> runnable) {
        commands.add(runnable::accept);
    }

    /**
     * Add a pause after the last command or last pause.
     * @param length The length of the pause in ms.
     */
    public void addPause(int length) {
        commands.add(productionSite1 -> sleep(length));
    }

    /**
     * Start the Scenario.
     * @throws IllegalStateException if the production site isn't set (@see setProductionSite).
     */
    public void startScenario() {
        if (null == productionSite) {
            throw new IllegalStateException("Before running a scenario, the production site needs to be set");
        }
        thread = new Thread(this);
        stop = false;
        thread.start();
    }

    /**
     * Stop the Scenario.
     */
    public void cancelScenario() {
        stop = true;
        if (null == thread) {
            throw new IllegalStateException("Scenario has not been started yet");
        }
        if (finishedListener != null) {
            finishedListener.run();
        }
    }

    /**
     * Report whether the Scenario is currently running.
     * @return true if the Scenario is running, false otherwise.
     */
    public boolean isRunning() {
        return (null != thread && thread.isAlive());
    }

    /**
     * Set a production site which is needed to execute the runnables.
     * This needs to be done before starting the scenario.
     * @param productionSite The production site which gets set.
     */
    public void setProductionSite(ProductionSite productionSite) {
        this.productionSite = productionSite;
    }

    public void setScenarioFinishedListener(Runnable listener) {
        finishedListener = listener;
    }
}
