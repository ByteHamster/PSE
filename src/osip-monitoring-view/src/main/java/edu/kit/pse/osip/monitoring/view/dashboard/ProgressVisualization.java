package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.Line;

/**
 * Visualizes a progression.
 * 
 * @author Martin Armbruster
 * @version 1.6
 */
class ProgressVisualization {
    /**
     * The tank to which this progression is assigned.
     */
    private AbstractTank tank;
    
    /**
     * Saves the x coordinate for the next data point.
     */
    private long x;
    
    /**
     * true when this progression is enabled and tracked.
     */
    private boolean isEnabled;
    
    /**
     * Saves the name of this progression.
     */
    private String progressName;
    
    /**
     * The chart shows the progression.
     */
    private LineChart<Number, Number> progressChart;
    
    /**
     * The series that stores all values.
     */
    private XYChart.Series<Number, Number> progressSeries;
    
    /**
     * Creates and initializes a new chart for the progression.
     * 
     * @param progressName The name of this progression.
     * @param tank the tank to which this progression is assigned.
     */
    protected ProgressVisualization(String progressName, AbstractTank tank) {
        x = 0;
        this.tank = tank;
        this.progressName = progressName;
        isEnabled = true;
        setupChart();
    }
    
    /**
     * Setups the chart.
     */
    private void setupChart() {
        NumberAxis x = new NumberAxis();
        x.setForceZeroInRange(false);
        x.setLabel(Translator.getInstance().getString("monitoring.tank.progress.x"));
        NumberAxis y = new NumberAxis();
        y.setAutoRanging(false);
        if (progressName.equals(Translator.getInstance().getString("monitoring.tank.progress.temperature"))) {
            y.setLowerBound(SimulationConstants.MIN_TEMPERATURE - SimulationConstants.CELSIUS_OFFSET);
            y.setUpperBound(SimulationConstants.MAX_TEMPERATURE - SimulationConstants.CELSIUS_OFFSET);
            y.setTickUnit((SimulationConstants.MAX_TEMPERATURE - SimulationConstants.MIN_TEMPERATURE) / 10);
        } else {
            y.setLowerBound(0.0);
            y.setUpperBound(1.0);
            y.setTickUnit(0.1);
        }
        y.setLabel(Translator.getInstance().getString("monitoring.tank.progress.y"));
        progressChart = new LineChart<>(x, y);
        progressChart.setPrefHeight(MonitoringViewConstants.PREF_SIZE_FOR_BARS * 1.25);
        progressChart.setLegendVisible(false);
        progressSeries = new XYChart.Series<>();
        progressChart.getData().add(progressSeries);
    }
    
    /**
     * Returns the name of this progression.
     * 
     * @return the progression name.
     */
    protected final String getProgressName() {
        return progressName;
    }
    
    /**
     * Returns the chart showing the progression.
     * 
     * @return the chart showing the progression.
     */
    protected final LineChart<Number, Number> getProgressChart() {
        return progressChart;
    }
    
    /**
     * Enables or disables this progression.
     * 
     * @param progressEnabled true if the progression should be enabled and false otherwise.
     */
    protected void setProgressEnabled(boolean progressEnabled) {
        if (progressEnabled && !isEnabled) {
            progressSeries.getData().clear();
            x = 0;
        }
        isEnabled = progressEnabled;
        progressChart.setDisable(!progressEnabled);
    }
    
    /**
     * Updates this progression.
     */
    protected void update() {
        if (!isEnabled) {
            return;
        }
        XYChart.Data<Number, Number> newDataPoint;
        if (progressName.equals(Translator.getInstance().getString("monitoring.tank.progress.temperature"))) {
            newDataPoint = new XYChart.Data<>(x, tank.getLiquid().getTemperature()
                    - SimulationConstants.CELSIUS_OFFSET);
        } else {
            newDataPoint = new XYChart.Data<>(x, tank.getFillLevel());
        }
        Line dataPointVisual = new Line();
        dataPointVisual.setStrokeWidth(0);
        newDataPoint.setNode(dataPointVisual);
        if (progressSeries.getData().size() > 70) {
            progressSeries.getData().remove(0, 10);
        }
        progressSeries.getData().add(newDataPoint);
        x++;
    }
}
