package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.Pipe;
import javafx.application.Platform;

import java.util.Observable;
import java.util.Observer;
import jfxtras.scene.control.gauge.linear.SimpleMetroArcGauge;

/**
 * Visualizes the amount of liquid flowing through a pipe with a simple gauge.
 * 
 * @author Martin Armbruster
 * @version 1.4
 */
class GaugeVisualization extends BarLayout implements Observer {
    /**
     * The gauge shows the current value.
     */
    protected SimpleMetroArcGauge gauge;
    
    /**
     * Creates and initializes a new visualization.
     * 
     * @param name The name of the represented bar.
     */
    protected GaugeVisualization(String name) {
        super(name);
        gauge = new SimpleMetroArcGauge();
        gauge.setPrefSize(MonitoringViewConstants.PREF_HEIGHT_FOR_BARS, MonitoringViewConstants.PREF_HEIGHT_FOR_BARS);
        this.getChildren().add(0, gauge);
    }
    
    /**
     * Creates a new visualization that is connected to a pipe.
     * 
     * @param name The name of the represented bar.
     * @param connectedPipe The pipe, the bar is connected to.
     * @throws NullPointerException when the pipe is null.
     */
    protected GaugeVisualization(String name, Pipe connectedPipe) {
        this(name);
        if (connectedPipe == null) {
            throw new NullPointerException("Pipe is null.");
        }
        gauge.setMaxValue(connectedPipe.getMaxInputWithFullyOpenedValve());
        connectedPipe.addObserver(this);
    }
    
    /**
     * Called when the observed object has changed.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        Pipe pipe = (Pipe) observable;
        Platform.runLater(() -> gauge.setValue(pipe.getMaxInput()));
    }
}
