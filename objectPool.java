import java.util.Stack;

abstract class ObjectPool<T> {
  protected Stack<T> pool;// Stack of pooled objects
  protected int maxSize; // max number of pooled objects (i.e., stack size)
  protected static final int DEFAULTMAXSIZE = 8;
 
  // Workhorse constructor. Initialize variables and create the pool
  // maxSize places a limit on the size of the pool.
  // Throws IllegalArgumentException if maxSize < 1
  ObjectPool(int maxSize)
  {
	  if (maxSize < 1)
	  {
		  throw new IllegalArgumentException("Error! maxSize is less than 1");
		  
	  }
	  this. maxSize = maxSize;
	  this.pool = new Stack<T>();
  }
 
  // Convenience constructor that calls the other
  // constructor using DEFAULTMAXSIZE
  ObjectPool( )
  {
	this(DEFAULTMAXSIZE);  
  }
 
  // Add object x to the pool of available objects if the current
  // pool size is less than maxSize.
  // Resets x (by reinitializing its data members) before adding
  // x to the pool.
  public void release(T x)
  {
	  if (size() < maxSize)
	  {
		  x = reset(x);
		  pool.push(x);
	  } 
  }
  //num of objects in the pool should never be greater than maxsize
  // Create a pretty representation of the pool.
  // Display the bottom of the Stack first. (Note that Stack inherits
  // from Vector: public class Stack<E> extends Vector<E>,
  // so you can iterate through the elements of the Stack/Vector
  // using the methods of Vector.
  public String toString()
  {
	  String stringAnswer = "";
	  
	  for (int i = 0; i < pool.size(); i++)
	  {
		  if (i == pool.size()-1)
		  {
	  			stringAnswer = pool.get(i) + ","+ stringAnswer;
		  }
		  else
		  {
			  stringAnswer = pool.get(i) + stringAnswer;
		  }
	  }
	  return stringAnswer;
  }
 
  // Return the current size of pool
  // Target Complexity: O(1)
  public int size()
  {
	  return pool.size();
  }
 
  // Returns a newly constructed object of type T.
  // Called by allocate() when an object is requested from an empty pool.
  protected abstract T create();
 
  // Resets the values in x to their initial values.
  // Called by release() before adding x back to the pool
  protected abstract T reset(T x);
 
  // If the pool is empty, create() an object and return it; otherwise,
  // return an object from the pool. 
  protected T allocate()
  {
	  if (size() == 0)
	  {
		  //create an object
		  return create();
	  }
	  return pool.pop();
  }
}

