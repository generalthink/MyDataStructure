package com.generalthink.common.datastructure;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class MyLinkedListLikeJdkTest {

  private MyLinkedListLikeJdk<Integer> list;
  
  @Before
  public void setUp() throws Exception {
    
    list = new MyLinkedListLikeJdk<Integer>();
    for(int i = 0;i < 5;i++) {
      list.add(i,i);
    }
  }

  @Test
  public void testAdd() {
    list.add(5, 5);
    list.add(0, -1);
    assertTrue(list.size() == 7);
    assertTrue(list.toString().equals("[-1,0,1,2,3,4,5]"));
  }

  @Test
  public void testGet() {
    assertTrue(list.get(0).equals(0));
    assertTrue(list.get(1).equals(1));
    assertTrue(list.get(2).equals(2));
    assertTrue(list.get(3).equals(3));
    assertTrue(list.get(4).equals(4));
    try {
      list.get(5);
    } catch (IndexOutOfBoundsException e) {
      fail("IndexOutOfBounds");
    }
    
  }
  
  @Test
  public void testRemove() {
    list.remove(2);
    assertTrue(list.size() == 4);
    assertTrue(list.get(0) == 0);
    assertTrue(list.get(1) == 1);
    assertTrue(list.get(2) == 3);
    assertTrue(list.get(3) == 4);
  }
  
  @Test
  public void testSet() {
    list.set(0, -1);
    assertTrue(list.size() == 5);
    assertTrue(list.get(0) == -1);
    try {
      list.set(5,5);
    } catch (Exception e) {
      fail("IndexOutOfBounds");
    }
  }
  
  @Test
  public void testIterator() {
    Iterator<Integer> it = list.iterator();
    try {
      it.next();
    } catch (Exception e) {
      fail(e.toString());
    }
  }
  @Test
  public void testRemoveUseIterator() {
	Iterator<Integer> it = list.iterator();
	while(it.hasNext()) {
		if(it.next() % 2 == 0) {
			it.remove();
		}
	}
	assertTrue(list.size() == 2);
  }
  
  @Test
  public void testIteratorModifi() {
    Iterator<Integer> it = list.iterator();
    while(it.hasNext()) {
      System.out.println(it.next());
      try {
        list.add(0, 10);
      } catch (Exception e) {
        fail("ConcurrentModifiy Exception");
      }
    }
  }

}
