package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.MixTank;
import edu.kit.pse.osip.core.model.behavior.AlarmGroup;
import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import javafx.scene.paint.Color;

/**
 * Visualization for the mixtank.
 * 
 * @author Martin Armbruster
 * @version 1.3
 */
class MixTankVisualization extends AbstractTankVisualization {
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
     * @param tank The tank to display.
     * @param alarms The alarms of the tank.
     * @throws NullPointerException when the tank or the alarms are null.
     */
    protected MixTankVisualization(MixTank tank, AlarmGroup<ObservableBoolean, ObservableBoolean> alarms) {
        super(tank, alarms);
        this.setBorderColor(Color.BLACK);
        motorSpeed = new MotorSpeedVisualization();
        color = new ColorVisualization();
        alarmPane.getChildren().add(color);
        this.add(motorSpeed, 0, 1);
        this.add(drain, 1, 1);
        this.add(fillLevel, 0, 2);
        this.add(temperature, 1, 2);
        this.add(progresses, 0, 3, 2, 1);
        tank.addObserver(color);
        tank.getMotor().addObserver(motorSpeed);
    }
}
