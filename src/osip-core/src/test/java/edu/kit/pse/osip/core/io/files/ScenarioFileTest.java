package edu.kit.pse.osip.core.io.files;

import java.io.File;
import java.util.regex.Matcher;
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
     * @throws Exception If something goes wrong
     */
    @Test
    public void testReadingFile() throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource("test.scenario.txt").toURI().getPath()
                .replaceAll("/", Matcher.quoteReplacement(File.separator));
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            path = path.substring(1);
        }
        ScenarioFile file = new ScenarioFile(path);
        file.getScenario();
    }
}
