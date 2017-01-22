package edu.kit.pse.osip.core.model.base;

/**
 * This is a mixtank. The difference to AbstractTank is the presence of the motor, which mixes the liquids.
 */
public class MixTank extends edu.kit.pse.osip.core.model.base.AbstractTank {
    public Motor motor;
    /**
     * Create a new mixtank.
     * @param capacity The capacity of the tank in cm³.
     * @param liquid The default content of the tank.
     * @param outPipe The outgoing pipe.
     */
    public MixTank (float capacity, Liquid liquid, Pipe outPipe) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Get the motor attached to the tank
     * @return the motor
     */
    public final Motor getMotor () {
        throw new RuntimeException("Not implemented!");
    }
}
