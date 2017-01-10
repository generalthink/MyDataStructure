package com.generalthink.common.datastructure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * 
 * @ClassName: MyLinkedListLikeJdk
 * @Description: 
 *               和MyLinkedList一样的实现方式,有区别的是head和tail节点不是虚拟的节点，而是真实的数据节点,这里和JDK中保持一致
 *               1, ... ... (这里用一句话描述这个类的主要功能1, 可选) 2, ... ...
 *               (这里用一句话描述这个类的主要功能2, 可选)
 * @author TKing
 * @date 2017年1月9日 上午11:32:03
 */
public class MyLinkedListLikeJdk<T> extends AbstractList<T> {

	// 头节点
	private Node<T> head;

	// 尾节点
	private Node<T> tail;

	// 链表数据数量
	private int size;

	// 用于保证迭代器遍历时数据的一致性
	private int modalCount;

	public MyLinkedListLikeJdk() {

	}

	@Override
	public T get(int index) {
		rangeCheckForElements(index);

		return getNode(index).value;
	}

	@Override
	public T set(int index, T element) {

		rangeCheckForElements(index);

		Node<T> node = getNode(index);
		T oldValue = node.value;
		node.value = element;

		modalCount++;

		return oldValue;
	}

	@Override
	public void add(int index, T element) {
		rangeCheck(index);
		if (index == size) {
			linkLast(index, element);
		} else {
			linkBefore(element, getNode(index));
		}
		size++;
		modalCount++;
	}

	// 考虑两种情况,1. 已经存在一个元素,插入元素到第一个位置 2. 存在多个元素,插入元素的位置在集合中间
	private void linkBefore(T element, Node<T> node) {
		assert null != node;
		Node<T> prev = node.prev;
		Node<T> newNode = new Node<T>(prev, element, node);
		node.prev = newNode;
		if (null == prev) {
			// 此时属于情况1,已经存在了一个元素,然后插入元素到第一个位置
			head = newNode;
		} else {
			// 情况2
			prev.next = newNode;
		}
	}

	// 考虑两种情况,1. 插入第一个元素 2. 插入元素到最后一个位置
	private void linkLast(int index, T element) {
		Node<T> node = tail;
		Node<T> newNode = new Node<T>(node, element, null);
		tail = newNode;
		if (null == node) {
			// 情况1
			head = newNode;
		} else {
			// 情况2
			node.next = newNode;
		}
	}

	private Node<T> getNode(int index) {
		Node<T> node = null;
		// 数据集中在前半部分,从前往后遍历
		if (index < size / 2) {
			node = head;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		} else {
			// 数据集中在后半部分,从后往前遍历
			node = tail;
			for (int i = size - 1; i > index; i--) {
				node = node.prev;
			}
		}

		return node;
	}

	private void rangeCheckForElements(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException("index = " + index + ",size = " + size);
		}
	}

	@Override
	public T remove(int index) {

		rangeCheckForElements(index);
		Node<T> removeNode = getNode(index);
		Node<T> nextNode = removeNode.next;
		Node<T> prevNode = removeNode.prev;
		if (null != nextNode) {
			nextNode.prev = prevNode;
		} else {
			tail = prevNode;
		}
		if (null != prevNode) {
			prevNode.next = nextNode;
		} else {
			head = nextNode;
		}
		size--;
		modalCount++;

		return removeNode.value;
	}

	private void rangeCheck(int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("index = " + index + ",size = " + size);
		}
	}

	@Override
	public Iterator<T> iterator() {

		return new MyIterator();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Iterator<T> it = iterator();
		while (true) {
			if (it.hasNext()) {
				sb.append(it.next() + ",");
			} else {
				break;
			}
		}
		if (sb.indexOf(",") > -1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public int size() {
		return size;
	}

	private class MyIterator implements Iterator<T> {

		private boolean allowNext;

		private int expectCount;

		private boolean allowRemove;

		private int nextIndex;

		public MyIterator() {
			allowNext = false;
			allowRemove = false;
			expectCount = modalCount;
			nextIndex = -1;
		}

		@Override
		public boolean hasNext() {
			boolean hasNext = false;
			if (size > 0 && nextIndex < size - 1) {
				hasNext = true;
				allowNext = true;
				nextIndex++;
			}
			return hasNext;
		}

		@Override
		public T next() {
			if (!allowNext) {
				throw new IllegalStateException("you must invoke hasNext method");
			}

			checkForModification();

			T value = MyLinkedListLikeJdk.this.get(nextIndex);

			// 必须保证每次调用了hasNext方法之后才能调用next方法,然而JDK中不需要调用hasNext方法即可调用next
			allowNext = false;

			// 必须保证每次调用了next方法之后才可以调用remove方法
			allowRemove = true;

			return value;
		}

		private void checkForModification() {
			if (expectCount != modalCount) {
				throw new ConcurrentModificationException();
			}
		}

		@Override
		public void remove() {

			if (!allowRemove) {
				throw new IllegalStateException("you must invoke next method");
			}
			checkForModification();

			MyLinkedListLikeJdk.this.remove(nextIndex);
			// 移除元素之后,下一个元素的索引需要重新定位
			nextIndex--;
			expectCount = modalCount;

			allowRemove = false;
		}

	}

	@SuppressWarnings("hiding")
	private class Node<T> {

		// 链表节点的前一个节点
		private Node<T> prev;

		// 节点的后一个节点
		private Node<T> next;

		// 节点值
		private T value;

		public Node(Node<T> prevNode, T value, Node<T> nextNode) {
			this.prev = prevNode;
			this.value = value;
			this.next = nextNode;
		}
	}
}
