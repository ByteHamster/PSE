package edu.kit.pse.osip.simulation.view.main;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.utils.language.Translator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertTrue;

/**
 * Test class for the callbacks in the simulation view.
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class SimulationViewCallbackTest extends ApplicationTest {
    /**
     * Saves the SimulationMainWindow used for testing.
     */
    private SimulationMainWindow mainWindow;

    @Override
    public void start(Stage stage) throws Exception {
        Translator.getInstance().setLocalizationDisabled(true);
        mainWindow = new SimulationMainWindow(new ProductionSite());
        mainWindow.start(stage);
        mainWindow.setProgressIndicatorVisible(false);
    }

    /**
     * Resets the translator to show localizations again.
     */
    @AfterClass
    public static void resetTranslator() {
        Translator.getInstance().setLocalizationDisabled(false);
    }

    /**
     * Waits for the UI to show up.
     * 
     * @throws Exception If test goes wrong.
     */
    @Before
    public void waitForUI() throws Exception {
        Thread.sleep(1000);
    }

    /**
     * Tests if the settings callback works.
     * 
     * @throws Exception If test goes wrong.
     */
    @Test
    public void testSettings() throws Exception {
        CompletableFuture<Boolean> callbackReceived = new CompletableFuture<>();
        mainWindow.setSettingsButtonHandler(event -> callbackReceived.complete(true));
        clickOn("simulation.view.menu.file")
                .clickOn("simulation.view.menu.file.settings");
        assertTrue(callbackReceived.get(5, TimeUnit.SECONDS));
    }

    /**
     * Tests if the reset callback works.
     * 
     * @throws Exception If test goes wrong.
     */
    @Test
    public void testReset() throws Exception {
        CompletableFuture<Boolean> callbackReceived = new CompletableFuture<>();
        mainWindow.setResetListener(event -> callbackReceived.complete(true));
        clickOn("simulation.view.menu.file")
                .clickOn("simulation.view.menu.file.reset");
        assertTrue(callbackReceived.get(5, TimeUnit.SECONDS));
    }

    /**
     * Tests if the about callback works.
     * 
     * @throws Exception If test goes wrong.
     */
    @Test
    public void testAbout() throws Exception {
        CompletableFuture<Boolean> callbackReceived = new CompletableFuture<>();
        mainWindow.setAboutButtonHandler(event -> callbackReceived.complete(true));
        clickOn("simulation.view.menu.other")
                .clickOn("simulation.view.menu.other.about");
        assertTrue(callbackReceived.get(5, TimeUnit.SECONDS));
    }

    /**
     * Tests if the help callback works.
     * 
     * @throws Exception If test goes wrong.
     */
    @Test
    public void testHelp() throws Exception {
        CompletableFuture<Boolean> callbackReceived = new CompletableFuture<>();
        mainWindow.setHelpButtonHandler(event -> callbackReceived.complete(true));
        clickOn("simulation.view.menu.other")
                .clickOn("simulation.view.menu.other.help");
        assertTrue(callbackReceived.get(5, TimeUnit.SECONDS));
    }

    /**
     * Tests if the control callback works.
     * 
     * @throws Exception If test goes wrong.
     */
    @Test
    public void testControl() throws Exception {
        CompletableFuture<Boolean> callbackReceived = new CompletableFuture<>();
        mainWindow.setControlButtonHandler(event -> callbackReceived.complete(true));
        clickOn("simulation.view.menu.view")
                .clickOn("simulation.view.menu.view.control");
        assertTrue(callbackReceived.get(5, TimeUnit.SECONDS));
    }
}
