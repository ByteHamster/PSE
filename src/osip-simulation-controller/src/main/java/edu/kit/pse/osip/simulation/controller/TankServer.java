package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.io.networking.UAIdentifiers;
import edu.kit.pse.osip.core.utils.language.Translator;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

/**
 * Server for the upper tanks. They contain the input valve, in addition to the variables provided in the parent class.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TankServer extends edu.kit.pse.osip.simulation.controller.AbstractTankServer {
    /**
     * Creates a new server for a tank
     * @param port The port to add the server
     * @throws UaException If the server can not be created
     */
    public TankServer(int port) throws UaException {
        super(UAIdentifiers.TANK, port);

        Translator t = Translator.getInstance();
        addFolder(UAIdentifiers.FOLDER_PIPE_IN, t.getString("opcua.pipe.in"));
        addVariable(UAIdentifiers.PIPE_IN_FLOW_RATE, t.getString("opcua.pipe.flowRate"), Identifiers.Float);
    }
    /**
     * Sets the flow rate of the incoming valve
     * @param flowRate The flow rate of the input tanks
     */
    public final void setInputFlowRate(float flowRate) {
        setVariable(UAIdentifiers.PIPE_IN_FLOW_RATE, new DataValue(new Variant(flowRate)));
    }
}
