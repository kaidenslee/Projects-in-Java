package cp213;

/**
 * Implements a Popularity Tree. Extends BST.
 *
 * @author your name here
 * @author David Brown
 * @version 2024-10-15
 */
public class PopularityTree<T extends Comparable<T>> extends BST<T> {

    /**
     * Auxiliary method for valid. May force node rotation if the retrieval count of
     * the located node data is incremented.
     *
     * @param node The node to examine for key.
     * @param key  The data to search for. Count is updated to count in matching
     *             node data if key is found.
     * @return The updated node.
     */
    private TreeNode<T> retrieveAux(TreeNode<T> node, final CountedData<T> key) {

    	if (node == null) {
            return null;
        } else {
            final int result = node.getData().compareTo(key);
            this.comparisons++;

            if (result > 0) {
                node.setLeft(this.retrieveAux(node.getLeft(), key));
                if (node.getLeft() != null && node.getData().getCount() < node.getLeft().getData().getCount()) {
                    node = this.rotateRight(node);
                }
            } else if (result < 0) {
                node.setRight(this.retrieveAux(node.getRight(), key));
                if (node.getRight() != null && node.getData().getCount() < node.getRight().getData().getCount()) {
                    node = this.rotateLeft(node);
                }
            } else {
                node.getData().incrementCount();
                key.setCount(node.getData().getCount());
            }
        }
    	
    	node.updateHeight();
        return node;
    }
    
    /**
     * Performs a left rotation around node.
     *
     * @param parent The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateLeft(final TreeNode<T> parent) {
    	
    	if(parent == null || parent.getRight() == null) {
    		return parent;
    	}

    	TreeNode<T> newRoot = parent.getRight();
    	
    	parent.setRight(newRoot.getLeft());
    	
    	newRoot.setLeft(parent);
    	
    	parent.updateHeight();
    	newRoot.updateHeight();

    	return newRoot;
        }

    /**
     * Performs a right rotation around {@code node}.
     *
     * @param parent The subtree to rotate.
     * @return The new root of the subtree.
     */
    private TreeNode<T> rotateRight(final TreeNode<T> parent) {
    	
    	if(parent == null || parent.getLeft() == null) {
    		return parent;
    	}


    	TreeNode<T> newRoot = parent.getLeft();
    	
    	parent.setLeft(newRoot.getRight());
    	
    	newRoot.setRight(parent);
    	
    	parent.updateHeight();
    	newRoot.updateHeight();


    	return newRoot;
    }

    /**
     * Replaces BST insertAux - does not increment count on repeated insertion.
     * Counts are incremented only on retrieve.
     */
    @Override
    protected TreeNode<T> insertAux(TreeNode<T> node, final CountedData<T> data) {

    	if (node == null) {
    	    // Base case - add a new node containing the data.
    	    node = new TreeNode<T>(data);
    	    node.getData();
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
    		node.getData();
    	    }
    	}
    	
    	if(node.getLeft() != null && node.getLeft().getData().getCount() > node.getData().getCount()) {
			node = rotateRight(node);
		}else if(node.getRight() != null && node.getRight().getData().getCount() > node.getData().getCount()) {
			node = rotateLeft(node);
		}
    	
    	
    	node.updateHeight();
    	return node;
        }

    /**
     * Auxiliary method for valid. Determines if a subtree based on node is a valid
     * subtree. An Popularity Tree must meet the BST validation conditions, and
     * additionally the counts of any node data must be greater than or equal to the
     * counts of its children.
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
    	
    	if(node.getLeft() != null && node.getLeft().getData().getCount() > node.getData().getCount()) {
    		return false;
    	}
    	if (node.getRight() != null && node.getRight().getData().getCount() > node.getData().getCount()) {
            return false;
        }
    	
    
    	
    	boolean isleftValid = isValidAux(node.getLeft(), minNode, node);
    	boolean isRightValid = isValidAux(node.getRight(), node, maxNode);
    	


    	return isleftValid && isRightValid;
        }

    /**
     * Determines whether two PopularityTrees are identical.
     *
     * @param target The PopularityTree to compare this PopularityTree against.
     * @return true if this PopularityTree and target contain nodes that match in
     *         position, data, count, and height, false otherwise.
     */
    public boolean equals(final PopularityTree<T> target) {
	return super.equals(target);
    }

    /**
     * Very similar to the BST retrieve, but increments the data count here instead
     * of in the insertion.
     *
     * @param key The key to search for.
     */
    @Override
    public CountedData<T> retrieve(CountedData<T> key) {
    	root = retrieveAux(root, key);
    	TreeNode<T> node = root;
    	
    	
    	while(node != null) {
    		if(node.getData().compareTo(key) > 0) {
    			node = node.getLeft();
    		}else if(node.getData().compareTo(key) < 0) {
    			node = node.getRight();
    		}else {
    			
    			return node.getData();
    		}
    	}
    	
    	return null;
        }
    }
   
