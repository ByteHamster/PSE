package edu.kit.pse.osip.core.model.base;

/**
 * This is the model of an upper tank. The difference to AbstractTank is the pipe which puts liquid into the tank
 * (a mixtank does not have this pipe).
 * @author David Kahles
 * @version 1.0
 */
public class Tank extends AbstractTank {
    private Pipe inPipe;

    /**
     * Constructs a new Tank.
     * @param capacity The capacity of a tank in cm³.
     * @param tankSelector Specifies which tank it is. This is useful if you have access to the tank and need to get a
     *                     unique identifier.
     * @param liquid The default content of the tank.
     * @param outPipe The outgoing pipe.
     * @param inPipe The incoming pipe.
     */
    public Tank(float capacity, TankSelector tankSelector, Liquid liquid, Pipe outPipe, Pipe inPipe) {
        super(capacity, tankSelector, liquid, outPipe);
        if (inPipe == null) {
            throw new NullPointerException("The input pipe of a tank must not be null");
        }
        if (inPipe == outPipe) {
            throw new IllegalArgumentException("The input and output pipe of a tank must not be the same");
        }
        this.inPipe = inPipe;
    }
    /**
     * Get the pipe which puts liquid into the tank.
     * @return the ingoing pipe.
     */
    public Pipe getInPipe() {
        return inPipe;
    }

    @Override
    public synchronized void reset() {
        super.reset();
        inPipe.reset();
    }
}
