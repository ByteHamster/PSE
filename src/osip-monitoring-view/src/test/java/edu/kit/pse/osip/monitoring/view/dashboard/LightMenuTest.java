package edu.kit.pse.osip.monitoring.view.dashboard;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

/**
 * Test class for the Light and MonitoringMenu classes.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class LightMenuTest extends ApplicationTest {
    @Override
    public void start(Stage arg0) throws Exception {
    }

    /**
     * Tests the Light class's constructor.
     */
    @Test
    public void testLight() {
        new Light();
    }
    
    /**
     * Tests the constructor of the MonitoringMenu class.
     */
    @Test
    public void testMenu() {
        new MonitoringMenu();
    }
}
