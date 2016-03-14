import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayLinkedList<T>{
    protected final static int NULL = -1;      
    protected ArrayList <Node<T>> array;
    protected NodePool<T> pool;
    protected int head; // position of dummy node in array
    protected int tail; // position of tail node in array
    protected int firstEmpty; // head of the list of empty nodes
    protected int numberEmpty; // number of empty nodes
    protected int size; // number of non-empty nodes
    protected int modCount; // number of modifications made
    protected final static int NODEPOOLSIZE = 8;
 
    // Constructor to initialize the data members, increment modCount,
    // create the dummy header Node, and add it to array
    public ArrayLinkedList()
    {
     //initialize the data members
     array = new ArrayList <Node<T>>();
     pool = new NodePool<T>(NODEPOOLSIZE);
     //allocate a head node 
     Node <T> newNode = pool.allocate();
     newNode.init();
     //add head node to the arrayList 
     array.add(newNode);
     head = 0;
     tail =0;
     size = 0;
     firstEmpty = NULL;
     numberEmpty = 0;
     modCount++;
    }
    // Return number of non-empty nodes
    // Target Complexity: O(1)
    public int size()
    {
     return size;
    }
 
    // convenience methods for debugging and testing
    protected int getFirstEmpty()
    {
     return firstEmpty;
    }
    protected int getHead()
    {
     return head;
    }
    protected int getTail()
    {
     return tail;
    }
    protected ArrayList<Node<T>> getArray()
    {
     return array;
    }
        
    // Appends the specified element to the end of this list. 
    // Target Complexity: Amortized O(1)
    public boolean add(T e)
    {
        assert size>=0 && head==0 && numberEmpty >=0 && (numberEmpty==0  
         && firstEmpty==NULL) || (numberEmpty>0 && firstEmpty!=NULL)
          && (array.size() == size+numberEmpty+1);   
        Node <T> newNode = null;
        //if there are empty nodes
        if (numberEmpty > 0)
        {
        	//get a new node from the list 
        	newNode = array.get(firstEmpty);
        	//if there is only one empty node 
        	if (newNode.next == NULL)
        	{
        		firstEmpty = NULL;
        		numberEmpty =0;
        	}
        	else
        	{
        		firstEmpty = newNode.next;
        		numberEmpty --;
        	}
        }
        else
        {
        	//allocate a new node from the pool
        	newNode = pool.allocate();
        	array.add(newNode);
        }
        //reset node
        newNode.init();
        
        if (e == null)
        {
         throw new IllegalArgumentException("Error! e is null in the add(T e) method");
        }
        else
        {
           //add the newNode to the array
           newNode.data =e;
           newNode.next = NULL;
           newNode.previous = tail;
           //set the node at tail.next = position of the newNode
           Node <T> previous = array.get(tail);
           previous.next = array.indexOf(newNode);
           //set the tail to new position 
           tail = array.indexOf(newNode);
           size++;
           modCount ++;
        }
        
        // check this assertion before each return statement
        assert size>0 && head==0 && numberEmpty >=0 
          && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
            && firstEmpty!=NULL)
            && (array.size() == size+numberEmpty+1);
        return true;
    }
 
    // Inserts the specified element at the specified index in this list.  
    // Target Complexity: O(n)
    public void add(int index, T e) 
    {
       assert size>=0 && head==0 && numberEmpty >=0 
         && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
           && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
       if (size == index)
      	{
    	   //add last
      		add(e);
      		return;
      	}
      	
      	if (index < 0 || index > size())
      	{
      		throw new IndexOutOfBoundsException("Error! index out of bound in the add(int index,T e) method");
      	}
      	
       Node <T> newNode = null;
       if (numberEmpty > 0)
       {
       	//grab a new node from the list 
       	newNode = array.get(firstEmpty);
       	//if there is only one empty node 
       	if (newNode.next == NULL)
       	{
       		firstEmpty = NULL;
       		numberEmpty =0;
       	}
       	else
       	{
       		firstEmpty = newNode.next;
       		numberEmpty --;
       	}
       }
       else
       {
       	//allocate a new node from the pool
       	newNode = pool.allocate();
		array.add(newNode);
       }
       //reset node
       newNode.init();
       
       if (e == null)
       {
        throw new IllegalArgumentException("Error! e is null in the add(int index, T e) method");
       }
       else
       {
    	   //add 
    	   int count = 0;
    	   Node <T> current = array.get(head);
    	   Node <T> nextNode = null;
    	   while (count != index && current.next != NULL)
    	   {
    		   current = array.get(current.next);
    		   count ++;
    	   }
    	   if (current.next == NULL)
    	   {
    		   addLast(e);
    		   return;
    	   }
    	   else if (count == index)
    	   {
    		   newNode.data = e;
    		   //link the nodes 
    		   nextNode = array.get(current.next);
    		   newNode.next = array.indexOf(nextNode);
    		   current.next = array.indexOf(newNode);
    		   newNode.previous = array.indexOf(current);
        	   nextNode.previous = array.indexOf(newNode);   
    	   }
    	   else 
    	   {
    		   addLast(e);
    		   return;
    	   }
    	   
    	   size++;
    	   modCount++;
       }
       // check this assertion before each return statement
       assert size>0 && head==0 && numberEmpty >=0 
         && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
             && firstEmpty!=NULL) && (array.size() == 
                 size+numberEmpty+1);
    }
 
    // Equivalent to add(0,e).
    // Target Complexity: O(1)
    public void addFirst(T e)
    {
    	if (e == null)
    	{
    		throw new IllegalArgumentException("Error! e is null in the addFirst(T e) method");
    	}
    	add(0,e);
    }
 
    // Equivalent to add(e).
    // Target Complexity: O(1)
    public void addLast(T e)
    {
    	if (e == null)
    	{
    		throw new IllegalArgumentException("Error! e is null in the addLast(T e) method");
    	}
    	add(e);
    }
 
    // Add all of the elements (if any) in Collection c to the end 
    // of the list, one-by-one.
    // Target Complexity: O(number of elements in c)
    @SuppressWarnings("unchecked") 
    public boolean addAll(Collection<? extends T> c)
    {
    	//add all the elements in collection c 
    	for (T newNode: c)
    	{
    		add(newNode);
    	}
    	modCount++;
    	return true;
    }
 
    // Returns true if this list contains the specified element.
    // Target Complexity: O(n)
   public boolean contains(T e)
    {
    	if (e == null)
    	{
    		throw new IllegalArgumentException("e is null in contains(T e) method");
    	}
    	if (indexOf(e) < 0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
   
    // Returns the index of the first occurrence of the 
    // specified element in this list
    // Target Complexity: O(n)
    public int indexOf(T e)
    {
    	if (e == null)
    	{
 		   	throw new IllegalArgumentException("Error! e is null in the indexOf(T e) method");
 	   	}
    	//Node at index 0 
    	Node <T> current = array.get(array.get(head).next);
    	//counter that counts the index of the linked list
    	int count =0;
    	while (current.next != NULL)
    	{
    		if (current.data == e)
    		{
    			return count;
    		}
    		//current = current.next
    		current = array.get(current.next);
    		count++;
    	}
    	if (count == size-1 && e != current.data)
    	{
    		count = NULL;
    	}
    	return count;
    }
    
   // Returns the array position of the first occurrence of 
   // the specified element in array
   // Target Complexity: O(n)
   protected int positionOf(T e)
   {
	   if (e == null)
	   	{
		   	throw new IllegalArgumentException("Error! e is null in the positionOf(T e) method");
	   	}
	    Node <T> current = null;
	    //iterate through the array 
	   	for (int i =0; i < array.size(); i++)
	   	{
	   		current = array.get(i);
	   		
	   		if (current.data != null && current.data == e)
	   		{	 
		   		return array.indexOf(current);
		   	}
	   	}
	   return NULL;
   }
 
    // Returns the element at the specified index in this list.
    // Target Complexity: O(n)
    public T get(int index)
    {
    	if (index < 0 || index >= size)
    	{
    		throw new IndexOutOfBoundsException("Error! index is out of bound in the get(int index) method");
    	}
    	Node <T> current = array.get(array.get(head).next);
    	for(int i =0; i < index; i++)
    	{
    		current = array.get(current.next);
    	}
    	return current.data;
    
    }
 
    // Returns the first element in the list.
    // Target Complexity: O(1)
    public T getFirst()
    {
    	if (size == 0 || array.get(head).next == NULL)
    	{
    		throw new NoSuchElementException("Error! the List is empty");
    	}
    	//returns the first element after the head 
    	return ((array.get(array.get(head).next)).data);
    }
 
    // Returns the last element in the list
    // Target Complexity: O(1)
    public T getLast()
    {
    	if (size == 0)
    	{
    		throw new NoSuchElementException("Error! the List is empty");
    	}
    	//return the element at the tail 
    	return ((array.get(tail)).data);
    }
 
    // Remove the node at position current in the array.
    // Target Complexity: O(1)
    protected void removeNode(int current)
    {
       assert current > 0 && current < array.size();
       Node <T> currentNode = array.get(current);
       Node <T> previousNode = null;
       Node <T> nextNode = null;
       
       if (currentNode.previous != NULL && currentNode.next != NULL)
       {
    	   previousNode = array.get(currentNode.previous);
    	   nextNode = array.get(currentNode.next);
    	   previousNode.next = currentNode.next;
    	   nextNode.previous = currentNode.previous;
       }
       else if (currentNode.previous != NULL && currentNode.next == NULL)
       {
    	   previousNode = array.get(currentNode.previous);
    	   previousNode.next = NULL;
    	   tail = array.indexOf(previousNode);   
       }
       //reset the currentNode
       currentNode.init();
       numberEmpty++;
       size--;
       //add the empty node to the linked list of empty nodes 
       currentNode.next = firstEmpty;
       firstEmpty = array.indexOf(currentNode);
       modCount++;
       
    }
 
    // Removes the first occurrence of the specified element from 
    // this list, if it is present. 
    // Target Complexity: O(n)
    public boolean remove(T e) 
    {
       assert size>=0 && head==0 && numberEmpty >=0
        && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
          && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
       
       if (e == null)
	   	{
		   	throw new IllegalArgumentException("Error! e is null in the remove(T e) method");
	   	}
       //returns the array position
       int position = positionOf(e);
       if (position > 0)
       {
    	    removeNode(position);
       }
       else
       {
    	   return false;
       }
       
       // check this assertion before each return statement
       assert size>=0 && head==0 && numberEmpty >=0 
         && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
          && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
       return true;
    }
 
    // Removes the element at the specified index in this list.
    // Target Complexity: O(n)
    public T remove(int index) 
    {
      assert size>=0 && head==0 && numberEmpty >=0 
        && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
          && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
        if (index < 0 || index >= size)
      	{
      		throw new IndexOutOfBoundsException("Error! index out of bound in the remove(int index) method");
      	}
        
        T i = get(index);
      	remove (i);
        // check this assertion before each return statement
       assert size>=0 && head==0 && numberEmpty >=0 && (numberEmpty==0 
        && firstEmpty==NULL) || (numberEmpty>0 && firstEmpty!=NULL) 
        && (array.size() == size+numberEmpty+1);
        return i ;
    }
 
    // Returns the first element in the list.
    // Target Complexity: O(1)
    public T removeFirst()
    {
    	//get the first element
    	T currentData = getFirst();
    	if (currentData == null)
    	{
    		throw new NoSuchElementException("Error! no such element in the removeFirst() method");
    	}
    	//remove the first element 
    	remove(currentData);
    	return currentData;
    }
 
    // Returns the last element in the list
    // Target Complexity: O(1)
    public T removeLast()
    {
    	//get the last element 
    	T currentData = getLast();
    	if (currentData == null)
    	{
    		throw new NoSuchElementException("Error! no such element in the removeLast() method");
    	}
        //remove the last element 
    	remove(currentData);
    	return currentData;
    }
 
    // Returns true if the Node at the specified position in the 
    // array is an empty node.
    // Target Complexity: O(1)
    protected boolean positionIsEmpty (int position)
    {
      assert position > 0 && position < array.size();
      //get the node from the array at the position
      Node <T> current = array.get(position);
      //return the data of the node
      return (current.data == null);
    }
 
    // Returns number of empty nodes.
    // Target Complexity: O(1)
    protected int numEmpty()
    {
    	return numberEmpty;
    }
 
    // Replaces the element at the specified position in this 
    // list with the specified element. 
    // Target Complexity: O(n)
    public T set(int index, T e)
    {
    	if (index < 0 || index >= size)
    	{
    		throw new IndexOutOfBoundsException("Error index out of bound in the set(int index, T e) method");
    	}
    	if (e == null)
    	{
    		throw new IllegalArgumentException("e is null in the set(int index, T e) method");
    	}
    	T current = null;
    	//get the head node
    	Node <T> currentNode = array.get(head);
    	int counter = NULL;
    	//iterate through the list 
    	while(currentNode.next != NULL && counter != index)
    	{
    		counter++;
    		currentNode = array.get(currentNode.next);
    	}
    	if (counter == index)
		{
    		current = currentNode.data;
			currentNode.data = e;
		}
    	return current;
    }
 
    // Removes all of the elements from this list. 
    // Target Complexity: O(n)
    public void clear()
    {
       assert size>=0 && head==0 && numberEmpty >=0 
        && (numberEmpty==0 && firstEmpty==NULL) || (numberEmpty>0 
        && firstEmpty!=NULL) && (array.size() == size+numberEmpty+1);
       
       Node <T> current = null;
       //headNode
       Node <T> dummyNode = array.get(head);
       //iterate through linked list
       while (dummyNode.next != NULL)
       {
    	   //get the next node
    	   current = array.get(dummyNode.next);
    	   dummyNode.next = current.next;
    	   //reinitialize and add to the empty linked list 
    	   current.init();
    	   numberEmpty++;
           current.next = firstEmpty;
           firstEmpty = array.indexOf(current);  
       }
       //set the next of the last node to NULL
       dummyNode.next = NULL;
       head = tail = size =0;
       modCount ++;
       //call compress to send the empty nodes to the pool 
       compress();
       // check this assertion before each return statement
       assert size==0 && head==0 && numberEmpty==0 && firstEmpty==NULL
       && (array.size() == size+numberEmpty+1);
       
    }
    // Convenience debugging method to display the internal 
    // values of the list, including ArrayList array
    protected void dump() {
      System.out.println();
      System.out.println("**** dump start ****");
      System.out.println("size:" + size);
      System.out.println("number empty:" + numberEmpty);
      System.out.println("first empty:"+firstEmpty);
      System.out.println("head:" + head);
      System.out.println("tail:" + tail); 
      System.out.println("list:");
      System.out.println("array:");
      for (int i=0; i<array.size(); i++) // show all elements of array
         System.out.println(i + ": " + array.get(i));
      System.out.println("**** dump end ****");
      System.out.println();
    }
 
    // Returns a textual representation of the list
    public String toString()
    {
    	//headNode
    	Node <T> current = array.get(head);
    	String answer = "";
    	while (current.next != NULL)
    	{
    		//add the data of the node to a string
    		answer = answer + " " +(array.get(current.next)).data;
    		//update current
    		current = array.get(current.next);
    	}
    	//return the string
    	return answer;
    }
 
    // Compress the array by releasing all of the empty nodes to the 
    // node pool. 
    // Target Complexity: O(n)
    public void compress()
    {
    	//iterate through the empty nodes
    	while (numberEmpty > 0)
    	{
    		Node <T> remNode = array.get(firstEmpty);
    		firstEmpty = remNode.next;
    		//release all the empty nodes to the pool 
    		pool.release(remNode);
    		numberEmpty --;
    	}
    	//reset firstEmpty and numberEmpty
    	firstEmpty = NULL;
    	numberEmpty =0;
    	ArrayList <Node<T>> tempArray = new ArrayList <Node<T>>();
    	int current = head;
       //add the linked list to a temporary array 
       for (int i =0; i <size+1; i++)
       {
    	  Node <T> currentNode = array.get(current);
    	  tempArray.add(i, currentNode);
    	  current = currentNode.next;
       }
       Node <T> currentNode = null;
       //iterate through the temporary array and reset each node's previous and next 
       for (int i =0; i <tempArray.size(); i++)
       {
    	   currentNode = tempArray.get(i);
    	   currentNode.next = tempArray.indexOf(currentNode)+1;
    	   currentNode.previous = tempArray.indexOf(currentNode)-1;
       }
       currentNode.next = NULL;
       //move the Nodes from the temporary array to array 
       array = new ArrayList <Node<T>>();
       for(int i=0; i <tempArray.size();i++)
       {
    	   array.add(i, tempArray.get(i));
       }
       //adjust tail 
      tail = tempArray.indexOf(currentNode);
       
    }
    // Returns an Iterator of the elements in this list, 
    // starting at the front of the list
    // Target Complexity: O(1)
    Iterator<T> iterator()
    {
    	return new ArrayLinkedListIterator();
    }
 
    // Iterator for ArrayLinkedList.
    private class ArrayLinkedListIterator implements Iterator<T>
    {
    	private Node<T> current = null;
    	private int currentModCount = modCount;
      // Constructor
      // Target Complexity: O(1)
      public ArrayLinkedListIterator ()
      {
    	  //set current to the head s
    	  current = array.get(head);
      }
 
      // Returns true if the iterator can be moved to the next() element.
      public boolean hasNext()
      {
    	  if (currentModCount != modCount)
    	  {
    		  throw new ConcurrentModificationException("Error! the linkedList has been modified");
    	  }
    	  if (current.next == NULL)
    	  {
    		  return false;
    	  }
    	  else
    		  return true;
      }
 
      // Move the iterator forward and return the passed-over element
      public T next()
      {
    	  if (currentModCount != modCount)
    	  {
    		  throw new ConcurrentModificationException("Error! the linkedList has been modified");
    	  }
    	  if (hasNext())
    	  {
    		  //modify current to the next element 
    		  current = array.get(current.next);
    		  //return data 
    		  return current.data;
    	  }
    	  else
    		  return null;
      }
      public void remove()
      {
    	  throw new UnsupportedOperationException("remove is not supported by the iterator");
      }
   }
}