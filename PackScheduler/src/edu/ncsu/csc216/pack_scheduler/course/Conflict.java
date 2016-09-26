/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * This interface imposes the ability to check for conflicts between any two
 * instances of the class. Classes that implement Conflict must have some 
 * state used to differentiate instances and detect conflicts between them.
 * The Conflict interface is useful for scheduling and other systems that 
 * require conflict detection between two units. Conflict methods currently
 * only support checking for conflicts with instances of the Activity class.
 * @author Connor Hall
 */
public interface Conflict {
	
	/**
	 * Checks for conflicts with the given Activity. ConflictException is throw 
	 * if there is a conflict between the object and the given Activity.
	 * @param possibleConflictingActivity the Activity to check for conflicts
	 * @throws ConflictException if there is a conflict with the given Activity
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
	
}
