package edu.ncsu.csc216.pack_scheduler.user.schedule;
import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the Schedule class
 * 
 * @author Renata Ann Zeitler
 * @author Connor Hall
 *
 */
public class ScheduleTest {
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";

	/** Course name */
	private static final String NAME = "CSC226";
	/** Course title */
	private static final String TITLE = "Discrete Mathematics for Computer Scientists";
	/** Course section */
	private static final String SECTION = "001";

	
	/**
	 * Tests the constructor
	 */
	@Test
	public void testSchedule() {
		Schedule schedule = new Schedule();
		assertEquals("My Schedule", schedule.getTitle());
		assertEquals(0, schedule.getScheduledCourses().length);
	}

	/**
	 * Tests adding Course to schedule
	 */
	@Test
	public void testAddCourseToSchedule() {
		Schedule schedule = new Schedule();
		CourseCatalog catalog  = new CourseCatalog();
		catalog.loadCoursesFromFile(validTestFile);
		
		//Attempt to add a course that does exist to schedule
		Course c = null;
		try {
			assertFalse(schedule.addCourseToSchedule(c));
			fail();
		} catch (NullPointerException n) {
			assertEquals(0, schedule.getScheduledCourses().length);
			assertEquals(1, catalog.getCourseCatalog().length);
		}
		
		c = catalog.getCourseFromCatalog(NAME, SECTION);
		
		assertTrue(schedule.addCourseToSchedule(c));
		assertEquals(1, schedule.getScheduledCourses().length);

		try {
			assertFalse(schedule.addCourseToSchedule(c));
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You are already enrolled in " + c.getName(), e.getMessage());
		}
		
		assertEquals(1, schedule.getScheduledCourses().length);

		//Attempt to add a Course with time conflict
		Course b = catalog.getCourseFromCatalog("CSC116", "001");
		try {
			schedule.addCourseToSchedule(b);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}
		assertEquals(1, schedule.getScheduledCourses().length);
		
	}
	
	/**
	 * Tests removing a course from the schedule
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		Schedule schedule = new Schedule();
		CourseCatalog catalog  = new CourseCatalog();
		catalog.loadCoursesFromFile(validTestFile);
		

		//Add some courses and remove them
		Course e = catalog.getCourseFromCatalog(NAME, SECTION);
		assertTrue(schedule.addCourseToSchedule(e));
		Course d = catalog.getCourseFromCatalog("CSC216", "002");
		assertTrue(schedule.addCourseToSchedule(d));
		
		assertFalse(schedule.addCourseToSchedule(e));
		assertFalse(schedule.addCourseToSchedule(d));
		assertEquals(2, schedule.getScheduledCourses().length);
		

		//Remove first course
		assertTrue(schedule.removeCourseFromSchedule(d));
		assertEquals(1, schedule.getScheduledCourses().length);
		//Remove second course
		assertTrue(schedule.removeCourseFromSchedule(e));
		assertEquals(0, schedule.getScheduledCourses().length);
		}
	
	/**
	 * Test Schedule's resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		Schedule schedule = new Schedule();
		CourseCatalog catalog  = new CourseCatalog();
		catalog.loadCoursesFromFile(validTestFile);
		
		//Add some courses and reset schedule
		Course e = catalog.getCourseFromCatalog(NAME, SECTION);
		assertTrue(schedule.addCourseToSchedule(e));
		Course d = catalog.getCourseFromCatalog("CSC216", "002");
		assertTrue(schedule.addCourseToSchedule(d));
		
		assertEquals(2, schedule.getScheduledCourses().length);
		
		schedule.resetSchedule();
		assertEquals(0, schedule.getScheduledCourses().length);
		
		//Check that resetting doesn't break future adds
		assertTrue(schedule.addCourseToSchedule(d));
		assertEquals(1, schedule.getScheduledCourses().length);
	}
	
	/**
	 * Test Schedule's getScheduledCourses().
	 */
	@Test
	public void testGetScheduledActivities() {
		Schedule schedule = new Schedule();
		CourseCatalog catalog  = new CourseCatalog();
		catalog.loadCoursesFromFile(validTestFile);
		
		//Add some courses and get schedule
		Course e = catalog.getCourseFromCatalog(NAME, SECTION);
		assertTrue(schedule.addCourseToSchedule(e));
		Course d = catalog.getCourseFromCatalog("CSC216", "002");
		assertTrue(schedule.addCourseToSchedule(d));
		
		String [][] schedule1 = schedule.getScheduledCourses();
		//Row 0
		assertEquals(NAME, schedule1[0][0]);
		assertEquals(SECTION, schedule1[0][1]);
		assertEquals(TITLE, schedule1[0][2]);
		//Row 1
		assertEquals("CSC216", schedule1[1][0]);
		assertEquals("002", schedule1[1][1]);
		assertEquals("Programming Concepts - Java", schedule1[1][2]);

	}
	
	/**
	 * Test WolfScheduler.setTitle().
	 */
	@Test
	public void testSetTitle() {
		Schedule schedule = new Schedule();
		
		//Set Title and check that changed
		schedule.setTitle("New Title");
		assertEquals("New Title", schedule.getTitle());
		
		//Check that exception is thrown if null title and no
		//change to title already there.
		try {
			schedule.setTitle(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("New Title", schedule.getTitle());
		}
	}
	
	
}
