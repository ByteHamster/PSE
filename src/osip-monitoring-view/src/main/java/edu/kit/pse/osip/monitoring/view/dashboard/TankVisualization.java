package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.behavior.AlarmGroup;
import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.scene.paint.Color;

/**
 * Visualization for a normal tank.
 * 
 * @author Martin Armbruster
 * @version 1.2
 */
class TankVisualization extends AbstractTankVisualization {
    /**
     * Visualization of the supply pipe.
     */
    private GaugeVisualization supply;
    
    /**
     * Creates a new visualization.
     * 
     * @param tank The tank to display
     * @param alarms The alarms of the tank
     * @throws NullPointerException when the tank is null.
     */
    protected TankVisualization(Tank tank, AlarmGroup<ObservableBoolean, ObservableBoolean> alarms) {
        super(tank, alarms);
        supply = new GaugeVisualization(Translator.getInstance().getString("monitoring.tank.inflow"));
        tank.getInPipe().addObserver(supply);
        this.add(supply, 0, 1);
        this.add(drain, 1, 1);
        this.add(fillLevel, 0, 2);
        this.add(temperature, 1, 2);
        this.add(progresses, 0, 3, 2, 1);        
        edu.kit.pse.osip.core.model.base.Color currentColor = tank.getLiquid().getColor();
        Color convertedColor = new Color(currentColor.getR(), currentColor.getG(), currentColor.getB(), 1.0);
        this.setBorderColor(convertedColor);
    }
}
