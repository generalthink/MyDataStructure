package com.generalthink.common.datastructure;

/*队列是先进先出*/
public class MyQueue<T> extends MyLinkedListLikeJdk<T> {

	// 入队
	public boolean offer(T e) {
		super.add(size(), e);
		return true;
	}

	// 出队
	public T poll() {
		if(size() == 0) {
			return null;
		}
		return remove(0);
	}
}
