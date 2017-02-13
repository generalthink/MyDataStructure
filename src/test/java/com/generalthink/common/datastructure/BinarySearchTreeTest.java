package com.generalthink.common.datastructure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeTest {

	private BinarySearchTree<Integer> tree;
	
	@Before
	public void setUp() throws Exception {
		tree = new BinarySearchTree<Integer>();
	}

	@Test
	public void testInsert() {
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
	}
	
	@Test
	public void testContains() {
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		Assert.assertTrue(tree.contains(2));
		Assert.assertFalse(tree.contains(6));
	}
	
	@Test
	public void testRemove() {
		tree.insert(3);
		tree.insert(1);
		tree.insert(5);
		tree.insert(4);
		tree.insert(2);
		tree.remove(2);
		System.out.println(tree);
	}
	
	

}
