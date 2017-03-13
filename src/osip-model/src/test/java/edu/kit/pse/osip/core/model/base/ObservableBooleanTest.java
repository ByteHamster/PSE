package edu.kit.pse.osip.core.model.base;

import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the ObservableBoolean class.
 *
 * @author David Kahles
 * @version 1.0
 */
public class ObservableBooleanTest implements Observer {
    private boolean updated = false;

    /**
     * Test whether the ObservableBoolean works when initializing it to true
     */
    @Test
    public void testTrue() {
        ObservableBoolean b = new ObservableBoolean(true);
        b.addObserver(this);
        b.setValue(false);
        assertFalse(b.getValue());
        assertTrue(updated);
        updated = false;
        b.setValue(true);
        assertTrue(b.getValue());
        assertTrue(updated);
    }

    /**
     * Test whether the ObservableBoolean works when initializing it to false
     */
    @Test
    public void testFalse() {
        ObservableBoolean b = new ObservableBoolean(false);
        b.addObserver(this);
        b.setValue(true);
        assertTrue(b.getValue());
        assertTrue(updated);
        updated = false;
        b.setValue(false);
        assertFalse(b.getValue());
        assertTrue(updated);
    }

    @Override
    public void update(Observable o, Object arg) {
        updated = true;
    }
}
