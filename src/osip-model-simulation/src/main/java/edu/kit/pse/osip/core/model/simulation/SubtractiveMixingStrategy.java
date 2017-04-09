package edu.kit.pse.osip.core.model.simulation;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import java.util.LinkedList;

/**
 * Mixing liquids using subtractive color mixture and Richmann's formula for temperature mixing.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class SubtractiveMixingStrategy implements MixingStrategy {
    /**
     * Mixes the given liquids and generates a new one. Colors are mixed using a subtractive strategy
     * and the temperatures are mixed using the Richmann's formula. If the resulting liquid amount is zero,
     * the temperature and color of the first liquid will be used.
     *
     * @param inflow Liquids to mix.
     * @return a single Liquid element containing a mixture of the given liquids,
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
            return new Liquid(0, inflow.peekFirst().getTemperature(), inflow.peekFirst().getColor());
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
