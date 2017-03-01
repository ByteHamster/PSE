package edu.kit.pse.osip.core;

/**
 * Some constants which are used in multiple places but don't belong into the model.
 * @version 1.0
 * @author David Kahles
 */
public final class OSIPConstants {
    private OSIPConstants() {

    }

    /**
     * The default port of the mix tank. The default ports of the other tanks are the ports following this port, in the
     * same order as in TankSelector.
     */
    public static final int DEFAULT_PORT_MIX = 12868;
}
