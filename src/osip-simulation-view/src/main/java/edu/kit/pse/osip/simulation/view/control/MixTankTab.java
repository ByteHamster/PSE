package edu.kit.pse.osip.simulation.view.control;

import edu.kit.pse.osip.core.model.base.MixTank;

/**
 * This class has controls specific to the tanks in which several inputs are mixed.
 */
public class MixTankTab extends AbstractTankTab {

    public MixTankTab(String name, MixTank mixTank) {
        super(name);
    }

    /**
     * Gets the value of the motor rpm slider
     * 
     * @return The rpm of the motor in the mixtank
     */
    public final int getMotorRPM() {
        throw new RuntimeException("Not implemented!");
    }
}
