package edu.kit.pse.osip.core.model.base;

/**
 * This is the motor which is installed in the mixtank.
 * @author David Kahles
 * @version 1.0
 */
public class Motor extends java.util.Observable {
    private int rpm;

    /**
     * Set the RPM of the motor.
     * @throws IllegalArgumentException if rpm is negative or greater than 3000.
     * @param rpm The target RPM.
     */
    public final void setRPM(int rpm) {
        if (rpm < 0 || rpm > 3000) {
            throw new IllegalArgumentException("Motor RPM needs to be in range 0 to 3000");
        }
        this.rpm = rpm;
    }
    /**
     * Get the RPM.
     * @return the RPM.
     */
    public final int getRPM() {
        return rpm;
    }
}
