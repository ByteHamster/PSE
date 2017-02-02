package edu.kit.pse.osip.core.io.files;

import java.util.HashMap;

/**
 * Extended parser for more complex expressions
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ExtendedParser extends edu.kit.pse.osip.core.io.files.BaseParser {
    protected HashMap<String, Float> variables = new HashMap<String, Float>();
    
    /**
     * Constructor of ExtendedParser
     * @param toParse The string that should be parsed
     */
    public ExtendedParser(String toParse) {
        super(toParse);
    }
    /**
     * Reads expression
     * @return the expression
     * @throws ParserException If something goes wrong
     */
    protected final float readExpression() throws ParserException {
        skipWhitespaces();
        float result = readTerm();
        skipWhitespaces();
        while (available() && (peek() == '+' || peek() == '-')) {
            if (pop() == '+') {
                result += readTerm();
            } else {
                result -= readTerm();
            }
            skipWhitespaces();
        }
        return result;
    }
    /**
     * Reads number
     * @return the number
     * @throws ParserException If something goes wrong
     */
    protected final float readNumber() throws ParserException {
        float number = 0;
        boolean charWasRead = false;
        while (available() && Character.isDigit(peek())) {
            charWasRead = true;
            number *= 10;
            number += pop() - 48;
        }
        if (!charWasRead) {
            die("Expected number");
        }
        if (available() && peek() == '.') {
            check('.');
            float factor = 0.1f;
            charWasRead = false;
            while (available() && Character.isDigit(peek())) {
                charWasRead = true;
                number += (pop() - 48) * factor;
                factor /= 10;
            }
            if (!charWasRead) {
                die("Expected number");
            }
        }
        return number;
    }
    /**
     * Reads term
     * @return the term
     * @throws ParserException If something goes wrong
     */
    protected final float readTerm() throws ParserException {
        skipWhitespaces();
        float result = readFactor();
        skipWhitespaces();
        while (available() && (peek() == '*' || peek() == '/')) {
            if (pop() == '*') {
                skipWhitespaces();
                result *= readFactor();
            } else {
                skipWhitespaces();
                result /= readFactor();
            }
            skipWhitespaces();
        }
        return result;
    }
    /**
     * Reads factor
     * @return the factor
     * @throws ParserException If something goes wrong
     */
    protected final float readFactor() throws ParserException {
        if (Character.isDigit(peek())) {
            return readNumber();
        } else if (Character.isAlphabetic(peek())) {
            String variableName = "";
            while (available() && Character.isAlphabetic(peek())) {
                variableName += pop();
            }
            
            if (variables.containsKey(variableName)) {
                return variables.get(variableName);
            } else {
                die("Unknown variable");
                return 0;
            }
        } else if (peek() == '(') {
            check('(');
            skipWhitespaces();
            float expression = readExpression();
            skipWhitespaces();
            check(')');
            return expression;
        } else if (peek() == '+') {
            check('+');
            return readFactor();
        } else if (peek() == '-') {
            check('-');
            return -readFactor();
        } else {
            die("Expected mathematical expression.");
            return 0;
        }
    }
}
