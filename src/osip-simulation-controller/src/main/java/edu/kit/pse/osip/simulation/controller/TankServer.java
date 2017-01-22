package edu.kit.pse.osip.simulation.controller;

/**
 * Server for the upper tanks. They contain the input valve, in addition to the variables provided in the parent class.
 */
public class TankServer extends edu.kit.pse.osip.simulation.controller.AbstractTankServer {
    /**
     * Creates a new server for a tank
     * @param port The port to add the server
     */
    public TankServer (int port) {
        super("", port);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the flow rate of the incoming valve
     * @param flowRate The flow rate of the input tanks
     */
    public final void setInputFlowRate (float flowRate) {
        throw new RuntimeException("Not implemented!");
    }
}
