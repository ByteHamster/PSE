package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.MixTank;
import javafx.scene.paint.Color;

/**
 * Visualization for the mixtank.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
final class MixTankVisualization extends AbstractTankVisualization {
    /**
     * Visualization of the motor speed.
     */
    private GaugeVisualization motorSpeed;
    
    /**
     * Visualization of the mixed color.
     */
    private ColorVisualization color;
    
    /**
     * Creates a new visualization.
     * 
     * @param tank The tank to display
     */
    protected MixTankVisualization(MixTank tank) {
        super(tank);
        this.setBorderColor(Color.BLACK);
        motorSpeed = new MotorSpeedVisualization();
        this.add(motorSpeed, 0, 4);
        color = new ColorVisualization();
        this.add(color, 1, 4);
        this.add(drain, 0, 5);
        this.add(temperature, 1, 5);
        this.add(progresses, 0, 6, 2, 1);
        tank.addObserver(color);
        tank.getMotor().addObserver(motorSpeed);
    }
}
