package edu.kit.pse.osip.core.io.files;

import edu.kit.pse.osip.core.SimulationConstants;
import edu.kit.pse.osip.core.model.base.ProductionSite;
import edu.kit.pse.osip.core.model.base.TankSelector;
import edu.kit.pse.osip.core.model.behavior.Scenario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Parser for OSIP scenarios: LL(1) parser.
 * 
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ScenarioParser extends ExtendedParser {
    /**
     * Saves the available commands.
     */
    private HashMap<String, CommandAction> commands = new HashMap<>();
    /**
     * Saves the parsed scenario.
     */
    protected Scenario scenario = new Scenario();
    
    /**
     * Constructor of ScenarioParser.
     * 
     * @param toParse String to parse.
     */
    public ScenarioParser(String toParse) {
        super(toParse);
        addCommands();
        variables.put("true", 1f);
        variables.put("false", 0f);
    }
    
    /**
     * Parses scenario.
     * 
     * @return parsed scenario.
     * @throws ParserException If something goes wrong.
     */
    public final Scenario readScenario() throws ParserException {
        skipUtf8Bom();
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
     * Skips the UTF-8 ByteOrderMark that is sometimes added to the beginning of a file.
     *
     * @throws ParserException If something goes wrong.
     */
    private void skipUtf8Bom() throws ParserException {
        if (available() && peek() == 0xFEFF) {
            pop();
        }
    }
    
    /**
     * Reads a statement in the input file.
     * 
     * @throws ParserException If something goes wrong.
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
            } else if (command.equals("setRepeat")) {
                checkArgumentCount(parameters, 1);
                scenario.setRepeat(Math.abs(parameters.get(0) - 1) < 0.000001);
            } else if (!commands.containsKey(command)) {
                die("Unknown command: " + command);
            } else {
                scenario.appendRunnable(commands.get(command).create(parameters));
            }
        }
    }
    
    /**
     * Reads an alphabetic string, must not be empty.
     * 
     * @return The read string.
     * @throws ParserException If something goes wrong.
     */
    private String readAlphabetic() throws ParserException {
        StringBuilder builder = new StringBuilder();
        while (available() && (Character.isAlphabetic(peek()) || peek() == '_')) {
            builder.append(pop());
        }
        String name = builder.toString();
        if (name.equals("")) {
            die("Expected String literal");
        }
        return name;
    }
    
    /**
     * Requires a specific number of arguments to be available.
     * 
     * @param parameters Parameters given.
     * @param required Expected parameter count.
     * @throws ParserException If something goes wrong.
     */
    private void checkArgumentCount(ArrayList<Float> parameters, int required) throws ParserException {
        if (parameters.size() != required) {
            die("Invalid number uf arguments");
        }
    }
    
    /**
     * Creates and adds all supported commands.
     */
    private void addCommands() {
        commands.put("setMotorRpm", (parameters) -> {
            checkArgumentCount(parameters, 1);
            if (Math.round(parameters.get(0)) > SimulationConstants.MAX_MOTOR_SPEED
                    || Math.round(parameters.get(0)) < 0) {
                die("Argument out of range");
            }
            return (site) -> site.getMixTank().getMotor().setRPM(Math.round(parameters.get(0)));
        });
        commands.put("setUpperInThreshold", (parameters) -> {
            checkArgumentCount(parameters, 2);
            if (Math.round(parameters.get(1)) > 100 || Math.round(parameters.get(1)) < 0
                || Math.round(parameters.get(0)) >= TankSelector.getUpperTankCount()) {
                die("Argument out of range");
            }
            return (site) -> site.getUpperTank(TankSelector.valuesWithoutMix()[Math.round(parameters.get(0))])
                .getInPipe().setValveThreshold((byte) Math.round(parameters.get(1)));
        });
        commands.put("setUpperOutThreshold", (parameters) -> {
            checkArgumentCount(parameters, 2);
            if (Math.round(parameters.get(1)) > 100 || Math.round(parameters.get(1)) < 0
                || Math.round(parameters.get(0)) >= TankSelector.getUpperTankCount()) {
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
        commands.put("setInputTemperature", (parameters) -> {
            checkArgumentCount(parameters, 2);
            if (Math.round(parameters.get(0)) >= TankSelector.getUpperTankCount()
                    || parameters.get(1) > SimulationConstants.MAX_TEMPERATURE
                    || parameters.get(1) < SimulationConstants.MIN_TEMPERATURE) {
                die("Argument out of range");
            }
            return (site) -> site.setInputTemperature(
                TankSelector.valuesWithoutMix()[Math.round(parameters.get(0))], parameters.get(1));
        });
    }
    
    /**
     * Allows to call a specific command.
     */
    @FunctionalInterface
    interface CommandAction {
        /**
         * Creates the Consumer that is needed by ProductionSite.
         * 
         * @param parameters Parameters of this function call.
         * @return The consumer.
         * @throws ParserException If the argument count does not match.
         */
        Consumer<ProductionSite> create(ArrayList<Float> parameters) throws ParserException;
    }
}
