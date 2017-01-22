package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * Visualises a fill level.
 */
public class FillLevelVisualization implements java.util.Observer {
    /**
     * The label shows the fill level section.
     */
    private javafx.scene.control.Label levelLabel;
    /**
     * The gauge shows the actual fill level.
     */
    private jfxtras.scene.control.gauge.linear.BasicRoundDailGauge levelBar;
    /**
     * Creates a new fill level visualization.
     */
    protected FillLevelVisualization () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the label for showing the fill level section.
     * @return the label for showing the fill level section.
     */
    protected final javafx.scene.control.Label getFillLevelLabel () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the gauge showing the current fill level.
     * @return the gauge showing the current fill level.
     */
    protected final jfxtras.scene.control.gauge.linear.BasicRoundDailGauge getFillLevelBar () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when the observed object is updated.
     * @param observable The observed object.
     * @param object The new value.
     */
    public final void update (java.util.Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
}
