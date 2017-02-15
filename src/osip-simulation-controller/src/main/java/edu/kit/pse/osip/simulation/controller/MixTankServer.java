package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.io.networking.UAIdentifiers;
import edu.kit.pse.osip.core.utils.language.Translator;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

/**
 * Server for the mixtank. Contains the motor, in addition to the variables provided in the parent class.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class MixTankServer extends AbstractTankServer {
    /**
     * Creates a new server for a mixtank
     * @param port The port to start the server on
     * @throws UaException If the server can not be created
     */
    public MixTankServer(int port) throws UaException {
        super(UAIdentifiers.TANK_MIX, port);

        Translator t = Translator.getInstance();
        addFolder(UAIdentifiers.FOLDER_MOTOR, t.getString("opcua.motor"));
        addVariable(UAIdentifiers.MOTOR_RPM, t.getString("opcua.motor.rpm"), Identifiers.Int32);
    }
    /**
     * Sets the speed of the motor in rpm
     * @param speed The speed in rpm
     */
    public final void setMotorSpeed(int speed) {
        setVariable(UAIdentifiers.MOTOR_RPM, new DataValue(new Variant(speed)));
    }
}
