
public class main 
{
	public static void main(String args[])
	{
	      ArrayLinkedList<String> list = new ArrayLinkedList<String>();
	      System.out.println("list.add(\"one\")");
	      list.add("one");
	      System.out.println();
	      System.out.println("list:");
	      System.out.println(list);
	      list.dump();
	      System.out.println();
	      System.out.println("list.add(\"two\")");
	      list.add("two");
	      System.out.println();
	      System.out.println("list:");
	      System.out.println(list);
	      list.dump();
	      System.out.println();
	      System.out.println("list.indexOf(\"one\"):" + list.indexOf("one"));
	      System.out.println("list.indexOf(\"two\"):" + list.indexOf("two"));
	      System.out.println("list.positionOf(\"one\"):" + list.positionOf("one"));
	      System.out.println("list.positionOf(\"two\"):" + list.positionOf("two"));
	      System.out.println();
	      System.out.println("remove(new Integer(\"one\")");
	      list.remove("one");
	      System.out.println();
	      System.out.println("list:");
	      System.out.println(list);
	      list.dump();
	      System.out.println();
	      System.out.println("add(\"three\")");
	      list.add("three");
	      System.out.println();
	      System.out.println("list:");
	      System.out.println(list);
	      list.dump();
	      System.out.println();
	      System.out.println("add(\"four\")");
	      list.add("four");
	      System.out.println();
	      System.out.println("list:");
	      System.out.println(list);
	      list.dump();
	      System.out.println();
	      // list is now: two three four
	      // remove element at index 1, which is "three"
	      System.out.println("remove(1)"); 
	      list.remove(1);
	      System.out.println();
	      System.out.println("list:");
	      System.out.println(list);
	      list.dump();
	      System.out.println();
	      System.out.println("compress"); 
	      list.compress();
	      System.out.println();
	      System.out.println("list:");
	      System.out.println(list);
	      list.dump();
	      System.out.println();
	      System.out.println("list.clear()");
	      list.clear();
	      System.out.println();
	      System.out.println("list:");
	      System.out.println(list);
	      list.dump(); // array contains dummy node only
	    }
}
