package org.iMage.stateMachine.a5;

import org.iMage.stateMachine.base.InputID;
import org.iMage.stateMachine.base.StateMachineI;

/**
 * Provides basic functionality of a state machine
 * @author Hans-Peter Lehmann
 *
 */
public class BaseStateMachine implements StateMachineI {
	private State currentState;
	
	/**
	 * Sets the state that the machine currently has
	 * @param current The state
	 */
	public void setCurrentState(State current) {
		this.currentState = current;
	}

	@Override
	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void processInputA() {
		processInput(InputID.a);
	}

	@Override
	public void processInputB() {
		processInput(InputID.b);
	}

	@Override
	public void processInputC() {
		processInput(InputID.c);
	}

	@Override
	public void processInputD() {
		processInput(InputID.d);
	}

	private void processInput(InputID input) {
		if (currentState.hasAction(input)) {
			currentState.exit();
			currentState.transition(input);
			currentState = currentState.getAction(input);
			currentState.entry();
			currentState.doState();
		} else {
		    throw new IllegalStateException("Transition not defined");
		}
	}

}
