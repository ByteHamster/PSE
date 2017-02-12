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
 * @version 1.3
 */
class TemperatureVisualization extends BarLayout implements Observer {
    /**
     * The number of all major ticks on the temperature bar with a label.
     */
    private static final int NUMBER_OF_MAJOR_TICKS = 10;
    
    /**
     * The number of minor ticks between two major ticks.
     */
    private static final int NUMBER_OF_MINOR_TICKS = 10;
    
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
        temperatureBar.setMin(SimulationConstants.MIN_TEMPERATURE);
        temperatureBar.setMax(SimulationConstants.MAX_TEMPERATURE);
        temperatureBar.setShowTickMarks(true);
        temperatureBar.setMinorTickCount(NUMBER_OF_MINOR_TICKS);
        temperatureBar.setMajorTickUnit((SimulationConstants.MAX_TEMPERATURE - SimulationConstants.MIN_TEMPERATURE)
                / NUMBER_OF_MAJOR_TICKS);
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
        temperatureBar.setValue(tank.getLiquid().getTemperature());
    }
}
