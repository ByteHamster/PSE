package edu.kit.pse.osip.monitoring.view.settings;

import edu.kit.pse.osip.core.io.files.ClientSettingsWrapper;
import edu.kit.pse.osip.core.utils.language.Translator;
import edu.kit.pse.osip.monitoring.view.dashboard.MonitoringViewConstants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The main entry point for the settings view. Within, the user can set all available parameters for his needs.
 * 
 * @author Martin Armbruster
 * @version 1.4
 */
class SettingsMainWindow {
    private static final int MIN_WINDOW_WIDTH = 500;
    private static final int MIN_WINDOW_HEIGHT = 300;
    /**
     * The window in which the settings are presented.
     */
    private Stage window;
    
    /**
     * The used settings.
     */
    private ClientSettingsWrapper currentSettings;
    
    /**
     * Pane containing tabs with the different settings possibilities.
     */
    private TabPane tabsPane;
    
    /**
     * Tab for showing all general settings.
     */
    private Tab tabGeneralSettings;
    
    /**
     * Contains the ui elements for the general settings.
     */
    private GeneralSettings generalSettingsTab;
    
    /**
     * Tab for showing all settings regarding the progressions.
     */
    private Tab tabProgressions;
    
    /**
     * Contains all ui elements for the progressions tab.
     */
    private Progressions progressionsTab;
    
    /**
     * Tab for showing all settings regarding alarms.
     */
    private Tab tabAlarms;
    
    /**
     * Contains the ui elements for the alarm settings.
     */
    private AlarmSettings alarmsTab;
    
    /**
     * The button to cancel the setting of the settings.
     */
    private Button buttonCancel;
    
    /**
     * The button to save the current settings.
     */
    private Button buttonSave;
    
    /**
     * Creates all controls for displaying and setting the settings. It shows also the window.
     * 
     * @param currentSettings The current settings used for displaying.
     */
    protected SettingsMainWindow(ClientSettingsWrapper currentSettings) {
        this.currentSettings = currentSettings;
        generalSettingsTab = new GeneralSettings(currentSettings);
        alarmsTab = new AlarmSettings(currentSettings);
        progressionsTab = new Progressions(currentSettings);
        createLayout();
    }
    
    /**
     * Creates the layout for the settings view.
     */
    private void createLayout() {
        window = new Stage();
        Translator translator = Translator.getInstance();
        
        tabsPane = new TabPane();
        tabsPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabsPane.setPrefHeight(MonitoringViewConstants.PREF_SIZE_FOR_BARS * 1.5);
        tabGeneralSettings = new Tab(translator.getString("monitoring.settings.tab.generalSettings"));
        tabGeneralSettings.setContent(generalSettingsTab);
        tabAlarms = new Tab(translator.getString("monitoring.settings.tab.alarms"));
        tabAlarms.setContent(alarmsTab);
        tabProgressions = new Tab(translator.getString("monitoring.settings.tab.progressions"));
        tabProgressions.setContent(progressionsTab);
        tabsPane.getTabs().addAll(tabGeneralSettings, tabAlarms, tabProgressions);
        
        HBox buttons = new HBox(MonitoringViewConstants.ELEMENTS_GAP);
        buttons.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttonCancel = new Button(translator.getString("monitoring.settings.button.cancel"));
        buttonCancel.setCancelButton(true);
        buttonSave = new Button(translator.getString("monitoring.settings.button.save"));
        buttonSave.setDefaultButton(true);
        buttonSave.disableProperty().bind(generalSettingsTab.invalidHostnameProperty());
        buttons.getChildren().addAll(buttonCancel, buttonSave);
        
        BorderPane generalLayout = new BorderPane();
        generalLayout.setStyle("-fx-font-size: " + MonitoringViewConstants.FONT_SIZE + "px;");
        generalLayout.setCenter(tabsPane);
        generalLayout.setBottom(buttons);
        Scene scene = new Scene(generalLayout);
        window.setTitle(translator.getString("monitoring.settings.title"));
        window.getIcons().add(new Image("icon.png"));
        window.setOnCloseRequest(event -> {
            if (buttonCancel.isDisabled()) {
                showSettingsClosedWarning();
            }
        });
        window.setScene(scene);
        window.setMinWidth(MIN_WINDOW_WIDTH);
        window.setMinHeight(MIN_WINDOW_HEIGHT);
    }
    
    /**
     * Shows a warning that the settings window was closed without saving valid settings.
     */
    private void showSettingsClosedWarning() {
        Translator translator = Translator.getInstance();
        Alert alert = new Alert(AlertType.WARNING);        
        alert.setTitle(translator.getString("monitoring.settings.closewarning.title"));
        alert.setHeaderText(translator.getString("monitoring.settings.closewarning.header"));
        alert.setContentText(translator.getString("monitoring.settings.closewarning.text"));
        alert.show();
    }
    
    /**
     * Returns the used window.
     * 
     * @return the current used window.
     */
    protected Stage getStage() {
        return window;
    }
    
    /**
     * Resets the settings when no settings are saved.
     */
    protected void reset() {
        tabsPane.getSelectionModel().clearAndSelect(0);
        generalSettingsTab.reset(currentSettings);
        alarmsTab.reset(currentSettings);
        progressionsTab.reset(currentSettings);
    }
    
    /**
     * Returns the current used GeneralSettings object for the "General Settings" tab.
     * 
     * @return the current used GeneralSettings object.
     */
    protected GeneralSettings getGeneralSettings() {
        return generalSettingsTab;
    }
    
    /**
     * Returns the current used Progressions object containing all controls for the "Progressions" tab.
     * 
     * @return the current used Progressions object.
     */
    protected Progressions getProgressions() {
        return progressionsTab;
    }
    
    /**
     * Returns the current AlarmSettings object used for the content of the "Alarm" tab.
     * 
     * @return the current used AlarmSettings object.
     */
    protected AlarmSettings getAlarmSettings() {
        return alarmsTab;
    }
    
    /**
     * Sets the disabled status of the cancel button.
     * 
     * @param disabled The boolean value for the disabled status. True if disabled.
     */
    protected void setCancelButtonDisabled(boolean disabled) {
        buttonCancel.setDisable(disabled);
    }
    
    /**
     * Sets the handler for the cancel button in the settings view.
     * 
     * @param handler The handler for the cancel button in the settings view.
     */
    protected void setSettingsCancelButtonHandler(EventHandler<ActionEvent> handler) {
        buttonCancel.setOnAction(handler);
    }
    
    /**
     * Sets the handler for the save button in the settings view.
     * 
     * @param handler The handler for the save button int eh settings view.
     */
    protected void setSettingsSaveButtonHandler(EventHandler<ActionEvent> handler) {
        buttonSave.setOnAction(handler);
    }
}
