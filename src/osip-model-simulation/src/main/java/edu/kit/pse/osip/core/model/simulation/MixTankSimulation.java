package edu.kit.pse.osip.core.model.simulation;

import java.util.LinkedList;

import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;

/**
 * The model of a mixtank, allowing to put in and take out liquid. Besides this addition, it is a normal MixTank.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class MixTankSimulation extends edu.kit.pse.osip.core.model.base.MixTank {
    private MixingStrategy mixingStrategy;

    /**
     * Create a new mixtank.
     * @param capacity The size of the tank in cm³.
     * @param liquid The default content of the tank.
     * @param outPipe The outgoing pipe.
     */
    public MixTankSimulation(float capacity, Liquid liquid, Pipe outPipe) {
        super(capacity, liquid, outPipe);
        mixingStrategy = new SubtractiveMixingStrategy();
    }

    /**
     * Add new liquid to the tank. The tank mixes the input with its current content and sets its values.
     * @param input The liquids to put in.
     */
    public void putIn(LinkedList<Liquid> input) {
        input.add(this.getLiquid());
        this.setLiquid(mixingStrategy.mixLiquids(input));
    }

    /**
     * Takes liquid out of the tank.
     * @return the liquid that was taken out.
     * @param amount The amount of liquid to take out.
     */
    public Liquid takeOut(float amount) {
        Liquid current = this.getLiquid();
        if (current.getAmount() < amount) {
            this.setLiquid(new Liquid(0, current.getTemperature(), current.getColor()));
            return current;
        } else {
            this.setLiquid(new Liquid(current.getAmount() - amount, current.getTemperature(), current.getColor()));
            return new Liquid(amount, current.getTemperature(), current.getColor());
        }
    }
}
