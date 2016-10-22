package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * A catalog of college courses. Provides useful methods for adding, getting,
 * and removing Courses as well as writing and reading to files.
 * @author Boyang Zhang, Connor Hall
 */
public class CourseCatalog {
	private SortedList<Course> catalog;

	/**
	 * Constructs a new, empty catalog.
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Replaces the current catalog with a new, empty
	 * catalog.
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Loads course records from a file into the catalog. Previously loaded 
	 * course records will be overwritten.
	 * @param fileName the name of the file containing the course records
	 * @throws IllegalArgumentException if the file cannot be found
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot find file");
		}
	}

	/**
	 * Adds a Course with the all of the necessary Course information.
	 * @param name the name of the Course
	 * @param title the title of the Course
	 * @param section the section number of the Course
	 * @param credits the number of credit hours the Course has
	 * @param instructorId the Course's instructor's unity id
	 * @param meetingDays a String listing the days of the week the Course meets on
	 * @param startTime the Course's start time
	 * @param endTime the Course's end time
	 * @return true if the Course was added successfully
	 * @throws IllegalArgumentException if the Course could not be constructed from the
	 * given information
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		for (int i = 0; i < catalog.size(); i++) {
			Course other = catalog.get(i);
			if (other.getName().equals(name) && other.getSection().equals(section)) {
				return false;
			}
		}
		
		Course c;
		
		// Bug fix for GUIs that don't have blank options for times
		if (meetingDays.equals("A")) {
			c = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays);
		} else {
			c = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime); 
		}
		
		return catalog.add(c);
	}

	/**
	 * Removes a course from the Course catalog.
	 * @param name the name of the Course to remove
	 * @param section the section number of the Course to remove
	 * @return true if the Course was successfully removed
	 */
	public boolean removeCourseFromCatalog(String name, String section) {

		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			if (c.getName().equals(name) && c.getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the Course from that catalog that matches the given name
	 * and section number.
	 * @param name the name of the Course
	 * @param section the section number of the Course
	 * @return the Course that matches the given name and section or null if 
	 * one cannot be found
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			if (c.getName().equals(name) && c.getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns the name, section, title, and meeting information for each 
	 * of the Courses in the catalog
	 * @return a two-dimensional String array with the Course catalog information
	 */
	public String[][] getCourseCatalog() {
		String[][] array = new String[catalog.size()][4];
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			array[i][0] = c.getName();
			array[i][1] = c.getSection();
			array[i][2] = c.getTitle();
			array[i][3] = c.getMeetingString();
		}
		return array;
	}
	
	/**
	 * Saves the catalog of course records to the given file
	 * @param fileName the name of the file to save to
	 * @throws IllegalArgumentException if the file cannot be written
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
	
}
