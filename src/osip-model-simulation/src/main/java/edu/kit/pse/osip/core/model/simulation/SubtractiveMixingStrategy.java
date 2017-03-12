package edu.kit.pse.osip.core.model.simulation;

import java.util.LinkedList;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;

/**
 * Mixing liquids using subtractive color mixture and richmann's formula for temperature mixing.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class SubtractiveMixingStrategy implements MixingStrategy {
    /**
     * Mixes the given liquids and generates a new one. Colors are mixed using a substractive strategy
     * and the temperatures are mixed using the richmann's formula.
     *
     * @return a single Liquid element containing a mixture of the given liquids
     * @param inflow Liquids to mix.
     */
    public Liquid mixLiquids(LinkedList<Liquid> inflow) {
        if (inflow == null || inflow.isEmpty()) {
            throw new IllegalArgumentException("Must at least mix one liquid");
        }

        // Count full amount first...
        float fullAmount = 0;
        for (Liquid liquid : inflow) {
            fullAmount += liquid.getAmount();
        }

        if (Math.abs(fullAmount) < 0.000001) {
            return new Liquid(0, SimulationConstants.MIN_TEMPERATURE, new Color(0));
        }

        // Real calculations...
        double c = 0;
        double m = 0;
        double y = 0;
        float temperature = 0;

        for (Liquid liquid : inflow) {
            c += (liquid.getAmount() / fullAmount) * liquid.getColor().getCyan();
            m += (liquid.getAmount() / fullAmount) * liquid.getColor().getMagenta();
            y += (liquid.getAmount() / fullAmount) * liquid.getColor().getYellow();

            temperature += (liquid.getAmount() / fullAmount) * liquid.getTemperature();
        }

        // Fix floating point inaccuracies
        c = Math.min(1, c);
        m = Math.min(1, m);
        y = Math.min(1, y);
        temperature = Math.min(SimulationConstants.MAX_TEMPERATURE,
                Math.max(SimulationConstants.MIN_TEMPERATURE, temperature));

        return new Liquid(fullAmount, temperature, new Color(c, m, y));
    }
}
