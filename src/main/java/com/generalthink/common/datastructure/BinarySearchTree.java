package com.generalthink.common.datastructure;



public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
	
	private BinaryNode<AnyType> root;
	
	public BinarySearchTree() {
		root = null;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	//实现insert,remove,contains方法
	
	public boolean contains(AnyType element) {
		return contain(element, root);
	}
	
	public void insert(AnyType element) {
		//每次插入的节点都作为根节点,由于每次根节点都在改变,所以会导致二叉树不平衡,解决方案见平衡二叉树
		root = insert(element,root);
	}
	
	public BinaryNode<AnyType> remove(AnyType element) {
		if(isEmpty()) {
			throw new RuntimeException("empty tree!");
		}
		return remove(element,root);
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder(32);
		sb.append("[");
		print(root, sb);
		sb.append("]");
		
		return sb.toString();
	}
	
	private void print(BinaryNode<AnyType> node,StringBuilder result) {
		if(null == node) {
			return;
		}
		result.append(node.element + ",");
		print(node.left, result);
		print(node.right, result);
	}
	
	/**
	 * 删除一个元素.
	 * @param element 需要删除的元素
	 * @param rootNode 子树的根节点
	 * @return 子树的根节点
	 */
	private BinaryNode<AnyType> remove(AnyType element, BinaryNode<AnyType> rootNode) {
		if(null == rootNode) {
			return null;//删除的节点不存在
		}
		int compareResult = element.compareTo(rootNode.element);
		if(compareResult > 0) {
			//进行再赋值是为了保持树状关系
			rootNode.right = remove(element,rootNode.right);
		} else if(compareResult < 0) {
			rootNode.left = remove(element,rootNode.left);
		} else if(null != rootNode.left && null != rootNode.right) {
			//需要删除的节点有两个孩子找到右子树的最小节点作为被删除节点的元素,或者找到左子树最大的元素
			rootNode.element = findMin(rootNode.right).element;
			rootNode.right = remove(rootNode.element,rootNode.right);
		} else {
			rootNode = (null != rootNode.left ? rootNode.left : rootNode.right);
		}
		return rootNode;
	}
	
	private BinaryNode<AnyType> findMin(BinaryNode<AnyType> rootNode) {
		if(null == rootNode) {
			return null;
		} else if(null == rootNode.left) {
			return rootNode;
		} 
		return findMin(rootNode.left);
	}

	private BinaryNode<AnyType> insert(AnyType element, BinaryNode<AnyType> rootNode) {
		if(null == rootNode) {
			return new BinaryNode<AnyType>(element, null, null);
		}
		int compareResult = element.compareTo(rootNode.element);
		if(compareResult > 0) {
			//保持树状关系
			rootNode.right = insert(element,rootNode.right);
		} else if(compareResult < 0) {
			//保持树状关系
			rootNode.left = insert(element,rootNode.left);
		} else {
			//存在重复值,不做处理
		}
		return rootNode;
	}

	private boolean contain(AnyType element,BinaryNode<AnyType> rootNode) {
		if(null == rootNode) {
			return false;
		}
		int compareResult = element.compareTo(rootNode.element);
		if(compareResult > 0) {
			return contain(element, rootNode.right);
		} else if(compareResult < 0) {
			return contain(element, rootNode.left);
		} else {
			return true;
		}
	}

	private static class BinaryNode<AnyType> {
		
		private AnyType element;
		
		private BinaryNode<AnyType> left;
		
		private BinaryNode<AnyType> right;
		
		@SuppressWarnings("unused")
		public BinaryNode(AnyType element) {
			this.element = element;
		}
		public BinaryNode(AnyType element,BinaryNode<AnyType> left,BinaryNode<AnyType> right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}
	}
	
}
