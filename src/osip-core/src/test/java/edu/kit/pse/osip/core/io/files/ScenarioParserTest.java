package edu.kit.pse.osip.core.io.files;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests the extended parser class.
 *
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class ScenarioParserTest {
    /**
     * Tests if variables can be assigned.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test
    public void testVariableAssignments() throws ParserException {
        ScenarioParser parser = new ScenarioParser("var x = 15;");
        parser.readStatement();
        assertEquals(15f, parser.variables.get("x"), 0.0001);
        
        parser = new ScenarioParser("var myVariable = -(3+3);");
        parser.readStatement();
        assertEquals(-6f, parser.variables.get("myVariable"), 0.0001);
        
        parser = new ScenarioParser("var var = -var;");
        parser.variables.put("var", 42f);
        parser.readStatement();
        assertEquals(-42f, parser.variables.get("var"), 0.0001);
    }
    
    /**
     * Tests if an undefined variable can be read.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test(expected = ParserException.class)
    public void testUnknownVariableAssignment() throws ParserException {
        ScenarioParser parser = new ScenarioParser("var x = 15 + z;");
        parser.readStatement();
    }
    
    /**
     * Tests if invalid assignments results in errors.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test(expected = ParserException.class)
    public void testInvalidAssignmentSyntax() throws ParserException {
        ScenarioParser parser = new ScenarioParser("var x = 10 = 5;");
        parser.readStatement();
    }

    /**
     * Tests if invalid assignments results in errors.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test(expected = ParserException.class)
    public void testInvalidAssignmentNumber() throws ParserException {
        ScenarioParser parser = new ScenarioParser("var x = 12a3;");
        parser.readStatement();
    }

    /**
     * Tests if multiple variables can be assigned.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test
    public void testMultipleVariableAssignments() throws ParserException {
        ScenarioParser parser = new ScenarioParser("var x = 15;var y = 16;");
        parser.readScenario();
        assertEquals(15f, parser.variables.get("x"), 0.0001);
        assertEquals(16f, parser.variables.get("y"), 0.0001);
        
        parser = new ScenarioParser("var x = 15;var y = x + 3;");
        parser.readScenario();
        assertEquals(15f, parser.variables.get("x"), 0.0001);
        assertEquals(18f, parser.variables.get("y"), 0.0001);
    }
    
    /**
     * Tests if comments and assignments can be mixed.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test
    public void testComments() throws ParserException {
        ScenarioParser parser = new ScenarioParser("# Comment\n"
            + "var x = 5; # Assigns 5 to x\n"
            + "\n\n"
            + "var y = 10;");
        parser.readScenario();
        assertEquals(5f, parser.variables.get("x"), 0.0001);
        assertEquals(10f, parser.variables.get("y"), 0.0001);

        parser = new ScenarioParser("# Comment");
        parser.readScenario();
    }
    
    /**
     * Tests if wrong argument count leads to errors.
     * 
     * @throws ParserException If something goes wrong.
     */
    @Test(expected = ParserException.class)
    public void testCommandArgumentCount() throws ParserException {
        ScenarioParser parser = new ScenarioParser("delay(5, 10);");
        parser.readScenario();
    }
}
