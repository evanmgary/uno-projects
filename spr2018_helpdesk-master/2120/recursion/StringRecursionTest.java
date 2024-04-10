import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;

public class StringRecursionTest{
	
	ArrayList<String> stringArray = new ArrayList<String>(); 
	ArrayList<String> stringArray2 = new ArrayList<String>(); 

	
	@Before
	public void setup(){
		stringArray.add("foo");    
		stringArray.add("bar");    
		stringArray.add("foobar"); 
		stringArray.add("zzzzzz");     
		stringArray.add("a");      
		stringArray.add("Foobar"); 
		stringArray.add("AA");     

		stringArray2.add("foobar");


	}		

	@Test
	public void testcompareTo(){
		assertTrue(StringRecursion.compareTo("bar", "foo") < 0);
		assertTrue(StringRecursion.compareTo("bar", "ball") > 0);
		assertTrue(StringRecursion.compareTo("bar", "bar") == 0);
		assertTrue(StringRecursion.compareTo("bar", "BAR") == 0);
		assertTrue(StringRecursion.compareTo("bar", "") > 0);
		assertTrue(StringRecursion.compareTo("bar", "barstool") < 0);
		assertTrue(StringRecursion.compareTo("zzzz", "zzzzz") < 0);
		assertTrue(StringRecursion.compareTo("", "") == 0);

	}

	@Test
	public void testFindMinimum(){
		assertEquals("a", StringRecursion.findMinimum(stringArray));
		assertEquals("foobar", StringRecursion.findMinimum(stringArray2));
		try{
			stringArray2.remove(0);
			StringRecursion.findMinimum(stringArray2);
			fail();
		}
		catch (IllegalArgumentException e){}

	}
}