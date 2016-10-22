package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests course catalog
 * @author Boyang Zhang, Connor Hall
 */
public class CourseCatalogTest {
	
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Programming Concepts - Java";
	/** Course section */
	private static final String SECTION = "001";
	/** Course section */
	private static final String SECTIONTWO = "002";
	/** Course credits */
	private static final int CREDITS = 4;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course enrollment capacity */
	private static final int ENROLLMENT_CAP = 20;
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests WolfScheduler().
	 */
	@Test
	public void testCourseCatalog() {
		// Test with invalid file. Should have an empty catalog and schedule.
		CourseCatalog c = new CourseCatalog();
		assertEquals(0, c.getCourseCatalog().length);
	}

	/**
	 * Test WolfScheduler.getCourseFromCatalog().
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog c = new CourseCatalog();

		// Attempt to get a course that doesn't exist
		assertNull(c.getCourseFromCatalog("CSC492", "001"));

		// Attempt to get a course that does exist
		Course course = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(course, c.getCourseFromCatalog(NAME, SECTION));
	}

	/**
	 * Test WolfScheduler.addCourse().
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog c = new CourseCatalog();
		Course course = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);

		// Add a valid course
		assertTrue(
				c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(1, c.getCourseCatalog().length);
		String[] a = c.getCourseCatalog()[0];
		assertEquals(NAME, a[0]);
		assertEquals(SECTION, a[1]);
		assertEquals(TITLE, a[2]);
		assertEquals(course.getMeetingString(), a[3]);
		
		// Attempt the same course again
		assertFalse(c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		
		// Attempt to add an invalid course
		try {
			c.addCourseToCatalog(NAME, TITLE, SECTIONTWO, 0, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
			fail();
		} catch (IllegalArgumentException e) {
			// credits cannot be 1
		}
	}

	/**
	 * Test CourseCatalog.removeCourse().
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog c = new CourseCatalog();

		// Attempt to remove from empty schedule
		assertFalse(c.removeCourseFromCatalog(NAME, SECTION));

		// Add some courses and remove them
		assertTrue(c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(1, c.getCourseCatalog().length);
		assertTrue(c.removeCourseFromCatalog(NAME, SECTION));
	}

	/**
	 * Test WolfScheduler.resetSchedule()
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog c = new CourseCatalog();

		// Add some courses and reset schedule
		assertTrue(c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(1, c.getCourseCatalog().length);

		c.newCourseCatalog();
		assertEquals(0, c.getCourseCatalog().length);

		// Check that resetting doesn't break future adds
		assertTrue(c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertEquals(1, c.getCourseCatalog().length);
	}

	/**
	 * test Get Course Catalog
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog c = new CourseCatalog();

		// Get the catalog and make sure contents are correct
		// Name, section, title
		c.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		String[][] catalog = c.getCourseCatalog();
		// Row 0
		assertEquals(NAME, catalog[0][0]);
		assertEquals(SECTION, catalog[0][1]);
		assertEquals(TITLE, catalog[0][2]);
		assertEquals("TH 1:30PM-2:45PM", catalog[0][3]);
	}

	/**
	 * test Save Course Catalog
	 */
	@Test
	public void testSaveCourseCatalog() {
		// Test that empty schedule exports correctly
		CourseCatalog c = new CourseCatalog();
		assertEquals(0, c.getCourseCatalog().length);
		c.saveCourseCatalog("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile
	 *            expected output
	 * @param actFile
	 *            actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new File(expFile));
			Scanner actScanner = new Scanner(new File(actFile));

			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
