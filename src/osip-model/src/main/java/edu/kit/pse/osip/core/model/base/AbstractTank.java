package edu.kit.pse.osip.core.model.base;

/**
 * The model of a tank. It is the base class of the mixtank and the upper tanks, so it has some shared functionality which is avaiable in the mixtank and the upper tanks.
 */
public abstract class AbstractTank extends java.util.Observable {
    public Liquid liquid;
    /**
     * Create a new tank.
     * @param capacity The size of the tank in cm³.
     * @param tankSelector Specifies which tank it is. This is useful if you have access to the tank and need to get a unique identifier.
     * @param liquid The default liquid of the tank.
     * @param outPipe The outgoing pipe.
     */
    public final void Tank (float capacity, TankSelector tankSelector, Liquid liquid, Pipe outPipe) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the fill level of the tank in %.
     * @return the fill level in %.
     */
    public final float getFillLevel () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the liquid of the tank. It doesn't throw an exception if you put in more liquid than possible, so you can detect overflow conditions.
     * @param liquid The liquid which should be in the tank afterwards.
     */
    public final void setLiquid (Liquid liquid) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the tank selector. This is useful if you have access to the tank and need to get a unique identifier.
     * @return the tank selecor.
     */
    public final TankSelector getTankSelector () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the outgoing pipe of the tank.
     * @return the outgoing pipe.
     */
    public final Pipe getOutPipe () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the liquid of the tank.
     * @return the liquid of the tank.
     */
    public final Liquid getLiquid () {
        throw new RuntimeException("Not implemented!");
    }
}
