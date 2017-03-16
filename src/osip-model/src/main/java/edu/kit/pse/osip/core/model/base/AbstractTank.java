package edu.kit.pse.osip.core.model.base;

/**
 * The model of a tank. It is the base class of the mixtank and the upper tanks, so it has some shared functionality
 * which is available in the mixtank and the upper tanks.
 * @author David Kahles
 * @version 1.0
 */
public abstract class AbstractTank extends java.util.Observable {
    private Liquid liquid;
    private float capacity;
    private TankSelector selector;
    private Pipe outPipe;
    private Liquid initialLiquid;

    /**
     * Create a new tank.
     * @param capacity The size of the tank in cm³.
     * @param tankSelector Specifies which tank it is. This is useful if you have access to the tank and need to get
     *                     a unique identifier.
     * @param liquid The default liquid of the tank.
     * @param outPipe The outgoing pipe.
     */
    protected AbstractTank(float capacity, TankSelector tankSelector, Liquid liquid, Pipe outPipe) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity of a tank needs to be greater than zero");
        }
        if (tankSelector == null || liquid == null || outPipe == null) {
            throw new NullPointerException("Arguments of AbstractTank must not be null");
        }
        this.capacity = capacity;
        this.selector = tankSelector;
        this.initialLiquid = liquid;
        this.liquid = liquid;
        this.outPipe = outPipe;
    }

    /**
     * Get the fill level of the tank in % between 0 and 1 (or more than 1, if the tank is overfull).
     * @return the fill level in % between 0 and 1.
     */
    public float getFillLevel() {
        return liquid.getAmount() / capacity;
    }

    /**
     * Set the liquid of the tank. It doesn't throw an exception if you put in more liquid than possible, so you can
     * detect overflow conditions.
     * @param liquid The liquid which should be in the tank afterwards.
     */
    public synchronized void setLiquid(Liquid liquid) {
        if (liquid == null) {
            throw new NullPointerException("The liquid of a tank must not be null");
        }
        setChanged();
        this.liquid = liquid;
        notifyObservers(liquid);
    }

    /**
     * Get the tank selector. This is useful if you have access to the tank and need to get a unique identifier.
     * @return the tank selector.
     */
    public TankSelector getTankSelector() {
        return selector;
    }

    /**
     * Get the outgoing pipe of the tank.
     * @return the outgoing pipe.
     */
    public Pipe getOutPipe() {
        return outPipe;
    }

    /**
     * Get the liquid of the tank.
     * @return the liquid of the tank.
     */
    public Liquid getLiquid() {
        return liquid;
    }

    /**
     * Resets the tank and all attached units like pipes or motors.
     */
    protected synchronized void reset() {
        setLiquid(initialLiquid);
        outPipe.reset();
    }
}
