package edu.kit.pse.osip.core.io.files;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.Scenario;

/**
 * Parser for OSIP scenarios
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ScenarioParser extends edu.kit.pse.osip.core.io.files.ExtendedParser {
    private HashMap<String, CommandAction> commands = new HashMap<>();    
    protected Scenario scenario = new Scenario();
    
    /**
     * Constructor of ScenarioParser
     * @param toParse
     * @param toParse String to parse
     */
    public ScenarioParser(String toParse) {
        super(toParse);
        addCommands();
    }
    
    /**
     * Parse scenario
     * @return parsed scenario
     * @throws ParserException If something goes wrong
     */
    public final Scenario readScenario() throws ParserException {
        while (available()) {
            int lastPosition = -1;
            while (available() && lastPosition != currentPosition) {
                lastPosition = currentPosition;
                skipWhitespaces();
                skipComments();
            }
            if (available()) {
              readStatement();
            }
        }
        return scenario;
    }
    
    /**
     * Reads a statement in the input file
     * @throws ParserException If something goes wrong
     */
    public final void readStatement() throws ParserException {
        String command = readAlphabetic();
        skipWhitespaces();
        
        if (command.equals("var")) {
            // Variable assignment
            String name = readAlphabetic();
            skipWhitespaces();
            check('=');
            skipWhitespaces();
            float value = readExpression();
            skipWhitespaces();
            check(';');
            super.variables.put(name, value);
        } else {
            // Function call
            ArrayList<Float> parameters = new ArrayList<>();
            check('(');
            while (true) {
                skipWhitespaces();
                parameters.add(readExpression());
                skipWhitespaces();
                if (peek() == ',') {
                    check(',');
                    skipWhitespaces();
                } else {
                    break;
                }
            }
            skipWhitespaces();
            check(')');
            skipWhitespaces();
            check(';');
            
            if (command.equals("delay")) {
              checkArgumentCount(parameters, 1);
              scenario.addPause(Math.round(parameters.get(0)));
            } else if (!commands.containsKey(command)) {
                die("Unknown command: " + command);
            } else {
                scenario.appendRunnable(commands.get(command).create(parameters));
            }
        }
    }
    
    /**
     * Reads an alphabetic string, must not be empty
     * @return The read string
     * @throws ParserException If something goes wrong
     */
    private String readAlphabetic() throws ParserException {
        String name = "";
        while (available() && Character.isAlphabetic(peek())) {
            name += pop();
        }
        if (name.equals("")) {
            die("Expected String literal");
        }
        return name;
    }
    
    /**
     * Requires a specific number of arguments to be available
     * @param parameters Parameters given
     * @param required Expected parameter count
     * @throws ParserException If something goes wrong
     */
    private void checkArgumentCount(ArrayList<Float> parameters, int required) throws ParserException {
        if (parameters.size() != required) {
            die("Invalid number uf arguments");
        }
    }
    
    /**
     * Create and add all supported commands
     */
    private void addCommands() {
        commands.put("setMotorRpm", (parameters) -> {
            checkArgumentCount(parameters, 1);
            return (site) -> site.getMixTank().getMotor().setRPM(Math.round(parameters.get(0)));
        });
        commands.put("setUpperInThreshold", (parameters) -> {
            checkArgumentCount(parameters, 2);
            if (Math.round(parameters.get(1)) > 100 || Math.round(parameters.get(1)) < 0) {
                die("Argument out of range");
            }
            return (site) -> site.getUpperTank(TankSelector.valuesWithoutMix()[Math.round(parameters.get(0))])
                .getInPipe().setValveThreshold((byte) Math.round(parameters.get(1)));
        });
        commands.put("setUpperOutThreshold", (parameters) -> {
            checkArgumentCount(parameters, 2);
            if (Math.round(parameters.get(1)) > 100 || Math.round(parameters.get(1)) < 0) {
                die("Argument out of range");
            }
            return (site) -> site.getUpperTank(TankSelector.valuesWithoutMix()[Math.round(parameters.get(0))])
                .getOutPipe().setValveThreshold((byte) Math.round(parameters.get(1)));
        });
        commands.put("setMixOutThreshold", (parameters) -> {
            checkArgumentCount(parameters, 1);
            if (Math.round(parameters.get(0)) > 100 || Math.round(parameters.get(0)) < 0) {
                die("Argument out of range");
            }
            return (site) -> site.getMixTank().getOutPipe().setValveThreshold((byte) Math.round(parameters.get(0)));
        });
    }
    
    /**
     * Allows to call a specific command.
     */
    @FunctionalInterface
    interface CommandAction {
        /**
         * Creates the Consumer that is needed by ProductionSite
         * @param parameters Parameters of this function call
         * @return The consumer
         * @throws ParserException If the argument count does not match
         */
        Consumer<ProductionSite> create(ArrayList<Float> parameters) throws ParserException;
    }
}
