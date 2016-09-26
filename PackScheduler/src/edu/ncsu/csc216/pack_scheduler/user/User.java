package edu.ncsu.csc216.pack_scheduler.user;

public abstract class User {

	private String firstName;
	private String lastName;
	private String id;
	private String email;
	private String password;

	public User(String firstName, String lastName, String id, String email, String password) {
		super();
	}

	/**
	 * Returns the Student's first name.
	 * @return the Student's first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the Student's first name. An IllegalArgumentException is
	 * thrown if firstName is null or is an empty String.
	 * be an empty String.
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		if(firstName == null || firstName.equals("")){
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Returns the Student's last name.
	 * @return the Student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the Student's last name. An IllegalArgumentException is
	 * thrown if lastName is null or is an empty String.
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		if(lastName == null || lastName.equals("")){
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Returns the Student's email address.
	 * @return the Student's email address.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the Student's email address. An IllegalArgumentException
	 * is throw if email is null, is an empty string, does not contain
	 * an '@' and '.' character, or if the final '.' comes before '@'.
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		if(email == null || email.equals("")){
			throw new IllegalArgumentException("Invalid email");
		}
		if(email.indexOf('@') == -1){
			throw new IllegalArgumentException("Invalid email");
		}
		if(email.indexOf('.') == -1){
			throw new IllegalArgumentException("Invalid email");
		}
		if(email.lastIndexOf('.') < email.indexOf('@')){
			throw new IllegalArgumentException("Invalid email");
		}
		this.email = email;
	}

	/**
	 * Returns the Student's hashed password.
	 * @return the hashed password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the Student's hashed password. An IllegalArgumentException
	 * is thrown if password is null or is an empty String.
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if(password == null || password.equals("")){
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}
	
	/**
	 * Returns the Student's Unity id.
	 * @return the Student's Unity id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the Student's id. An id cannot be null or an empty string.
	 * @param id the id to set
	 */
	public void setId(String id) {
		if(id == null || id.equals("")){
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}