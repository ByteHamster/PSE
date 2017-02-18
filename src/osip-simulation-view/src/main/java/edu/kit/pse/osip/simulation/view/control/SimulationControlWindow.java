package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This is the main window for controlling the OSIP simulation. It provides scroll bars in
 * different tabs for all tanks to adjust their values, like outflow, temperature and motor speed.
 */
public class SimulationControlWindow extends Stage implements SimulationControlInterface {
    private MixTankTab mixTankTab;
    private TankTab tankTabs;

    /**
     * Constructs a new SimulationControlWindow
     * @param productionSite The ProductionSite, to get all the tanks
     */
    public SimulationControlWindow(ProductionSite productionSite) {
        throw new RuntimeException("Not implemented!");
    }
    
    /**
     * Sets the listener that is notified of changes to valve thresholds.
     * @param listener The Consumer that gets all changes to valve thresholds.
     */
    public void setValveListener(BiConsumer<Pipe, Byte> listener) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the listener that is notified of changes in the temperature.
     * @param listener The Consumer that gets all changes to Tank temperatures
     */
    public void setTemperatureListener(BiConsumer<TankSelector, Float> listener) {
        throw new RuntimeException("Not implemented");
    }

    public void setMotorListener(Consumer<Integer> listener) {
        throw new RuntimeException("Not implemented");
    }
}
