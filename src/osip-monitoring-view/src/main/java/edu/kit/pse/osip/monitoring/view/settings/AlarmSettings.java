package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
import java.util.EnumMap;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 * Contains all controls for enabling / disabling the alarms in the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.3
 */
class AlarmSettings extends ScrollPane {
    /**
     * All check boxes for disabling / enabling underflow alarms.
     */
    private EnumMap<TankSelector, CheckBox> underflowEnabled;
    
    /**
     * Contains all check boxes for disabling / enabling overflow alarms.
     */
    private EnumMap<TankSelector, CheckBox> overflowEnabled;
    
    /**
     * All check boxes for disabling / enabling overheating alarms for temperatures.
     */
    private EnumMap<TankSelector, CheckBox> temperatureOverheatingEnabled;
    
    /**
     * All check boxes for disabling / enabling the undercooling alarms for the temperature.
     */
    private EnumMap<TankSelector, CheckBox> temperatureUndercoolingEnabled;
    
    /**
     * Creates and initializes all controls regarding alarms for the settings.
     * 
     * @param currentSettings The current settings used to display them.
     */
    protected AlarmSettings(ClientSettingsWrapper currentSettings) {
        underflowEnabled = new EnumMap<TankSelector, CheckBox>(TankSelector.class);
        overflowEnabled = new EnumMap<TankSelector, CheckBox>(TankSelector.class);
        temperatureOverheatingEnabled = new EnumMap<TankSelector, CheckBox>(TankSelector.class);
        temperatureUndercoolingEnabled = new EnumMap<TankSelector, CheckBox>(TankSelector.class);
        createLayout(currentSettings);
    }
    
    /**
     * Creates the layout for this element.
     * 
     * @param currentSettings The current settings.
     */
    private void createLayout(ClientSettingsWrapper currentSettings) {
        Translator translator = Translator.getInstance();
        VBox layout = new VBox(MonitoringViewConstants.ELEMENTS_GAP);
        layout.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        CheckBox currentBox;
        String tankName;
        for (TankSelector tank : TankSelector.values()) {
            tankName = translator.getString(TankSelector.TRANSLATOR_LABEL_PREFIX + tank.name()).toLowerCase();
            currentBox = new CheckBox(String.format(translator.getString("monitoring.settings.alarms.liquidUnderflow"),
                    tankName));
            underflowEnabled.put(tank, currentBox);
            layout.getChildren().add(currentBox);
            currentBox = new CheckBox(String.format(translator.getString("monitoring.settings.alarms.liquidOverflow"),
                    tankName));
            overflowEnabled.put(tank, currentBox);
            layout.getChildren().add(currentBox);
            currentBox = new CheckBox(String.format(
                    translator.getString("monitoring.settings.alarms.temperatureOverheating"), tankName));
            temperatureOverheatingEnabled.put(tank, currentBox);
            layout.getChildren().add(currentBox);
            currentBox = new CheckBox(String.format(
                    translator.getString("monitoring.settings.alarms.temperatureUndercooling"), tankName));
            temperatureUndercoolingEnabled.put(tank, currentBox);
            layout.getChildren().addAll(currentBox, new Separator());
        }
        reset(currentSettings);
        this.setContent(layout);
    }
    
    /**
     * Resets the settings to the current settings.
     * 
     * @param currentSettings the current settings.
     */
    protected void reset(ClientSettingsWrapper currentSettings) {
        for (TankSelector tank : TankSelector.values()) {
            underflowEnabled.get(tank).setSelected(currentSettings.getUnderflowAlarm(tank, true));
            overflowEnabled.get(tank).setSelected(currentSettings.getOverflowAlarm(tank, true));
            temperatureOverheatingEnabled.get(tank).setSelected(currentSettings.getOverheatingAlarm(tank, true));
            temperatureUndercoolingEnabled.get(tank).setSelected(currentSettings.getUndercoolingAlarm(tank, true));
        }
    }
    
    /**
     * Returns true if the underflow alarm for a specified tank should be enabled and false otherwise.
     * 
     * @return true if the underflow alarm for a specified tank should be enabled and false otherwise.
     * @param tank Tank for which should be looked up if the underflow alarm should be enabled or not.
     */
    protected boolean isUnderflowEnabled(TankSelector tank) {
        return underflowEnabled.get(tank).isSelected();
    }
    
    /**
     * Returns true if the overflow alarm for a specified tank should be enabled and false otherwise.
     * 
     * @return true if the overflow alarm for a specified tank should be enabled and false otherwise.
     * @param tank Tank for which should be looked up if the overflow alarm should be enabled or not.
     */
    protected boolean isOverflowEnabled(TankSelector tank) {
        return overflowEnabled.get(tank).isSelected();
    }
    
    /**
     * Returns true if the overheating alarm for the temperature for a specified tank should be enabled
     * and false otherwise.
     * 
     * @return true if the overheating alarm for the temperature for a specified tank should be enabled.
     * false otherwise.
     * @param tank The tank whose value for the temperature overflow alarm will be returned.
     */
    protected boolean isTemperatureOverheatingEnabled(TankSelector tank) {
        return temperatureOverheatingEnabled.get(tank).isSelected();
    }
    
    /**
     * Returns true if the undercooling alarm for the temperature for a specified tank should be enabled
     * and false otherwise.
     * 
     * @return true if the undercooling alarm for the temperature for a specified tank should be enabled.
     * false otherwise.
     * @param tank The tank whose value for enabling / disabling the temperature underflow alarm will be returned.
     */
    protected boolean isTemperatureUndercoolingEnabled(TankSelector tank) {
        return temperatureUndercoolingEnabled.get(tank).isSelected();
    }
}
