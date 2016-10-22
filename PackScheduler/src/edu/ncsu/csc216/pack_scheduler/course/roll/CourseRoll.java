package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Sets the capacity of seats for a given Course and checks if Students can enroll
 * 
 * @author Connor Hall
 * @author Renata Zeitler
 * @author Nick Board
 *
 */
public class CourseRoll {
	//List of students
	private LinkedAbstractList<Student> roll;
	//Roll's enrollment capacity
	private int enrollmentCap;
	//Minimum amount for enrollment
	private static final int MIN_ENROLLMENT = 10;
	//Maximum amount for enrollment
	private static final int MAX_ENROLLMENT = 250;
	
	/**
	 * Constructor
	 * @param enrollmentCap max amount of Students allowed to enroll in a Course
	 */
	public CourseRoll(int enrollmentCap) {
		setEnrollmentCap(enrollmentCap);
		this.roll = new LinkedAbstractList<Student>(this.enrollmentCap);
	}
	
	/**
	 * Sets the enrollment Cap
	 * @param enrollmentCap max amount of Students allowed to enroll in a Course
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		
		if (roll == null || enrollmentCap >= roll.size()) {
			this.enrollmentCap = enrollmentCap;
		}
	}
	
	/**
	 * Gets the max amount of Students allowed to enroll in a Course
	 * @return the max amount of Students allowed to enroll in a Course 
	 */
	public int getEnrollmentCap() {
		return this.enrollmentCap;
	}
	
	/**
	 * Gets the amount of open seats left in a course
	 * @return the number of open seats for a course
	 */
	public int getOpenSeats() {
		return this.enrollmentCap - roll.size();
	}
	
	/**
	 * Enrolling a Student in a Course
	 * @param s Student wanting to enroll
	 */
	public void enroll(Student s) {
		if (!canEnroll(s)) {
			throw new IllegalArgumentException();
		}
		roll.add(0, s);
	}
	
	/**
	 * Student wishing to remove Course from Schedule
	 * @param s Student who wants to remove the Course
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		roll.remove(s);
	}
	
	/**
	 * Checks to see if Student can enroll in class
	 * @param s Student trying to enroll
	 * @return true if they are allowed to enroll, false if there are no open seats or if Student is already enrolled
	 */
	public boolean canEnroll(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		if (getOpenSeats() == 0) {
			return false;
		}
		for (int i = 0; i < roll.size(); i++) {
			if (s.equals(roll.get(i))) {
				return false;
			}
		}
		return true;
	}
	
}
