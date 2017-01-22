package edu.kit.pse.osip.simulation.controller;

/**
 * Server for the mixtank. Contain the motor, in addition to the variables provided in the parent class.
 */
public class MixTankServer extends edu.kit.pse.osip.simulation.controller.AbstractTankServer {
    /**
     * Creates a new server for a mixtank
     * @param port The port to start the server on
     */
    public MixTankServer (int port) {
        super("", port);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the speed of the motor in rpm
     * @param speed The speed in rpm
     */
    public final void setMotorSpeed (int speed) {
        throw new RuntimeException("Not implemented!");
    }
}
