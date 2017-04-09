package edu.kit.pse.osip.core.model.behavior;

/**
 * A class grouping several alarms.
 * 
 * @author David Kahles
 * @version 1.0
 * @param <F></F> The type of the fill alarms.
 * @param <T></T> the type of the temperature alarms.
 */
public class AlarmGroup<F, T> {
    /**
     * Saves the overflow alarm.
     */
    private F overflow;
    /**
     * Saves the underflow alarm.
     */
    private F underflow;
    /**
     * Saves the overheating alarm.
     */
    private T overheat;
    /**
     * Saves the undercooling alarm.
     */
    private T undercool;

    /**
     * Creates a new AlarmGroup.
     * 
     * @param overflow The overflow alarm.
     * @param underflow The underflow alarm.
     * @param overheat The overheating alarm.
     * @param undercool The undercooling alarm.
     */
    public AlarmGroup(F overflow, F underflow, T overheat, T undercool) {
        this.overflow = overflow;
        this.underflow = underflow;
        this.overheat = overheat;
        this.undercool = undercool;
    }

    /**
     * Gets the overflow alarm.
     * 
     * @return the overflow alarm.
     */
    public F getOverflow() {
        return overflow;
    }

    /**
     * Gets the underflow alarm.
     * 
     * @return the underflow alarm.
     */
    public F getUnderflow() {
        return underflow;
    }

    /**
     * Gets the overheating alarm.
     * 
     * @return the overheating alarm.
     */
    public T getOverheat() {
        return overheat;
    }

    /**
     * Gets the undercooling alarm.
     * 
     * @return the undercooling alarm.
     */
    public T getUndercool() {
        return undercool;
    }
}
