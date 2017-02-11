package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;
import java.util.Observer;
import jfxtras.scene.control.gauge.linear.BasicRoundDailGauge;

/**
 * Visualizes a fill level.
 * 
 * @author Martin Armbruster
 * @version 1.1s
 */
class FillLevelVisualization extends BarLayout implements Observer {
    /**
     * The gauge shows the actual fill level.
     */
    private BasicRoundDailGauge levelBar;
    
    /**
     * Creates a new fill level visualization.
     */
    protected FillLevelVisualization() {
        super(Translator.getInstance().getString("monitoring.tank.fillLevel"));
        levelBar = new BasicRoundDailGauge();
        levelBar.setPrefHeight(MonitoringViewConstants.PREF_HEIGHT_FOR_BARS);
        levelBar.setMaxValue(SimulationConstants.TANK_SIZE);
        this.getChildren().add(levelBar);
    }
    
    /**
     * Called when the observed object is updated.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        AbstractTank tank = (AbstractTank) observable;
        levelBar.setValue(tank.getFillLevel());
    }
}
