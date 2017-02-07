package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.SimulationConstants;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A pipe, where you can insert and take out liquid. It is a queue, so if you put in liquid, you can take it out in @see length steps. It has a valve attached, so you can limit the throughput. If you limit the throughput or put in less liquid than possible, the liquid of takeOut will be smaller when the put in liquid reaches the other end. You can put in the liquid of $SIM_STEP_SIZE milimeter of the pipe at once.
 */
public class Pipe extends java.util.Observable {
    private float crosssection;
    private int length;
    private Queue<Liquid> queue = new LinkedBlockingQueue<>();
    private byte threshold = 100; /* in percent */

    /**
     * Construct a Pipe.
     * @param crosssection Crosssection of the pipe in cm².
     * @param length Length in cm.
     * @throws IllegalArgumentException if crosssection or length are negative or zero.
     */
    public Pipe (float crosssection, int length) {
        if (crosssection <= 0 || length <= 0) {
            throw new IllegalArgumentException("Crosssection and length of a pipe need to be greather than zero");
        }
        this.crosssection = crosssection;
        this.length = length;
    }
    /**
     * Insert liquid into one $SIM_STEP_SIZE of the pipe and take out the liquid at the other side, which gets pushed out.
     * @throws OverfullLiquidContainerException if you try to put more liquid into the pipe, than getMaxInput tells you.
     * @return the liquid which was pushed out at the other side.
     * @param liquid The liquid to put into the pipe.
     */
    public synchronized final Liquid putIn (Liquid liquid) {
        if (liquid.getAmount() > getMaxInput()) {
            throw new OverfullLiquidContainerException("To much liquid inserted into the pipe",
                    getMaxInput(), liquid.getAmount());
        }
        queue.add(liquid);
        if (queue.size() <= length) {
            /* Pipe not filled */
            return new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        } else {
            return queue.remove();
        }
    }

    /**
     * Set the threshold in %.
     * @throws IllegalArgumentException if threshold is not between 0 and 100.
     * @param threshold The threshold. It needs to be between 0 and 100.
     */
    public synchronized final void setValveThreshold (byte threshold) {
        if (threshold > 100 || threshold < 0) {
            throw new IllegalArgumentException("Valve threshold needs to be in range 0 to 100");
        }
        this.threshold = threshold;
    }
    /**
     * Get the valve threshold in %.
     * @return the threshold, which is between 0 and 100.
     */
    public final byte getValveThreshold () {
        return threshold;
    }
    /**
     * This tells you the maximal amount of liquid in cm³, which you can put into the pipe. It is calculated as:
     * crosssection * SimulationConstants.SIMULATION_STEP * threshold
     * (SimulationConstants.SIMULATION_STEP, because you fill in so much liquid with every call to putIn).
     * @return the maximum amout of liquid you can put into the pipe with one call to putIn().
     */
    public final float getMaxInput () {
        return crosssection * SimulationConstants.SIMULATION_STEP * (threshold / 100f);
    }
}
