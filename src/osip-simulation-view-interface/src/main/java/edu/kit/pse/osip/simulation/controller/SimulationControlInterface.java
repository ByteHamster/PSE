package edu.kit.pse.osip.simulation.controller;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Provides abstraction from the view
 */
public interface SimulationControlInterface {
    /**
     * Gets the value of inFlow of tank.
     * @return The value of inFlow of tank
     * @param tank The tank to get the inflow from
     */
    public int getInFlow (TankSelector tank);
    /**
     * 		Gets the value of outFlow of the tank.
     * @return The value of outFlow of the tank
     * @param tank The tank to get the outflow from
     */
    public int getOutFlow (TankSelector tank);
    /**
     * Gets the value of temp of the tank
     * @return The value of temp the tank.
     * @param tank The tank to get the temperature from
     */
    public int getTemperature (TankSelector tank);
    /**
     * Returns the motor speed of the mixtank
     * 
     * @return the motor speed of the mixtank
     */
    public int getMotorSpeed ();
    /**
     * Sets the listener that is notified of changes in the flow rate. Listner gets the TankSelector of the actually modified tank
     * @param listener The listener to be called on changes
     */
    public void setFlowRateListener (FlowRateListener listener);
    /**
     * Sets the listener that is notified of changes in the temperature. Listner gets the TankSelector of the actually modified tank
     * @param listener The listener to be called on changes
     */
    public void setTemperatureListener (TemperatureListener listener);
    /**
     * Sets the listener that is notified of changes in motor speed.
     * @param listener The listener to be called on changes
     */
    public void setMotorListener (MotorListener listener);
}
