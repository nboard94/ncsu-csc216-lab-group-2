package edu.ncsu.csc216.pack_scheduler.user;
/**
 * Represents a college Student. Student extends User with additional information about the maximum course
 * credits each student may take.
 * @author Boyang Zhang, Connor Hall
 */
public class Student extends User implements Comparable<Student> {
	
	private int maxCredits;
	
	
	/** The default maximum number of credits a student can take */
	public static final int MAX_CREDITS = 18;
	
	/**
	 * Constructs a new Student with a first name, last name, id, email, password, and max number of credits.
	 * @param firstName the Student's firstName
	 * @param lastName the Student's lastName
	 * @param id the Student's unity id
	 * @param email the Student's email address
	 * @param password the Student's hashed password
	 * @param maxCredits the Student's max credit hours
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
	}
	
	/**
	 * Constructs a new Student without specifying a number of maxCredits.
	 * @param firstName the Student's firstName
	 * @param lastName the Student's lastName
	 * @param id the Student's unity id
	 * @param email the Student's email address
	 * @param password the Student's hashed password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}
	
	/**
	 * Returns the Student's maximum number of credit hours.
	 * @return the Student's maximum number of credit hours
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the maximum number of credits hours a Student can take.
	 * Students must take between 3 and 18 credits hours, inclusive.
	 * An IllegalArgumentException is throw if maxCredits is out of
	 * this range.
	 * @param maxCredits the new number of credit hours
	 */
	public void setMaxCredits(int maxCredits) {
		if(maxCredits < 3 || maxCredits > 18){
			throw new IllegalArgumentException("Invalid max credits");
		}
		
		this.maxCredits = maxCredits;
	}


	/**
	 * Represents the Student as a String.
	 * @return the Student's description as a String
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCredits;
	}

	/**
	 * Compares this Student to Student s. Students are first compared for alphabetic order by their
	 * last names, then their first names, then their unity id.
	 * @param s the Student to compare to
	 * @return a negative integer if this Student comes before s, 0 if they are equivalent, and a
	 * positive integer if this Student comes after s
	 */
	@Override
	public int compareTo(Student s) {
		String thisStudent = getLastName() + getFirstName() + getId();
		String otherStudent = s.getLastName() + s.getFirstName() + s.getId();
		return thisStudent.compareTo(otherStudent);
	}

	/**
	 * Returns the hashcode for this Student
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Determines whether two Students are equal. Students are considered equal if they have the same 
	 * values for all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}
}
