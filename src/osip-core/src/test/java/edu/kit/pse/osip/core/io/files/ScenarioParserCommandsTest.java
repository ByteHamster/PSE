package edu.kit.pse.osip.core.io.files;

import edu.kit.pse.osip.core.model.behavior.Scenario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Tests the extended parser class.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ScenarioParserCommandsTest { 
    /**
     * Tests if example commands are called correctly.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test
    public void testMockCommands() throws ParserException {
        ScenarioParser parser = new ScenarioParser("delay(5);");
        parser.scenario = Mockito.mock(Scenario.class);
        parser.readScenario();
        Mockito.verify(parser.scenario).addPause(5);
        
        parser = new ScenarioParser("delay(500);delay(10*1000);");
        parser.scenario = Mockito.mock(Scenario.class);
        parser.readScenario();
        Mockito.verify(parser.scenario).addPause(500);
        Mockito.verify(parser.scenario).addPause(10000);
        
        parser = new ScenarioParser("var ms = 10; delay(2*ms); ");
        parser.scenario = Mockito.mock(Scenario.class);
        parser.readScenario();
        Mockito.verify(parser.scenario).addPause(20);
    }
}
