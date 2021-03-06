package edu.kit.pse.osip.core.model.simulation;

import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import java.util.LinkedList;

/**
 * The model of a tank, allowing to put in and take out liquid. Besides this addition, it is a normal tank.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class TankSimulation extends Tank {
    /**
     * The strategy to mix the current liquid with the put-in liquid.
     */
    private MixingStrategy mixingStrategy;

    /**
     * Creates a new tank.
     * 
     * @param capacity The size of the tank in cm³.
     * @param tankSelector Specifies which tank it is. This is useful if you have access
     *                     to the tank and need to get a unique identifier.
     * @param liquid The default content of the tank.
     * @param outPipe The outgoing pipe.
     * @param inPipe The incoming pipe.
     */
    public TankSimulation(float capacity, TankSelector tankSelector, Liquid liquid, Pipe outPipe, Pipe inPipe) {
        super(capacity, tankSelector, liquid, outPipe, inPipe);
        mixingStrategy = new SubtractiveMixingStrategy();
    }

    /**
     * Adds new liquid to the tank. The tank mixes the input with its current content and sets its values.
     * 
     * @param input The liquid to put in.
     */
    public void putIn(Liquid input) {
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(this.getLiquid());
        liquids.add(input);
        this.setLiquid(mixingStrategy.mixLiquids(liquids));
    }

    /**
     * Takes liquid out of the tank.
     * 
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
