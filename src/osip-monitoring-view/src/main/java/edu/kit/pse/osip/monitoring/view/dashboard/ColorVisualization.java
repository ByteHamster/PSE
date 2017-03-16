package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Visualizes the color of the mixtank.
 * 
 * @author Martin Armbruster
 * @version 1.4
 */
class ColorVisualization extends BarLayout implements Observer {
    /**
     * Circle contains the color.
     */
    private Circle colorCircle;
    
    /**
     * Creates and initializes all controls for the color visualization.
     */
    protected ColorVisualization() {
        super(Translator.getInstance().getString("monitoring.mixTank.color"));
        colorCircle = new Circle();
        this.getChildren().add(0, colorCircle);
        this.setPrefWidth(MonitoringViewConstants.PREF_SIZE_FOR_BARS * 0.8);
    }
    
    @Override
    protected void layoutChildren() {
        double radius = (Math.min(this.getHeight(), this.getWidth()) - MonitoringViewConstants.ELEMENTS_GAP * 2
                - MonitoringViewConstants.FONT_SIZE) / 2;
        colorCircle.setRadius(radius);
        super.layoutChildren();
    }

    /**
     * Called when the observed object changed.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        edu.kit.pse.osip.core.model.base.Color col = ((AbstractTank) observable).getLiquid().getColor();
        Color currentColor = new Color(col.getR(), col.getG(), col.getB(), 1.0);
        Platform.runLater(() -> colorCircle.setFill(currentColor));
    }
}
