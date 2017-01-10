package com.generalthink.common.datastructure;

import java.util.EmptyStackException;

public class MyStackWithArray<T> extends MyArrayList<T>{

  public T push(T element) {
	super.add(size(), element);
    return element;
  }
  
  public T pop() {
	if(size() == 0) {
		throw new EmptyStackException();
	}
	return super.remove(size()-1);
  }
}
