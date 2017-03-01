package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.TankSelector;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Provides abstraction from the view
 *
 * @version 1.1
 * @author Niko Wihelm
 */
public interface SimulationControlInterface {

    /**
     * Disables or enables all control elements in the SimulationControlWindow to block or allow inputs.
     * @param isDisable true if inputs shall be blocked, false if they shall be enabled
     */
    public void setControlsDisabled(boolean isDisable);
    /**
     * Sets the listener that is notified of changes to valve thresholds.
     * @param listener The Consumer that gets all changes to valve thresholds.
     */
    void setValveListener(BiConsumer<Pipe, Byte> listener);
    /**
     * Sets the listener that is notified of changes in the temperature.
     * @param listener The Consumer that gets all changes to Tank temperatures
     */
    void setTemperatureListener(BiConsumer<TankSelector, Float> listener);
    /**
     * Sets the listener that is notified of changes in motor speed.
     * @param listener The Consumer that gets all to the motorSpeed
     */
    void setMotorListener(Consumer<Integer> listener);
    /**
     * Show the control window
     */
    void show();
}
