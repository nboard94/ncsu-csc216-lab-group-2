package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test for ConflictException class.
 * @author Connor Hall, Renata Zeitler
 */
public class InvalidTransitionExceptionTest {
	
	/**
	 * Test method for InvalidTransitionException.
	 */
	@Test
	public void testConflictException() {
		InvalidTransitionException e = new InvalidTransitionException();
	    assertEquals("Invalid FSM Transition.", e.getMessage());
	}

	/**
	 * Test method for InvalidTransitionException(String).
	 */
	@Test
	public void testConflictExceptionString() {
		InvalidTransitionException e = new InvalidTransitionException("Custom exception message");
	    assertEquals("Custom exception message", e.getMessage());
	}

}
