package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Represents an Activity object with information about its title, meeting days
 * and meeting times. Implements Conflict interface to allow clients to check for
 * conflicts between two instances.
 * @author Connor Hall
 */
public abstract class Activity implements Conflict {

	/** The title of this Activity. */
	private String title;
	/** The meeting days of this Activity. */
	private String meetingDays;
	/** The start time of this Activity. */
	private int startTime;
	/** The end time of this Activity. */
	private int endTime;
	
	/**
	 * Checks for Conflicts between this and the given Activity. Two Activities are 
	 * considered to be conflicting if their times ever overlap on the same day.
	 * Activities are also considered to be conflicting if one ends the same minute
	 * another begins. Arranged Activities never overlap because they do not occur
	 * at a specific time.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		// Conflicting if same day appears in both && times do not overlap
		if (this.meetingDays.equals("A") || possibleConflictingActivity.meetingDays.equals("A")) {
			return;
		}
		
		for (int i = 0; i < this.meetingDays.length(); i++) {
			for (int j = 0; j < possibleConflictingActivity.meetingDays.length(); j++) {
				if (meetingDays.charAt(i) == possibleConflictingActivity.meetingDays.charAt(j)) {
					if (this.startTime <= possibleConflictingActivity.startTime) {
						if (this.endTime >= possibleConflictingActivity.startTime) {
							throw new ConflictException();
						}
						return;
					}
					if (this.startTime <= possibleConflictingActivity.endTime) {
						throw new ConflictException();
					}
				}
			}
		}
	}

	/**
	 * Returns a short description of Activity information.
	 * @return the array with Activity information
	 */
	public abstract String[] getShortDisplayArray();
	
	/** 
	 * Returns a long description of Activity information.
	 * @return the array with Activity information
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Checks whether the given Activity is a duplicate of this Activity.
	 * @param activity the Activity to check for duplication
	 * @return true if it is a duplicate, false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Constructs a new Activity with information about its title, meeting
	 * days, start time, and end time.
	 * @param title the Activity's title
	 * @param meetingDays the days the Activity meets
	 * @param startTime the Activity's start time
	 * @param endTime the Activity's end time
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
	}

	/**
	 * Returns the Activity's title.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activity title. If the title is null or is an empty string, 
	 * IllegalArgumentException is thrown.
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is null or is an empty string
	 */
	public void setTitle(String title) {
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException("Invalid title");
		}
		this.title = title;
	}

	/**
	 * Returns the Activity's meeting days.
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the Activity's meeting days.
	 * @param meetingDays the meetingDays to set
	 */
	public void setMeetingDays(String meetingDays) {
		this.meetingDays = meetingDays;
	}

	/**
	 * Returns the Activity's meeting days followed by the Activity's start and end times in standard format.
	 * Returns "Arranged" if meetingDays is "A".
	 * @return the meetingString
	 */
	public String getMeetingString() {
		if (getMeetingDays().equals("A")) {
			return "Arranged";
		}
		return getMeetingDays() + " " + toStandardTime(getStartTime()) + "-" + toStandardTime(getEndTime());
	}

	/**
	 * Returns the Activity's start time.
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Activity's end time.
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the Activity's start and end times. If the times are invalid military
	 * times or if the end time is before the start time or if times are listed when meeting
	 * days is 'A', IllegalArgumentException is thrown.
	 * @param startTime the Activity's start time
	 * @param endTime the Activity's end time
	 * @throws IllegalArgumentException if the times are invalid military
	 * times, the end time is before start time, or if times are listed when meeting
	 * days is 'A'
	 */
	public void setActivityTime(int startTime, int endTime) {
		if (getMeetingDays().equals("A") && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		if (!isValidTime(startTime) || !isValidTime(endTime)) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns false if time is less than 0000 or greater than 2359 or if 
	 * the last two digits are greater than 59
	 * @param time the time to validate
	 * @return true if valid otherwise false
	 */
	private boolean isValidTime(int time) {
		return time >= 0 && time <= 2359 && time % 100 <= 59;
	}

	/**
	 * Takes a time and returns a String representation of the time in standard format.
	 * @param time the time to convert
	 * @return time String in standard format
	 * @throws IllegalArgumentException if time is not a valid military time
	 */
	private String toStandardTime(int time) {
		if (!isValidTime(time)) {
			throw new IllegalArgumentException();
		}
		int minutes = time % 100;
		int hours = time / 100;
		String half = "AM";
		
		if (hours == 0) {
			hours = 12;
			half = "AM";
		} else if (hours == 12) {
			half = "PM";
		} else if (hours > 12) {
			hours -= 12;
			half = "PM";
		}
		return String.format("%d:%02d%s", hours, minutes, half);
	}
	
	/**
	 * Generates a hashCode for Activity using all fields.
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}

