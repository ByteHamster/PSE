package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * Contains all controls for enabling / disabling the alarms in the monitoring view.
 */
public class AlarmSettings extends javafx.scene.control.ScrollPane {
    /**
     * All check boxes for disabling / enabling underflow alarms.
     */
    private java.util.Collection<javafx.scene.control.CheckBox> underflowEnabled;
    /**
     * Contains all check boxes for disabling / enabling overflow alarms.
     */
    private java.util.Collection<javafx.scene.control.CheckBox> overflowEnabled;
    /**
     * All check boxes for disabling / enabling overflow alarms for temperatures.
     */
    private java.util.Collection<javafx.scene.control.CheckBox> temperatureOverflowEnabled;
    /**
     * All check boxes for disabling / enabling the underflow alarms for the temperature.
     */
    private java.util.Collection<javafx.scene.control.CheckBox> temperatureUnderflowEnabled;
    /**
     * Creates and initializes all controls regarding alarms for the settings.
     * @param currentSettings The current settings used to display them.
     */
    protected AlarmSettings (ClientSettingsWrapper currentSettings) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the underflow alarm for a specified tank should be enabled and false otherwise.
     * @return true if the underflow alarm for a specified tank should be enabled and false otherwise.
     * @param tank Tank for which should be looked up if the underflow alarm should be enabled or not.
     */
    protected final boolean isUnderflowEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the overflow alarm for a specified tank should be enabled and false otherwise.
     * @return true if the overflow alarm for a specified tank should be enabled and false otherwise.
     * @param tank Tank for which should be looked up if the overflow alarm should be enabled or not.
     */
    protected final boolean isOverflowEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the overflow alarm for the temperature for a specified tank should be enabled and false otherwise.
     * @return true if the overflow alarm for the temperature for a specified tank should be enabled. false otherwise.
     * @param tank The tank whose value for the temperature overflow alarm will be returned.
     */
    protected final boolean isTemperatureOverflowEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns true if the underflow alarm for the temperature for a specified tank should be enabled and false otherwise.
     * @return true if the overflow alarm for the temperature for a specified tank should be enabled. false otherwise.
     * @param tank The tank whose value for enabling / disabling the temperature underflow alarm will be returned.
     */
    protected final boolean isTemperatureUnderflowEnabled (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
}
