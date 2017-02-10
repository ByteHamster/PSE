package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Orientation;
import javafx.scene.control.Slider;

/**
 * Visualizes the current temperature.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
final class TemperatureVisualization extends BarLayout implements Observer {
    /**
     * The slider for showing the current temperature.
     */
    private javafx.scene.control.Slider temperatureBar;
    
    /**
     * Creates and initializes a new temperature visualization.
     */
    protected TemperatureVisualization() {
        super(Translator.getInstance().getString("monitoring.tank.fillLevel"));
        temperatureBar = new Slider();
        temperatureBar.setDisable(true);
        temperatureBar.setShowTickLabels(true);
        temperatureBar.setMin(SimulationConstants.MIN_TEMPERATURE);
        temperatureBar.setMax(SimulationConstants.MAX_TEMPERATURE);
        temperatureBar.setShowTickMarks(true);
        temperatureBar.setMinorTickCount(2);
        temperatureBar.setMajorTickUnit(10);
        temperatureBar.setOrientation(Orientation.VERTICAL);
        temperatureBar.setPrefHeight(MonitoringViewConstants.PREF_HEIGHT_FOR_BARS);
        this.getChildren().add(temperatureBar);
    }

    /**
     * Called when the observed object is updated.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        AbstractTank tank = (AbstractTank) observable;
        temperatureBar.setValue(tank.getLiquid().getTemperature());
    }
}
