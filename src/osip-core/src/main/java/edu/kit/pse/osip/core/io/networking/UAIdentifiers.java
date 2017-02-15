package edu.kit.pse.osip.core.io.networking;

/**
 * Identifiers for the OPC UA tanks
 *
 * Folder structure:
 *  /Pipes/In/FlowRate
 *  /Pipes/Out/FlowRate
 *  /Motor/RPM
 *  /Liquid/Color
 *  /Liquid/Temperature
 *  /Liquid/FillLevel
 *  /Liquid/Alarms/Overflow
 *  /Liquid/Alarms/Underflow
 *  /Liquid/Alarms/Overheat
 *  /Liquid/Alarms/Undercool
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public final class UAIdentifiers {
    /**
     * Utility class must not be instantiated
     */
    private UAIdentifiers() {
    }

    /**
     * This server is a mix tank
     */
    public static final String TANK_MIX = "mixTank";
    /**
     * This server is a normal tank
     */
    public static final String TANK = "tank";

    /**
     * Folder identifier of a tank
     */
    public static final String FOLDER_TANK = "Tank";
    /**
     * Folder identifier of a pipe
     */
    public static final String FOLDER_PIPE = FOLDER_TANK + "/Pipes";
    /**
     * Folder identifier of the motor
     */
    public static final String FOLDER_MOTOR = FOLDER_TANK + "/Motor";
    /**
     * Folder identifier of the incoming pipe
     */
    public static final String FOLDER_PIPE_IN = FOLDER_PIPE + "/In";
    /**
     * Folder identifier of the outgoing pipe
     */
    public static final String FOLDER_PIPE_OUT = FOLDER_PIPE + "/Out";
    /**
     * Folder identifier of the tank content
     */
    public static final String FOLDER_LIQUID = FOLDER_TANK + "/Liquid";
    /**
     * Folder identifier of the alarms related to liquids
     */
    public static final String FOLDER_LIQUID_ALARMS = FOLDER_LIQUID + "/Alarms";

    /**
     * Variable identifier of the color
     */
    public static final String COLOR = FOLDER_LIQUID + "/Color";
    /**
     * Variable identifier of the temperature
     */
    public static final String TEMPERATURE = FOLDER_LIQUID + "/Temperature";
    /**
     * Variable identifier of the fill level
     */
    public static final String FILL_LEVEL = FOLDER_LIQUID + "/FillLevel";

    /**
     * Variable identifier of the overflow alarm
     */
    public static final String ALARM_OVERFLOW = FOLDER_LIQUID_ALARMS + "/Overflow";
    /**
     * Variable identifier of the underflow alarm
     */
    public static final String ALARM_UNDERFLOW = FOLDER_LIQUID_ALARMS + "/Underflow";
    /**
     * Variable identifier of the overheat alarm
     */
    public static final String ALARM_OVERHEAT = FOLDER_LIQUID_ALARMS + "/Overheat";
    /**
     * Variable identifier of the undercool alarm
     */
    public static final String ALARM_UNDERCOOL = FOLDER_LIQUID_ALARMS + "/Undercool";

    /**
     * Variable identifier of the output pipe flow rate
     */
    public static final String PIPE_OUT_FLOW_RATE = FOLDER_PIPE_OUT + "/FlowRate";
    /**
     * Variable identifier of the input pipe flow rate
     */
    public static final String PIPE_IN_FLOW_RATE = FOLDER_PIPE_IN + "/FlowRate";

    /**
     * Variable identifier of the motor rpm
     */
    public static final String MOTOR_RPM = FOLDER_MOTOR + "/RPM";
}
