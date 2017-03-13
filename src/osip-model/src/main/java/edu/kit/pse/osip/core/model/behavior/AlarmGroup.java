package edu.kit.pse.osip.core.model.behavior;

/**
 * A class grouping several alarms
 * @author David Kahles
 * @version 1.0
 * @param <F></F> The type of the fillalarms
 * @param <T></T> the type of the temperaturealarms
 */
public class AlarmGroup<F, T> {
    private F overflow;
    private F underflow;
    private T overheat;
    private T undercool;

    /**
     * Create a new AlarmGroup
     * @param overflow The overflow alarm
     * @param underflow The underflow alarm
     * @param overheat The overheat alarm
     * @param undercool The undercool alarm
     */
    public AlarmGroup(F overflow, F underflow, T overheat, T undercool) {
        this.overflow = overflow;
        this.underflow = underflow;
        this.overheat = overheat;
        this.undercool = undercool;
    }

    /**
     * Get the overflow alarm
     * @return the overflow alarm
     */
    public F getOverflow() {
        return overflow;
    }

    /**
     * Get the underflow alarm
     * @return the underflow alarm
     */
    public F getUnderflow() {
        return underflow;
    }

    /**
     * Get the overheat alarm
     * @return the overheat alarm
     */
    public T getOverheat() {
        return overheat;
    }

    /**
     * Get the undercool alarm
     * @return the undercool alarm
     */
    public T getUndercool() {
        return undercool;
    }
}
