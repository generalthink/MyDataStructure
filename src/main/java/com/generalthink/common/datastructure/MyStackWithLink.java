package com.generalthink.common.datastructure;

import java.util.EmptyStackException;

public class MyStackWithLink<T> extends MyLinkedList<T> {

	public T push(T element) {
		super.add(size(), element);
		return element;
	}

	public T pop() {
		if (size() == 0) {
			throw new EmptyStackException();
		}
		return super.remove(size() - 1);
	}

	public T peek() {
		if (size() == 0) {
			throw new EmptyStackException();
		}
		return get(size()-1);
	}
	
	@Override
	public boolean isEmpty() {
		
		return size() == 0;
	}

}
