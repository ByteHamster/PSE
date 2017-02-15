package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
import java.util.EnumMap;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Contains all controls for setting the general settings.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
class GeneralSettings extends ScrollPane {
    /**
     * Used for setting the servers' host name.
     */
    private TextField serverHostname;
    
    /**
     * Used for setting the server ports.
     */
    private EnumMap<TankSelector, Spinner<Integer>> serverPorts;
    
    /**
     * Slider to set the update interval.
     */
    private Slider timeSlider;
    
    /**
     * Shows the update interval as a number.
     */
    private Spinner<Integer> timeBox;
    
    /**
     * Creates a new tab that holds the general settings.
     * 
     * @param currentSettings The current settings used for displaying them.
     */
    protected GeneralSettings(ClientSettingsWrapper currentSettings) {
        serverPorts = new EnumMap<>(TankSelector.class);
        createLayout(currentSettings);
    }
    
    /**
     * Creates the layout for this element.
     * 
     * @param currentSettings The current settings.
     */
    private void createLayout(ClientSettingsWrapper currentSettings) {
        VBox layout = new VBox(MonitoringViewConstants.ELEMENTS_GAP);
        layout.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        Label generalLabel;
        Translator translator = Translator.getInstance();
        
        HBox box = new HBox(MonitoringViewConstants.ELEMENTS_GAP);
        generalLabel = new Label(translator.getString("monitoring.settings.generalSettings.updateInterval"));
        timeSlider = new Slider(MonitoringViewConstants.MIN_UPDATE_INTERVAL,
                MonitoringViewConstants.MAX_UPDATE_INTERVAL,
                currentSettings.getFetchInterval(MonitoringViewConstants.MIN_UPDATE_INTERVAL));
        timeBox = new Spinner<Integer>(MonitoringViewConstants.MIN_UPDATE_INTERVAL,
                MonitoringViewConstants.MAX_UPDATE_INTERVAL, currentSettings.getFetchInterval(500));
        timeBox.setEditable(true);
        timeSlider.valueProperty().bind(timeBox.valueProperty());
        box.getChildren().addAll(generalLabel, timeSlider, timeBox);
        generalLabel = new Label(translator.getString("monitoring.settings.generalSettings.milliseconds"));
        box.getChildren().add(generalLabel);
        layout.getChildren().add(box);
        
        box = new HBox(MonitoringViewConstants.ELEMENTS_GAP);
        generalLabel = new Label(translator.getString("monitoring.settings.generalSettings.serverHost"));
        serverHostname = new TextField(currentSettings.getHostname(TankSelector.MIX, ""));
        box.getChildren().addAll(generalLabel, serverHostname);
        layout.getChildren().add(box);
        
        Spinner<Integer> currentField;
        int defaultPort = 12868;
        for (TankSelector tank : TankSelector.values()) {
            box = new HBox(MonitoringViewConstants.ELEMENTS_GAP);
            generalLabel = new Label(String.format(
                    translator.getString("monitoring.settings.generalSettings.serverPort"), tank.toString()));
            currentField = new Spinner<Integer>(1024, 61000, currentSettings.getPort(tank, defaultPort++));
            currentField.setEditable(true);
            serverPorts.put(tank, currentField);
            box.getChildren().addAll(generalLabel, currentField);
            layout.getChildren().add(box);
        }
        
        this.setContent(layout);
    }
    
    /**
     * Returns the servers' host name.
     * 
     * @return the servers' host name.
     */
    protected String getServerHost() {
        return serverHostname.getText();
    }
    
    /**
     * Returns the port number for a specified server.
     * 
     * @return the port number for the specified server.
     * @param tank Selector of the tank to get the port for.
     */
    protected int getServerPort(TankSelector tank) {
        return serverPorts.get(tank).getValue();
    }
    
    /**
     * Returns the update interval.
     * 
     * @return the update interval.
     */
    protected int getUpdateInterval() {
        return timeBox.getValue();
    }
}
