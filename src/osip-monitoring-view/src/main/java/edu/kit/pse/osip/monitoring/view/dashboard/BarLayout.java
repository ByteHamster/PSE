package edu.kit.pse.osip.monitoring.view.dashboard;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Super class for all visualizations showing a type of a bar.
 * The general layout and the label naming the bar are concentrated here.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
abstract class BarLayout extends VBox {
    /**
     * The label showing the name of the bar.
     */
    private Label label;
    
    /**
     * Creates a new BarLayout.
     * 
     * @param labelString The name of the bar.
     */
    protected BarLayout(String labelString) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(MonitoringViewConstants.ELEMENTS_GAP);
        label = new Label(labelString);
        this.getChildren().add(label);
    }
}
