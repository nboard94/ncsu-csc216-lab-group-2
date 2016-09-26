package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Student class.
 * @author Boyang Zhang, Connor Hall
 */
public class StudentTest {

	/**
	 * Tests hashCode().
	 */
	@Test
	public void testHashCode() {
		Student original = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		Student same = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		Student different =  new Student("Bob", "Jones", "id", "email@ncsu.edu", "hashedpassword", 16);
		
		assertEquals(original.hashCode(), same.hashCode());
		assertNotEquals(original.hashCode(), different.hashCode());
	}
	
	/**
	 * Tests Student(firstName, lastName, id, email, password, maxCredits).
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		assertEquals("first", s.getFirstName());
		assertEquals("last", s.getLastName());
		assertEquals("id", s.getId());
		assertEquals("email@ncsu.edu", s.getEmail());
		assertEquals("hashedpassword", s.getPassword());
		assertEquals(16, s.getMaxCredits());
		
		s = null;
		try {
			s = new Student(null, "last", "id", "email@ncsu.edu", "hashedpassword", 16);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", null, "id", "email@ncsu.edu", "hashedpassword", 16);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", "last", null, "email@ncsu.edu", "hashedpassword", 16);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", "last", "id", null, "hashedpassword", 16);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", null, 16);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
	}

	/**
	 * Tests Student(firstName, lastName, id, email, password).
	 */
	@Test
	public void testStudentStringStringStringStringString() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		assertEquals("first", s.getFirstName());
		assertEquals("last", s.getLastName());
		assertEquals("id", s.getId());
		assertEquals("email@ncsu.edu", s.getEmail());
		assertEquals("hashedpassword", s.getPassword());
		
		s = null;
		try {
			s = new Student(null, "last", "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", null, "id", "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", "last", null, "email@ncsu.edu", "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", "last", "id", null, "hashedpassword");
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		
		try {
			s = new Student("first", "last", "id", "email@ncsu.edu", null);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
	}

	/**
	 * Tests setFirstName().
	 */
	@Test
	public void testSetFirstName() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
		    s.setFirstName(null);
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("first", s.getFirstName());
		}
		
		try {
		    s.setFirstName("");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("first", s.getFirstName());
		}
		
		s.setFirstName("Bob");
		assertEquals("Bob", s.getFirstName());
	}

	/**
	 * Tests setLastName().
	 */
	@Test
	public void testSetLastName() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
		    s.setLastName(null);
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("last", s.getLastName());
		}
		
		try {
		    s.setLastName("");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("last", s.getLastName());
		}
		
		s.setLastName("Jones");
		assertEquals("Jones", s.getLastName());
	}
	
	/**
	 * Tests setId().
	 */
	@Test
	public void testSetId() {
		Student s = null;
		try {
			s = new Student("first", "last", null, "email@ncsu.edu", "hashedpassword");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    assertNull(s);
		}
		
		try {
			s = new Student("first", "last", "", "email@ncsu.edu", "hashedpassword");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
	}

	/**
	 * Tests setEmail().
	 */
	@Test
	public void testSetEmail() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
		    s.setEmail(null);
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("email@ncsu.edu", s.getEmail());
		}
		
		try {
		    s.setEmail("");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("email@ncsu.edu", s.getEmail());
		}
		
		try {
		    s.setEmail("emailncsu.edu");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("email@ncsu.edu", s.getEmail());
		}
		
		try {
		    s.setEmail("email@ncsuedu");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("email@ncsu.edu", s.getEmail());
		}
		
		try {
		    s.setEmail("email.ncsu@edu");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("email@ncsu.edu", s.getEmail());
		}
		
		s.setEmail("my.mail@ncsu.edu");
		assertEquals("my.mail@ncsu.edu", s.getEmail());
	}

	/**
	 * Tests setPassword().
	 */
	@Test
	public void testSetPassword() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
		    s.setPassword(null);
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("hashedpassword", s.getPassword());
		}
		
		try {
			s.setPassword("");
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals("hashedpassword", s.getPassword());
		}
		
		s.setPassword("123password");
		assertEquals("123password", s.getPassword());
	}

	/**
	 * Tests setMaxCredits().
	 */
	@Test
	public void testSetMaxCredits() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		try {
		    s.setMaxCredits(2);
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals(18, s.getMaxCredits());
		}
		
		try {
		    s.setMaxCredits(20);
		    fail(); //We don't want to reach this point - an exception should be thrown!
		} catch (IllegalArgumentException e) {
		    //We've caught the exception, now we need to make sure that the field didn't change
		    assertEquals(18, s.getMaxCredits());
		}
		
		s.setMaxCredits(16);
		assertEquals(16, s.getMaxCredits());
	}

	/**
	 * Tests equals().
	 */
	@Test
	public void testEqualsObject() {
		Student original = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		Student same = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		
		// Test Student with same state
		assertEquals(original, same);
		
		// Test the same Student
		assertEquals(original, original);
		
		// Test Student with different first name
		Student different =  new Student("Bob", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		assertNotEquals(original, different);
		
		// Test Student with different last name
		different =  new Student("first", "Jones", "id", "email@ncsu.edu", "hashedpassword", 16);
		assertNotEquals(original, different);
		
		// Test Student with different id
		different =  new Student("first", "last", "my_id", "email@ncsu.edu", "hashedpassword", 16);
		assertNotEquals(original, different);
		
		// Test Student with different email
		different =  new Student("first", "last", "id", "different@ncsu.edu", "hashedpassword", 16);
		assertNotEquals(original, different);
		
		// Test Student with different password
		different =  new Student("first", "last", "id", "email@ncsu.edu", "pw", 16);
		assertNotEquals(original, different);
		
		// Test Student with different credit hours
		different =  new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 15);
		assertNotEquals(original, different);
	}

	/**
	 * Tests toString().
	 */
	@Test
	public void testToString() {
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		assertEquals("first,last,id,email@ncsu.edu,hashedpassword,16", s.toString());
	}
	
	/**
	 * Tests CompareTo()
	 */
	@Test
	public void testCompareTo() {
		Student s1 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		Student s2 = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		Student s3 = new Student("first", "z", "id", "email@ncsu.edu", "hashedpassword", 16);
		Student s4 = new Student("z", "last", "id", "email@ncsu.edu", "hashedpassword", 16);
		Student s5 = new Student("first", "last", "z", "email@ncsu.edu", "hashedpassword", 16);
		
		assertEquals(0, s1.compareTo(s2)); // Same value
		assertTrue(s1.compareTo(s3) < 0);  // s1 comes before s3
		assertTrue(s3.compareTo(s1) > 0);  // reversed order
		
		assertTrue(s1.compareTo(s4) < 0); // s1 comes before s4
		assertTrue(s1.compareTo(s5) < 0); // s1 comes before s5
		assertTrue(s3.compareTo(s4) > 0); // s3 comes after s4
		assertTrue(s4.compareTo(s5) > 0); // s4 comes after s5
	}
}
