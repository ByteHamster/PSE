package edu.kit.pse.osip.core.io.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.kit.pse.osip.core.model.behavior.Scenario;

/**
 * ScenarioFile saves scenario settings and creates scenarios from the settings
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ScenarioFile {
    private final ScenarioParser parser;
    private final Scenario scenario;

    /**
     * Constructor of ScenarioFile
     * @param file The scenario definition file
     */
    public ScenarioFile(String file) throws ParserException, IOException {
        String scenarioDefinition = new String(Files.readAllBytes(Paths.get(file)));
        parser = new ScenarioParser(scenarioDefinition);
        scenario = parser.readScenario();
    }

    /**
     * Getter method of scenario
     * @return parsed scenario
     */
    public final Scenario getScenario() {
        return scenario;
    }
}
