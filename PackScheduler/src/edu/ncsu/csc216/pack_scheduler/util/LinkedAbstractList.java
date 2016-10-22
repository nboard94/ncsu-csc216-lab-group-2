package edu.ncsu.csc216.pack_scheduler.util;

import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Linked Abstract List class
 * 
 * @author Connor Hall
 * @author Renata Ann Zeitler
 * @author Nick Board
 *
 * @param <E> the object held by the Linked Abstract List
 */
public class LinkedAbstractList<E> extends ArrayList<E> {
	
	private ListNode<E> front;
	/** Size of the list */
	private int size;
	/**Max size of list */
	private int capacity;
	
	/**
	 * Constructor
	 * @param capacity max amount allowed in this List
	 */
	public LinkedAbstractList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		front = null;
		size = 0;
		this.capacity = capacity;
	}
	
	/**
	 * Returns the number of elements in this list.
	 * @return the number of elements in the list
	 */
	public int size() {
		return size;
	}
	
	
	/**
	 * Inserts the element at the specified index in the list.
	 * @param index the location to add the element
	 * @param element the element to add
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is already in the list
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater
	 * than the ArrayList's size.
	 */
	public void add(int index, E element) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (index == capacity) {
			throw new IllegalArgumentException();
		}
		
		// Check if duplicate
		ListNode<E> current = front;
		for (int i = 0; i < size; i++) {
			if (element.equals(current.data)) {
				throw new IllegalArgumentException();
			}
			current = current.next;
		}
		
		
		// Check if capacity too high
		
		if (front == null) {
			front = new ListNode<E>(element);
		} else if (index == 0) {
			ListNode<E> oldFront = front;
			front = new ListNode<E>(element);
			front.next = oldFront;
		} else {
			
			current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			
			ListNode<E> old = current.next;
			current.next = new ListNode<E>(element);
			current.next.next = old;
		}
		size++;
	}
	
	/**
	 * Removes the element from the specified index in the list.
	 * @param index the location to remove from
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than
	 * or equals to this ArrayList's size
	 */
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode<E> rtn;
		if (index == 0) {
			rtn = front;
			front = front.next;
		} else {
			ListNode<E> current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			rtn = current.next;
			current.next = current.next.next;
		}
		size--;
		return rtn.data;
	}
	
	/**
	 * Returns the element at the specified position in the list
	 * @param index the index of the element
	 * @return the element at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode<E> current = front;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.data;
	}
	
	/**
	 * Replaces the element at the specified index with the given element.
	 * @param index index of the element to replace
	 * @param element the element to stored at the given index
	 * @return the element previously at the specified position
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is already in the list
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than 
	 * this ArrayList's size.
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}

		// Check if duplicate
		ListNode<E> current = front;
		for (int i = 0; i < size; i++) {
			if (current.data.equals(element)) {
				throw new IllegalArgumentException();
			}
			current = current.next;
		}
		
		ListNode<E> rtn;
		if (index == 0) {
			rtn = front;
			ListNode<E> old = front.next;
			front = new ListNode<E>(element);
			front.next = old;
		} else {
			current = front;
			
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			rtn = current.next;
			ListNode<E> old = current.next.next;
			current.next = new ListNode<E>(element);
			current.next.next = old;
			
		}
		return rtn.data;
	}
	
	/**
	 * ListNode class
	 * 
	 * @author Connor Hall
	 * @author Renata Ann Zeitler
	 * @author Nick Board
	 * 
	 * @param <T> The generic type to represent the object that can be added.
	 */
	private class ListNode<T> {
		public T data;
		public ListNode<T> next;
		
		public ListNode(T data) {
			this(data, null);
		}
		
		public ListNode(T data, ListNode<T> next) {
			this.data = data;
			this.next = next;
		}
	}

}
