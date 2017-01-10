package com.generalthink.common.datastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MyStackTest {

	private MyStackWithArray<Integer> arrayStack;

	private MyStackWithLink<Integer> linkStack;

	@Before
	public void setUp() throws Exception {
		arrayStack = new MyStackWithArray<Integer>();
		linkStack = new MyStackWithLink<Integer>();
	}

	@Test
	public void testPushWithArray() {
		for (int i = 0; i < 4; i++) {
			arrayStack.push(i + 1);
		}
		assertTrue(arrayStack.size() == 4);
		assertEquals(arrayStack.toString(), "[1, 2, 3, 4]");
	}

	@Test
	public void testPopWithArray() {
		for (int i = 0; i < 4; i++) {
			arrayStack.push(i + 1);
		}
		assertTrue(arrayStack.pop() == 4);
		assertTrue(arrayStack.pop() == 3);
		assertTrue(arrayStack.pop() == 2);
		assertTrue(arrayStack.pop() == 1);
	}

	@Test
	public void testPushWithLink() {
		for (int i = 0; i < 4; i++) {
			linkStack.push(i + 1);
		}
		assertTrue(linkStack.size() == 4);
		assertEquals(linkStack.toString(), "[1,2,3,4]");
	}

	@Test
	public void testPopWithLink() {
		for (int i = 0; i < 4; i++) {
			linkStack.push(i + 1);
		}
		assertTrue(linkStack.pop() == 4);
		assertTrue(linkStack.pop() == 3);
		assertTrue(linkStack.pop() == 2);
		assertTrue(linkStack.pop() == 1);
	}

}
