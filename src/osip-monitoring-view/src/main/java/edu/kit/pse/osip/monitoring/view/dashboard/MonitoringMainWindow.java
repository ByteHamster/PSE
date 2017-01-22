package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.TankSelector;

/**
 * The entry point for the monitoring view. It shows all sensor datas graphically.
 */
public class MonitoringMainWindow {
    public MixTankVisualization mixTank;
    public TankVisualization tanks;
    public Light light;
    public LoggingConsole log;
    public MonitoringMenu menu;
    /**
     * Initializes the window.
     * @param primaryStage The primary window.
     */
    protected MonitoringMainWindow (javafx.stage.Stage primaryStage) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the current used visualization for a specified tank.
     * @return The current used visualization of a specified tank.
     * @param tank The tank whose current used visualization should be returned.
     */
    protected final AbstractTankVisualization getTank (TankSelector tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the current used menu for the monitoring view.
     * @return the current used menu for the monitoring view.
     */
    protected final MonitoringMenu getMonitoringMenu () {
        throw new RuntimeException("Not implemented!");
    }
}
