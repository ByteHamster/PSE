package edu.kit.pse.osip.simulation.view.main;

import javafx.stage.Screen;

/**
 * This class provides multiple constants to configure the drawing of
 * the simulation view.
 *
 * @version 1.0
 * @author Niko Wilhelm
 */
public final class ViewConstants {

    /**
     * Private constructor to avoid instantiation.
     */
    private ViewConstants() {

    }

    /**
     * The gap between ui elements.
     */
    public static final int ELEMENTS_GAP = 5;

    /**
     * The font size in pixels, relative to the absolute screen height.
     */
    public static final int FONT_SIZE = (int) Math.round(0.01389 * Screen.getPrimary().getBounds().getHeight());

    /**
     * Percentage of width and height of a tank compartment that is
     * occupied by the outer box marking the tank as separate unit.
     */
    protected static final double OUTBOX_PERCENT = 0.9;

    /**
     * Percentage of width and height of a tank compartment that is
     * occupied by the inner box, the "real" tank.
     */
    protected static final double INBOX_PERCENT = 0.55;

    /**
     * The height of the oval marking top and bottom of the actual tank as well
     * as the top of the fluid relative to a tank compartment.
     */
    protected static final double OVAL_PERCENT = 0.25;

    /**
     * The radius of the motor in percent of the width of a tank compartment.
     */
    protected static final double MOTOR_RADIUS = 0.15;

    /**
     * The width of the lines used to draw the motor of the MixTankDrawer, relative to the width
     * of the box containing a tank and its sensors.
     */
    protected static final double MOTOR_LINE_WIDTH = 5;

    /**
     * The width of the TemperatureSensor relative to the height of the box containing a tank and its
     * sensors.
     */
    protected static final double TEMP_WIDTH = 0.12;

    /**
     * The height of the TemperatureSensor relative to the height of the box containing a tank and its
     * sensors
     */
    protected static final double TEMP_HEIGHT = 0.30;

    /**
     * The width of a pipe relative to the width of a tank compartment.
     */
    protected static final double PIPE_WIDTH = 0.025;

    /**
     * The width of a valve relative to the width of a tank compartment.
     * This constant should always be chosen lower than VALVE_HEIGHT
     */
    protected static final double VALVE_WIDTH = 0.08;

    /**
     * The height of a valve relative to the height of a tank compartment.
     * This constant should always be chosen higher than VALVE_WIDTH.
     */
    protected static final double VALVE_HEIGHT = 0.12;

    /**
     * The width of the lines making up the valves.
     */
    protected static final double VALVE_LINE_WIDTH = 2;

    /**
     * The width of a FillSensorDrawer relative to the height of a tank compartment.
     */
    public static final double FILLSENSOR_WIDTH = 0.1;

    /**
     * The height of a FillSensorDrawer relative to the height of a tank compartment.
     */
    public static final double FILLSENSOR_HEIGHT = 0.07;
}
