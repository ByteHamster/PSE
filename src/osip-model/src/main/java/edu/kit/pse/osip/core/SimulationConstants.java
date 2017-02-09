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
    public static final float MIN_TEMPERATURE = 270;

    /**
     * The maximum temperature in kelvin one can set as tank input temperature
     */
    public static final float MAX_TEMPERATURE = 500;
}
