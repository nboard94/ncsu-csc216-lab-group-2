package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Tests the RegistrationManager class
 * @author Connor Hall, Revana Zeitler
 */
public class RegistrationManagerTest {
	private static final String firstName = "JOHN";
	private static final String lastName = "Smith";
	private static final String id = "jsmith";
	private static final String email = "jsmith@ncsu.edu";
	private static final String password = "pw";
	private static final int credits = 18;
	private static final String registrarId = "registrar";
	private static final String registrarPassword = "Regi5tr@r";
	
	private RegistrationManager manager;
	
	/**
	 * Sets up the CourseManager and clears the data.
	 * @throws Exception if error occurs
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.logout();
		manager.clearData();
	}

	/**
	 * Tests RegistrationManger.getCourseCatalog().
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog catalog;
		
		// No courses in catalog
		catalog = manager.getCourseCatalog();
		assertEquals(0, catalog.getCourseCatalog().length);
		
		// Add course to catalog
		catalog.addCourseToCatalog("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", "MW", 910, 1100);
		catalog = manager.getCourseCatalog();
		assertEquals(1, catalog.getCourseCatalog().length);
		Course course = catalog.getCourseFromCatalog("CSC116", "001");
		assertEquals("CSC116", course.getName());
		assertEquals("Intro to Programming - Java", course.getTitle());
		assertEquals("001", course.getSection());
		assertEquals(3, course.getCredits());
		assertEquals("jdyoung2", course.getInstructorId());
		assertEquals("MW 9:10AM-11:00AM", course.getMeetingString());
		
		// Clear course catalog 
		manager.clearData();
		catalog = manager.getCourseCatalog();
		assertEquals(0, catalog.getCourseCatalog().length);
	}

	/**
	 * Tests RegistrationManger.getStudentDirectory().
	 */
	@Test
	public void testGetStudentDirectory() {
		StudentDirectory students;
		
		
		// No courses in catalog
		students = manager.getStudentDirectory();
		assertEquals(0, students.getStudentDirectory().length);
		
		// Add course to catalog
		students.addStudent(firstName, lastName, id, email, password, password, credits);
		students = manager.getStudentDirectory();
		assertEquals(1, students.getStudentDirectory().length);
		Student s = students.getStudentById(id);
		assertEquals(firstName, s.getFirstName());
		assertEquals(lastName, s.getLastName());
		assertEquals(id, s.getId());
		assertEquals(email, s.getEmail());
		
		// Clear course catalog 
		manager.clearData();
		students = manager.getStudentDirectory();
		assertEquals(0, students.getStudentDirectory().length);
	}

	/**
	 * Tests RegistrationManger.login().
	 */
	@Test
	public void testLogin() {
		StudentDirectory students = manager.getStudentDirectory();
		students.addStudent(firstName, lastName, id, email, password, password, credits);
		
		assertNull(manager.getCurrentUser());
		
		// Log in with invalid information
		assertFalse(manager.login("admin", "admin"));
		assertNull(manager.getCurrentUser());
		
		// Log in as student
		assertTrue(manager.login(id, password));
		assertNotNull(manager.getCurrentUser());
		
		// Log in as registrar
		assertTrue(manager.login(registrarId, registrarPassword));
		assertNotNull(manager.getCurrentUser());		
	}

	/**
	 * Tests RegistrationManger.logout().
	 */
	@Test
	public void testLogout() {
		StudentDirectory students = manager.getStudentDirectory();
		students.addStudent(firstName, lastName, id, email, password, password, credits);
		
		assertNull(manager.getCurrentUser());
		
		// No current user
		manager.logout();
		assertNull(manager.getCurrentUser());
		
		// Current user is a student
		manager.login(id, password);
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		assertNull(manager.getCurrentUser());
		
		
		// Current user is registrar
		manager.login(registrarId, registrarPassword);
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		assertNull(manager.getCurrentUser());
	}

	/**
	 * Tests RegistrationManger.getCurrentUser().
	 */
	@Test
	public void testGetCurrentUser() {
		User user;
		StudentDirectory students = manager.getStudentDirectory();
		students.addStudent(firstName, lastName, id, email, password, password, credits);
		
		// No current user
		user = manager.getCurrentUser();
		assertNull(user);
		
		// Current user is a student
		manager.login(id, password);
		user = manager.getCurrentUser();
		assertEquals(firstName, user.getFirstName());
		assertEquals(lastName, user.getLastName());
		assertEquals(id, user.getId());
		assertEquals(email, user.getEmail());
		
		// Current user is registrar
		manager.login(registrarId, registrarPassword);
		user = manager.getCurrentUser();
		assertEquals("Wolf", user.getFirstName());
		assertEquals("Scheduler", user.getLastName());
		assertEquals("registrar", user.getId());
		assertEquals("registrar@ncsu.edu", user.getEmail());
	}

}
