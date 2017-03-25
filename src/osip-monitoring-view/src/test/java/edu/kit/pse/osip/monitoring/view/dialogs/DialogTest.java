package edu.kit.pse.osip.monitoring.view.dialogs;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the Dialog classes.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class DialogTest extends ApplicationTest {
    /**
     * Saves a test instance of the AboutDialog.
     */
    private AboutDialog about;
    /**
     * Saves a test instance of the HelpDialog.
     */
    private HelpDialog help;
    
    @Override
    public void start(Stage arg0) throws Exception {
        about = new AboutDialog();
        help = new HelpDialog();
    }
    
    /**
     * Tests the constructors of the Dialog classes.
     * 
     * @throws Exception when something goes wrong.
     */
    @Test
    public void testDialogs() throws Exception {
        assertNotNull(about);
        assertNotNull(help);
        final CompletableFuture<Boolean> showed = new CompletableFuture<>();
        Platform.runLater(() -> {
            about.show();
            help.show();
            showed.complete(true);
        });
        assertTrue(showed.get(5, TimeUnit.SECONDS));
        assertTrue(about.isShowing());
        assertTrue(help.isShowing());
        final CompletableFuture<Boolean> hidden = new CompletableFuture<>();
        Platform.runLater(() -> {
            about.hide();
            help.hide();
            hidden.complete(true);
        });
        assertTrue(hidden.get(5, TimeUnit.SECONDS));
        assertFalse(about.isShowing());
        assertFalse(help.isShowing());
    }
}
