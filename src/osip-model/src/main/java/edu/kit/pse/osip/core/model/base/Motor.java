package edu.kit.pse.osip.core.model.base;

/**
 * This is the motor which is installed in the mixtank.
 */
public class Motor extends java.util.Observable {
    /**
     * Set the RPM of the motor.
     * @throws IllegalArgumentException if rpm is negative or greater than 3000.
     * @param rpm The target RPM.
     */
    public final void setRPM (int rpm) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the RPM.
     * @return the RPM.
     */
    public final int getRPM () {
        throw new RuntimeException("Not implemented!");
    }
}
