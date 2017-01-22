package edu.kit.pse.osip.core.model.base;

/**
 * This is the model of an upper tank. The difference to AbstractTank is the pipe which puts liquid into the tank (a mixtank does not have this pipe).
 */
public class Tank extends edu.kit.pse.osip.core.model.base.AbstractTank {
    /**
     * Constructs a new Tank.
     * @param capacity The capacity of a tank in cm³.
     * @param tankSelector Specifies which tank it is. This is useful if you have access to the tank and need to get a unique identifier.
     * @param liquid The default content of the tank.
     * @param outPipe The outgoing pipe.
     * @param inPipe The incoming pipe.
     */
    public Tank (float capacity, TankSelector tankSelector, Liquid liquid, Pipe outPipe, Pipe inPipe) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the pipe which puts liquid into the tank.
     * @return the ingoing pipe.
     */
    public final Pipe getInPipe () {
        throw new RuntimeException("Not implemented!");
    }
}
