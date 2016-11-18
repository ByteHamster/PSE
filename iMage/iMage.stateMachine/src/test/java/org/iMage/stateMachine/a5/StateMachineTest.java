package org.iMage.stateMachine.a5;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests state machine given in the assignment
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class StateMachineTest {
	private StateMachine stateMachine;
	private ByteArrayOutputStream sysout;
	
	/**
	 * Prepares the state machine
	 */
	@Before
	public void prepare() {
		stateMachine = new StateMachine();
        sysout = new ByteArrayOutputStream();
        System.setOut(new PrintStream(sysout));
	}
	
	/**
	 * Clean up all changed settings
	 */
	@After
	public void cleanUp() {
        System.setOut(System.out);
	}
	
	/**
	 * Simple test that visits states a,b,c
	 */
	@Test
	public void testStateABCEnd() {
		assertEquals("A", stateMachine.getCurrentState().getName());
		stateMachine.processInputB();
		assertEquals("B", stateMachine.getCurrentState().getName());
		stateMachine.processInputD();
		assertEquals("C", stateMachine.getCurrentState().getName());
		stateMachine.processInputA();
		assertEquals("End", stateMachine.getCurrentState().getName());
	}
	
	/**
	 * Should throw exception in state c if pressing c because no action is specified
	 */
	@Test(expected=IllegalStateException.class)
	public void testPressingCInStateC() {
		stateMachine.processInputC();
		assertEquals("C", stateMachine.getCurrentState().getName());
		stateMachine.processInputC();
	}
	
	/**
	 * Tests if correct output is printed when using the transition
	 */
	@Test
	public void testStateTransitionSysout() {
		final ByteArrayOutputStream sysout = new ByteArrayOutputStream();
		System.setOut(new PrintStream(sysout));
		
		stateMachine.processInputD();
		assertEquals("Got a new input\n", sysout.toString());
	}
	
	/**
	 * Tests if correct output is printed when entering/exiting states
	 */
	@Test
	public void testEnterExitSysout() {
		stateMachine.processInputC();
		assertEquals("Entering C\n", sysout.toString());
		stateMachine.processInputB();
		stateMachine.processInputB();
		stateMachine.processInputA();
		assertEquals("Entering C\nLeaving B\n", sysout.toString());
	}
	
	/**
	 * Test returning to a state again
	 */
	@Test
	public void testLoop() {
		stateMachine.processInputA();
		assertEquals("A", stateMachine.getCurrentState().getName());
		stateMachine.processInputA();
		assertEquals("A", stateMachine.getCurrentState().getName());
		stateMachine.processInputB();
		stateMachine.processInputC();
		assertEquals("A", stateMachine.getCurrentState().getName());
	}
}
