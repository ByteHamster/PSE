package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Visualizes the color of the mixtank.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
final class ColorVisualization extends BarLayout implements Observer {
    /**
     * Rectangle contains the color.
     */
    private Rectangle colorRectangle;
    
    /**
     * Creates and initializes all controls for the color visualization.
     */
    protected ColorVisualization() {
        super(Translator.getInstance().getString("monitoring.mixTank.color"));
        colorRectangle = new Rectangle();
        this.getChildren().add(colorRectangle);
    }
    
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        double length = this.getWidth() / 2.0;
        colorRectangle.setLayoutX(length / 2);
        colorRectangle.setWidth(length);
        colorRectangle.setHeight(length);
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
        colorRectangle.setFill(currentColor);
    }
}
