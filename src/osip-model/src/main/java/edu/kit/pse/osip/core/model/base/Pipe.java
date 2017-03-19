package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * A pipe, where you can insert and take out liquid. It is a queue, so if you put in liquid, you can take it out in
 * {@code length} steps. It has a valve attached, so you can limit the throughput. If you limit the throughput or put in
 * less liquid than possible, the liquid of takeOut will be smaller when the put in liquid reaches the other end. You
 * can put in the liquid of SimulationConstants.SIMULATION_STEP millimeter of the pipe at once.
 */
public class Pipe extends java.util.Observable {
    private float crosssection;
    private int length;
    private Deque<Liquid> queue = new LinkedBlockingDeque<>();
    private byte threshold = 100; /* in percent */
    private byte defaultThreshold;

    /**
     * Construct a Pipe.
     * @param crosssection Crosssection of the pipe in cm².
     * @param length Length in cm.
     * @param defaultThreshold The default threshold
     * @throws IllegalArgumentException if crosssection or length are negative or zero.
     */
    public Pipe(float crosssection, int length, byte defaultThreshold) {
        if (crosssection <= 0 || length <= 0) {
            throw new IllegalArgumentException("Crosssection and length of a pipe need to be greater than zero");
        }
        this.crosssection = crosssection;
        this.defaultThreshold = defaultThreshold;
        this.threshold = defaultThreshold;
        this.length = length;
    }

    /**
     * Insert liquid into one SimulationConstants.SIMULATION_STEP of the pipe and take out the liquid at the other side,
     * which gets pushed out.
     * @throws OverfullLiquidContainerException if you try to put more liquid into the pipe, than getMaxInput tells you.
     * @return the liquid which was pushed out at the other side.
     * @param liquid The liquid to put into the pipe.
     */
    public synchronized Liquid putIn(Liquid liquid) {
        if (liquid.getAmount() > getMaxInput()) {
            throw new OverfullLiquidContainerException("To much liquid inserted into the pipe",
                    getMaxInput(), liquid.getAmount());
        }
        queue.addLast(liquid);
        setChanged();
        if (queue.size() <= length) {
            notifyObservers();
            /* Pipe not filled */
            return new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        } else {
            Liquid ret = queue.removeFirst();
            notifyObservers();
            return ret;
        }
    }

    /**
     * Set the threshold in %.
     * @throws IllegalArgumentException if threshold is not between 0 and 100.
     * @param threshold The threshold. It needs to be between 0 and 100.
     */
    public synchronized void setValveThreshold(byte threshold) {
        if (threshold > 100 || threshold < 0) {
            throw new IllegalArgumentException("Valve threshold needs to be in range 0 to 100");
        }
        this.threshold = threshold;
        setChanged();
        notifyObservers();
    }
    /**
     * Get the valve threshold in %.
     * @return the threshold, which is between 0 and 100.
     */
    public synchronized byte getValveThreshold() {
        return threshold;
    }
    /**
     * This tells you the maximal amount of liquid in cm³, which you can put into the pipe. It is calculated as:
     * crosssection * SimulationConstants.SIMULATION_STEP * threshold
     * (SimulationConstants.SIMULATION_STEP, because you fill in so much liquid with every call to putIn).
     * @return the maximum amount of liquid you can put into the pipe with one call to putIn().
     */
    public synchronized float getMaxInput() {
        return crosssection * SimulationConstants.SIMULATION_STEP * (threshold / 100f);
    }

    /**
     * Resets the Pipe.
     */
    public synchronized void reset() {
        threshold = defaultThreshold;
        queue.clear();
        setChanged();
        notifyObservers();
    }

    /**
     * Checks whether there is currently any amount of liquid at the beginning and thus
     * entering the pipe.
     * @return True if the last Liquid put in has an amount > 0, false if the amount is 0 or there
     *      is not a Liquid object.
     */
    public boolean isLiquidEntering() {
        return queue.peekLast() != null && queue.peekLast().getAmount() != 0;
    }
}
