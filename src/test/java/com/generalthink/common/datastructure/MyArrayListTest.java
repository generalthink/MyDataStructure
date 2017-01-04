package com.generalthink.common.datastructure;

import junit.framework.TestCase;


public class MyArrayListTest extends TestCase{

  
  public void testAdd() {
    MyArrayList<Integer> list = new MyArrayList<Integer>(20);
    for(int i = 0;i < 30;i++) {
      list.add(i,i);
    }
    assertEquals(list.size(), 30);
    System.out.println("testAdd success");
  }
  
  public void testRemove() {
    MyArrayList<Integer> list = new MyArrayList<Integer>();
    for(int i = 0;i < 6;i++) {
      list.add(i,i);
    }
    list.remove(4);
    list.remove(4);
    list.remove(4);
    assertEquals(list.toString(), "[0, 1, 2]");
    System.out.println("testRemove success");
  }
  
  public void testGet() {
    MyArrayList<Integer> list = new MyArrayList<Integer>(-2);
    for(int i = 0;i < 10;i++) {
      list.add(i,i);
    }
   
    assertEquals(list.get(2).intValue(), 2);
    assertEquals(list.get(3).intValue(), 3);
    assertEquals(list.get(9).intValue(), 9);
    
    System.out.println("testGet success");
  }
  
  public void testSet() {
    MyArrayList<Integer> list = new MyArrayList<Integer>();
    list.add(0,-1);
    list.add(1,-2);
    
    list.set(0, 0);
    list.set(1, 2);
    assertTrue(list.get(1) == 2);
    System.out.println("testSet success");
  }
  
}
