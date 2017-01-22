package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * Visualises a progression.
 */
public class ProgressVisualization implements java.util.Observer {
    /**
     * The chart shows the progression.
     */
    private javafx.scene.chart.LineChart<Number, Number> progressChart;
    /**
     * The series that stores all values.
     */
    private javafx.scene.chart.XYChart.Series<Number, Number> progressSeries;
    /**
     * Creates and initializes a new chart for the progression.
     * @param progressName The name of this progression.
     */
    protected ProgressVisualization (String progressName) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the name of this progression.
     * @return
     * the progression name.
     */
    protected final String getProgressName () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the chart showing the progression.
     * @return
     * the chart showing the progression.
     */
    protected final javafx.scene.chart.LineChart<Number, Number> getProgressChart () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables this progression.
     * @param progressEnabled true if the progression should be enabled and false otherwise.
     */
    protected final void setProgressEnabled (boolean progressEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when the observed object has updated.
     * @param observable The observed object.
     * @param object The new value.
     */
    public final void update (java.util.Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
}
