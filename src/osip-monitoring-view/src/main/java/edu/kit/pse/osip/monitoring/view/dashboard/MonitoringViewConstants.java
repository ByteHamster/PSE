package edu.kit.pse.osip.monitoring.view.dashboard;

import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 * Constants for the monitoring view, appearing in more than one class.
 * 
 * @author Martin Armbruster
 * @version 1.4
 */
public final class MonitoringViewConstants {
    /**
     * Threshold (in %) when a overflow alarm triggers.
     */
    public static final Float OVERFLOW_ALARM_THRESHOLD = Float.valueOf(95);
    
    /**
     * Threshold (in %) when an underflow alarm triggers.
     */
    public static final Float UNDERFLOW_ALARM_THRESHOLD = Float.valueOf(5);
        
    /**
     * Threshold (in °K) when an alarm for overheating triggers.
     */
    public static final Float TEMPERATURE_OVERHEATING_THRESHOLD = Float.valueOf(678.5f);
    
    /**
     * Threshold (in °K) when an alarm for undercooling triggers.
     */
    public static final Float TEMPERATURE_UNDERCOOLING_THRESHOLD = Float.valueOf(291.5f);
    
    /**
     * The minimum update interval in milliseconds.
     */
    public static final int MIN_UPDATE_INTERVAL = 500;
    
    /**
     * The default update interval in milliseconds.
     */
    public static final int DEFAULT_UPDATE_INTERVAL = 1000;
    
    /**
     * The maximum update interval in milliseconds.
     */
    public static final int MAX_UPDATE_INTERVAL = 4000;
    
    /**
     * The minimum server port.
     */
    public static final int MIN_PORT = 1024;
    
    /**
     * The maximum server port.
     */
    public static final int MAX_PORT = 61000;
    
    /**
     * The number of major ticks for a slider.
     */
    public static final int NUMBER_OF_MAJOR_TICKS = 7;
    
    /**
     * The number of minor ticks between two major ticks.
     */
    public static final int NUMBER_OF_MINOR_TICKS = 8;
    
    /**
     * The gap between ui elements.
     */
    public static final int ELEMENTS_GAP = 5;
    
    /**
     * The border width of the AbstractTankVisualizations.
     */
    public static final int TANK_BORDER_WIDTH = 5;
    
    /**
     * The width of general borders.
     */
    public static final int STROKE_WIDTH = 2;
    
    /**
     * The font size in pixels, relative to the absolute screen height.
     */
    public static final int FONT_SIZE = (int) Math.round(0.01389 * Screen.getPrimary().getBounds().getHeight());
    
    /**
     * The preferred height for any type of a bar, relative to the absolute screen height.
     */
    public static final int PREF_HEIGHT_FOR_BARS = (int)
            Math.round(0.27778 * Screen.getPrimary().getBounds().getHeight());
    
    /**
     * Color when an alarm is enabled, but not activated.
     */
    public static final Color ALARM_ENABLED = Color.LIME;
    
    /**
     * Color when an alarm is activated.
     */
    public static final Color ALARM_TRIGGERED = Color.RED;
    
    /**
     * Color when an alarm is disabled.
     */
    public static final Color ALARM_DISABLED = Color.GRAY;
    
    /**
     * Private constructor to avoid instantiation.
     */
    private MonitoringViewConstants() {
    }
}
