package org.iMage.stateMachine.a5;

import org.iMage.stateMachine.base.InputID;

/**
 * State machine given in the assignment sheet
 * @author Hans-Peter Lehmann
 * @version 1.0
 */
public class StateMachine extends BaseStateMachine {
	State	stateA		= new State("A");
	State	stateB		= new State("B");
	State	stateC		= new State("C");
	State	stateEnd	= new State("End");

	/**
	 * Creates the state machine given in assignment sheet
	 */
	public StateMachine() {
		constructMachine();
		this.setCurrentState(stateA);
	}

	private void constructMachine() {
		stateA.addAction(InputID.a, stateA);
		stateA.addAction(InputID.b, stateB);
		stateA.addAction(InputID.c, stateC);
		stateA.addAction(InputID.d, stateEnd, new Transition.TransitionAction() {
			@Override
			public void onTransitionAction() {
				System.out.println("Got a new input");
			}
		});

		stateB.addAction(InputID.a, stateEnd);
		stateB.addAction(InputID.c, stateA);
		stateB.addAction(InputID.d, stateC);
		stateB.setActionListener(new State.StateActionListener() {
			@Override
			public void exit() {
				System.out.println("Leaving B");
			}
		});

		stateC.addAction(InputID.a, stateEnd);
		stateC.addAction(InputID.b, stateA);
		stateC.setActionListener(new State.StateActionListener() {
			@Override
			public void entry() {
				System.out.println("Entering C");
			}
		});
	}
}
