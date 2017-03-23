package edu.kit.pse.osip.monitoring.view.dashboard;

import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.ObservableBoolean;
import java.lang.reflect.Field;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the LoggingConsole.
 * 
 * @author Martin Armbruster
 * @version 1.0
 */
public class LoggingConsoleTest extends ApplicationTest {
    @Override
    public void start(Stage arg0) throws Exception {
    }
    
    /**
     * Tests the "Alarms" section of the LoggingConsole.
     * 
     * @throws Exception when something goes wrong.
     */
    @Test
    public void testAlarmsTab() throws Exception {
        LoggingConsole l = new LoggingConsole();
        Field field = l.getClass().getDeclaredField("alarms");
        field.setAccessible(true);
        TextFlow t = (TextFlow) field.get(l);
        assertNotNull(t);
        ObservableBoolean ob = new ObservableBoolean(false);
        AlarmVisualization av = new AlarmVisualization("Test", ob, TankSelector.MIX);
        av.addObserver(l);
        ob.setValue(true);
        Thread.sleep(1000);
        Text text = (Text) t.getChildren().get(0);
        assertTrue(text.getText().endsWith("The alarm \"Test\" for the label.tank.mix tank is triggered.\n"));
    }
    
    /**
     * Tests the "IO" section of the LoggingConsole.
     * 
     * @throws Exception when something goes wrong.
     */
    @Test
    public void testIOTab() throws Exception {
        LoggingConsole l = new LoggingConsole();
        Field field = l.getClass().getDeclaredField("stdIO");
        field.setAccessible(true);
        TextFlow t = (TextFlow) field.get(l);
        assertNotNull(t);
        String output = "Test";
        System.out.println(output);
        Thread.sleep(1000);
        Text text = (Text) t.getChildren().get(0);
        assertTrue(text.getText().endsWith(output));
    }
}
