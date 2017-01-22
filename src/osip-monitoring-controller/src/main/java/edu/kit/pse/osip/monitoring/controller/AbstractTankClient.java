package edu.kit.pse.osip.monitoring.controller;

import edu.kit.pse.osip.core.opcua.client.BooleanReceivedListener;
import edu.kit.pse.osip.core.opcua.client.FloatReceivedListener;
import edu.kit.pse.osip.core.opcua.client.IntReceivedListener;

/**
 * Client for retreiving generic tank information from OPC UA. Allows to subscribe to a concrete value on the server without having to think about NodeIds and namespaces.
 */
public abstract class AbstractTankClient extends edu.kit.pse.osip.core.opcua.client.UAClientWrapper {
    /**
     * Creates a new tank client
     * @param serverUrl The url of the server
     * @param namespace The namespace to use
     */
    public AbstractTankClient (String serverUrl, String namespace) {
        super(serverUrl, namespace);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the color of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeColor (int interval, IntReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the temperature of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeTemperature (int interval, FloatReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the output flow rate of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeOutputFlowRate (int interval, FloatReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the fill level of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeFillLevel (int interval, FloatReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the underflow sensor of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeUnderflowSensor (int interval, BooleanReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the overflow sensor of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeOverflowSensor (int interval, BooleanReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the overheat sensor of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeOverheatSensor (int interval, BooleanReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Subscribes the undercool sensor of the tank at the given interval. Can be called again with the same listener to change interval.
     * @param interval The interval to use when subscribing to the value
     * @param listener The callback function to be called as soon as the subscribed value was received
     */
    public final void subscribeUndercoolSensor (int interval, BooleanReceivedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
}
