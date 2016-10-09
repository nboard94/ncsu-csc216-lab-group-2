package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests the CourseNameValidator class
 * @author Renata Zeitler
 * @author Connor Hall
 *
 */
public class CourseNameValidatorFSMTest {
	/**
	 * Tests the isValid method within CourseNameValidator to make sure only valid courses are
	 * passed through
	 */
	@Test
	public void testIsValid() throws InvalidTransitionException {
		CourseNameValidatorFSM s = new CourseNameValidatorFSM();
		//Valid test cases
		assertTrue(s.isValid("C116"));
		assertTrue(s.isValid("C116C"));
		assertTrue(s.isValid("CS116"));
		assertTrue(s.isValid("CS116C"));
		assertTrue(s.isValid("CSC116"));
		assertTrue(s.isValid("CSC116C"));
		assertTrue(s.isValid("CSCA116"));
		assertTrue(s.isValid("CSCA116C"));
		
		//Invalid test cases
		try {
			assertFalse(s.isValid("%"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
		
		try {
			assertFalse(s.isValid("1"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
		
		try {
			assertFalse(s.isValid("CSCAC"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}
		
		assertFalse(s.isValid("CSCA"));
		assertFalse(s.isValid("CSCA1"));
		assertFalse(s.isValid("CSCA12"));
		
		try {
			assertFalse(s.isValid("CSCA1A"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
		
		try {
			assertFalse(s.isValid("CSCA12A"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
		
		try {
			assertFalse(s.isValid("CSCA1234"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
		
		try {
			assertFalse(s.isValid("CSCA123AA"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
		
		try {
			assertFalse(s.isValid("CSCA123A1"));
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
		
	}

}
