package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.AbstractTank;
import edu.kit.pse.osip.core.model.base.Color;
import edu.kit.pse.osip.core.model.base.Liquid;
import edu.kit.pse.osip.core.model.base.Pipe;
import edu.kit.pse.osip.core.model.base.Tank;
import edu.kit.pse.osip.core.model.base.TankSelector;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for nearly all visualization classes.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class VisualizationTest extends ApplicationTest {
    /**
     * Saves a pane for displaying controls.
     */
    private StackPane pane;
    /**
     * Saves a tank for the visualizations.
     */
    private AbstractTank tank;
    
    @Override
    public void start(Stage stage) throws Exception {
        pane = new StackPane();
        stage.setScene(new Scene(pane));
        stage.show();
    }
    
    /**
     * Setups all necessary objects out of the model.
     */
    @Before
    public void setUp() {
        Pipe i = new Pipe(SimulationConstants.PIPE_CROSSSECTION, SimulationConstants.PIPE_LENGTH, (byte) 50);
        Pipe o = new Pipe(SimulationConstants.PIPE_CROSSSECTION, SimulationConstants.PIPE_LENGTH, (byte) 50);
        tank = new Tank(10000, TankSelector.CYAN, new Liquid(5000, 350, new Color(1, 1, 1)), o, i);
    }
    
    /**
     * Tests the constructor of the ColorVisualization.
     */
    @Test
    public void testColor() {
        ColorVisualization c = new ColorVisualization();
        Platform.runLater(() -> pane.getChildren().add(c));
        assertNotNull(c);
    }
    
    /**
     * Tests the constructor of the FillLevelVisualization.
     */
    @Test
    public void testFillLevel() {
        assertNotNull(new FillLevelVisualization());
    }
    
    /**
     * Tests the constructor of the GaugeVisualization.
     */
    @Test
    public void testGauge() {
        assertNotNull(new GaugeVisualization(""));
    }
    
    /**
     * Tests the constructor of the MotorSpeedVisualization.
     */
    @Test
    public void testMotorSpeed() {
        assertNotNull(new MotorSpeedVisualization());
    }
    
    /**
     * Tests the ProgressVisualization.
     */
    @Test
    public void testProgress() {
        ProgressVisualization p = new ProgressVisualization("Test", tank);
        assertNotNull(p);
        assertEquals("Test", p.getProgressName());
        assertNotNull(p.getProgressChart());
    }
    
    /**
     * Tests the constructor of the ProgressOverview with a null argument.
     */
    @Test(expected = NullPointerException.class)
    public void testProgressOverviewNull() {
        new ProgressOverview(null);
    }
    
    /**
     * Tests the ProgressOverview and with that, the ProgressVisualization.
     */
    @Test
    public void testProgressOverview() {
        ProgressOverview po = new ProgressOverview(tank);
        assertNotNull(po);
        po.setFillLevelProgressEnabled(false);
        po.setTemperatureProgressEnabled(false);
        Platform.runLater(() -> {
            pane.getChildren().add(po);
            po.updateProgressions();
        });
        po.setFillLevelProgressEnabled(true);
        po.setTemperatureProgressEnabled(true);
        Platform.runLater(() -> po.updateProgressions());
        assertNotNull(po);
    }
    
    /**
     * Tests the constructor of the TemperatureVisualization.
     */
    @Test
    public void testTemperature() {
        assertNotNull(new TemperatureVisualization());
    }
}
