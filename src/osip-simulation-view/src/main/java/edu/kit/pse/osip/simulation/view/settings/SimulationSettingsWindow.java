package edu.kit.pse.osip.simulation.view.settings;

import edu.kit.pse.osip.core.io.files.ServerSettingsWrapper;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.formatting.InvalidPortException;
import edu.kit.pse.osip.simulation.controller.SettingsChangedListener;

/**
 * This class is the main window of the OSIP simulation settings.
 */
public class SimulationSettingsWindow  extends javafx.stage.Stage implements edu.kit.pse.osip.simulation.controller.SimulationSettingsWindow {
    private PortTextField portField;
    /**
     * Generates a new window that shows the simulation settings
     * @param settings 
     * @param productionSite The ProductionSite, to get all the tanks
     */
    public SimulationSettingsWindow (ServerSettingsWrapper settings, ProductionSite productionSite) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets he current value of the jitter-scrollbar.
     * @return The current value of the jitter-scrollbar.
     */
    public final int getJitter () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Gets the port number in Porttextfield id.
     * @throws InvalidPortException Thrown if the current value in port is not valid (see FormatChecker.parsePort(String port).
     * @return The port number in Porttextfield id.
     * @param tank 
     */
    public final int getPort (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Sets the listener that gets notified as soon as the settings change
     * @param listener 
     */
    public final void setSettingsChangedListener (SettingsChangedListener listener) {
        throw new RuntimeException("Not implemented!");
    }
}
