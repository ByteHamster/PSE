package edu.kit.pse.osip.core.model.base;

/**
 * A pipe, where you can insert and take out liquid. It is a queue, so if you put in liquid, you can take it out in @see length steps. It has a valve attached, so you can limit the throughput. If you limit the throughput or put in less liquid than possible, the liquid of takeOut will be smaller when the put in liquid reaches the other end. You can put in the liquid of $SIM_STEP_SIZE milimeter of the pipe at once.
 */
public class Pipe extends java.util.Observable {
    /**
     * Construct a Pipe.
     * @param corsssection Crosssection of the pipe in cm².
     * @param length Length in cm.
     */
    public Pipe (float corsssection, int length) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Insert liquid into one $SIM_STEP_SIZE of the pipe and take out the liquid at the other side, which gets pushed out.
     * @throws OverfullLiquidContainerException if the pipe is full and you need to take out some liquit first, or if you try to put more liquid into the pipe, than getMaxInput tells you.
     * @return the liquid which was pushed out at the other side.
     * @param liquid The liquid to put into the pipe.
     */
    public final Liquid putIn (Liquid liquid) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the threshold in %.
     * @throws IllegalArgumentException if threshold is not between 0 and 100.
     * @param threshold The threshold. It needs to be between 0 and 100.
     */
    public final void setValveThreshold (byte threshold) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the valve threshold in %.
     * @return the threshold, which is between 0 and 100.
     */
    public final byte getValveThreshold () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * This tells you the maximal amount of liquid in cm³, which you can put into the pipe. It is calculated as:
     * crosssection*$SIM_STEP_SIZE*threshold
     * ($SIM_STEP_SIZE, because you fill one $SIM_STEP_SIZE with every call to putIn).
     * @return the maximum amout of liquid you can put into the pipe with one call to putIn().
     */
    public final float getMaxInput () {
        throw new RuntimeException("Not implemented!");
    }
}
