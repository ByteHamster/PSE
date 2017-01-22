package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Controls all controls for disabling / enabling the progressions in the monitoring view.
 */
public class Progressions extends javafx.scene.control.ScrollPane {
    /**
     * All check boxes for disabling / enabling the logging of the fill level progressions.
     */
    private java.util.Collection<javafx.scene.control.CheckBox> fillLevelsEnabled;
    /**
     * All check boxes for disabling / enabling the logging of the temperature progressions.
     */
    private java.util.Collection<javafx.scene.control.CheckBox> temperaturesEnabled;
    /**
     * Creates and initializes all controls regarding the progressions for the settings.
     * @param currentSettings The current settings used for displaying the current state.
     */
    protected Progressions (ClientSettingsWrapper currentSettings) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the fill level progression should be enabled for a specified tank and false otherwise.
     * @return
     * true if the fill level progression should be enabled for the specified tank or false otherwise.
     * @param tank The tank whose value will be returned.
     */
    protected final boolean isFillLevelEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the temperature pogression should be enabled for a specified tank and false otherwise.
     * @return
     * true if the temperature progression should be enabled for the specified tank. false otherwise.
     * @param tank The tank whose value for enabling / disabling the temperature progression will be returned.
     */
    protected final boolean isTemperatureEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
}
