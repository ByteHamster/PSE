package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.networking.RemoteMachine;
import edu.kit.pse.osip.core.opcua.client.FloatReceivedListener;

/**
 * Client for reading the values of a mixtank. Contains a motor.
 */
public class MixTankClient extends edu.kit.pse.osip.monitoring.controller.AbstractTankClient {
    /**
     * Creates a new OPC UA client to allow reading the values of a mixtank
     * @param machine The machine to connect to
     */
    public MixTankClient (RemoteMachine machine) {
        super("", "");
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the motor speed of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeMotorSpeed (int interval, FloatReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
}
