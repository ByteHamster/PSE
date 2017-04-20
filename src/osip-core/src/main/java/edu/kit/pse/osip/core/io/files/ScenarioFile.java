package edu.kit.pse.osip.core.io.files;

import edu.kit.pse.osip.core.model.behavior.Scenario;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ScenarioFile saves scenario settings and creates scenarios from the settings.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ScenarioFile {
    private final Scenario scenario;

    /**
     * Constructor of ScenarioFile.
     * 
     * @param file The scenario definition file. Must be UTF-8 encoded.
     * @throws ParserException if the scenario file has syntax errors.
     * @throws IOException if the file can't be opened for reading.
     */
    public ScenarioFile(String file) throws ParserException, IOException {
        String scenarioDefinition = new String(Files.readAllBytes(Paths.get(file)), "UTF-8");
        ScenarioParser parser = new ScenarioParser(scenarioDefinition);
        scenario = parser.readScenario();
    }

    /**
     * Getter method of scenario.
     * 
     * @return parsed scenario.
     */
    public final Scenario getScenario() {
        return scenario;
    }
}
