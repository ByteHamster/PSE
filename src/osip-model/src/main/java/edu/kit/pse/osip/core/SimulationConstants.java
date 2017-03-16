package edu.kit.pse.osip.core;

/**
 * Defines some constants.
 * @author David Kahles
 * @version 1.0
 */
public final class SimulationConstants {
    private SimulationConstants() {
        throw new RuntimeException("SimulationConstants should not be instanced");
    }

    /**
     * The size of the tanks.
     */
    public static final int TANK_SIZE = 10000;

    /**
     * The maximum motor speed
     */
    public static final int MAX_MOTOR_SPEED = 3000;

    /**
     * The granularity of the simulation, e.g. how much liquid gets put into a pipe in every step.
     */
    public static final int SIMULATION_STEP = 1;

    /**
     * The minimum temperature in kelvin one can set as tank input temperature
     */
    public static final float MIN_TEMPERATURE = 273.15f;

    /**
     * The maximum temperature in kelvin one can set as tank input temperature
     */
    public static final float MAX_TEMPERATURE = 503.15f;

    /**
     * The offset needed to get from the current temperature to Celsius
     */
    public static final float CELSIUS_OFFSET = 273.15f;

    /**
     * The default pipe crosssection
     */
    public static final float PIPE_CROSSSECTION = 25;

    /**
     * The default pipe length
     */
    public static final int PIPE_LENGTH = 1;
}
