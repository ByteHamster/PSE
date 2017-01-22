package edu.kit.pse.osip.core.model.base;

/**
 * Thrown to indicate that a too big liquid got inserted into a pipe or a tank.
 */
public class OverfullLiquidContainerException extends java.lang.IllegalArgumentException {
    private static final long serialVersionUID = 9182764382384359500L;
    /**
     * Create a new OverfullLiquidContainerException
     * @throws IllegalArgumentException if maximumAmount or triedAmount is < 0.
     * @param message The error message.
     * @param maximumAmount How much one can insert at maximum.
     * @param triedAmount How much was tried to be inserted. This should be more than maximumAmount.
     */
    public OverfullLiquidContainerException (java.lang.String message, float maximumAmount, float triedAmount) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the maximum amount
     * @return the maximum amount.
     */
    public final float getMaximumAmount () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the amount that was tried to input.
     * @return the tried amount.
     */
    public final float getTriedAmount () {
        throw new RuntimeException("Not implemented!");
    }
}
