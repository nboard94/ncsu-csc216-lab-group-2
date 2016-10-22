package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.Activity;
import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;


/**
 * Schedule class 
 * 
 * @author Connor Hall
 * @author Renata Ann Zeitler
 */
public class Schedule {
	
	//List containing current courses
	private ArrayList<Course> schedule;
	//Title of schedule
	private String title;
	
	/**
	 * Constructor
	 */
	public Schedule() {
		this.schedule = new ArrayList<Course>();
		this.title = "My Schedule";
	}
	
	/**
	 * Creates a 2D array of courses in schedule
	 * @return display of courses in schedule or empty array
	 */
	public String[][] getScheduledCourses() {
		if (schedule.size() == 0){
			String[][] empty = new String [0][0];
			return empty;
		}
		int row = schedule.size();
		int col = 4;
		
		String[][] display = new String [row][col];
		for(int i = 0; i < row; i++){
			display[i] = schedule.get(i).getShortDisplayArray();
		}
		return display;
	}
	
	/**
	 * Gets schedule name
	 * @return title of schedule
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Adds Course to schedule if possible. Checks to see if a Course has already been added to 
	 * schedule or if the new Course has time conflicts with other Activities already added.
	 * @param c the Course to add to the Schedule
	 * @return true if Course can be added, false if not
	 * @throws IllegalArgumentException if this Schedule already contains a duplicate or
	 * equivalent course or if there is a scheduling conflict
	 * @throws NullPointerException if c is null
	 */
	public boolean addCourseToSchedule(Course c) {
		if (c == null) {
			throw new NullPointerException();
		}
		
		//Checks to see if Course is already in Schedule
		for (int i = 0; i < schedule.size(); i++) {
			Activity activity = schedule.get(i);
			if (activity.equals(c) || activity.isDuplicate(c)) {
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			}
		}
		
		//Tests checkConflict
		try {
			for (int i = 0; i < schedule.size(); i++) {
				schedule.get(i).checkConflict(c); 
			}
		} catch(ConflictException e) {
			throw new IllegalArgumentException("The course cannot be added due to a conflict.");
		}

		return schedule.add(c);
	}
	
	/**
	 * Removes course from schedule
	 * @param c the Course to remove from the Schedule
	 * @return true if it can be removed, false if otherwise
	 */
	public boolean removeCourseFromSchedule(Course c) {
		if (c == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			Course d = schedule.get(i);
			if (d.getName().equals(c.getName()) && d.getSection().equals(c.getSection())) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Resets schedule
	 */
	public void resetSchedule() {
		this.schedule.clear();	
	}
	
	/**
	 * Sets title
	 * @param titleNew sets new title name
	 * @throws IllegalArgumentException if null
	 */
	public void setTitle(String titleNew) {
		if(titleNew == null) {
			throw new IllegalArgumentException("Title cannot be null");
		}
		this.title = titleNew;
	}
	
	/**
	 * Gets a cumulative sum of total credits in schedule
	 * @return count is the sum of total credits
	 */
	public int getScheduleCredits() {
		int count = 0;
		for (int i = 0; i < schedule.size(); i++) {
			count = count + schedule.get(i).getCredits();
		}
		
		return count;
	}
	
	/**
	 * Checks to see if course can be added to schedule
	 * @param c Course Student wants to add
	 * @return true if the Course can be added to the schedule. If the Course is null, if the Course is already in the schedule, 
	 * or if there is a conflict, canAdd() will return false.
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		//Checks if already enrolled
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i) == c) {
				return false;
			}
		}
		//Tests checkConflict
		try {
			for (int i = 0; i < schedule.size(); i++) {
				schedule.get(i).checkConflict(c); 
			}
		} catch(ConflictException e) {
			throw new IllegalArgumentException("The course cannot be added due to a conflict.");
		}
		return true;
	}
	
}
