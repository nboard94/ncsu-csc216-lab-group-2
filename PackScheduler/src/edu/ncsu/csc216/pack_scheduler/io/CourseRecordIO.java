package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * The CourseRecordIO class provides static IO methods for reading 
 * and writing Course records to files.
 * 
 * @author Connor Hall
 */
public class CourseRecordIO {

    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new File(fileName));
	    SortedList<Course> courses = new SortedList<Course>();
	    while (fileReader.hasNextLine()) {
	        try {
	            Course course = readCourse(fileReader.nextLine());
	            boolean duplicate = false;
	            for (int i = 0; i < courses.size(); i++) {
	                Course c = courses.get(i);
	                if (course.getName().equals(c.getName()) &&
	                        course.getSection().equals(c.getSection())) {
	                    //it's a duplicate
	                    duplicate = true;
	                }
	            }
	            if (!duplicate) {
	                courses.add(course);
	            }
	        } catch (IllegalArgumentException e) {
	            //skip the line
	        }
	    }
	    fileReader.close();
	    return courses;
	}
	
	/**
	 * Takes a comma delimited string and returns a new Course
	 * object with matching information.
	 * same information. 
	 * @param line the line to read
	 * @return the constructed Course
	 * @throws IllegalArgumentException if line cannot be parsed
	 */
	private static Course readCourse(String line) {
		String name;
		String title;
		String section;
		int credits;
		String instructorId;
		String meetingDays;
		int startTime;
		int endTime;
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter(",");
		
		try {
			name = lineReader.next();
			title = lineReader.next();
			section = lineReader.next();
			credits = lineReader.nextInt();
			instructorId = lineReader.next();
			meetingDays = lineReader.next();
			if (lineReader.hasNext()) {
				startTime = lineReader.nextInt();
				endTime = lineReader.nextInt();
				lineReader.close();
				return new Course(name, title, section, credits, instructorId, meetingDays, startTime, endTime);
			}
			lineReader.close();
			return new Course(name, title, section, credits, instructorId, meetingDays);
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
	}
	
    /**
     * Writes the given list of Courses to the given file.
     * @param fileName name of the file
     * @param courses the list of Courses to write
     * @throws FileNotFoundException if the file cannot be written to
     */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws FileNotFoundException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
		    fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();		
	}

}
