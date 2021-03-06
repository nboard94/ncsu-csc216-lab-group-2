package edu.ncsu.csc216.pack_scheduler.manager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * This class handles users logging into and out of the PackScheduler. It also holds
 * instances of CourseCatalog and StudentDirectory for the user to interact with. 
 * This class makes use of the Singleton pattern, so you there is no public constructor
 * You must call getInstance() in order to get an instance of the class.
 * 
 * @author Connor Hall, Revana Zeitler
 */
public class RegistrationManager {

	private static RegistrationManager instance;
	private CourseCatalog courseCatalog;
	private StudentDirectory studentDirectory;
	private User registrar;
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	private static final String PW = "Regi5tr@r";
	private static String hashPW;

	// Static code block for hashing the registrar user's password
	{
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(PW.getBytes());
			hashPW = new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	/**
	 * Main constructor for this class
	 */
	private RegistrationManager() {
		this.courseCatalog = new CourseCatalog();
		this.studentDirectory = new StudentDirectory();
		this.registrar = new Registrar();
	}
	/**
	 * Checks if instance is null, if so, it is initialized as a new RegistrationManager
	 * @return instance of new RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Gets the current user logged in
	 * @return currentUser that is logged in
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	
	/**
	 * Gets the catalog of courses
	 * @return courseCatalog of all courses current in the catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Gets the directory containing students
	 * @return studentDirectory of all students currently in the directory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Attempts to log in user to the system, given that they have the correct credentials
	 * @param id ID number of the user
	 * @param password password of the user
	 * @return true if the user can be logged in, false otherwise
	 */
	public boolean login(String id, String password) {
		Student s = studentDirectory.getStudentById(id);
		
		if (getCurrentUser() == null) {
			if (s != null) {
				try {
					MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String localHashPW = new String(digest.digest());
					if (s.getId().equals(id) && s.getPassword().equals(localHashPW)) {
						currentUser = s;
						return true;
					}
				} catch (NoSuchAlgorithmException e) {
					throw new IllegalArgumentException();
				}
			}	
			
			if (registrar.getId().equals(id)) {
				MessageDigest digest;
				try {
					digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String localHashPW = new String(digest.digest());
					if (registrar.getPassword().equals(localHashPW)) {
						currentUser = registrar;
						return true;
					}
				} catch (NoSuchAlgorithmException e) {
					throw new IllegalArgumentException();
				}
			} 
			if (s == null && !registrar.getId().equals(id)) {
				throw new IllegalArgumentException("User doesn't exist.");
			}
		}
		
		return false;
	}

	/**
	 * Logs out user from the system so that another user may log in
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Clears the data in the courseCatalog and the studentDirectory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
	}
	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        CourseRoll roll = c.getCourseRoll();
	        
	        if (s.canAdd(c) && roll.canEnroll(s)) {
	            schedule.addCourseToSchedule(c);
	            roll.enroll(s);
	            return true;
	        }
	        
	    } catch (IllegalArgumentException e) {
	        return false;
	    }
	    return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        c.getCourseRoll().drop(s);
	        return s.getSchedule().removeCourseFromSchedule(c);
	    } catch (IllegalArgumentException e) {
	        return false; 
	    }
	}

	/**
	 * Resets the logged in student's schedule by dropping them
	 * from every course and then resetting the schedule.
	 */
	public void resetSchedule() {
	    if (currentUser == null || !(currentUser instanceof Student)) {
	        throw new IllegalArgumentException("Illegal Action");
	    }
	    try {
	        Student s = (Student)currentUser;
	        Schedule schedule = s.getSchedule();
	        String [][] scheduleArray = schedule.getScheduledCourses();
	        for (int i = 0; i < scheduleArray.length; i++) {
	            Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
	            c.getCourseRoll().drop(s);
	        }
	        schedule.resetSchedule();
	    } catch (IllegalArgumentException e) {
	        //do nothing 
	    }
	}
	
	/**
	 * Class that contains the Registrar information
	 * @author Sarah Heckman
	 */
	private static class Registrar extends User {

		private static final String FIRST_NAME = "Wolf";
		private static final String LAST_NAME = "Scheduler";
		private static final String ID = "registrar";
		private static final String EMAIL = "registrar@ncsu.edu";

		/**
		 * Create a registrar user with the user id of registrar and password of
		 * Regi5tr@r. Note that hard coding passwords in a project is HORRIBLY
		 * INSECURE, but it simplifies testing here. This should NEVER be done
		 * in practice!
		 */
		public Registrar() {
			super(FIRST_NAME, LAST_NAME, ID, EMAIL, hashPW);
		}
	}
}