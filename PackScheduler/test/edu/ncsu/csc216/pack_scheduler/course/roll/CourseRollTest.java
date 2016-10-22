package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the CourseRoll class.
 * @author Connor Hall
 * @author Renata Zeitler
 * @author Nick Board
 */
public class CourseRollTest {

	/**
	 * Tests CourseRoll constructor
	 */
	@Test
	public void testCourseRoll() {
		CourseRoll c = new CourseRoll(100);
		assertEquals(100, c.getOpenSeats());
		assertEquals(100, c.getEnrollmentCap());
		
		try {
			c = new CourseRoll(5);
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot have less than 10 students
		}
		
		try {
			c = new CourseRoll(500);
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot have more than 250 students
		}
		
		c.setEnrollmentCap(20);
		assertEquals(20, c.getEnrollmentCap());
	}
	
	/**
	 * Test CourseRoll.enroll()
	 */
	@Test
	public void testEnroll() {
		CourseRoll c = new CourseRoll(10);
		Student s = new Student("first", "last", "flast", "flast@ncsu.edu", "pw");
		
		c.enroll(s);
		assertEquals(10, c.getEnrollmentCap());
		
		try {
			c.enroll(s);
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot add same student twice
			assertEquals(9, c.getOpenSeats());
		}
		
		try {
			c.enroll(null);
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot enroll null
			assertEquals(9, c.getOpenSeats());
		}
		
		for (int i = 0; c.getOpenSeats() > 0; i++) {
			String value = "student" + i;
			c.enroll(new Student(value, value, value, value + "@ncsu.edu", "pw"));
		}
		
		try {
			c.enroll(new Student("f", "l", "fl", "fl@ncsu.edu", "pw"));
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot enroll past cap
			assertEquals(0, c.getOpenSeats());
		}
	}

	/**
	 * Tests CourseRoll.drop().
	 */
	@Test
	public void testDrop() {
		CourseRoll c = new CourseRoll(10);
		Student s = new Student("first", "last", "flast", "flast@ncsu.edu", "pw");

		try {
			c.drop(s);
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot drop from empty course roll
			assertEquals(10, c.getOpenSeats());
		}
		
		c.enroll(s);
		assertEquals(9, c.getOpenSeats());
		
		try {
			c.drop(null);
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot drop null
			assertEquals(9, c.getOpenSeats());
		}

		c.drop(s);
		assertEquals(10, c.getOpenSeats());		
	}
	
	/**
	 * Tests CourseRoll.canEnroll();
	 */
	@Test
	public void testCanEnroll() {
		CourseRoll c = new CourseRoll(10);
		Student s = new Student("first", "last", "flast", "flast@ncsu.edu", "pw");
		
		assertTrue(c.canEnroll(s));
		c.enroll(s);
		assertFalse(c.canEnroll(s));
		c.drop(s);
		assertTrue(c.canEnroll(s));
		
		for (int i = 0; c.getOpenSeats() > 0; i++) {
			String value = "student" + i;
			c.enroll(new Student(value, value, value, value + "@ncsu.edu", "pw"));
		}
		assertFalse(c.canEnroll(s));
		
		try {
			c.canEnroll(null);
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot be null
		}
	}
	
}
