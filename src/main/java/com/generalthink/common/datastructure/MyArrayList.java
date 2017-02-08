package com.generalthink.common.datastructure;

public class MyArrayList<T> extends AbstractList<T> {

	// 数组初始化大小,默认为10
	private final int DEFAULT_CAPECITY = 10;

	// 集合实际大小
	private int size;

	// 保存数组数据的数组
	private Object[] elements;

	public MyArrayList() {
		elements = new Object[DEFAULT_CAPECITY];
	}

	public MyArrayList(int capecity) {

		if (capecity <= 0) {
			elements = new Object[DEFAULT_CAPECITY];
		} else {
			elements = new Object[capecity];
		}
	}

	@SuppressWarnings("unchecked")
	private T getElement(int index) {
		return (T) elements[index];
	}

	// add,set,get,remove
	@Override
	public T get(int index) {
		rangeCheckForElements(index);
		return getElement(index);
	}

	@Override
	public T set(int index, T element) {
		rangeCheckForElements(index);
		T oldElement = getElement(index);
		elements[index] = element;
		return oldElement;
	}

	private void rangeCheckForElements(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("index = " + index + ",size = " + size);
		}
	}

	@Override
	public void add(int index, T element) {
		rangeCheck(index);

		if (size == elements.length) {
			ensureCapecity(elements.length * 2);
		}
		// 向后进行移位操作
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}

		elements[index] = element;
		size++;
	}

	@Override
	public T remove(int index) {

		rangeCheck(index);
		T removeElement = getElement(index);
		// 向前移位
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
		return removeElement;
	}

	@Override
	public boolean add(T e) {
		add(size(),e);
		return true;
	}
	
	private void rangeCheck(int index) {
		// 这里不是index>=size是因为添加元素的时候是可以添加到集合末尾(size)
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("index = " + index + ",size = " + size);
		}
	}

	private void ensureCapecity(int newCapecity) {
		// 如果数组中已经装满了数据那么此时应该进行扩容了
		if (newCapecity < elements.length) {
			return;
		}
		Object[] oldElements = elements;
		elements = new Object[newCapecity];
		for (int i = 0, length = oldElements.length; i < length; i++) {
			elements[i] = oldElements[i];
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0;; i++) {
			sb.append(elements[i]);
			if (i != size - 1) {
				sb.append(", ");
			} else {
				break;
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
