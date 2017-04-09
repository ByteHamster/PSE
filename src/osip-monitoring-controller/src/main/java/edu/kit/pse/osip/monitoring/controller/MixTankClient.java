package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.networking.RemoteMachine;
import edu.kit.pse.osip.core.io.networking.UAIdentifiers;
import edu.kit.pse.osip.core.opcua.client.IntReceivedListener;
import edu.kit.pse.osip.core.opcua.client.UAClientException;

/**
 * Client for reading the values of a mixtank. Contains a motor.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class MixTankClient extends AbstractTankClient {
    /**
     * Creates a new OPC UA client to allow reading the values of a mixtank.
     * 
     * @param machine The machine to connect to.
     */
    public MixTankClient(RemoteMachine machine) {
        super(machine, UAIdentifiers.TANK_MIX);
    }

    /**
     * Subscribes the motor speed of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * 
     * @param interval The interval to use when subscribing to the value. Single, immediate read when providing -1.
     * @param listener The callback function to be called as soon as the subscribed value was received.
     * @throws UAClientException If adding the subscription fails.
     */
    public void subscribeMotorSpeed(int interval, IntReceivedListener listener) throws UAClientException {
        subscribeInt(UAIdentifiers.MOTOR_RPM, interval, listener);
    }
}
