package edu.kit.pse.osip.core.model.behavior;

import java.util.Observable;

/**
 * An observable boolean object.
 * 
 * @author David Kahles
 * @version 1.0
 */
public class ObservableBoolean extends Observable {
    /**
     * Saves the current value.
     */
    private boolean value;

    /**
     * Creates a new ObservableBoolean.
     * 
     * @param value the initial value.
     */
    public ObservableBoolean(boolean value) {
        this.value = value;
    }

    /**
     * Gets the value.
     * 
     * @return the value.
     */
    public boolean getValue() {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param value The new value.
     */
    public void setValue(boolean value) {
        this.value = value;
        setChanged();
        notifyObservers();
    }
}
