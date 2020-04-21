package com.pixelcross.cavemanbrawl.states;

/**
 * @author Justin Schreiber
 *
 * Holds the current state
 */
public class StateManager {

	private State currentState;
	
	public StateManager(State startingState) {
		this.currentState = startingState;
	}

	public void setState(State newState) {
		this.currentState = newState;
	}
	
	public State getState() {
		return currentState;
	}
}
