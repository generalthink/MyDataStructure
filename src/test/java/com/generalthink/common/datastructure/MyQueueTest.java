package com.generalthink.common.datastructure;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class MyQueueTest {

	private MyQueue<Integer> queue;
	
	@Before
	public void setUp() throws Exception {
		queue = new MyQueue<Integer>();
	}

	@Test
	public void testOffer() {
		for(int i = 0;i < 5;i++) {
			queue.offer(i+1);
		}
		assertTrue(queue.size() == 5);
		LinkedList<Integer> list = new LinkedList<Integer>();
		for(int i = 0;i < 5;i++) {
			list.offer(i+1);
		}
		System.out.println(list.poll());
		System.out.println(list.poll());
		System.out.println(list.poll());
		System.out.println(list.poll());
		System.out.println(list.poll());
	}
	
	@Test
	public void testPoll() {
		for(int i = 0;i < 5;i++) {
			queue.offer(i+1);
		}
		for(int i = 0;i < 5;i++) {
			queue.poll();
		}
		assertTrue(queue.size() == 0);
	}

}
