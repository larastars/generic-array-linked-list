public class NodePool<T> extends ObjectPool<Node<T>> 

{	//managing creating Nodes
	
  // The data element of a Node has type T
  // Constructor invokes the constructor of the parent class.
  //calls a constructor from object pool 
  // Throws IllegalArgumentException if maxSize < 1
  NodePool(int maxSize)
  {
	  super(maxSize);
  }
 
  // Returns a newly constructed Node with data type T. 
  // Called when an object is requested from an empty pool.
  // Target Complexity: O(1)
  protected Node<T> create()
  {
	Node<T> newNode = new Node<T>();  
	return newNode;
  }
 
  // Resets values of x to their initial values. 
  // Called when an empty Node is released back to the pool.
  // Empty nodes are released to the NodePool by methods clear() 
  // and compress() in class ArrayLinkedList, as described below.
  // Target Complexity: O(1)
  protected Node<T> reset(Node<T> x)
  {
	  //call init 
	 x.init();
	 return x;
  }


  
  
  
  
  
  
  
  
}