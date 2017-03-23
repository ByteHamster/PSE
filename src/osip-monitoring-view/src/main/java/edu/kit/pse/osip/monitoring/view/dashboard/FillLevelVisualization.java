package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import jfxtras.scene.control.gauge.linear.BasicRoundDailGauge;

/**
 * Visualizes a fill level.
 * 
 * @author Martin Armbruster
 * @version 1.3
 */
class FillLevelVisualization extends BarLayout implements Observer {
    /**
     * The gauge shows the actual fill level.
     */
    private BasicRoundDailGauge levelBar;
    
    /**
     * Creates a new fill level visualization.
     */
    protected FillLevelVisualization() {
        super(Translator.getInstance().getString("monitoring.tank.fillLevel"));
        levelBar = new BasicRoundDailGauge();
        levelBar.setPrefSize(MonitoringViewConstants.PREF_SIZE_FOR_BARS,
                MonitoringViewConstants.PREF_SIZE_FOR_BARS);
        levelBar.setMaxValue(1);
        levelBar.setStyle("-fxx-value-color: -fxx-backplate-color;");
        levelBar.getStylesheets().add("BasicRoundDailGaugeShadowRemoval.css");
        this.getChildren().add(0, levelBar);
    }
    
    /**
     * Called when the observed object is updated.
     * 
     * @param observable The observed object.
     * @param object The new value.
     */
    public void update(Observable observable, Object object) {
        AbstractTank tank = (AbstractTank) observable;
        Platform.runLater(() -> levelBar.setValue(Math.min(1, tank.getFillLevel())));
    }
}
