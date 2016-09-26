package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Reads and writes Student records to files.
 * @author Boyang Zhang, Connor Hall
 */
public class StudentRecordIO {

	/**
	 * Reads in Student records for the given file.
	 * @param fileName the name of the file to read
	 * @return an SortedList of Students constructed from the file
	 * @throws FileNotFoundException if the file cannot be found
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));
	    SortedList<Student> students = new SortedList<Student>();
	    while (fileReader.hasNextLine()) {
	        try {
	        	Student student = processStudent(fileReader.nextLine());
	        	students.add(student);

	            boolean duplicate = false;
	            for (int i = 0; i < students.size(); i++) {
	                Student s = students.get(i);
	                if (student.getId().equals(s.getId())) {
	                    //it's a duplicate
	                    duplicate = true;
	                }
	            }
	            if (!duplicate) {
	                students.add(student);
	            }
	        } catch (IllegalArgumentException e) {
	            //skip the line
	        }
	    }
	    fileReader.close();
	    return students;
	}
	
	private static Student processStudent(String line) {
		Scanner lineReader = new Scanner(line);
		lineReader.useDelimiter(",");
		try {
			String firstName = lineReader.next();
			String lastName = lineReader.next();
			String id = lineReader.next();
			String email = lineReader.next();
			String password = lineReader.next();
			int maxCredits = lineReader.nextInt();
			lineReader.close();
			return new Student(firstName, lastName, id, email, password, maxCredits);
		} catch (NoSuchElementException e) {
			lineReader.close();
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Writes the Students in studentDirectory to the given file one Student at a time.
	 * @param fileName the name of the file to write to
	 * @param studentDirectory the SortedList of students to output
	 * @throws IOException if unable to write to the file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < studentDirectory.size(); i++) {
		    fileWriter.println(studentDirectory.get(i).toString());
		}

		fileWriter.close();
	}

}
