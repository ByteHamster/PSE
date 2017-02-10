package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.Motor;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;

/**
 * Visualizes the motor speed.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
final class MotorSpeedVisualization extends GaugeVisualization {
    /**
     * Creates a new visualization for the motor speed.
     */
    protected MotorSpeedVisualization() {
        super(Translator.getInstance().getString("monitoring.mixTank.motorSpeed"));
        gauge.setMaxValue(SimulationConstants.MAX_MOTOR_SPEED);
    }
    
    @Override
    public void update(Observable observable, Object object) {
        Motor motor = (Motor) observable;
        gauge.setValue(motor.getRPM());
    }
}
