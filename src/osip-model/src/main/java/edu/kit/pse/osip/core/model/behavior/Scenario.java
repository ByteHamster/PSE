package edu.kit.pse.osip.core.model.behavior;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
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
public class Scenario extends Observable implements Runnable {
    /**
     * List of all commands forming the scenario.
     */
    private List<ThrowingConsumer<ProductionSite>> commands = new LinkedList<>();
    /**
     * The thread on which the scenario is executed.
     */
    private Thread thread;
    /**
     * The production site on which the scenario operates.
     */
    private ProductionSite productionSite;
    /**
     * Indicates whether the scenario is running.
     */
    private boolean stop = false;
    /**
     * Contains actions for after the scenario has finished.
     */
    private Runnable finishedListener;
    /**
     * Indicates if the scenario should repeat. 
     */
    private boolean repeat;

    @Override
    public void run() {
        assert (null != productionSite);
        do {
            productionSite.reset();
            for (ThrowingConsumer<ProductionSite> c : commands) {
                try {
                    c.accept(productionSite);
                } catch (InterruptedException ex) {
                    System.err.println("Scenario: sleep command failed: " + ex.getLocalizedMessage());
                    ex.printStackTrace();
                }
                if (stop) {
                    return;
                }
            }
        } while (repeat);
        if (finishedListener != null) {
            finishedListener.run();
        }
    }

    @FunctionalInterface
    private interface ThrowingConsumer<T> {
        void accept(T t) throws InterruptedException;
    }

    /**
     * Adds a command to the Scenario. It gets executed after the last command or pause.
     * 
     * @param runnable The command to run.
     */
    public void appendRunnable(Consumer<ProductionSite> runnable) {
        commands.add(runnable::accept);
    }

    /**
     * Adds a pause after the last command or last pause.
     * 
     * @param length The length of the pause in ms.
     */
    public void addPause(int length) {
        commands.add(productionSite1 -> sleep(length));
    }

    /**
     * Start the Scenario.
     * 
     * @throws IllegalStateException if the production site isn't set (@see setProductionSite).
     */
    public void startScenario() {
        if (null == productionSite) {
            throw new IllegalStateException("Before running a scenario, the production site needs to be set");
        }
        thread = new Thread(this);
        thread.setDaemon(true);
        stop = false;
        thread.start();
    }

    /**
     * Stops the Scenario.
     */
    public void cancelScenario() {
        stop = true;
        if (null == thread) {
            throw new IllegalStateException("Scenario has not been started yet");
        }
    }

    /**
     * Reports whether the Scenario is currently running.
     * 
     * @return true if the Scenario is running, false otherwise.
     */
    public boolean isRunning() {
        return (null != thread && thread.isAlive());
    }

    /**
     * Sets a production site which is needed to execute the runnables.
     * This needs to be done before starting the scenario.
     * 
     * @param productionSite The production site which gets set.
     */
    public void setProductionSite(ProductionSite productionSite) {
        this.productionSite = productionSite;
    }

    /**
     * Sets the listener which gets called when the scenario has finished.
     * 
     * @param listener The listener.
     */
    public void setScenarioFinishedListener(Runnable listener) {
        finishedListener = listener;
    }

    /**
     * Sets whether the scenario should run in an endless loop.
     * 
     * @param repeat Whether it should run in an endless loop.
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
}
