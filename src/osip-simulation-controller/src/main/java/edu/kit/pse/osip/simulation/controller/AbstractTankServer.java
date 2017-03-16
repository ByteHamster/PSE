package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.io.networking.UAIdentifiers;
import edu.kit.pse.osip.core.opcua.server.UAServerWrapper;
import edu.kit.pse.osip.core.utils.language.Translator;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

/**
 * Allows setting the values inside the OPC UA server without having to think about NodeIds,
 * namespaces or data types. Provides concrete methods for the variables used in our simulation.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public abstract class AbstractTankServer extends UAServerWrapper {
    /**
     * Represents a tank inside an OPC UA server
     * @param namespaceName The name of the namespace
     * @param port The port on which the server should be available
     * @throws UaException If the server can not be created
     */
    protected AbstractTankServer(String namespaceName, int port) throws UaException {
        super(namespaceName, port);

        Translator t = Translator.getInstance();
        addFolder(UAIdentifiers.FOLDER_TANK, t.getString("opcua.tank"));
        addFolder(UAIdentifiers.FOLDER_LIQUID, t.getString("opcua.liquid"));
        addFolder(UAIdentifiers.FOLDER_LIQUID_ALARMS, t.getString("opcua.alarms"));
        addFolder(UAIdentifiers.FOLDER_PIPE, t.getString("opcua.pipe"));
        addFolder(UAIdentifiers.FOLDER_PIPE_OUT, t.getString("opcua.pipe.out"));

        addVariable(UAIdentifiers.COLOR, t.getString("opcua.liquid.color"), Identifiers.Int32);
        addVariable(UAIdentifiers.FILL_LEVEL, t.getString("opcua.tank.fillLevel"), Identifiers.Float);
        addVariable(UAIdentifiers.TEMPERATURE, t.getString("opcua.liquid.temperature"), Identifiers.Float);

        addVariable(UAIdentifiers.ALARM_OVERFLOW, t.getString("opcua.tank.overflowAlarm"), Identifiers.Boolean);
        addVariable(UAIdentifiers.ALARM_UNDERFLOW, t.getString("opcua.tank.underflowAlarm"), Identifiers.Boolean);
        addVariable(UAIdentifiers.ALARM_OVERHEAT, t.getString("opcua.tank.overheatAlarm"), Identifiers.Boolean);
        addVariable(UAIdentifiers.ALARM_UNDERCOOL, t.getString("opcua.tank.undercoolAlarm"), Identifiers.Boolean);

        addVariable(UAIdentifiers.PIPE_OUT_FLOW_RATE, t.getString("opcua.pipe.flowRate"), Identifiers.Boolean);
    }

    /**
     * Sets the color of the liquid in this tank
     * @param color The color of the tank infill
     */
    public final void setColor(int color) {
        setVariable(UAIdentifiers.COLOR, new DataValue(new Variant(color)));
    }

    /**
     * Sets the fill level of the tank
     * @param filled The fill level of the tank
     */
    public final void setFillLevel(float filled) {
        setVariable(UAIdentifiers.FILL_LEVEL, new DataValue(new Variant(filled)));
    }

    /**
     * Sets the flow rate of the outgoing valve
     * @param rate The flow rate of the outgoing valve
     */
    public final void setOutputFlowRate(float rate) {
        setVariable(UAIdentifiers.PIPE_OUT_FLOW_RATE, new DataValue(new Variant(rate)));
    }

    /**
     * Sets temperature of the contained liquid
     * @param temperature Temperature of the tank content
     */
    public final void setTemperature(float temperature) {
        setVariable(UAIdentifiers.TEMPERATURE, new DataValue(new Variant(temperature)));
    }

    /**
     * Sets the overflow alarm state
     * @param overflow true if the tank is overflowing
     */
    public final void setOverflowAlarm(boolean overflow) {
        setVariable(UAIdentifiers.ALARM_OVERFLOW, new DataValue(new Variant(overflow)));
    }

    /**
     * Sets the underflow alarm state
     * @param underflow true if the tank is underflowing
     */
    public final void setUnderflowAlarm(boolean underflow) {
        setVariable(UAIdentifiers.ALARM_UNDERFLOW, new DataValue(new Variant(underflow)));
    }

    /**
     * Sets the overheat alarm state
     * @param overheat true if the tank is overheating
     */
    public final void setOverheatAlarm(boolean overheat) {
        setVariable(UAIdentifiers.ALARM_OVERHEAT, new DataValue(new Variant(overheat)));
    }

    /**
     * Sets the undercool alarm state
     * @param undercool true if the tank is undercooling
     */
    public final void setUndercoolAlarm(boolean undercool) {
        setVariable(UAIdentifiers.ALARM_UNDERCOOL, new DataValue(new Variant(undercool)));
    }
}
