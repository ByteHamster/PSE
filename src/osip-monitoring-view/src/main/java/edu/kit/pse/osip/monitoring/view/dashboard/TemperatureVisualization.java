package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * Visualises the current temperature.
 */
public class TemperatureVisualization implements java.util.Observer {
    /**
     * The Label shows the temperature section.
     */
    private javafx.scene.control.Label temperatureLabel;
    /**
     * The slider for showing the current temperature.
     */
    private javafx.scene.control.Slider temperatureBar;
    /**
     * Creates and initializes a new temperature visualization.
     */
    protected TemperatureVisualization () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the label for showing the temperature section.
     * @return the label showing the temperature section.
     */
    protected final javafx.scene.control.Label getTemperatureLabel () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Slider for showing the actual temperature.
     * @return the slider showing the actual temperature.
     */
    protected final javafx.scene.control.Slider getTemperatureBar () {
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
