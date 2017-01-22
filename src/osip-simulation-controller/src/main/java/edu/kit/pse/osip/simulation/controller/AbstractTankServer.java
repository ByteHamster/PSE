package edu.kit.pse.osip.simulation.controller;

/**
 * Allows setting the values inside the OPC UA server without having to think about NodeIds, namespaces or data types. Provides concrete methods for the variables used in our simulation.
 */
public abstract class AbstractTankServer extends edu.kit.pse.osip.core.opcua.server.UAServerWrapper {
    /**
     * Represents a tank inside an OPC UA server
     * @param namespaceName The name of the namespace
     * @param port The port on which the server should be available
     */
    public AbstractTankServer (String namespaceName, int port) {
        super(namespaceName, port);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the color of the liquid in this tank
     * @param color The color of the tank infill
     */
    public final void setColor (int color) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the fill level of the tank
     * @param filled The fill level of the tank
     */
    public final void setFillLevel (float filled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set the flow rate of the outgoing valve
     * @param rate The flow rate of the outgoing valve
     */
    public final void setOutputFlowRate (float rate) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Set temperature of the contained liquid
     * @param temperature Temperature of the tank content
     */
    public final void setTemperature (float temperature) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the overflow alarm state
     * @param overflow true if the tank is overflowing
     */
    public final void setOverflowAlarm (boolean overflow) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the underflow alarm state
     * @param underflow true if the tank is underflowing
     */
    public final void setUnderflowAlarm (boolean underflow) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the overheat alarm state
     * @param overheat true if the tank is overheating
     */
    public final void setOverheatAlarm (boolean overheat) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the undercool alarm state
     * @param undercool true if the tank is undercooling
     */
    public final void setUndercoolAlarm (boolean undercool) {
        throw new RuntimeException("Not implemented!");
    }
}
