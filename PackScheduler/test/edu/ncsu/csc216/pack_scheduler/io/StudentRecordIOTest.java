package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.io.StudentRecordIO;

/**
 * Tests StudentRecordIO methods
 * @author Boyang Zhang, Connor Hall
 */
public class StudentRecordIOTest {

	private String hashPW;
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Generates the hashed password before each test is run.
	 */
	@Before
	public void setUp() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = new String(digest.digest());
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	//Test for 100% coverage purposes
	StudentRecordIO n = new StudentRecordIO();
	/**
	 * Tests readStudentRecords().
	 */
	@Test
	public void testReadStudentRecords() throws FileNotFoundException {
		SortedList<Student> actualStudents = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
		SortedList<Student> expectStudents = new SortedList<Student>();
		expectStudents.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		expectStudents.add(new Student("Cassandra", "Schwartz", "cschwartz", "semper@imperdietornare.co.uk", hashPW, 4));
		expectStudents.add(new Student("Shannon", "Hansen", "shansen", "convallis.est.vitae@arcu.ca", hashPW, 14));
		expectStudents.add(new Student("Demetrius", "Austin", "daustin", "Curabitur.egestas.nunc@placeratorcilacus.co.uk", hashPW, 18));
		expectStudents.add(new Student("Raymond", "Brennan", "rbrennan", "litora.torquent@pellentesquemassalobortis.ca", hashPW, 12));
		expectStudents.add(new Student("Emerald", "Frost", "efrost", "adipiscing@acipsumPhasellus.edu", hashPW, 3));
		expectStudents.add(new Student("Lane", "Berg", "lberg", "sociis@non.org", hashPW, 14));
		expectStudents.add(new Student("Griffith", "Stone", "gstone", "porta@magnamalesuadavel.net", hashPW, 17));
		expectStudents.add(new Student("Althea", "Hicks", "ahicks", "Phasellus.dapibus@luctusfelis.com", hashPW, 11));
		expectStudents.add(new Student("Dylan", "Nolan", "dnolan", "placerat.Cras.dictum@dictum.net", hashPW, 5));
		assertEquals( expectStudents.size(), actualStudents.size());
		for(int i = 0; i < actualStudents.size(); i++){
			assertEquals(expectStudents.get(i), actualStudents.get(i));
		}
		actualStudents = StudentRecordIO.readStudentRecords("test-files/invalid_student_records.txt");
		assertEquals(0, actualStudents.size());
		try{
			StudentRecordIO.readStudentRecords("abc.txt");
			fail();
		} catch (FileNotFoundException e) {
			// Threw the expected error
		}
	}

	/**
	 * Tests writeStudentRecords().
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
	    students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
	    try {
		    StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		    checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	    } catch (IOException e) {
	    	fail();
	    }
	}
	
	/**
	 * Tests writeStudentRecords with an invalid file location.
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
	    students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
	    //Assumption that you are using a hash of "pw" stored in hashPW
	    
	    try {
	        StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students);
	        fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
	    } catch (Exception e) {
	    	assertTrue(e instanceof IOException);
	    }
	}
	
	/**
	 * Asserts that two files contain the same output.
	 * @param expFile the file containing the expected output
	 * @param actFile the file containing the actual output
	 */
	private void checkFiles(String expFile, String actFile) {
	    try {
	        Scanner expScanner = new Scanner(new FileInputStream(expFile));
	        Scanner actScanner = new Scanner(new FileInputStream(actFile));
	        
	        while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
	            String exp = expScanner.nextLine();
	            String act = actScanner.nextLine();
	            assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
	        }
	        if (expScanner.hasNextLine()) {
	            fail("The expected results expect another line " + expScanner.nextLine());
	        }
	        if (actScanner.hasNextLine()) {
	            fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
	        }
	        
	        expScanner.close();
	        actScanner.close();
	    } catch (IOException e) {
	        fail("Error reading files.");
	    }
	}
}