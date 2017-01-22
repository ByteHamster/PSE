package edu.kit.pse.osip.simulation.controller;

/**
 * This Listener listens for changes in the motor speed slider.
 */
public interface MotorListener {
    /**
     * Alerts the Controller that the speed changed.
     */
    public void onSpeedUpdated ();
}
