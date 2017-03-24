package edu.kit.pse.osip.monitoring.view.dialogs;

import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNotNull;

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
     */
    @Test
    public void testDialogs() {
        assertNotNull(about);
        assertNotNull(help);
    }
}
