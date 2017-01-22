package edu.kit.pse.osip.monitoring.view.dashboard;

/**
 * Visualises the color of the mixtank.
 */
public class ColorVisualization implements java.util.Observer {
    /**
     * Label for showing the color section.
     */
    private javafx.scene.control.Label colorLabel;
    /**
     * Rectangle contains the color.
     */
    private javafx.scene.shape.Rectangle colorRectangle;
    /**
     * Creates and initializes all controls for the color visualization.
     */
    protected ColorVisualization () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the label showing the color section.
     * @return the label showing the color section.
     */
    protected final javafx.scene.control.Label getColorLabel () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Returns the rectangle containing the current color.
     * @return the rectangle containing the current color.
     */
    protected final javafx.scene.shape.Rectangle getColorRectangle () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Called when the observed object changed.
     * @param observable The observed object.
     * @param object The new value.
     */
    public final void update (java.util.Observable observable, Object object) {
        throw new RuntimeException("Not implemented!");
    }
}
