package edu.kit.pse.osip.core.model.simulation;

import java.util.LinkedList;

import edu.kit.pse.osip.core.model.base.Liquid;

/**
 * A strategy to mix liquids. This includes the mixing of colors and the mixing of temperatures.
 */
public interface MixingStrategy {
    /**
     * Mixes the given liquids and generates a new one. Volume equals the sum of all others. Colors and temperatures get mixed pro rata.
     * 
     * @return a single Liquid element containing a mixture of the given liquids.
     * @param inflow The different liquids to mix.
     */
    public Liquid mixLiquids (LinkedList<Liquid> inflow);
}
