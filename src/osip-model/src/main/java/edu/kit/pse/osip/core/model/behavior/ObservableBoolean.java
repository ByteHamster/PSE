package edu.kit.pse.osip.core.model.behavior;

import java.util.Observable;

/**
 * An observable boolean object
 * @author David Kahles
 * @version 1.0
 */
public class ObservableBoolean extends Observable {
    private boolean value;

    /**
     * Creates a new ObservableBoolean
     * @param value the initial valie
     */
    public ObservableBoolean(boolean value) {
        this.value = value;
    }

    /**
     * Get the value
     * @return the value
     */
    public boolean getValue() {
        return value;
    }

    /**
     * Set the value
     * @param value The new value
     */
    public void setValue(boolean value) {
        this.value = value;
        setChanged();
        notifyObservers();
    }
}
