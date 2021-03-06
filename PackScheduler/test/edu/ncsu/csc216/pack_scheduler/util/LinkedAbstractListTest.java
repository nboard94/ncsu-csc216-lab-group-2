package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
/**
 * Tests the Linked Abstract List class
 * @author Connor Hall
 * @author Renata Ann Zeitler
 * @author Nick Board
 *
 */

public class LinkedAbstractListTest {

	/**
	 * Tests the LinkedAbstractList constructor.
	 */
	@Test
	public void testLinkedAbstractList() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(0);
		assertEquals(0, a.size());
		
		try {
			a = new LinkedAbstractList<>(-1);
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot have negative capacity;
		}
	}
	
	/**
	 * Tests LinkedAbstractList.add()
	 */
	@Test
	public void testAdd() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(10);
		assertEquals(0, a.size());
		
		// Add to empty
		a.add(0, "apple");
		assertEquals(1, a.size());
		assertEquals("apple", a.get(0));
		
		// Add to end
		a.add(1, "banana");
		assertEquals(2, a.size());
		assertEquals("apple", a.get(0));
		assertEquals("banana", a.get(1));
		
		// Add to beginning
		a.add(0, "cantalope");
		assertEquals(3, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("apple", a.get(1));
		assertEquals("banana", a.get(2));
		
		// Add to middle
		a.add(1, "orange");
		assertEquals(4, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("apple", a.get(2));
		assertEquals("banana", a.get(3));
		
		try {
			a.add(-1, "kiwi");
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot add to negative index
		}
		assertEquals(4, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("apple", a.get(2));
		assertEquals("banana", a.get(3));
		
		try {
			a.add(5, "kiwi");
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot add beyond size
		}
		assertEquals(4, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("apple", a.get(2));
		assertEquals("banana", a.get(3));
		
		try {
			a.add(4, "apple");
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot add duplicate
		}
		assertEquals(4, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("apple", a.get(2));
		assertEquals("banana", a.get(3));
		
		try {
			a.add(4, null);
			fail();
		} catch (NullPointerException e) {
			// Cannot add null
		}
		assertEquals(4, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("apple", a.get(2));
		assertEquals("banana", a.get(3));
		
		// Extend capacity
		try {
			for (int i = a.size(); i < 20; i++) {
				a.add(i, "" + i);
			}
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(10, a.size());
		}
	}
	
	
	/**
	 * Tests LinkedAbstractList.remove()
	 */
	@Test
	public void testRemove() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(10);
		assertEquals(0, a.size());
		
		a.add(0, "apple");
		a.add(1, "banana");
		a.add(2, "cantalope");
		a.add(3, "orange");
		assertEquals(4, a.size());
		
		try {
			a.remove(4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Unable to remove when index == size
		}
		
		try {
			a.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Unable to remove when index < 0
		}
		
		// Remove from end
		assertEquals("orange", a.remove(3));
		assertEquals("apple", a.get(0));
		assertEquals("banana", a.get(1));
		assertEquals("cantalope", a.get(2));
		assertEquals(3, a.size());
		
		// Remove from middle
		assertEquals("banana", a.remove(1));
		assertEquals("apple", a.get(0));
		assertEquals("cantalope", a.get(1));
		assertEquals(2, a.size());
		
		// Remove from beginning
		assertEquals("apple", a.remove(0));
		assertEquals("cantalope", a.get(0));
		assertEquals(1, a.size());
	}
	
	/**
	 * Tests LinkedAbstractList.set().
	 */
	@Test
	public void testSet() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(10);
		assertEquals(0, a.size());
		
		// Set to empty
		try {
			a.set(0, "apple");
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot set when empty
		}
		assertEquals(0, a.size());
		
		a.add(0, "apple");
		a.add(1, "banana");
		
		// Set at end
		assertEquals("banana", a.set(1, "peach"));
		assertEquals(2, a.size());
		assertEquals("apple", a.get(0));
		assertEquals("peach", a.get(1));
		
		// Set at beginning
		assertEquals("apple", a.set(0, "cantalope"));
		assertEquals(2, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("peach", a.get(1));

		a.add(2, "blueberry");
		assertEquals(3, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("peach", a.get(1));
		assertEquals("blueberry", a.get(2));
		
		// Set in middle
		assertEquals("peach", a.set(1, "orange"));
		assertEquals(3, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("blueberry", a.get(2));
		
		try {
			a.set(-1, "kiwi");
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot set to negative index
		}
		assertEquals(3, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("blueberry", a.get(2));
		
		try {
			a.set(5, "kiwi");
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot set beyond size
		}
		assertEquals(3, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("blueberry", a.get(2));
		
		try {
			a.set(0, "orange");
			fail();
		} catch (IllegalArgumentException e) {
			// Cannot set duplicate
		}
		assertEquals(3, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("blueberry", a.get(2));
		
		try {
			a.set(0, null);
			fail();
		} catch (NullPointerException e) {
			// Cannot set null
		}
		assertEquals(3, a.size());
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("blueberry", a.get(2));
		
		// Set another middle
		a.add(3, "mango");
		a.add(4, "grape");
		assertEquals("mango", a.set(3, "durian"));
		assertEquals("cantalope", a.get(0));
		assertEquals("orange", a.get(1));
		assertEquals("blueberry", a.get(2));
		assertEquals("durian", a.get(3));
		assertEquals("grape", a.get(4));
	}
	
	/**
	 * Tests LinkedAbstractList.get()
	 */
	@Test
	public void testGet() {
		LinkedAbstractList<String> a = new LinkedAbstractList<String>(10);
		try {
			a.get(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot get element from empty list
		}
		
		a.add(0, "apple");
		
		try {
			a.get(1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot get element when index == size
		}
		
		try {
			a.get(2);
			fail();
		} catch(IndexOutOfBoundsException e) {
			// Cannot get element at size
		}
		
		try {
			a.get(10);
			fail();
		} catch(IndexOutOfBoundsException e) {
			// Cannot get element beyond size
		}
		
		assertEquals("apple", a.get(0));
	}
}