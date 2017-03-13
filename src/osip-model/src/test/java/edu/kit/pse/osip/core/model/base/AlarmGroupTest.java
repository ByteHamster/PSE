package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.model.behavior.AlarmGroup;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the AlarmGroup class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class AlarmGroupTest {
    /**
     * Test whether the AlarmGroup works
     */
    @Test
    public void test() {
        AtomicBoolean b1 = new AtomicBoolean(true);
        AtomicBoolean b2 = new AtomicBoolean(false);
        AtomicBoolean b3 = new AtomicBoolean(true);
        AtomicBoolean b4 = new AtomicBoolean(false);
        AlarmGroup<AtomicBoolean, AtomicBoolean> a = new AlarmGroup<>(b1, b2, b3, b4);
        assertTrue(a.getOverflow().get());
        assertFalse(a.getUnderflow().get());
        assertTrue(a.getOverheat().get());
        assertFalse(a.getUndercool().get());
        b1.set(true);
        b2.set(true);
        b3.set(false);
        b4.set(false);
        assertTrue(a.getOverflow().get());
        assertTrue(a.getUnderflow().get());
        assertFalse(a.getOverheat().get());
        assertFalse(a.getUndercool().get());
    }
}
