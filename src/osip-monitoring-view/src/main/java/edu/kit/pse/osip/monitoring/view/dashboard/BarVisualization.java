package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * Visualizes the amount of liquid flowing through a pipe or the motor speed.
 */
public class BarVisualization implements java.util.Observer {
    /**
     * The label shows which type is represented in this visualization.
     */
    private javafx.scene.control.Label label;
    /**
     * The gauge shows the current value.
     */
    private jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge bar;
    /**
     * Creates and initializes a new visualization.
     * @param name The name of the represented bar.
     */
    protected BarVisualization (String name) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the label for showing which type is represented.
     * @return the label
     */
    protected final javafx.scene.control.Label getLabel () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the gauge showing the current value
     * @return the gauge showing the current value
     */
    protected final jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge getBar () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when the observed object has changed.
     * @param observable The observed object.
     * @param object The new value.
     */
    public final void update (java.util.Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
}
