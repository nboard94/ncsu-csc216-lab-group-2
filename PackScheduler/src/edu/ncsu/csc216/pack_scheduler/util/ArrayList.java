package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A mutable ordered set of elements. Elements may not be duplicates,
 * and the number of elements may change.
 * @author Connor Hall
 * @author Renata Zeitler
 * 
 * @param <E> the type of Object held by the ArrayList
 */
public class ArrayList<E> extends AbstractList<E> {
	private static final int INIT_SIZE = 10;
	private E[] list;
	private int size;
	
	/**
	 * Creates a new, empty Arraylist with the default capacity.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[])(new Object[INIT_SIZE]);
		size = 0;
	}
	
	/**
	 * Returns the element at the specified position in the list
	 * @param index the index of the element
	 * @return the element at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}
	
	/**
	 * Returns the number of elements in this list.
	 * @return the number of elements in the list
	 */
	@Override
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
		if (element == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (size == list.length) {
			growArray();
		}
		
		if (index == size) {
			list[index] = element;
		} else {
			for (int i = size; i > index; i--) {
				list[i] = list[i - 1];
			}
			list[index] = element;
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
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E element = list[index];
		
		for (int i = index; i < size - 1; i++) {
			list[i] = list[i+1];
		}
		
		list[--size] = null;
		return element;
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
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size(); i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		if (index == size()) {
			if (size() == list.length) {
				growArray();
			}
			size++;
		}
		
		E oldElement = list[index];
		list[index] = element;
		return oldElement;
	}
	
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] newList = (E[])(new Object[list.length * 2]);
		for (int i = 0; i < list.length; i++) {
			newList[i] = list[i];
		}
		list = newList;
	}	
}
