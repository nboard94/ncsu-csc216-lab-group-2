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
	private int size;
	private int capacity;
	
	public LinkedAbstractList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		front = null;
		size = 0;
		this.capacity = capacity;
	}
	
	public int size() {
		return size;
	}
	
	
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
			front = new ListNode(element);
		} else if (index == 0) {
			ListNode<E> oldFront = front;
			front = new ListNode(element);
			front.next = oldFront;
		} else {
			
			current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			
			ListNode<E> old = current.next;
			current.next = new ListNode(element);
			current.next.next = old;
		}
		size++;
	}
	
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
