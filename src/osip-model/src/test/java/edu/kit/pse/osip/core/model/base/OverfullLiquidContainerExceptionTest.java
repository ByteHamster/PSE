package edu.kit.pse.osip.core.model.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the OverfullLiquidContainerException class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class OverfullLiquidContainerExceptionTest {
    /**
     * Check whether the getters work as expected.
     */
    @Test
    public void testGetter() {
        OverfullLiquidContainerException ex = new OverfullLiquidContainerException("test", 1, 2);
        assertEquals(1, ex.getMaximumAmount(), 0.001);
        assertEquals(2, ex.getTriedAmount(), 0.001);
        assertEquals("test", ex.getMessage());
    }
}
