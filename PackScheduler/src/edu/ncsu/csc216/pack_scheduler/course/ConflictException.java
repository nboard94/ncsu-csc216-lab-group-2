package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Exception throw when a conflict is found between two objects
 * that inherit from the Conflict interface.
 * @author Connor Hall
 */
public class ConflictException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a ConflictException with the default message,
	 * "Schedule conflict."
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
	
	/**
	 * Creates a new ConflictException with a custom error message.
	 * @param message a custom error message
	 */
	public ConflictException(String message) {
		super(message);
	}
	
}
