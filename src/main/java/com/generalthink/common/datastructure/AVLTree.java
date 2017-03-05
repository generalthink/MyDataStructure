package com.generalthink.common.datastructure;

/**
 * 实现简单的AVL
 * AVL树的平衡条件是,某个节点的左右两颗子树的高度差的绝对值必须小于2,空子树的高度为-1
 * 分为四种情况
 * 1. LL : 在不平衡的节点的左孩子的左孩子插入导致的不平衡
 * 2. RR : 不平衡节点的右孩子的右孩子插入导致的不平衡 
 * 3. LR : 不平衡节点的左孩子的有孩子导致的不平衡
 *         需要先RR,然后在LL
 * 4. RL ： 不平衡节点的右孩子的左孩子导致的不平衡
 *         需要先LL,然后在RR
 * @see http://blog.csdn.net/javazejian/article/details/53892797
 * @see http://blog.csdn.net/sheepmu/article/details/38694003
 * @author TKing
 *
 */
public class AVLTree<AnyType extends Comparable<? super AnyType>> {

    //树的根节点
    private AVLNode<AnyType> root;
    
    //树元素大小
    private int size;
    
    
    public AVLTree() {
        root = null;
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public void insert(AnyType element) {
        root = insert(root,element);
        size++;
    }
    
    
    private AVLNode<AnyType> insert(AVLNode<AnyType> rootNode, AnyType element) {

        if(null == rootNode) {
            //叶子节点的高度为0
            return new AVLNode<AnyType>(null,null,element);
        }
        
        int compare = element.compareTo(rootNode.element);
        
        if(compare > 0) {
            rootNode.right = insert(rootNode.right, element);
            if(height(rootNode.right) - height(rootNode.left) == 2) {
                if(element.compareTo(rootNode.right.element) > 0) {
                    rootNode = rotaleWithRightChild(rootNode);
                } else {
                    rootNode = doubleWithRightChild(rootNode);
                }
            }
            
        } else if(compare < 0) {
            rootNode.left = insert(rootNode.left, element);
            if(height(rootNode.left) - height(rootNode.right) == 2) {
                if(element.compareTo(rootNode.left.element) < 0) {
                    rootNode = rotaleWithLeftChild(rootNode);
                } else {
                    rootNode = doubleWithLeftChild(rootNode);
                }
            }
        } else {
            //do nothing
        }
        
        rootNode.height = Math.max(height(rootNode.left), height(rootNode.right)) + 1;
        
        return rootNode;
    }
    /*双旋转,处理LR型问题*/
    private AVLNode<AnyType> doubleWithLeftChild(AVLNode<AnyType> k2) {
        
        k2.left = rotaleWithRightChild(k2.left);
        return rotaleWithLeftChild(k2);
        
    }

    /*进行左旋转*/
    private AVLNode<AnyType> rotaleWithLeftChild(AVLNode<AnyType> k2) {
        AVLNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }
    /*进行双旋转,处理RL型问题*/
    private AVLNode<AnyType> doubleWithRightChild(AVLNode<AnyType> k2) {

        k2.right = rotaleWithLeftChild(k2.right);
        return rotaleWithRightChild(k2);
        
    }
    /*右旋转,RR型问题*/
    private AVLNode<AnyType> rotaleWithRightChild(AVLNode<AnyType> k2) {
        AVLNode<AnyType> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private int height(AVLNode<AnyType> node) {
        return null == node ? -1 : node.height;
    }


    private static class AVLNode<AnyType> {
        
        private AVLNode<AnyType> left;
        
        private AVLNode<AnyType> right;
        
        private AnyType element;
        
        private int height;

        public AVLNode(AVLNode<AnyType> left, AVLNode<AnyType> right, AnyType element) {
            super();
            this.left = left;
            this.right = right;
            this.element = element;
        }
    }

}
