package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * This class has controls specific to the input tanks.
 */
public class TankTab extends AbstractTankTab {

    public TankTab(String name, Tank tank) {
        super(name);
    }

    /**
     * Gets the value of inFlow.
     * @return The value of inFlow.
     */
    public final int getInFlow() {
        throw new RuntimeException("Not implemented!");
    }
}
