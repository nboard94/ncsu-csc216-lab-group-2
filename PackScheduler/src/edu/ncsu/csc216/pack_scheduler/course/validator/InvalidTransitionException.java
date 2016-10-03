package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Exception thrown when a conflict is an invalid state is reached
 * in a Finite State Machine.
 * @author Connor Hall, Renata Zeitler
 */
public class InvalidTransitionException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a InvalidTransitionException with the default message,
	 * "Invalid FSM Transition."
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}
	
	/**
	 * Creates a new InvalidTransitionException with a custom error message.
	 * @param message a custom error message
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
}
