package org.iMage.stateMachine.a5;

import java.util.HashMap;
import java.util.Map;

import org.iMage.stateMachine.a5.Transition.TransitionAction;
import org.iMage.stateMachine.base.InputID;
import org.iMage.stateMachine.base.StateI;

/**
 * A state inside a state machine
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class State implements StateI {
	private final String				name;
	private StateActionListener			actionListener	= null;
	private Map<InputID, Transition>	actions			= new HashMap<InputID, Transition>();

	/**
	 * Creates a new state to be used in a state machine
	 * @param name Name of the state
	 */
	public State(String name) {
		this.name = name;
	}

	/**
	 * Sets listener that gets called when entering/exiting state
	 * @param listener The listener that overwrites at least one of entry, exit
	 */
	public void setActionListener(StateActionListener listener) {
		actionListener = listener;
	}
	
	/**
	 * Returns the name of this state
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the state that is switched to when pressing the given key
	 * @param input The given input key
	 * @return The new state
	 */
	public State getAction(InputID input) {
        if (!hasAction(input)) {
            throw new IllegalStateException("Transition not defined");
        }
        
		return actions.get(input).getDestination();
	}

	/**
	 * Checks if there is an action set for this key
	 * @param input The input key
	 * @return true if there is an action defined
	 */
	public boolean hasAction(InputID input) {
		return actions.containsKey(input);
	}

	/**
	 * Adds a new action to be executed on a certain key
	 * @param input The input key
	 * @param state The new state after the key was pressed
	 * @param action optional, the action to be executed while transaction runs
	 */
	public void addAction(InputID input, State state, TransitionAction action) {
		actions.put(input, new Transition(state, action));
	}
	
	/**
	 * Adds a new action to be executed on a certain key
	 * @param input The input key
	 * @param state The new state after the key was pressed
	 */
	public void addAction(InputID input, State state) {
		actions.put(input, new Transition(state, new TransitionAction() {
			@Override
			void onTransitionAction() {
				// Ignore
			}
		}));
	}

	/**
	 * Notify the listener that the state was entered
	 */
	public void entry() {
		if (actionListener != null) {
			actionListener.entry();
		}
	}
	   
	/**
     * Notify the listener that the state is now executing
     */
    public void doState() {
        if (actionListener != null) {
            actionListener.doState();
        }
    }

	/**
	 * Notify the listener that the state was left
	 */
	public void exit() {
		if (actionListener != null) {
			actionListener.exit();
		}
	}

	/**
	 * Notify the transition listener for the given input key
	 * @param input Input key
	 */
	public void transition(InputID input) {
		if (!hasAction(input)) {
		    throw new IllegalStateException("Transition not defined");
		}
		
		if (actions.get(input).getTransitionAction() != null) {
			actions.get(input).getTransitionAction().onTransitionAction();
		}
	}

	@Override
	public String toString() {
		return "State[" + name + "]";
	}

	/**
	 * Override at least one function. Can be used to get notified of state changes
	 * @author Hans-Peter Lehmann
	 * @version 1.0
	 */
	public static class StateActionListener {
		/**
		 * State entered
		 */
		public void entry() {
			// Override and implement
		}
		
		/**
         * State executing
         */
        public void doState() {
            // Override and implement
        }

		/**
		 * State left
		 */
		public void exit() {
			// Override and implement
		}
	}
}
