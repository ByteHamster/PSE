package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
import java.util.EnumMap;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import jfxtras.scene.layout.VBox;

/**
 * Controls all controls for disabling / enabling the progressions in the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
class Progressions extends ScrollPane {
    /**
     * All check boxes for disabling / enabling the logging of the fill level progressions.
     */
    private EnumMap<TankSelector, CheckBox> fillLevelsEnabled;
    
    /**
     * All check boxes for disabling / enabling the logging of the temperature progressions.
     */
    private EnumMap<TankSelector, CheckBox> temperaturesEnabled;
    
    /**
     * Creates and initializes all controls regarding the progressions for the settings.
     * 
     * @param currentSettings The current settings used for displaying the current state.
     */
    protected Progressions(ClientSettingsWrapper currentSettings) {
        fillLevelsEnabled = new EnumMap<>(TankSelector.class);
        temperaturesEnabled = new EnumMap<>(TankSelector.class);
        createLayout(currentSettings);
    }
    
    /**
     * Creates the layout for this element.
     * 
     * @param currentSettings The current settings.
     */
    private void createLayout(ClientSettingsWrapper currentSettings) {
        VBox root = new VBox(MonitoringViewConstants.ELEMENTS_GAP);
        root.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        CheckBox currentBox;
        for (TankSelector tank : TankSelector.values()) {
            currentBox = new CheckBox(String.format(
                    Translator.getInstance().getString("monitoring.settings.progressions.fillLevel"), tank.toString()));
            currentBox.setSelected(currentSettings.getFillLevelDiagram(tank, true));
            root.getChildren().add(currentBox);
            currentBox = new CheckBox(String.format(
                    Translator.getInstance().getString("monitoring.settings.progressions.temperature"),
                    tank.toString()));
            currentBox.setSelected(currentSettings.getTempDiagram(tank, true));
            root.getChildren().add(currentBox);
        }
        this.setContent(root);
    }
    
    /**
     * Returns true if the fill level progression should be enabled for a specified tank and false otherwise.
     * 
     * @return true if the fill level progression should be enabled for the specified tank or false otherwise.
     * @param tank The tank whose value will be returned.
     */
    protected boolean isFillLevelEnabled(TankSelector tank) {
        return fillLevelsEnabled.get(tank).isSelected();
    }
    
    /**
     * Returns true if the temperature pogression should be enabled for a specified tank and false otherwise.
     * 
     * @return true if the temperature progression should be enabled for the specified tank. false otherwise.
     * @param tank The tank whose value for enabling / disabling the temperature progression will be returned.
     */
    protected boolean isTemperatureEnabled(TankSelector tank) {
        return temperaturesEnabled.get(tank).isSelected();
    }
}
