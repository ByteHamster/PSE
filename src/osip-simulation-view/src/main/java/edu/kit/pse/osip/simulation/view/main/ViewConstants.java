package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.TankSelector;
import javafx.geometry.Insets;
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
     * The insets used as padding in the control window.
     */
    public static final Insets CONTROL_PADDING = new Insets(10, 5, 10, 5);

    /**
     * The width of the SimulationControlWindow.
     */
    public static final double CONTROL_WIDTH = Screen.getPrimary().getBounds().getWidth() * 0.3;

    /**
     * The width of a slider in the Simulation control window.
     */
    public static final double CONTROL_SLIDER_WIDTH = CONTROL_WIDTH * 0.3;

    /**
     * The number of labels on a Slider.
     */
    public static final int SLIDER_LABEL_COUNT = 10;

    /**
     * The gap between ui elements.
     */
    public static final int ELEMENTS_GAP = 5;

    /**
     * The font size in pixels, relative to the absolute screen height.
     */
    public static final int FONT_SIZE = (int) Math.round(0.012 * Screen.getPrimary().getBounds().getHeight());

    /**
     * The width of the input TextFields in pixel.
     */
    public static final double CONTROL_INPUT_WIDTH = FONT_SIZE * 6;

    /**
     * Percentage of width and height of a tank compartment that is
     * occupied by the outer box marking the tank as separate unit.
     */
    protected static final double OUTBOX_PERCENT = 0.9;

    /**
     * Percentage of height of a tank compartment that is
     * occupied by the inner box, the "real" tank.
     */
    protected static final double INBOX_HEIGHT = 0.35;

    /**
     * Percentage of width of a tank compartment that is
     * occupied by the inner box, the "real" tank.
     */
    protected static final double INBOX_WIDTH = 0.45;

    /**
     * The height of the oval marking top and bottom of the actual tank as well
     * as the top of the fluid relative to a tank compartment.
     */
    protected static final double OVAL_PERCENT = 0.1;

    /**
     * The radius of the motor in percent of the width of a tank compartment.
     */
    protected static final double MOTOR_RADIUS = 0.1;

    /**
     * The width of the lines used to draw the motor of the MixTankDrawer, relative to the width
     * of the box containing a tank and its sensors.
     */
    protected static final double MOTOR_LINE_WIDTH = 5;

    /**
     * The factor of the simulation motor speed that is shown in the simulation gui. It scales the
     * shown rpm to 0 - 50, as anything above that is not shown in an aesthetically pleasing way.
     */
    protected static final double MOTOR_SPEED_FACTOR = 1.0 / ((double) SimulationConstants.MAX_MOTOR_SPEED / 50);

    /**
     * The width of the TemperatureSensor relative to the height of the box containing a tank and its
     * sensors.
     */
    protected static final double TEMP_WIDTH = 0.07;

    /**
     * The height of the TemperatureSensor relative to the height of the box containing a tank and its
     * sensors.
     */
    protected static final double TEMP_HEIGHT = 0.2;

    /**
     * The width of a pipe relative to the width of a tank compartment.
     */
    protected static final double PIPE_WIDTH = ((((1 - OUTBOX_PERCENT) / 2)
            < (INBOX_WIDTH / TankSelector.getUpperTankCount()))
            ? (1 - OUTBOX_PERCENT) : INBOX_WIDTH / TankSelector.getUpperTankCount())
            / 3 / TankSelector.getUpperTankCount();

    /**
     * The width of a valve relative to the width of a tank compartment.
     * This constant should always be chosen lower than VALVE_HEIGHT.
     */
    protected static final double VALVE_WIDTH = 0.1;

    /**
     * The height of a valve relative to the height of a tank compartment.
     * This constant should always be chosen higher than VALVE_WIDTH.
     */
    protected static final double VALVE_HEIGHT = 0.15;

    /**
     * The width of the lines making up the valves.
     */
    protected static final double VALVE_LINE_WIDTH = 2;

    /**
     * The width of a FillSensorDrawer relative to the height of a tank compartment.
     */
    protected static final double FILLSENSOR_WIDTH = 0.1;

    /**
     * The height of a FillSensorDrawer relative to the height of a tank compartment.
     */
    protected static final double FILLSENSOR_HEIGHT = 0.07;

    /**
     * Percentage of the canvas width that is used as font size for the tank names.
     */
    protected static final double ABSTRACT_TANK_FONT_SIZE = 0.019;

    /**
     * Percentage of the valve width that is free between the outer line and the rotating part
     * on each side.
     */
    protected static final double VALVE_CIRCLE_DIST = 0.15;
}
