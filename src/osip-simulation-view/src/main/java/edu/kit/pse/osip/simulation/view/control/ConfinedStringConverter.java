package edu.kit.pse.osip.simulation.view.control;

import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;

/**
 * A String converter with bounds.
 * @author David Kahles
 * @version 1.0
 */
public class ConfinedStringConverter extends StringConverter<Number> {
    private double max;
    private double min;
    private StringProperty textFieldProperty;

    /**
     * Creates a new ConfinedStringConverter.
     * @param min The minimum value.
     * @param max The maximum value.
     * @param textFieldProperty The textfield which needs to be confined.
     */
    public ConfinedStringConverter(Double min, Double max, StringProperty textFieldProperty) {
        this.max = max;
        this.min = min;
        this.textFieldProperty = textFieldProperty;
    }

    @Override
    public String toString(Number d) {
        return Integer.toString(d.intValue());
    }

    @Override
    public Number fromString(String s) {
        Double d;
        try {
            d = Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return null;
        }
        if (d > max) {
            textFieldProperty.setValue(Integer.toString((int) max));
            return max;
        } else if (d < 0) {
            textFieldProperty.setValue(Integer.toString((int) min));
            return min;
        } else {
            return d;
        }
    }
}
