package edu.kit.pse.osip.core.model.base;

/**
 * This is a mixtank. The difference to AbstractTank is the presence of the motor, which mixes the liquids.
 * 
 * @author David Kahles
 * @version 1.0
 */
public class MixTank extends AbstractTank {
    /**
     * The motor.
     */
    private Motor motor;

    /**
     * Creats a new mixtank.
     * 
     * @param capacity The capacity of the tank in cm³.
     * @param liquid The default content of the tank.
     * @param outPipe The outgoing pipe.
     */
    public MixTank(float capacity, Liquid liquid, Pipe outPipe) {
        super(capacity, TankSelector.MIX, liquid, outPipe);
        motor = new Motor(0);
    }

    /**
     * Gets the motor attached to the tank.
     * 
     * @return the motor.
     */
    public Motor getMotor() {
        return motor;
    }

    @Override
    public synchronized void reset() {
        super.reset();
        motor.reset();
    }
}
