package edu.kit.pse.osip.core;

/**
 * Some constants which are used in multiple places, but don't belong to the model.
 * 
 * @version 1.1
 * @author David Kahles
 */
public final class OSIPConstants {
    /**
     * Private constructor to avoid instantiation.
     */
    private OSIPConstants() {
    }
    
    /**
     * The minimum server port.
     */
    public static final int MIN_PORT = 1024;

    /**
     * The default port of the mix tank. The default ports of the other tanks are the ports following this port, in the
     * same order as in TankSelector.
     */
    public static final int DEFAULT_PORT_MIX = 12868;
    
    /**
     * The maximum server port.
     */
    public static final int MAX_PORT = 61000;
}
