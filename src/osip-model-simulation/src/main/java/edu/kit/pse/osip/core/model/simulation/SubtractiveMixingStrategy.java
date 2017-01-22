package edu.kit.pse.osip.core.model.simulation;

import java.util.LinkedList;

import edu.kit.pse.osip.core.model.base.Liquid;

/**
 * Mixing liquids using subtractive color mixture and richmann's formula for temperature mixing.
 */
public class SubtractiveMixingStrategy implements edu.kit.pse.osip.core.model.simulation.MixingStrategy {
    /**
     * Mixes the given liquids and generates a new one. Colors are mixed using a substractive strategy and the temperatures are mixed using the richmann's formula.
     * 
     * @return a single Liquid element containing a mixture of the given liquids
     * @param inflow Liquids to mix.
     */
    public final Liquid mixLiquids (LinkedList<Liquid> inflow) {
        throw new RuntimeException("Not implemented!");
    }
}
