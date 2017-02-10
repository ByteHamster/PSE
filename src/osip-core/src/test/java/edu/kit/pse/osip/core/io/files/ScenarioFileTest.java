package edu.kit.pse.osip.core.io.files;

import org.junit.Test;

/**
 * Tests the basic parser class
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ScenarioFileTest {
    
    /**
     * Tests if real files can be parsed without ParserExceptions
     * @throws ParserException If something goes wrong
     */
    @Test
    public void testReadingFile() throws ParserException {
        String path = Thread.currentThread().getContextClassLoader()
                .getResource("test.scenario.txt").getPath();
        ScenarioFile file = new ScenarioFile(path);
        file.getScenario();
    }
}
