package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Activity class.
 * @author Connor Hall
 */
public class ActivityTest {

	/**
	 * Tests the checkConflict method in Activity
	 */
	@Test
	public void testCheckConflict() {
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "TH", 1330, 1445);
	    try {
	        a1.checkConflict(a2);
	        assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
	        assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM", a2.getMeetingString());
	    } catch (ConflictException e) {
	        fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
	    }
	    
	    try {
	        a2.checkConflict(a1);
	        assertEquals("Incorrect meeting string for possibleConflictingActivity Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
	        assertEquals("Incorrect meeting string for this Activity.", "TH 1:30PM-2:45PM", a2.getMeetingString());
	    } catch (ConflictException e) {
	        fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
	    }
	    
	    // Update a1 with the same meeting days and a start time that overlaps the end time of a2
	    a1.setMeetingDays("TH");
	    a1.setActivityTime(1445, 1530);
	    try {
	        a1.checkConflict(a2);
	        fail(); // ConflictException should have been thrown, but was not.
	    } catch (ConflictException e) {
	        // Check that the internal state didn't change during method call.
	        assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
	        assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
	    }
	    
	    // Same conflict as above, but with a1 and a2 switched
	    try {
	        a2.checkConflict(a1);
	        fail(); // ConflictException should have been thrown, but was not.
	    } catch (ConflictException e) {
	        // Check that the internal state didn't change during method call.
	        assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
	        assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
	    }
	    
	    // Conflict on a single day
	    a1.setMeetingDays("MTW");
	    a1.setActivityTime(1445, 1530);
	    try {
	        a1.checkConflict(a2);
	        fail(); // ConflictException should have been thrown, but was not.
	    } catch (ConflictException e) {
	        // Check that the internal state didn't change during method call.
	        assertEquals("MTW 2:45PM-3:30PM", a1.getMeetingString());
	        assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
	    }
	    
	    // Arranged Activity
	    Activity a3 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
	    try {
	    	a1.checkConflict(a3);
	    	a3.checkConflict(a1);
	    	assertEquals("MTW 2:45PM-3:30PM", a1.getMeetingString());
	        assertEquals("Arranged", a3.getMeetingString());
	    } catch (ConflictException e) {
	    	fail("ConflictException thrown with an arranged activity");
	    }
	    
	    // a1 occurs before a2
	    a1.setActivityTime(1000, 1145);
	    try {
	    	a1.checkConflict(a2);
	    	assertEquals("MTW 10:00AM-11:45AM", a1.getMeetingString());
	        assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
	    } catch (ConflictException e) {
	    	fail();
	    }
	}
	
}
