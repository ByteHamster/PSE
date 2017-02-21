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
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

/**
 * Contains all controls for setting the general settings.
 * 
 * @author Martin Armbruster
 * @version 1.3
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
    private Spinner<Number> timeBox;
    
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
        Label generalLabel;
        Translator translator = Translator.getInstance();
        
        GridPane layout = new GridPane();
        layout.setHgap(MonitoringViewConstants.ELEMENTS_GAP);
        layout.setVgap(MonitoringViewConstants.ELEMENTS_GAP);
        layout.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        
        generalLabel = new Label(translator.getString("monitoring.settings.generalSettings.updateInterval"));
        timeSlider = new Slider(MonitoringViewConstants.MIN_UPDATE_INTERVAL,
                MonitoringViewConstants.MAX_UPDATE_INTERVAL,
                currentSettings.getFetchInterval(MonitoringViewConstants.DEFAULT_UPDATE_INTERVAL));
        timeSlider.setMajorTickUnit((MonitoringViewConstants.MAX_UPDATE_INTERVAL
                - MonitoringViewConstants.MIN_UPDATE_INTERVAL) / MonitoringViewConstants.NUMBER_OF_MAJOR_TICKS);
        timeSlider.setMinorTickCount(MonitoringViewConstants.NUMBER_OF_MINOR_TICKS);
        timeSlider.setShowTickMarks(true);
        timeSlider.setShowTickLabels(true);
        timeSlider.setPrefWidth(MonitoringViewConstants.PREF_HEIGHT_FOR_BARS);
        // ** Solution for: Slider doesn't show integer-only values.
        // Based on: http://stackoverflow.com/questions/38681664/javafx-slider-integer-only
        timeSlider.valueProperty().addListener((obs, oldval, newVal) -> timeSlider.setValue(newVal.intValue()));
        // **
        layout.add(generalLabel, 0, 0);
        layout.add(timeSlider, 1, 0);
        
        timeBox = new Spinner<Number>((double) MonitoringViewConstants.MIN_UPDATE_INTERVAL,
                MonitoringViewConstants.MAX_UPDATE_INTERVAL,
                currentSettings.getFetchInterval(MonitoringViewConstants.DEFAULT_UPDATE_INTERVAL));
        timeBox.setEditable(true);
        timeBox.setPrefWidth(MonitoringViewConstants.PREF_HEIGHT_FOR_BARS);
        timeBox.getValueFactory().valueProperty().bindBidirectional(timeSlider.valueProperty());
        // ** Solution for: When the timeBox loses focus, the typed-in value will be taken.
        // Based on http://stackoverflow.com/questions/32340476
        //          /manually-typing-in-text-in-javafx-spinner-is-not-updating-the-value-unless-user
        TextFormatter<Number> formatter = new TextFormatter<>(timeBox.getValueFactory().getConverter(),
                timeBox.getValueFactory().getValue());
        timeBox.getEditor().setTextFormatter(formatter);
        formatter.valueProperty().bindBidirectional(timeBox.getValueFactory().valueProperty());
        // **
        generalLabel = new Label(translator.getString("monitoring.settings.generalSettings.milliseconds"));
        layout.add(timeBox, 1, 1);
        layout.add(generalLabel, 2, 1);
        
        generalLabel = new Label(translator.getString("monitoring.settings.generalSettings.serverHost"));
        serverHostname = new TextField(currentSettings.getHostname(TankSelector.MIX, "localhost"));
        layout.add(generalLabel, 0, 2);
        layout.add(serverHostname, 1, 2);
        
        Spinner<Integer> currentField;
        int defaultPort = 12868;
        int row = 3;
        for (TankSelector tank : TankSelector.values()) {
            generalLabel = new Label(String.format(
                    translator.getString("monitoring.settings.generalSettings.serverPort"),
                    translator.getString(TankSelector.TRANSLATOR_LABEL_PREFIX + tank.name()).toLowerCase()));
            currentField = new Spinner<Integer>(MonitoringViewConstants.MIN_PORT, MonitoringViewConstants.MAX_PORT,
                    currentSettings.getPort(tank, defaultPort++));
            currentField.setEditable(true);
            currentField.setPrefWidth(MonitoringViewConstants.PREF_HEIGHT_FOR_BARS);
            serverPorts.put(tank, currentField);
            layout.add(generalLabel, 0, row);
            layout.add(currentField, 1, row++);
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
        return timeBox.getValue().intValue();
    }
}
