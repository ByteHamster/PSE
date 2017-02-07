package edu.kit.pse.osip.core.model.base;

/**
 * Thrown to indicate that a too big liquid got inserted into a pipe or a tank.
 */
public class OverfullLiquidContainerException extends java.lang.IllegalArgumentException {
    private static final long serialVersionUID = 9182764382384359500L;
    private final float maximumAmount;
    private final float triedAmount;

    /**
     * Create a new OverfullLiquidContainerException
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
     * Returns the maximum amount
     * @return the maximum amount.
     */
    public final float getMaximumAmount() {
        return maximumAmount;
    }

    /**
     * Returns the amount that was tried to input.
     * @return the tried amount.
     */
    public final float getTriedAmount() {
        return triedAmount;
    }
}
