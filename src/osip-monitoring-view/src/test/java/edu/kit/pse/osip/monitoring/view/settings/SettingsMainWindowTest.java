package edu.kit.pse.osip.monitoring.view.settings;

import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test class for the SettingsMainWindow.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class SettingsMainWindowTest extends SettingsTest {
    /**
     * Saves a test instance.
     */
    private SettingsMainWindow win;
    
    @Override
    public void start(Stage arg0) throws Exception {
        super.prepareWrapper();
        win = new SettingsMainWindow(wrapper);
    }

    /**
     * Tests an instance.
     */
    @Test
    public void testInstance() {
        assertNotNull(win);
        assertNotNull(win.getAlarmSettings());
        assertNotNull(win.getGeneralSettings());
        assertNotNull(win.getProgressions());
        assertNotNull(win.getStage());
    }
}
