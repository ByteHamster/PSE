package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;

/**
 * Visualizes the current temperature.
 * 
 * @author Martin Armbruster
 * @version 1.6
 */
class TemperatureVisualization extends BarLayout implements Observer {
    /**
     * Difference between °K and °C to calculate values from one to another.
     */
    private static final double DIFFERENCE_KELVIN_CELSIUS = 273.15;
    
    /**
     * The slider for showing the current temperature.
     */
    private javafx.scene.control.Slider temperatureBar;
    
    /**
     * Creates and initializes a new temperature visualization.
     */
    protected TemperatureVisualization() {
        super(Translator.getInstance().getString("monitoring.tank.temperature"));
        temperatureBar = new Slider();
        temperatureBar.setDisable(true);
        temperatureBar.setShowTickLabels(true);
        temperatureBar.setMin(Math.round(SimulationConstants.MIN_TEMPERATURE - DIFFERENCE_KELVIN_CELSIUS));
        temperatureBar.setMax(Math.round(SimulationConstants.MAX_TEMPERATURE - DIFFERENCE_KELVIN_CELSIUS));
        temperatureBar.setShowTickMarks(true);
        temperatureBar.setMinorTickCount(MonitoringViewConstants.NUMBER_OF_MINOR_TICKS);
        temperatureBar.setMajorTickUnit((SimulationConstants.MAX_TEMPERATURE - SimulationConstants.MIN_TEMPERATURE)
                / MonitoringViewConstants.NUMBER_OF_MAJOR_TICKS);
        temperatureBar.setOrientation(Orientation.VERTICAL);
        temperatureBar.setPrefHeight(MonitoringViewConstants.PREF_HEIGHT_FOR_BARS);
        this.getChildren().add(0, temperatureBar);
    }

    /**
     * Called when the observed object is updated.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        AbstractTank tank = (AbstractTank) observable;
        Platform.runLater(() -> temperatureBar.setValue(tank.getLiquid().getTemperature() - DIFFERENCE_KELVIN_CELSIUS));
    }
}
