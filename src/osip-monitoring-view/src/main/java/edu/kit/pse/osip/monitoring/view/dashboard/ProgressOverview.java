package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Provides access to and all controls for the temperature and fill level progressions.
 * 
 * @author Martin Armbruster
 * @version 1.1
 */
class ProgressOverview extends TabPane {
    /**
     * Tab contains the temperature progression.
     */
    private Tab tabTemperature;
    
    /**
     * The actual temperature progression.
     */
    private ProgressVisualization temperature;
    
    /**
     * Tab contains the fill level progression.
     */
    private Tab tabFillLevel;
    
    /**
     * The actual fill level progression.
     */
    private ProgressVisualization fillLevel;
    
    /**
     * Creates and initializes all controls for the progressions.
     * 
     * @param tank The progressions are assigned to this tank.
     * @throws NullPointerException when the tank is null.
     */
    protected ProgressOverview(AbstractTank tank) {
        if (tank == null) {
            throw new NullPointerException("Tank is null.");
        }
        this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        fillLevel = new ProgressVisualization(Translator.getInstance()
                .getString("monitoring.tank.progress.fillLevel"), tank);
        tabFillLevel = new Tab(fillLevel.getProgressName());
        tabFillLevel.setContent(fillLevel.getProgressChart());
        this.getTabs().add(tabFillLevel);
        temperature = new ProgressVisualization(
                Translator.getInstance().getString("monitoring.tank.progress.temperature"), tank);
        tabTemperature = new Tab(temperature.getProgressName());
        tabTemperature.setContent(temperature.getProgressChart());
        this.getTabs().add(tabTemperature);
    }
    
    /**
     * Enables or disables the fill level progression.
     * 
     * @param progressEnabled true if the fill level progression should be enabled and false otherwise.
     */
    protected final void setFillLevelProgressEnabled(boolean progressEnabled) {
        fillLevel.setProgressEnabled(progressEnabled);
    }
    
    /**
     * Enables or disables the temperature progression.
     * 
     * @param progressEnabled true if the temperature progression should be enabled and false otherwise.
     */
    protected final void setTemperatureProgressEnabled(boolean progressEnabled) {
        temperature.setProgressEnabled(progressEnabled);
    }
    
    /**
     * Updates the progressions.
     */
    protected void updateProgressions() {
        temperature.update();
        fillLevel.update();
    }
}
