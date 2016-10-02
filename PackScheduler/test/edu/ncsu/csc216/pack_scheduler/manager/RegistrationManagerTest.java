package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Tests the RegistrationManager class
 * @author Connor Hall, Renata Zeitler
 */
public class RegistrationManagerTest {
	private static final String FIRST_NAME = "JOHN";
	private static final String LAST_NAME = "Smith";
	private static final String ID = "jsmith";
	private static final String EMAIL = "jsmith@ncsu.edu";
	private static final String PASSWORD = "pw";
	private static final int CREDITS = 18;
	private static final String REGISTRAR_ID = "registrar";
	private static final String REGISTRAR_PASSWORD = "Regi5tr@r";
	
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
		students.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, CREDITS);
		students = manager.getStudentDirectory();
		assertEquals(1, students.getStudentDirectory().length);
		Student s = students.getStudentById(ID);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		
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
		students.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, CREDITS);
		
		assertNull(manager.getCurrentUser());
		
		// Log in with invalid information
		assertFalse(manager.login("admin", "admin"));
		assertNull(manager.getCurrentUser());
		
		// Log in with incorrect password
		assertFalse(manager.login(ID, "pw1"));
		assertNull(manager.getCurrentUser());
		
		// Log in with incorrect id
		assertFalse(manager.login("john", PASSWORD));
		assertNull(manager.getCurrentUser());
		
		// Log in as student
		assertTrue(manager.login(ID, PASSWORD));
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		
		// Log in as registrar
		assertTrue(manager.login(REGISTRAR_ID, REGISTRAR_PASSWORD));
		assertNotNull(manager.getCurrentUser());		
	}

	/**
	 * Tests RegistrationManger.logout().
	 */
	@Test
	public void testLogout() {
		StudentDirectory students = manager.getStudentDirectory();
		students.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, CREDITS);
		
		assertNull(manager.getCurrentUser());
		
		// No current user
		manager.logout();
		assertNull(manager.getCurrentUser());
		
		// Current user is a student
		manager.login(ID, PASSWORD);
		assertNotNull(manager.getCurrentUser());
		manager.logout();
		assertNull(manager.getCurrentUser());
		
		
		// Current user is registrar
		manager.login(REGISTRAR_ID, REGISTRAR_PASSWORD);
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
		students.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, CREDITS);
		
		// No current user
		user = manager.getCurrentUser();
		assertNull(user);
		
		// Current user is a student
		manager.login(ID, PASSWORD);
		user = manager.getCurrentUser();
		assertEquals(FIRST_NAME, user.getFirstName());
		assertEquals(LAST_NAME, user.getLastName());
		assertEquals(ID, user.getId());
		assertEquals(EMAIL, user.getEmail());
		manager.logout();
		
		// Current user is registrar
		manager.login(REGISTRAR_ID, REGISTRAR_PASSWORD);
		user = manager.getCurrentUser();
		assertEquals("Wolf", user.getFirstName());
		assertEquals("Scheduler", user.getLastName());
		assertEquals("registrar", user.getId());
		assertEquals("registrar@ncsu.edu", user.getEmail());
	}

}