package com.generalthink.common.datastructure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;


public class MyLinkedList<T> extends AbstractList<T> implements Iterable<T>{

  //头节点
  private Node<T> head;
  
  //尾节点
  private Node<T> tail;
  
  //链表数据数量
  private int size;
  
  //用于保证迭代器遍历时数据的一致性
  private int modalCount;
  
  public MyLinkedList() {
    //初始化时构造头节点以及尾节点
    head = new Node<T>(null,null,null);
    tail = new Node<T>(head,null,null);
    head.next = tail;
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
  private Node<T> getNode(int index) {
    
    Node<T> node = null;
    //数据集中在前半部分,从前往后遍历
    if(index < size /2) {
      node = head;
      for(int i = 0;i <= index;i++) {
        node = node.next;
      }
    } else {
      //数据集中在后半部分,从后往前遍历
      node = tail;
      for(int i = size-1;i >= index;i--) {
        node = node.prev;
      }
    }
    
    return node;
  }
  private void rangeCheckForElements(int index) {
    if(index >= size || index < 0) {
      throw new IndexOutOfBoundsException("index = " + index + ",size = "  + size);
    }
  }
  
  @Override
  public void add(int index, T element) {
    rangeCheck(index);
   
    if(index == size) {
      Node<T> newNode = new Node<T>(tail.prev, element, tail);
      tail.prev.next = newNode;
      tail.prev = newNode;
    } else {
      Node<T> node = getNode(index);
      Node<T> newNode = new Node<T>(node.prev, element, node);
      node.prev.next = newNode;
      node.prev = newNode;
    }
    size++;
    modalCount++;
  }
  
  @Override
  public T remove(int index) {

    rangeCheckForElements(index);
    Node<T> needRemoveNode = getNode(index);
    Node<T> nextNode = needRemoveNode.next;
    Node<T> prevNode = needRemoveNode.prev;
    if(null != nextNode) {
    	nextNode.prev = prevNode;
    } else {
    	tail = prevNode;
    }
    if(null != prevNode) {
    	prevNode.next = nextNode;
    } else {
    	head = nextNode;
    }
    size--;
    modalCount++;
    
    return needRemoveNode.value;
  }
  
  
  private void rangeCheck(int index) {

    if(index < 0 || index > size) {
      throw new IndexOutOfBoundsException("index = " + index + ",size = "  + size);
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
    while(true) {
      if(it.hasNext()) {
        sb.append(it.next() + ",");
      } else {
        break;
      }
    }
    sb.deleteCharAt(sb.length() - 1);
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
      if(size > 0 && nextIndex < size-1) {
        hasNext = true;
        allowNext = true;
        nextIndex++;
      }
      return hasNext;
    }

    @Override
    public T next() {
      if(!allowNext) {
        throw new IllegalStateException("you must invoke hasNext method");
      }
      
      checkForModification();
      
      T value =  MyLinkedList.this.get(nextIndex);
      
      //必须保证每次调用了hasNext方法之后才能调用next方法,然而JDK中不需要调用hasNext方法即可调用next
      allowNext = false;
      
      //必须保证每次调用了next方法之后才可以调用remove方法
      allowRemove = true;
      
      return value;
    }

    private void checkForModification() {
      if(expectCount != modalCount) {
        throw new ConcurrentModificationException();
      }
    }

    @Override
    public void remove() {
      
      if(!allowRemove) {
        throw new IllegalStateException("you must invoke next method");
      }
      checkForModification();
      
      MyLinkedList.this.remove(nextIndex);
      
      //移除元素之后,下一个元素的索引需要重新定位
      nextIndex--;
      expectCount = modalCount;
      
      allowRemove = false;
    }
    
  }
  
  @SuppressWarnings("hiding")
  private class Node<T> {
    
    //链表节点的前一个节点
    private Node<T> prev;
    
    //节点的后一个节点
    private Node<T> next;
    
    //节点值
    private T value;
    
    public Node(Node<T> prevNode,T value,Node<T> nextNode) {
      this.prev = prevNode;
      this.value = value;
      this.next = nextNode;
    }
  }
}
