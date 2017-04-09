package edu.kit.pse.osip.core.io.files;

import java.nio.file.Paths;
import org.junit.Test;

/**
 * Tests the basic parser class.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ScenarioFileTest {
    
    /**
     * Tests if real files can be parsed without ParserExceptions.
     * 
     * @throws Exception If something goes wrong.
     */
    @Test
    public void testReadingFile() throws Exception {
        String path = Paths.get(Thread.currentThread().getContextClassLoader().getResource("test.scenario.txt")
                .toURI()).toFile().getAbsolutePath();
        ScenarioFile file = new ScenarioFile(path);
        file.getScenario();
    }
}
