package cp213;

/**
 * A single linked priority queue structure of <code>Node T</code> objects.
 * These data objects must be Comparable - i.e. they must provide the compareTo
 * method. Only the <code>T</code> data contained in the priority queue is
 * visible through the standard priority queue methods. Extends the
 * <code>SingleLink</code> class.
 *
 * @author Kaiden Lee 169050073 leex5007@mylaurier.ca
 * @version 2024-10-30
 * @param <T> the SinglePriorityQueue data type.
 */
public class SinglePriorityQueue<T extends Comparable<T>> extends SingleLink<T> {

    /**
     * Combines the contents of the left and right SinglePriorityQueues into the
     * current SinglePriorityQueue. Moves nodes only - does not move object or call
     * the high-level methods insert or remove. left and right SinglePriorityQueues
     * are empty when done. Nodes are moved alternately from left and right to this
     * SinglePriorityQueue. When finished all nodes must be in priority order from
     * front to rear.
     *
     * Do not use the SinglePriorityQueue insert and remove methods.
     *
     * Do not assume that both right and left priority queues are of the same
     * length.
     *
     * @param left  The first SinglePriorityQueue to extract nodes from.
     * @param right The second SinglePriorityQueue to extract nodes from.
     */
    public void combine(final SinglePriorityQueue<T> left, final SinglePriorityQueue<T> right) {
	assert this.front == null : "May combine into an empty Priority Queue only";

	while (!left.isEmpty() || !right.isEmpty()) {
        if (!left.isEmpty() && (right.isEmpty() || left.front.getObject().compareTo(right.front.getObject()) <= 0)) {
            this.moveFrontToRear(left);
        } else {
            this.moveFrontToRear(right);
        }
    }
}

    /**
     * Adds object to the SinglePriorityQueue. Data is stored in priority order,
     * with highest priority object at the front of the SinglePriorityQueue, and
     * lowest at the rear. Priority is determined by simple comparison - lower
     * objects have higher priority. For example, 1 has a higher priority than 2
     * because 1 is a lower object than 2. (Think of the phrase, "We're number one!"
     * as an indication of priority.)
     *
     * When inserting object to the priority queue, the queue must remain sorted.
     * Hence you need to find the proper location of inserting object. use the head
     * pointer to go through the queue. e.g., use SingleNode&lt;T&gt; current =
     * this.head;
     *
     * use current = current.getNext(); to traverse the queue.
     *
     * To get access to the object inside a node of queue use current.getValue().
     *
     * @param object object to insert in sorted order in priority queue.
     */
    public void insert(final T object) {

	SingleNode<T> current = this.front;
	SingleNode<T> previous = null;
	
	while (current != null && current.getObject().compareTo(object) <=0) {
			previous = current;
			current = current.getNext();
		}
	
	SingleNode<T> newNode = new SingleNode(object, current);
	
	if(previous == null) {
		this.front = newNode;
	}else {
		previous.setNext(newNode);
	}
	
	if(newNode.getNext() == null) {
		this.rear = newNode;
	}
	
	this.length += 1;

	
    }

    /**
{
     * Returns the highest priority object in the SinglePriorityQueue. Decrements
     * the count.
     *
     * @return the highest priority object currently in the SinglePriorityQueue.
     */
    public T remove() {

	
	if(this.front == null) {
		return null;
	}
	
	T val = this.front.getObject();

	
	this.front = this.front.getNext();
	if(this.front == null) {
		this.rear = null;
	}
		
	this.length -= 1;
	
	return val;
	}



    /**
     * Splits the contents of this SinglePriorityQueue into the higher and lower
     * SinglePriorityQueue. Moves nodes only - does not move object or call the
     * high-level methods insert or remove. this SinglePriorityQueue is empty when
     * done. Nodes with priority object higher than key are moved to the
     * SinglePriorityQueue higher. Nodes with a priority object lower than or equal
     * to key are moved to the SinglePriorityQueue lower.
     *
     * Do not use the SinglePriorityQueue insert and remove methods.
     *
     * @param key    object to compare against node objects in SinglePriorityQueue
     * @param higher an initially empty SinglePriorityQueue queue that ends up with
     *               all objects with priority higher than key.
     * @param lower  an initially empty SinglePriorityQueue queue that ends up with
     *               all objects with priority lower than or equal to key.
     */
    public void splitByKey(final T key, final SinglePriorityQueue<T> higher, final SinglePriorityQueue<T> lower) {

    	while(this.isEmpty() == false) { 
    		SingleNode<T> current = this.front;
    	if(current.getObject().compareTo(key) < 0) {
    		higher.moveFrontToRear(this);
    	}else {
    		lower.moveFrontToRear(this);
    	}
    	current = current.getNext();
    	
    	}
		return;
	
 
    }
}
