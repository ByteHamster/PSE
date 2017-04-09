package edu.kit.pse.osip.core.model.base;

/**
 * Thrown to indicate that a too big liquid got inserted into a pipe or a tank.
 * 
 * @author David Kahles
 * @version 1.0
 */
public class OverfullLiquidContainerException extends IllegalArgumentException {
    private static final long serialVersionUID = 9182764382384359500L;
    /**
     * The maximum amount of liquid that can be inserted into a pipe or tank.
     */
    private final float maximumAmount;
    /**
     * The amount of liquid that should be inserted into the pipe or tank.
     */
    private final float triedAmount;

    /**
     * Creates a new OverfullLiquidContainerException.
     * 
     * @throws IllegalArgumentException if maximumAmount or triedAmount is < 0.
     * @param message The error message.
     * @param maximumAmount How much one can insert at maximum.
     * @param triedAmount How much was tried to be inserted. This should be more than maximumAmount.
     */
    public OverfullLiquidContainerException(String message, float maximumAmount, float triedAmount) {
        super(message);
        this.maximumAmount = maximumAmount;
        this.triedAmount = triedAmount;
    }

    /**
     * Returns the maximum amount.
     * 
     * @return the maximum amount.
     */
    public final float getMaximumAmount() {
        return maximumAmount;
    }

    /**
     * Returns the amount that was tried to input.
     * 
     * @return the tried amount.
     */
    public final float getTriedAmount() {
        return triedAmount;
    }
}
