package edu.kit.pse.osip.monitoring.view.dashboard;

import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 * Constants for the monitoring view, appearing in more than one class.
 * 
 * @author Martin Armbruster
 * @version 1.3
 */
final class MonitoringViewConstants {
    /**
     * Threshold (in %) when a overflow alarm triggers.
     */
    static final Float OVERFLOW_ALARM_THRESHOLD = Float.valueOf(95);
    
    /**
     * Threshold (in %) when an underflow alarm triggers.
     */
    static final Float UNDERFLOW_ALARM_THRESHOLD = Float.valueOf(5);
        
    /**
     * Threshold (in °K) when an alarm for overheating triggers.
     */
    static final Float TEMPERATURE_OVERHEATING_THRESHOLD = Float.valueOf(678.5f);
    
    /**
     * Threshold (in °K) when an alarm for undercooling triggers.
     */
    static final Float TEMPERATURE_UNDERCOOLING_THRESHOLD = Float.valueOf(291.5f);
    
    /**
     * The gap between ui elements.
     */
    static final int ELEMENTS_GAP = 5;
    
    /**
     * The border width of the AbstractTankVisualizations.
     */
    static final int TANK_BORDER_WIDTH = 5;
    
    /**
     * The width of general borders.
     */
    static final int STROKE_WIDTH = 2;
    
    /**
     * The font size in pixels, relative to the absolute screen height.
     */
    static final int FONT_SIZE = (int) Math.round(0.01389 * Screen.getPrimary().getBounds().getHeight());
    
    /**
     * The preferred height for any type of a bar, relative to the absolute screen height.
     */
    static final int PREF_HEIGHT_FOR_BARS = (int) Math.round(0.27778 * Screen.getPrimary().getBounds().getHeight());
    
    /**
     * Color when an alarm is enabled, but not activated.
     */
    static final Color ALARM_ENABLED = Color.LIME;
    
    /**
     * Color when an alarm is activated.
     */
    static final Color ALARM_TRIGGERED = Color.RED;
    
    /**
     * Color when an alarm is disabled.
     */
    static final Color ALARM_DISABLED = Color.GRAY;
    
    /**
     * Private constructor to avoid instantiation.
     */
    private MonitoringViewConstants() {
    }
}
