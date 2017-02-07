package edu.kit.pse.osip.core.model.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * A class to test the TankSelector class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class TankSelectorTest {
    /**
     * Check whether valuesWithoutMix() returns all enum values exept the mix tank.
     */
    @Test
    public void testValuesWithoutMix() {
        TankSelector[] values = TankSelector.values();
        TankSelector[] valuesWithoutMix = TankSelector.valuesWithoutMix();
        assertEquals(values.length, valuesWithoutMix.length + 1);
        for (TankSelector i : valuesWithoutMix) {
            assertFalse(i == TankSelector.MIX);
        }
    }

    /**
     * Check whether getUpperTankCount() returns one less than the length of values().
     */
    @Test
    public void testGetUpperTankCount() {
        assertEquals(TankSelector.getUpperTankCount(), TankSelector.valuesWithoutMix().length);
    }

    /**
     * Check whether getIntialColor() is correct. This test uses hardcoded tanks and color values.
     */
    @Test
    public void testGetInitialColor() {
        assertEquals(TankSelector.MIX.getInitialColor(), new Color(1, 1, 1));
        assertEquals(TankSelector.YELLOW.getInitialColor(), new Color(0, 1, 0));
        assertEquals(TankSelector.MAGENTA.getInitialColor(), new Color(0, 0, 1));
        assertEquals(TankSelector.CYAN.getInitialColor(), new Color(1, 0, 0));
    }
}
