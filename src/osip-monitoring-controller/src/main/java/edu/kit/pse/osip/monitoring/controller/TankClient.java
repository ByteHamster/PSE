package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.networking.RemoteMachine;
import edu.kit.pse.osip.core.io.networking.UAIdentifiers;
import edu.kit.pse.osip.core.opcua.client.FloatReceivedListener;
import edu.kit.pse.osip.core.opcua.client.UAClientException;

/**
 * Client for receiving information about the upper tanks. Contains additional valve.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TankClient extends AbstractTankClient {
    /**
     * Creates a new OPC UA client to allow reading the values of an input tank
     * @param machine The machine to connnect to
     */
    public TankClient(RemoteMachine machine) {
        super(machine, UAIdentifiers.TANK);
    }

    /**
     * Subscribes the input flow rate of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeInputFlowRate(int interval, FloatReceivedListener listener) throws UAClientException {
        subscribeFloat(UAIdentifiers.PIPE_IN_FLOW_RATE, interval, listener);
    }
}
