// Liron Cohen, lironcohen3, 207481268
// Yuval Mor, yuvalmor, 209011543

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap
{
	protected final double GOLDEN_RATIO = (1 + Math.sqrt(5))/2;
	protected HeapNode min;
	protected HeapNode first;
	protected int size;
	
	/**
	 * public FibonacciHeap()
	 *
	 * precondition: none
	 * 
	 * The method creates a new heap with default values..
	 *   
	 */
	public FibonacciHeap() {
		this.min = null;
		this.first = null;
		this.size = 0;
	}
	
	
   /**
    * public boolean isEmpty()
    *
    * precondition: none
    * 
    * The method returns true if and only if the heap
    * is empty.
    *   
    */
    public boolean isEmpty()
    {
    	return this.size == 0; // should be replaced by student code
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * 
    * Returns the new node created. 
    */
    public HeapNode insert(int key)
    {    
    	HeapNode newFirst = new HeapNode(key);
    	
    	if (this.isEmpty())
    		this.min = newFirst;
    	
    	else {
    		this.first.prevBro.setNextBro(newFirst); // next of last
    		newFirst.setPrevBro(this.first.prevBro); // prev of new first 
    		this.first.prevBro = newFirst; // prev of first
    		newFirst.setNextBro(this.first); // next of new first
    		if (key < this.min.getKey())
        		this.min = newFirst;
    	}
    	
    	this.first = newFirst;
    	this.size++;
    
    	return newFirst;
    }
    
    /**
     * private HeapNode insertTree(int key)
     *
     * Inserts the given node (or tree) into the heap, as last tree.
     *  
     * Returns the given HeapNode;
     */
     private HeapNode insertTree(HeapNode x)
     {    
     	
     	if (this.isEmpty()) {
     		this.first = x;
     		this.min = x;
     	}
     	
     	else {
     		x.setPrevBro(this.first.getPrevBro()); // next of last
     		this.first.getPrevBro().setNextBro(x); // next of last
     		x.setNextBro(this.first); // prev of first
     		this.first.setPrevBro(x); // prev of first
     		if (x.getKey() < this.min.getKey())
         		this.min = x;
     	}
     	
     	this.size += Math.pow(2, this.first.getRank());
     
     	return x;
     }
    
    /**
     * private HeapNode link(HeapNode x, HeapNode y)()
     *
     * The method creates a simple link between 
     * two trees of the same order, by comparing their keys.
     */    
    protected HeapNode link(HeapNode x, HeapNode y) {
    	HeapNode temp = null;
    	if (x.getKey() > y.getKey()) { // in order to make y the bigger-key-node
    		temp = y;
    		y = x;
    		x = temp;
    	}
    	if (x.getFirstChild() == null) { // if x doesn't hasve children
    		y.setNextBro(y);
    		y.setPrevBro(y);
    	}
    	else {
    		y.setNextBro(x.getFirstChild()); // old first child to y
    		x.getFirstChild().setPrevBro(y); // old first child to y
    		y.setPrevBro(x.getFirstChild().getPrevBro());//old last child to y
    		x.getFirstChild().getPrevBro().setNextBro(y);// old last child to y
    	}
    	x.setFirstChild(y);
    	y.setParent(x);
    	
    	return x;
    }

    /**
     * private vois clearHeap()
     *
     * The method clears the attributes of the heap. 
     */
    private void clearHeap() {
    	this.first = null;
    	this.min = null;
    	this.size = 0;
    }
    
    /**
     * private HeapNode[] toBuckets()
     *
     * The method organises the trees of the heap in buckets 
     * and creates the links if necessary.
     * After running this method, the heap will have at most
     * one tree from any order.
     * The method returns an array of the trees, ordered by order.
     */
    private HeapNode[] toBuckets() {
    	HeapNode x = this.first;
    	int arraySize = (int) Math.ceil(Math.log(this.size())/ Math.log(GOLDEN_RATIO));
    	HeapNode[] B = new HeapNode[arraySize];
    	x.getPrevBro().setNextBro(null); // disconnecting x from the brothers cyclic list
    	x.setPrevBro(null);
    	while (x != null) {
    		HeapNode y = x; // variable for the node in buckets
    		x = x.getNextBro();
    		while (B[y.getRank()] != null) {
    			y = link(y, B[y.getRank()]); // linking y with tree in bucket
    			y.setRank(y.getRank()+1); // incrementing rank
    		}
    		B[y.getRank()] = y; 
    	}
    	return B; // buckets list
    }
    
    /**
     * private void fromBuckets()
     *
     * The method gets an array of tree and inserts them to the heap
     * left to right. 
     */
    private void fromBuckets(HeapNode[] B) {
    	clearHeap();
       	for (int i=0; i<B.length; i++) { // scanning the buckets array
    		if (B[i] != null)
    			this.insertTree(B[i]);
    		}
    }
    
    /**
     * private void consolidate()
     *
     * The method changes the heap to fulfil the rule
     * of at most one tree from any order.
     */
    private void consolidate() {
    	HeapNode[] B = toBuckets();
    	fromBuckets(B);
    }
    
   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
     	return; // should be replaced by student code
     	
    }

   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin()
    {
    	return this.min;// should be replaced by student code
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (FibonacciHeap heap2)
    {
    	  if (heap2.findMin().getKey() < this.findMin().getKey()) // updating min if necessary
    		  this.min = heap2.findMin();
    	  
    	  HeapNode heap2Last = heap2.first.prevBro;
    	  HeapNode thisLast = this.first.prevBro;
    	  thisLast.setNextBro(heap2.first); // this last and heap2 first
    	  heap2.first.setPrevBro(thisLast); // this last and heap2 first
    	  heap2Last.setNextBro(this.first); // heap2 last and this first
    	  this.first.setPrevBro(heap2Last); // heap2 last and this first
    	  
    	  this.size = this.size + heap2.size(); // updating size
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return this.size; // should be replaced by student code
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    * 
    */
    public int[] countersRep()
    {
	int[] arr = new int[42];
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap. 
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	return; // should be replaced by student code
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return 0; // should be replaced by student code
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of 
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value 
    * in its root.
    */
    public static int totalLinks()
    {    
    	return 0; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return 0; // should be replaced by student code
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k minimal elements in a binomial tree H.
    * The function should run in O(k*deg(H)). 
    * You are not allowed to change H.
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
        int[] arr = new int[42];
        return arr; // should be replaced by student code
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode{

	public int key; // the key of the node
	int rank; // the rank (number of children) of the node
	int mark; // the mark of the node
	HeapNode firstChild; // the left-most child of the node
	HeapNode prevBro; // the previous (to the left or cyclic) brother of the node
	HeapNode nextBro; // the next (to the right) brother of the node
	HeapNode parent; // the parent of the node

  	public HeapNode(int key) {
	    this.key = key;
	    this.prevBro = this;
	    this.nextBro = this;
      }

  	public int getKey() {
	    return this.key;
      }
  	
  	void setKey(int key) {
  		this.key = key;
  	  }
  	
  	int getRank() {
	    return this.rank;
      }
  	
  	void setRank(int rank) {
  		this.rank = rank;
  	  }
  	
  	int getMark() {
	    return this.mark;
      }
  	
  	void setMark(int mark) {
  		this.mark = mark;
  	  }
  	
  	HeapNode getFirstChild() {
	    return this.firstChild;
      }
  	
  	void setFirstChild(HeapNode child) {
  		this.firstChild = child;
  	  }
  	
  	HeapNode getPrevBro() {
	    return this.prevBro;
      }
  	
  	void setPrevBro(HeapNode prevBro) {
  		this.prevBro = prevBro;
  	  }
  	
  	HeapNode getNextBro() {
	    return this.nextBro;
      }
  	
  	void setNextBro(HeapNode nextBro) {
  		this.nextBro = nextBro;
  	  }
  	
  	HeapNode getParent() {
	    return this.parent;
      }
  	
  	void setParent(HeapNode parent) {
  		this.parent = parent;
  	  }

    }
   
}
