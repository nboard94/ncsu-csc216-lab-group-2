package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
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
	private final int MIN_ENROLLMENT = 10;
	//Maximum amount for enrollment
	private final int MAX_ENROLLMENT = 250;
	
	public CourseRoll(int enrollmentCap) {
		setEnrollmentCap(enrollmentCap);
		this.roll = new LinkedAbstractList<Student>(this.enrollmentCap);
	}
	
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}
		
		if (roll != null) {
			if (enrollmentCap > roll.size()) {
				this.enrollmentCap = enrollmentCap;
			}
		} else {
			this.enrollmentCap = enrollmentCap;
		}
	}
	
	public int getEnrollmentCap() {
		return this.enrollmentCap;
	}
	
	public int getOpenSeats() {
		return this.enrollmentCap - roll.size();
	}
	
	public void enroll(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		if (getOpenSeats() == 0) {
			throw new IllegalArgumentException();
		}
		roll.add(0, s);
	}
	
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		int remove = -1;
		for (int i = 0; i < roll.size(); i++) {
			if (s.equals(roll.get(i))) {
				remove = i;
			}
		}
		if (remove == -1) {
			throw new IllegalArgumentException();
		}
		roll.remove(remove);
	}
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
