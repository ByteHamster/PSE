package edu.kit.pse.osip.core.model.simulation;

import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;

/**
 * The model of a mixtank, allowing to put in and take out liquid. Besides this addition, it is a normal MixTank.
 */
public class MixTankSimulation extends edu.kit.pse.osip.core.model.base.MixTank {
    public MixingStrategy mixingStrategy;
    /**
     * Create a new mixtank.
     * @param capacity The size of the tank in cm³.
     * @param liquid The default content of the tank.
     * @param outPipe The outgoing pipe.
     */
    public MixTankSimulation (float capacity, Liquid liquid, Pipe outPipe) {
        super(capacity, liquid, outPipe);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Add new liquid to the tank. The tank mixes the input with its current content and sets its values.
     * @param input The liquid to put in.
     */
    public final void putIn (Liquid input) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Takes liquid out of the tank.
     * @return the liquid that was taken out.
     * @param amount The amount of liquid to take out.
     */
    public final Liquid takeOut (float amount) {
        throw new RuntimeException("Not implemented!");
    }
}
