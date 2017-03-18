package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.AlarmGroup;
import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import edu.kit.pse.osip.monitoring.controller.MonitoringViewInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.util.EnumMap;

/**
 * Provides one single access point to the monitoring view.
 * 
 * @author Martin Armbruster
 * @version 1.2
 */
public final class MonitoringViewFacade implements MonitoringViewInterface {
    /**
     * Saves the current used main window. All method invocations are directed to this object.
     */
    private MonitoringMainWindow mainWindow;
    
    @Override
    public void showMonitoringView(Stage stage, ProductionSite currentModel, EnumMap<TankSelector,
        AlarmGroup<ObservableBoolean, ObservableBoolean>> alarmGroup) {
        mainWindow = new MonitoringMainWindow(stage, currentModel, alarmGroup);
    }
    
    @Override
    public void setUnderflowAlarmEnabled(TankSelector tank, boolean alarmEnabled) {
        mainWindow.getTank(tank).setUnderflowAlarmEnabled(alarmEnabled);
    }
    
    @Override
    public void setOverflowAlarmEnabled(TankSelector tank, boolean alarmEnabled) {
        mainWindow.getTank(tank).setOverflowAlarmEnabled(alarmEnabled);
    }
    
    @Override
    public void setTemperatureOverheatingAlarmEnabled(TankSelector tank, boolean alarmEnabled) {
        mainWindow.getTank(tank).setTemperatureOverheatingAlarmEnabled(alarmEnabled);
    }
    
    @Override
    public void setTemperatureUndercoolingAlarmEnabled(TankSelector tank, boolean alarmEnabled) {
        mainWindow.getTank(tank).setTemperatureUndercoolingAlarmEnabled(alarmEnabled);
    }
    
    @Override
    public void setFillLevelProgressionEnabled(TankSelector tank, boolean progressionEnabled) {
        mainWindow.getTank(tank).setFillLevelProgressionEnabled(progressionEnabled);
    }
    
    @Override
    public void setTemperatureProgressionEnabled(TankSelector tank, boolean progressionEnabled) {
        mainWindow.getTank(tank).setTemperatureProgressionEnabled(progressionEnabled);
    }
    
    @Override
    public void setMenuSettingsButtonHandler(EventHandler<ActionEvent> handler) {
        mainWindow.getMonitoringMenu().setMenuSettingsButtonHandler(handler);
    }
    
    @Override
    public void setMenuAboutButtonHandler(EventHandler<ActionEvent> handler) {
        mainWindow.getMonitoringMenu().setMenuAboutButtonHandler(handler);
    }
    
    @Override
    public void setMenuHelpButtonHandler(EventHandler<ActionEvent> handler) {
        mainWindow.getMonitoringMenu().setMenuHelpButtonHandler(handler);
    }

    @Override
    public void setProgressIndicatorVisible(boolean visible) {
        mainWindow.setProgressIndicatorVisible(visible);
    }
}
