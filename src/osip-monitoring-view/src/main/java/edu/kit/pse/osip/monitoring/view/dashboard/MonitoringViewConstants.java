package edu.kit.pse.osip.monitoring.view.dashboard;

import javafx.scene.paint.Color;

/**
 * Constants for the monitoring view, appearing in more than one class.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
final class MonitoringViewConstants {
    /**
     * The gap between ui elements.
     */
    static final int ELEMENTS_GAP = 5;
    
    /**
     * The width of general borders.
     */
    static final int STROKE_WIDTH = 2;
    
    /**
     * The font size in pixels. 
     */
    static final int FONT_SIZE = 17;
    
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
