package org.iMage.stateMachine.a5;

/**
 * Transition between two states
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class Transition {
	private final TransitionAction	transitionAction;
	private final State				destination;

	/**
	 * Creates a new transition
	 * @param destination The destination state
	 * @param action The action that gets executed when switching state
	 */
	public Transition(State destination, TransitionAction action) {
		this.destination = destination;
		this.transitionAction = action;
	}

	/**
	 * @return The transition action listener
	 */
	public TransitionAction getTransitionAction() {
		return transitionAction;
	}

	/**
	 * @return The destination state
	 */
	public State getDestination() {
		return destination;
	}

	/**
	 * Override at least one function. Can be used to get notified of transitions
	 * @author Hans-Peter Lehmann
	 * @version 1.0
	 */
	public abstract static class TransitionAction {
		/**
		 * State transition in progress
		 */
		abstract void onTransitionAction();
	}
}
