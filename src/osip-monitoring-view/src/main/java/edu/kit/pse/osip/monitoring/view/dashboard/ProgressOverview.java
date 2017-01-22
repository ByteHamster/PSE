package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.Tank;

/**
 * Provides access to and all controls for the temperature and fill level progressions.
 */
public class ProgressOverview extends javafx.scene.control.TabPane {
    public ProgressVisualization fillLevel;
    public ProgressVisualization temperature;
    /**
     * Tab contains the temperature progression.
     */
    private javafx.scene.control.Tab tabTemperature;
    /**
     * Tab contains the fill level progression.
     */
    private javafx.scene.control.Tab tabFillLevel;
    /**
     * Creates and initializes all controls for the progressions.
     * @param tank The progressions are assigned to this tank.
     */
    protected ProgressOverview (Tank tank) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the fill level progression.
     * @param progressEnabled true if the fill level progression should be enabled and false otherwise.
     */
    protected final void setFillLevelProgressEnabled (boolean progressEnabled) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Enables or disables the temperature rprogression.
     * @param progressEnabled true if the temperature progression should be enabled and false otherwise.
     */
    protected final void setTemperatureProgressEnabled (boolean progressEnabled) {
        throw new RuntimeException("Not implemented!");
    }
}
