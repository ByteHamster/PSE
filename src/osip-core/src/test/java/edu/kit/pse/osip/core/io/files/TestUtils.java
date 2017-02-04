package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertTrue;

/**
 * Small utils to simplify testing process
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public final class TestUtils {
    private TestUtils() {
        // Must not be instantiated
    }

    /**
     * Asserts that floats are correct
     * @param f1 Float to check
     * @param f2 Expected value
     */
    protected static void assertFloat(float f1, float f2) {
        assertTrue("Expected " + f2 + ", got " + f1, Math.abs(f1 - f2) < 0.00001);
    }
}
