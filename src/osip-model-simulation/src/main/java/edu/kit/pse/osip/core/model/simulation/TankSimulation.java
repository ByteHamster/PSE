package edu.kit.pse.osip.core.model.simulation;

import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * The model of a tank, allowing to put in and take out liquid. Besides this addition, it is a normal tank.
 */
public class TankSimulation extends edu.kit.pse.osip.core.model.base.Tank {
    public MixingStrategy mixingStrategy;
    /**
     * Create a new tank.
     * @param capacity The size of the tank in cm³.
     * @param tankSelector Specifies which tank it is. This is useful if you have access to the tank and need to get a unique identifier.
     * @param liquid The default content of the tank.
     * @param outPipe The outgoing pipe.
     * @param inPipe The incoming pipe.
     */
    public TankSimulation (float capacity, TankSelector tankSelector, Liquid liquid, Pipe outPipe, Pipe inPipe) {
        super(capacity, tankSelector, liquid, outPipe, inPipe);
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
