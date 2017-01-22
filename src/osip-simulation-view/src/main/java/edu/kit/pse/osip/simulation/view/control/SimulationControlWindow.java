package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.simulation.controller.FlowRateListener;
import edu.kit.pse.osip.simulation.controller.MotorListener;
import edu.kit.pse.osip.simulation.controller.TemperatureListener;

/**
 * This is the main window for controlling the OSIP simulation. It provides scroll bars in different tabs for all tanks to adjust their values, like outflow, temperature and motor speed.
 */
public class SimulationControlWindow extends javafx.stage.Stage implements edu.kit.pse.osip.simulation.controller.SimulationControlWindow {
    public MixTankTab mixTankTab;
    public TankTab tankTabs;
    /**
     * The listener that is called when the temperature was changed
     */
    public TemperatureListener temperatureListener;
    /**
     * The listener that is called when the flow rate was changed
     */
    public FlowRateListener flowRateListener;
    /**
     * The listener that is called when the motor speed was changed
     */
    public MotorListener motorListener;
    /**
     * Constructs a new SImulationConstrolWindow
     * @param productionSite The ProducitonSite, to get all the tanks
     */
    public SimulationControlWindow (ProductionSite productionSite) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets the value of inFlow of tank.
     * @return The value of inFlow of tank
     * @param tank The tank to get the inflow from
     */
    public final int getInFlow (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * 		Gets the value of outFlow of the tank.
     * @return The value of outFlow of the tank
     * @param tank The tank to get the outflow from
     */
    public final int getOutFlow (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets the value of temp of the tank
     * @return The value of temp the tank.
     * @param tank The tank to get the temperature from
     */
    public final int getTemperature (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the motor speed of the mixtank
     * 
     * @return the motor speed of the mixtank
     */
    public final int getMotorSpeed () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the listener that is notified of changes in the flow rate. Listner gets the TankSelector of the actually modified tank
     * @param listener The listener to be called on changes
     */
    public final void setFlowRateListener (FlowRateListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the listener that is notified of changes in the temperature. Listener gets the TankSelector of the actually modified tank
     * @param listener The listener to be called on changes
     */
    public final void setTemperatureListener (TemperatureListener listener) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the listener that is notified of changes in motor speed.
     * @param listener The listener to be called on changes
     */
    public final void setMotorListener (MotorListener listener) {
        throw new RuntimeException("Not implemented!");
    }
}
