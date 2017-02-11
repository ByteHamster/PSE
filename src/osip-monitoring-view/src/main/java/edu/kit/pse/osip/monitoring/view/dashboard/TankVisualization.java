package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.scene.paint.Color;

/**
 * Visualization for a normal tank.
 * 
 * @author Martin Armbruster
 * @version 1.1
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
     * @throws NullPointerException when the tank is null.
     */
    protected TankVisualization(Tank tank) {
        super(tank);
        supply = new GaugeVisualization(Translator.getInstance().getString("monitoring.tank.supply"), tank.getInPipe());
        this.add(supply, 0, 4);
        this.add(drain, 1, 4);
        this.add(fillLevel, 0, 5);
        this.add(temperature, 1, 5);
        this.add(progresses, 0, 6, 2, 1);        
        edu.kit.pse.osip.core.model.base.Color currentColor = tank.getLiquid().getColor();
        Color convertedColor = new Color(currentColor.getR(), currentColor.getG(), currentColor.getB(), 1.0);
        this.setBorderColor(convertedColor);
    }
}
