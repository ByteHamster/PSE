package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.EnumMap;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Platform;

/**
 * The entry point for the monitoring view. It shows all sensor datas graphically.
 * 
 * @author Martin Armbruster
 * @version 1.5
 */
class MonitoringMainWindow {
    /**
     * The menu bar for the monitoring.
     */
    private MonitoringMenu menu;
    
    /**
     * The visualizations for the tanks.
     */
    private EnumMap<TankSelector, TankVisualization> tankVisualizations;
    
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
     * Width for the window.
     */
    private static final int WIDTH = 1000;
    
    /**
     * Height for the window.
     */
    private static final int HEIGHT = 700;
    
    /**
     * Initializes the window.
     * 
     * @param primaryStage The primary window.
     * @param currentModel The current model used to initialize all observable objects and initial values.
     */
    protected MonitoringMainWindow(Stage primaryStage, ProductionSite currentModel) {
        menu = new MonitoringMenu();
        tankVisualizations = new EnumMap<TankSelector, TankVisualization>(TankSelector.class);
        for (TankSelector selector : TankSelector.valuesWithoutMix()) {
            tankVisualizations.put(selector, new TankVisualization(currentModel.getUpperTank(selector)));
        }
        mixTank = new MixTankVisualization(currentModel.getMixTank());
        new AnimationTimer() {
            private long lastIntervalBegin;
            
            @Override
            public void handle(long now) {
                if (now - lastIntervalBegin > 1000 * 1000000) {
                    lastIntervalBegin = now;
                    for (TankSelector tank : TankSelector.valuesWithoutMix()) {
                        tankVisualizations.get(tank).updateProgressions();
                    }
                    mixTank.updateProgressions();
                }
            }
        }.start();
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
        GridPane tankPane = new GridPane();
        tankPane.setVgap(MonitoringViewConstants.ELEMENTS_GAP);
        tankPane.setHgap(MonitoringViewConstants.ELEMENTS_GAP);
        tankPane.setPadding(new Insets(MonitoringViewConstants.ELEMENTS_GAP));
        int currentColumn = 0;
        for (TankVisualization tank : tankVisualizations.values()) {
            tankPane.add(tank, currentColumn, 0);
            currentColumn++;
        }
        tankPane.add(mixTank, currentColumn, 0);
        
        HBox hbox = new HBox();
        hbox.setSpacing(MonitoringViewConstants.ELEMENTS_GAP);
        hbox.getChildren().addAll(log, light);
        tankPane.add(hbox, 0, 1, currentColumn + 1, 1);
        
        BorderPane mainPane = new BorderPane() {
            @Override
            public void resize(double width, double height) {
                log.setPrefWidth(width);
                log.setPrefHeight(0.2 * height);
                double prefMinWidth = 0.1 * width;
                double prefMinHeight = (0.2 * height) / 2;
                double realSize = Math.min(prefMinWidth, prefMinHeight);
                light.setMinSize(realSize, realSize * 2);
                super.resize(width, height);
            }
        };
        mainPane.setStyle("-fx-font-size: " + MonitoringViewConstants.FONT_SIZE + "px;");
        mainPane.setTop(menu);
        mainPane.setCenter(tankPane);
        
        Scene scene = new Scene(mainPane);
        primaryStage.setMaximized(true);
        primaryStage.setTitle(Translator.getInstance().getString("monitoring.title"));
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setScene(scene);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.show();
    }
    
    /**
     * Registers all alarms to the light.
     */
    private void registerAlarms() {
        for (TankVisualization tank : tankVisualizations.values()) {
            tank.getOverflowAlarm().addObserver(light);
            tank.getOverflowAlarm().addObserver(log);
            tank.getUnderflowAlarm().addObserver(light);
            tank.getUnderflowAlarm().addObserver(log);
            tank.getTemperatureOverheatingAlarm().addObserver(light);
            tank.getTemperatureOverheatingAlarm().addObserver(log);
            tank.getTemperatureUndercoolingAlarm().addObserver(light);
            tank.getTemperatureUndercoolingAlarm().addObserver(log);
        }
        mixTank.getOverflowAlarm().addObserver(light);
        mixTank.getOverflowAlarm().addObserver(log);
        mixTank.getUnderflowAlarm().addObserver(light);
        mixTank.getUnderflowAlarm().addObserver(log);
        mixTank.getTemperatureOverheatingAlarm().addObserver(light);
        mixTank.getTemperatureOverheatingAlarm().addObserver(log);
        mixTank.getTemperatureUndercoolingAlarm().addObserver(light);
        light.setAlarmCount((tankVisualizations.size() + 1) * 4);
    }
    
    /**
     * Returns the current used visualization for a specified tank.
     * 
     * @return The current used visualization of a specified tank.
     * @param tank The tank whose current used visualization should be returned.
     */
    protected AbstractTankVisualization getTank(TankSelector tank) {
        if (tank == TankSelector.MIX) {
            return mixTank;
        }
        return tankVisualizations.get(tank);
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
