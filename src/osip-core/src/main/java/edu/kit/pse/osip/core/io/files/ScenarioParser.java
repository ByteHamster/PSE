package edu.kit.pse.osip.core.io.files;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.behavior.Scenario;

/**
 * Parser for OSIP scenarios
 */
public class ScenarioParser extends edu.kit.pse.osip.core.io.files.ExtendedParser {
    /**
     * Constructor of ScenarioParser
     * @param toParse @param toParse String to parse
     */
    public ScenarioParser(String toParse) {
        super(toParse);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Parse scenario @return parsed scenario
     * @throws ParserException If something goes wrong
     */
    public final Scenario readScenario() {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Reads a command in the input file
     * @return The command 
     * @throws ParserException If something goes wrong
     */
    public final java.util.function.Consumer<ProductionSite> readCommand() {
        throw new RuntimeException("Not implemented!");
    }
}
