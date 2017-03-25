package edu.kit.pse.osip.monitoring.view.settings;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
     * 
     * @throws Exception when something goes wrong.
     */
    @Test
    public void testInstance() throws Exception {
        assertNotNull(win);
        assertNotNull(win.getAlarmSettings());
        assertNotNull(win.getGeneralSettings());
        assertNotNull(win.getProgressions());
        assertNotNull(win.getStage());
        assertFalse(win.getStage().isShowing());
        CompletableFuture<Boolean> showed = new CompletableFuture<>();
        Platform.runLater(() -> {
            win.getStage().show();
            showed.complete(true);
        });
        assertTrue(showed.get(5, TimeUnit.SECONDS));
        assertTrue(win.getStage().isShowing());
    }
}
