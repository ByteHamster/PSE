package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Contains all controls for setting the general settings.
 */
public class GeneralSettings extends javafx.scene.control.ScrollPane {
    /**
     * Used for setting the servers' host name.
     */
    private javafx.scene.control.TextField serverHostname;
    /**
     * Used for setting the server ports.
     */
    private java.util.Collection<javafx.scene.control.TextField> serverPorts;
    /**
     * Slider to set the update interval.
     */
    private javafx.scene.control.Slider timeSlider;
    /**
     * Shows the update interval as a number.
     */
    private javafx.scene.control.TextField timeBox;
    /**
     * Creates a new tab that holds the general settings
     * @param currentSettings The current settings used for displaying them.
     */
    protected GeneralSettings (ClientSettingsWrapper currentSettings) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the servers' hostname.
     * @return the servers' host name.
     */
    protected final String getServerHost () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the port number for a specified server.
     * @return the port number for the specified server.
     * @param tank Selector of the tank of which's server to get the port for
     */
    protected final int getServerPort (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the update interval.
     * @return the update interval.
     */
    protected final int getTime () {
        throw new RuntimeException("Not implemented!");
    }
}
