package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.EnumMap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The entry point for the monitoring view. It shows all sensor datas graphically.
 * 
 * @author Martin Armbruster
 * @version 1.1
 */
class MonitoringMainWindow {
    /**
     * The menu bar for the monitoring.
     */
    private MonitoringMenu menu;
    
    /**
     * The visualizations for the tanks.
     */
    private EnumMap<TankSelector, TankVisualization> tanks;
    
    /**
     * The visualization for the mixtank.
     */
    private MixTankVisualization mixTank;
    
    /**
     * The logging console.
     */
    private LoggingConsole log;
    
    /**
     * The traffic light to show the over all status of the alarms.
     */
    private Light light;
    
    /**
     * Initializes the window.
     * 
     * @param primaryStage The primary window.
     * @param currentModel The current model used to initialize all observable objects and initial values.
     */
    protected MonitoringMainWindow(Stage primaryStage, ProductionSite currentModel) {
        menu = new MonitoringMenu();
        tanks = new EnumMap<TankSelector, TankVisualization>(TankSelector.class);
        for (TankSelector selector : TankSelector.valuesWithoutMix()) {
            tanks.put(selector, new TankVisualization(currentModel.getUpperTank(selector)));
        }
        mixTank = new MixTankVisualization(currentModel.getMixTank());
        log = new LoggingConsole();
        light = new Light();
        makeLayout(primaryStage);
        registerAlarms();
    }
    
    /**
     * Does all relevant layout things.
     * 
     * @param primaryStage stage on which all elements will be shown.
     */
    private void makeLayout(Stage primaryStage) {
        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-font-size: " + MonitoringViewConstants.FONT_SIZE + "px;");
        mainPane.setTop(menu);
        
        GridPane tankPane = new GridPane();
        tankPane.setVgap(MonitoringViewConstants.ELEMENTS_GAP);
        tankPane.setHgap(MonitoringViewConstants.ELEMENTS_GAP);
        tankPane.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        int currentColumn = 0;
        for (TankVisualization tank : tanks.values()) {
            tankPane.add(tank, currentColumn, 0);
            currentColumn++;
        }
        tankPane.add(mixTank, currentColumn, 0);
        mainPane.setCenter(tankPane);
        
        HBox hbox = new HBox();
        hbox.setSpacing(MonitoringViewConstants.ELEMENTS_GAP);
        hbox.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        hbox.getChildren().addAll(log, light);
        mainPane.setBottom(hbox);
        
        Scene scene = new Scene(mainPane);
        primaryStage.setMaximized(true);
        primaryStage.setTitle(Translator.getInstance().getString("monitoring.title"));
        primaryStage.setScene(scene);
        primaryStage.show();
        
        log.setPrefWidth(0.95 * mainPane.getWidth());
        light.setMinWidth(0.05 * mainPane.getWidth());
        light.setPrefHeight(log.getPrefHeight());
    }
    
    /**
     * Registers all alarms to the light.
     */
    private void registerAlarms() {
        for (TankVisualization tank : tanks.values()) {
            tank.getOverflowAlarm().addObserver(light);
            tank.getUnderflowAlarm().addObserver(light);
            tank.getTemperatureOverheatingAlarm().addObserver(light);
            tank.getTemperatureUndercoolingAlarm().addObserver(light);
        }
        mixTank.getOverflowAlarm().addObserver(light);
        mixTank.getUnderflowAlarm().addObserver(light);
        mixTank.getTemperatureOverheatingAlarm().addObserver(light);
        mixTank.getTemperatureUndercoolingAlarm().addObserver(light);
    }
    
    /**
     * Returns the current used visualization for a specified tank.
     * 
     * @return The current used visualization of a specified tank.
     * @param tank The tank whose current used visualization should be returned.
     */
    protected AbstractTankVisualization getTank(TankSelector tank) {
        return tanks.get(tank);
    }
    
    /**
     * Returns the current used menu for the monitoring view.
     * 
     * @return the current used menu for the monitoring view.
     */
    protected MonitoringMenu getMonitoringMenu() {
        return menu;
    }
}
