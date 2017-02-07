package edu.kit.pse.osip.core.model.simulation;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;

/**
 * Tests the subtractive mixing strategy
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class SubtractiveMixingStrategyTest {
    /**
     * Tests colors, amounts and temperatures of two liquids
     */
    @Test
    public void testMixingTwoLiquids() {
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(new Liquid(100, 50, new Color(0x000000)));
        liquids.add(new Liquid(100,  0, new Color(0xfefefe)));

        Liquid result = new SubtractiveMixingStrategy().mixLiquids(liquids);
        assertEquals(200, result.getAmount(), 0.0001);
        assertEquals(25,  result.getTemperature(), 0.0001);
        assertEquals(0x7f7f7f, result.getColor().getRGB());
    }

    /**
     * Tests colors, amounts and temperatures of multiple liquids
     */
    @Test
    public void testMixingMultipleLiquids() {
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(new Liquid(100, 50, new Color(0x000000)));
        liquids.add(new Liquid(100,  0, new Color(0x000005)));
        liquids.add(new Liquid(100,  0, new Color(0x000000)));
        liquids.add(new Liquid(100,  0, new Color(0x00a000)));
        liquids.add(new Liquid(100,  0, new Color(0x500000)));

        Liquid result = new SubtractiveMixingStrategy().mixLiquids(liquids);
        assertEquals(500, result.getAmount(), 0.0001);
        assertEquals(10,  result.getTemperature(), 0.0001);
        assertEquals(0x102001, result.getColor().getRGB());
    }

    /**
     * Tests mixing a list that contains no liquids
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMixingEmptyListOfLiquids() {
        LinkedList<Liquid> liquids = new LinkedList<>();
        new SubtractiveMixingStrategy().mixLiquids(liquids);
    }

    /**
     * Tests mixing a null list of liquids
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMixingNullListOfLiquids() {
        new SubtractiveMixingStrategy().mixLiquids(null);
    }
}
