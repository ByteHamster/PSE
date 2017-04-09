package edu.kit.pse.osip.core.model.simulation;

import static org.junit.Assert.assertEquals;

import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import java.util.LinkedList;
import org.junit.Test;

/**
 * Tests the subtractive mixing strategy.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class SubtractiveMixingStrategyTest {
    /**
     * Verifies that one liquid stays the same.
     */
    @Test
    public void testSingleLiquid() {
        Liquid liquid = new Liquid(100, 350, new Color(0x000000));
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(liquid);

        assertEquals(liquid, new SubtractiveMixingStrategy().mixLiquids(liquids));
    }

    /**
     * Tests colors, amounts and temperatures of two liquids.
     */
    @Test
    public void testMixingTwoLiquids() {
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(new Liquid(100, 350, new Color(0x000000)));
        liquids.add(new Liquid(100, 300, new Color(0xfefefe)));

        Liquid result = new SubtractiveMixingStrategy().mixLiquids(liquids);
        assertEquals(new Liquid(200, 325, new Color(0x7f7f7f)), result);
    }

    /**
     * Tests colors, amounts and temperatures of two liquids which have different amounts.
     */
    @Test
    public void testMixingTwoLiquidsDifferentAmounts() {
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(new Liquid(300, 340, new Color(0x880000)));
        liquids.add(new Liquid(100, 300, new Color(0x440000)));

        Liquid result = new SubtractiveMixingStrategy().mixLiquids(liquids);
        assertEquals(new Liquid(400, 330, new Color(0x770000)), result);
    }

    /**
     * Tests colors, amounts and temperatures of multiple liquids.
     */
    @Test
    public void testMixingMultipleLiquids() {
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(new Liquid(100, 350, new Color(0x000000)));
        liquids.add(new Liquid(100, 300, new Color(0x000005)));
        liquids.add(new Liquid(100, 300, new Color(0x000000)));
        liquids.add(new Liquid(100, 300, new Color(0x00a000)));
        liquids.add(new Liquid(100, 300, new Color(0x500000)));

        Liquid result = new SubtractiveMixingStrategy().mixLiquids(liquids);
        assertEquals(new Liquid(500, 310, new Color(0x102001)), result);
    }

    /**
     * Tests mixing a list that contains no liquids.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMixingEmptyListOfLiquids() {
        LinkedList<Liquid> liquids = new LinkedList<>();
        new SubtractiveMixingStrategy().mixLiquids(liquids);
    }

    /**
     * Tests mixing a null list of liquids.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testMixingNullListOfLiquids() {
        new SubtractiveMixingStrategy().mixLiquids(null);
    }

    /**
     * Tests mixing liquids which have no amount.
     */
    @Test
    public void testNoAmount() {
        LinkedList<Liquid> liquids = new LinkedList<>();
        liquids.add(new Liquid(0, 300, new Color(0x000000)));
        liquids.add(new Liquid(0, 300, new Color(0xfefefe)));

        Liquid result = new SubtractiveMixingStrategy().mixLiquids(liquids);
        assertEquals(0, result.getAmount(), 0.00001);
    }
}
