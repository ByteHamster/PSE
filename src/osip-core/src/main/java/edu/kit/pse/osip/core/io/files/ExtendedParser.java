package edu.kit.pse.osip.core.io.files;

import edu.kit.pse.osip.core.model.base.ProductionSite;

/**
 * Extended parser for more complex expressions
 */
public class ExtendedParser extends edu.kit.pse.osip.core.io.files.BaseParser {
    /**
     * Constructor of ExtendedParser
     * @param toParse The string that should be parsed
     */
    public ExtendedParser (String toParse) {
        super(toParse);
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Reads a command in the input file
     * @return The command 
     * @throws ParserException If something goes wrong
     */
    public final java.util.function.Consumer<ProductionSite> readCommand () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Reads expression
     * @return the expression
     * @throws ParserException If something goes wrong
     */
    protected final float readExpression () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Reads number
     * @return the number
     * @throws ParserException If something goes wrong
     */
    protected final float readNumber () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Reads term
     * @return the term
     * @throws ParserException If something goes wrong
     */
    protected final float readTerm () {
        throw new RuntimeException("Not implemented!");
    }
    /**
     * Reads factor
     * @return the factor
     * @throws ParserException If something goes wrong
     */
    protected final float readFactor () {
        throw new RuntimeException("Not implemented!");
    }
}
