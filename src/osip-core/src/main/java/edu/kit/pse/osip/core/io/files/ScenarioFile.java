package edu.kit.pse.osip.core.io.files;

import edu.kit.pse.osip.core.model.behavior.Scenario;

/**
 * ScenarioFile saves scenario settings and creates scenarios from the settings
 */
public class ScenarioFile {
    public ScenarioParser parser;
    /**
     * Constructor of ScenarioFile
     * @param file The scenario definition file
     */
    public ScenarioFile (java.io.File file) {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * 	Getter method of scenario @return parsed scenario		
     */
    public final Scenario getScenario () {
        throw new RuntimeException("Not implemented!");
    }
}
