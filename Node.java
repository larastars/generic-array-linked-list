public class Node<T> {
	/////DONE
  protected static final int NULL = -1;
  protected int previous;
  protected int next;
  protected T data;
 
  // Constructor initializes data members. See method init().
  protected Node()
  {
	init();
  }
 
  // Create a pretty representation of the pool
  // Format: [previous,next,data]
  // Target Complexity: O(n)
  public String toString()
  {
	 return "[ "+ previous + ", " + next + ", " + data + " ]";
  }
 
  // Initializes the data members. (to null or NULL)
  // Called by the constructor and also by method reset() 
  // in class NodePool.
  // Target Complexity: O(1)
  protected void init()
  {
	  this.previous = NULL;
	  this.next = NULL;
	  this.data = null;
	  
  }
  
  public int hashCode()
  {
	  int hash = 7;
	  hash = 31 * hash + previous;
	  hash = 31 * hash + next;
	  hash = 31 * hash + (data == null ? 0: data.hashCode());
	  //to put it in a table, mod hash by table size 
	  return hash;
  }
  
  
  
  
  
} 