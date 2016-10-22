package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * An object representing a college level course.
 * The Course has getters and setters for course related data.
 * @author Connor Hall
 */
public class Course extends Activity implements Comparable<Course> {
	/** The name of this Course. */
	private String name;
	/** The section of this Course. */
	private String section;
	/** The number of credit hours for this Course. */
	private int credits;
	/** The instructor of this Course. */
	private String instructorId;
	/** Max capacity for a course to have*/
	private int enrollmentCap;
	/** CourseNameValidator object used to validate course names. */
	private CourseNameValidator validator;
	/** CourseRoll object used to store courses. */
	private CourseRoll roll;
	
	/**
	 * Constructs a Course object with values for all fields.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id
	 * @param enrollmentCap max Students allowed to enroll
	 * @param meetingDays meeting days for Course as a series of chars
	 * @param startTime	start time for Course
	 * @param endTime end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		validator = new CourseNameValidator();
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		this.enrollmentCap = enrollmentCap;
		this.roll = new CourseRoll(enrollmentCap);
	}
	
	/**
	 * Constructs a Course object with the given name, title, section, credits, instructorId, and meetingDays.
	 * @param name name of Course
	 * @param title title of Course
	 * @param section section of Course
	 * @param credits credit hours for Course
	 * @param instructorId instructor's unity id 
	 * @param enrollmentCap max Students allowed to enroll
	 * @param meetingDays end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}
	
	/**
	 * Returns the CourseRoll.
	 * @return roll The CourseRoll to return.
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

	/**
	 * Returns the Course's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the Course's name. If the name is null, has a length less than 4 or
	 * greater than 6, an IllegalArgumentException is thrown.
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null or length is less than 4 or
	 * greater than 6
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid name");
		}
		if (name.length() < 4 || name.length () > 6) {
			throw new IllegalArgumentException("Invalid name");
		}
		try {
			if (validator.isValid(name)) {
				this.name = name;
			} else {
				throw new IllegalArgumentException("Invalid name");
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid name");
		}
	}
	
	/**
	 * Returns the Course's section.
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * Sets the Course section. If the section number is not exactly three
	 * digits, IllegalArgumentException is thrown.
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is not exactly three digits
	 */
	public void setSection(String section) {
		if (section == null || section.length() != 3) {
			throw new IllegalArgumentException("Invalid section");
		}
		for(char c : section.toCharArray()) {
			if (!Character.isDigit(c)) {
				throw new IllegalArgumentException("Invalid section");
			}
		}
		this.section = section;
	}
	
	/**
	 * Returns the Course's credits.
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * Sets the number of course credits. If the number of credits is less than
	 * 1 or greater than 5, IllegalArgumentException is thrown.
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if credits is less than 1 or greater than 5
	 */
	public void setCredits(int credits) {
		if (credits < 1 || credits > 5) {
			throw new IllegalArgumentException("Invalid credits");
		}
		this.credits = credits;
	}
	
	/**
	 * Returns the Course's instructor's id.
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}
	
	/**
	 * Sets the Course instructor's id. If insructorId is null or is an empty string,
	 * IllegalArgumentException is thrown.
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructorId is null or is an empty string
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || instructorId.equals("")) {
			throw new IllegalArgumentException("Invalid instructor id");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Sets the Course's meeting days. If meetingDays is null, consists of 
	 * any characters other than 'M', 'T', 'W', 'H', 'F', or 'A', or if 'A' is in 
	 * meetingsDays and is not the only character, IllegalArgumentException is thrown.
	 * @param meetingDays the meetingDays to set
	 * @throws IllegalArgumentException if meetingDays is null, consists of 
	 * any characters other than 'M', 'T', 'W', 'H', 'F', or 'A', or if 'A' is in 
	 * meetingsDays and is not the only character.
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.equals("")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		for (char c : meetingDays.toCharArray()){
			if (c == 'A') {
				if (meetingDays.length() != 1) {
					throw new IllegalArgumentException("Invalid meeting days");
				}
			}
			else if (c != 'M' && c != 'T' && c != 'W' && c != 'H' && c != 'F'){
				throw new IllegalArgumentException("Invalid meeting days");
			}
		}
		super.setMeetingDays(meetingDays);
	}
	
	/**
	 * Returns a short description array of the Course.
	 * The first element contains the Course's name,
	 * the second the Course's section,
	 * the third the Course's title,
	 * the fourth the Course's meeting string.
	 * @return the Course's short display array
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] array = new String[5];
		array[0] = getName();
		array[1] = getSection();
		array[2] = getTitle();
		array[3] = getMeetingString();
		array[4] = roll.getOpenSeats() + "";
		return array;
	}

	/**
	 * Returns a long description array of the Course.
	 * The first element contains the Course's name,
	 * the second the Course's section,
	 * the third the Course's title,
	 * the fourth the Course's credit hours,
	 * the fifth the Course's instructor's id,
	 * the sixth the Course's meeting string.
	 * @return the Course's long display array
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] array = new String[7];
		array[0] = getName();
		array[1] = getSection();
		array[2] = getTitle();
		array[3] = Integer.toString(getCredits());
		array[4] = getInstructorId();
		array[5] = getMeetingString();
		array[6] = "";
		return array;
	}

	/**
	 * Checks if activity is a duplicate of this Course.
	 * It is a duplicate if it is an instance of Course and
	 * has the same name as this Course.
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity instanceof Course) {
			Course course = (Course)activity;
			return name.equals(course.getName());
		}
		return false;
	}

	/**
	 * Generates a hashCode for Course using all fields.
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}
	
	/**
	 * Compares a given object to this object for equality on all fields.
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}
	
    /**
     * Returns a comma separated value String of all Course fields.
     * @return String representation of Course
     */
	public String toString() {
	    if (getMeetingDays().equals("A")) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + enrollmentCap + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + enrollmentCap + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Compares two Courses alphabetically first by their name and then by their section.
	 * @param o the Course to compare to
	 * @return a negative integer if this comes before o, a positive number if this comes after o,
	 * and 0 if the Courses are equivalent.
	 */
	@Override
	public int compareTo(Course o) {
		String thisCourse = name + section;
		return thisCourse.compareTo(o.name + o.section);
	}
	
}
