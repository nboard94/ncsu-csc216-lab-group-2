package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests Student class.
 * @author Boyang Zhang, Connor Hall
 */
public class SortedListTest {

	/**
	 * Tests basic SortedList functionality.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		assertFalse(list.contains("apple"));

		for (int i = 1; i <= 11; i++) {
			list.add(Integer.toString(i));
		}
		assertEquals(11, list.size());
	}

	/**
	 * Tests add() method.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		list.add("zzzzz");
		assertEquals(2, list.size());
		assertEquals("banana", list.get(0));
		assertEquals("zzzzz", list.get(1));

		list.add("monkey");
		assertEquals(3, list.size());
		assertEquals("banana", list.get(0));
		assertEquals("monkey", list.get(1));
		assertEquals("zzzzz", list.get(2));

		try {
			list.add(null);
			fail();
		} catch (NullPointerException e) {
			// Should throw an error
		}
		try {
			list.add("banana");
			fail();
		} catch (IllegalArgumentException e) {
			// Should not add duplilcates
		}
	}

	/**
	 * Tests get() method.
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		// Test getting an element from an empty list
		try {
			list.get(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot get element from empty list
		}

		// Add some elements to the list
		list.add("element");
		assertEquals("element", list.get(0));

		// Test getting an element at an index < 0
		try {
			list.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot get element from negative index
		}

		// Test getting an element at size
		try {
			list.get(list.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Should throw an error
		}

	}

	/**
	 * Tests remove() method.
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		try {
			list.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot remove an element from an empty list
		}
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		assertEquals("a", list.get(0));
		assertEquals("b", list.get(1));
		assertEquals("c", list.get(2));
		assertEquals("d", list.get(3));
		
		try {
			list.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot remove an element at an index < 0
		}
		try {
			list.remove(list.size());
			fail();
		} catch (IndexOutOfBoundsException e) {
			// Cannot remove an element at size
		}
		assertEquals("b", list.remove(1));
		assertEquals("a", list.get(0));
		assertEquals("c", list.get(1));
		assertEquals("d", list.get(2));
		
		assertEquals("d", list.remove(2));
		assertEquals("a", list.get(0));
		assertEquals("c", list.get(1));
		
		assertEquals("a", list.remove(0));
		assertEquals("c", list.get(0));
		
		assertEquals("c", list.remove(0));
		assertTrue(list.isEmpty());
	}

	/**
	 * Tests indexOf() method.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		// Test indexOf on an empty list
		assertEquals(-1, list.indexOf("a"));
		
		// Add some elements
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		assertEquals("a", list.get(0));
		assertEquals("b", list.get(1));
		assertEquals("c", list.get(2));
		assertEquals("d", list.get(3));

		// Test various calls to indexOf for elements in the list
		// and not in the list
		assertEquals(0, list.indexOf("a"));
		assertEquals(1, list.indexOf("b"));
		assertEquals(2, list.indexOf("c"));
		assertEquals(3, list.indexOf("d"));
		assertEquals(-1, list.indexOf("e"));

		// Test checking the index of null
		try {
			list.indexOf(null);
			fail();
		} catch (NullPointerException e) {
			// Cannot be null
		}
	}

	/**
	 * Tests clear() method.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		assertEquals("a", list.get(0));
		assertEquals("b", list.get(1));
		assertEquals("c", list.get(2));
		assertEquals("d", list.get(3));

		// Clear the list
		list.clear();
		
		// Test that the list is empty
		assertTrue(list.isEmpty());
	}
	
	/**
	 * Tests isEmpty() method.
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// Test that the list starts empty
		assertTrue(list.isEmpty());
		
		// Add at least one element
		list.add("a");
		assertEquals("a", list.get(0));
		
		// Check that the list is no longer empty
		assertFalse(list.isEmpty());
	}

	/**
	 * Tests contains() method.
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// Test the empty list case
		assertFalse(list.contains(null));
		assertFalse(list.contains("a"));
		
		// Add some elements
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		assertEquals("a", list.get(0));
		assertEquals("b", list.get(1));
		assertEquals("c", list.get(2));
		assertEquals("d", list.get(3));
		
		// Test some true and false cases
		assertTrue(list.contains("a"));
		assertTrue(list.contains("b"));
		assertTrue(list.contains("c"));
		assertTrue(list.contains("d"));
		assertFalse(list.contains("e"));
		assertFalse(list.contains("f"));
		assertFalse(list.contains("g"));
		assertFalse(list.contains("h"));
		
	}

	/**
	 * Tests equals() method.
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("a");
		list1.add("b");
		
		list2.add("a");
		list2.add("b");
		
		list3.add("c");
		list3.add("d");

		// Test for equality and non-equality
		assertEquals(list1, list2);
		assertEquals(list2, list1);
		assertNotEquals(list1, list3);
		assertNotEquals(list3, list2);
	}

	/**
	 * Tests hashCode() method.
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		list1.add("a");
		list1.add("b");
		
		list2.add("a");
		list2.add("b");
		
		list3.add("c");
		list3.add("d");

		// Test for the same and different hashCodes
		assertEquals(list1.hashCode(), list2.hashCode());
		assertEquals(list2.hashCode(), list1.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list3.hashCode(), list2.hashCode());
	}

}
