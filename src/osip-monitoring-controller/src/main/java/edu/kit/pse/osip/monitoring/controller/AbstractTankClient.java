package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.io.networking.RemoteMachine;
import edu.kit.pse.osip.core.io.networking.UAIdentifiers;
import edu.kit.pse.osip.core.opcua.client.BooleanReceivedListener;
import edu.kit.pse.osip.core.opcua.client.FloatReceivedListener;
import edu.kit.pse.osip.core.opcua.client.IntReceivedListener;
import edu.kit.pse.osip.core.opcua.client.UAClientWrapper;
import edu.kit.pse.osip.core.opcua.client.UAClientException;

/**
 * Client for retrieving generic tank information from OPC UA.
 * Allows to subscribe to a concrete value on the server without having to think about NodeIds and namespaces.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public abstract class AbstractTankClient extends UAClientWrapper {
    /**
     * Creates a new tank client
     * @param machine The machine to connect to
     * @param namespace The namespace to use
     */
    public AbstractTankClient(RemoteMachine machine, String namespace) {
        super("opc.tcp://" + machine.getHostname() + ":" + machine.getPort() + "/osip", namespace);
    }

    /**
     * Subscribes the color of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeColor(int interval, IntReceivedListener listener) throws UAClientException {
        subscribeInt(UAIdentifiers.COLOR, interval, listener);
    }

    /**
     * Subscribes the temperature of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeTemperature(int interval, FloatReceivedListener listener) throws UAClientException {
        subscribeFloat(UAIdentifiers.TEMPERATURE, interval, listener);
    }

    /**
     * Subscribes the output flow rate of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeOutputFlowRate(int interval, FloatReceivedListener listener) throws UAClientException {
        subscribeFloat(UAIdentifiers.PIPE_OUT_FLOW_RATE, interval, listener);
    }

    /**
     * Subscribes the fill level of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeFillLevel(int interval, FloatReceivedListener listener) throws UAClientException {
        subscribeFloat(UAIdentifiers.FILL_LEVEL, interval, listener);
    }

    /**
     * Subscribes the underflow sensor of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeUnderflowSensor(int interval, BooleanReceivedListener listener) throws UAClientException {
        subscribeBoolean(UAIdentifiers.ALARM_UNDERFLOW, interval, listener);
    }

    /**
     * Subscribes the overflow sensor of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeOverflowSensor(int interval, BooleanReceivedListener listener) throws UAClientException {
        subscribeBoolean(UAIdentifiers.ALARM_OVERFLOW, interval, listener);
    }

    /**
     * Subscribes the overheat sensor of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeOverheatSensor(int interval, BooleanReceivedListener listener) throws UAClientException {
        subscribeBoolean(UAIdentifiers.ALARM_OVERHEAT, interval, listener);
    }

    /**
     * Subscribes the undercool sensor of the tank at the given interval.
     * Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     * @throws UAClientException If adding the subscription fails
     */
    public void subscribeUndercoolSensor(int interval, BooleanReceivedListener listener) throws UAClientException {
        subscribeBoolean(UAIdentifiers.ALARM_UNDERCOOL, interval, listener);
    }
}
