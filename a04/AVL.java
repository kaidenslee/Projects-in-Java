package cp213;

/**
 * Implements an AVL (Adelson-Velsky Landis) tree. Extends BST.
 *
 * @author Kaiden Lee 169050073 leex5007@mylaurier.ca
 * @author David Brown
 * @version 2024-10-15
 */
public class AVL<T extends Comparable<T>> extends BST<T> {

    /**
     * Returns the balance data of node. If greater than 1, then left heavy, if less
     * than -1, then right heavy. If in the range -1 to 1 inclusive, the node is
     * balanced. Used to determine whether to rotate a node upon insertion.
     *
     * @param node The TreeNode to analyze for balance.
     * @return A balance number.
     */
    private int balance(final TreeNode<T> node) {

	if (node == null) {
		return 0;
	}
	int leftHeight = (node.getLeft() != null) ? node.getLeft().getHeight() : 0;
    int rightHeight = (node.getRight() != null) ? node.getRight().getHeight() : 0;

    return leftHeight - rightHeight;
}

    /**
     * Rebalances the current node if its children are not balanced.
     *
     * @param node the node to rebalance
     * @return replacement for the rebalanced node
     */
    private TreeNode<T> rebalance(TreeNode<T> node) {

	int balanced = balance(node);
	
	if(balanced > 1) {
		int lBalanced = balance(node.getLeft());
		if(lBalanced >= 0) {
			node = rotateRight(node);
		}else {
			node.setLeft(rotateLeft(node.getLeft()));
			node = rotateRight(node);
		}
	}else if(balanced < -1) {
		int rBalanced = balance(node.getRight());
		if(rBalanced >= 0) {
			node = rotateLeft(node);
		}else {
			node.setRight(rotateRight(node.getRight()));
			node = rotateLeft(node);
		}
	}
	node.updateHeight();
	
	return node;
    }

    /**
     * Performs a left rotation around node.
     *
     * @param node The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateLeft(final TreeNode<T> node) {
    	

    if(node == null || node.getRight() == null) {
    		return node;
    }

	TreeNode<T> newRoot = node.getRight();
	
	node.setRight(newRoot.getLeft());
	
	newRoot.setLeft(node);
	
	node.updateHeight();
	newRoot.updateHeight();

	return newRoot;
    }

    /**
     * Performs a right rotation around node.
     *
     * @param node The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateRight(final TreeNode<T> node) {
    	
    	if(node == null || node.getLeft() == null) {
    		return node;
    	}

    	TreeNode<T> newRoot = node.getLeft();
    	
    	node.setLeft(newRoot.getRight());
    	
    	newRoot.setRight(node);
    	
    	node.updateHeight();
    	newRoot.updateHeight();

    	return newRoot;
    }

    /**
     * Auxiliary method for insert. Inserts data into this AVL. Same as BST
     * insertion with addition of rebalance of nodes.
     *
     * @param node The current node (TreeNode).
     * @param data Data to be inserted into the node.
     * @return The inserted node.
     */
    @Override
    protected TreeNode<T> insertAux(TreeNode<T> node, final CountedData<T> data) {

    	if (node == null) {
    	    // Base case - add a new node containing the data.
    	    node = new TreeNode<T>(data);
    	    node.getData().incrementCount();
    	    this.size++;
    	} else {
    	    // Compare the node data against the insert data.
    	    final int result = node.getData().compareTo(data);
    	    if (result > 0) {
    		// General case - check the left subtree.
    		node.setLeft(this.insertAux(node.getLeft(), data));
    	    } else if (result < 0) {
    		// General case - check the right subtree.
    		node.setRight(this.insertAux(node.getRight(), data));
    	    } else {
    		// Base case - data is already in the tree, increment its count
    		node.getData().incrementCount();
    	    }
    	}
    	node.updateHeight();
    	
    	return rebalance(node);
        }

    /**
     * Auxiliary method for valid. Determines if a subtree based on node is a valid
     * subtree. An AVL must meet the BST validation conditions, and additionally be
     * balanced in all its subtrees - i.e. the difference in height between any two
     * children must be no greater than 1.
     *
     * @param node The root of the subtree to test for validity.
     * @return true if the subtree base on node is valid, false otherwise.
     */
    @Override
    protected boolean isValidAux(final TreeNode<T> node, TreeNode<T> minNode, TreeNode<T> maxNode) {

	if(node == null) {
		return true;
	}
	
	if(minNode != null && node.getData().compareTo(minNode.getData()) <= 0) {
		return false;
	}

	if(maxNode != null && node.getData().compareTo(maxNode.getData()) >= 0) {
		return false;
	}

	
	int balance = balance(node);
	if(balance > 1 || balance < -1) {
		return false;
	}
	
	boolean isleftValid = isValidAux(node.getLeft(), minNode, node);
	boolean isRightValid = isValidAux(node.getRight(), node, maxNode);

	return isleftValid && isRightValid;
    }

    /**
     * Determines whether two AVLs are identical.
     *
     * @param target The AVL to compare this AVL against.
     * @return true if this AVL and target contain nodes that match in position,
     *         data, count, and height, false otherwise.
     */
    public boolean equals(final AVL<T> target) {
	return super.equals(target);
    }
    
    

    /**
     * Auxiliary method for remove. Removes data from this BST. Same as BST removal
     * with addition of rebalance of nodes.
     *
     * @param node The current node (TreeNode).
     * @param data Data to be removed from the tree.
     * @return The replacement node.
     */
    @Override
    protected TreeNode<T> removeAux(TreeNode<T> node, final CountedData<T> data) {

    	if (node == null) {
    		return null;
    	}
    	if(data.compareTo(node.getData()) < 0) {
    		node.setLeft(removeAux(node.getLeft(), data));
    	}else if(data.compareTo(node.getData()) > 0) {
    		node.setRight(removeAux(node.getRight(), data));
    	}else {
    		if(node.getLeft() != null && node.getRight() != null) {
    			TreeNode<T> successor = node.getRight();			
    			while(successor.getLeft() != null) {
    				successor = successor.getLeft();
    			}
    			
    			node.setRight(removeAux(node.getRight(), successor.getData()));
    			
    			successor.setLeft(node.getLeft());
    			successor.setRight(node.getRight());
    			node = successor;
    		}
    		else if(node.getLeft() != null || node.getRight() != null) {
    			if(node.getLeft() != null) {
    				return node.getLeft();
    			}else {
    				return node.getRight();
    			}
    		} else {
    			return null;
    		}
    		
    	}
    	
    	if(node != null) {
    		node.updateHeight();
    	}

    	return rebalance(node);
        }
}
