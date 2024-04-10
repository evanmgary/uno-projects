import static org.junit.Assert.*;
import org.junit.*;
import java.util.NoSuchElementException;

/**
 * Linked list tester class for homework 2.
 * @author Evan Gary, Christopher Summa
 * @version 28 June 2017
 */

public class LinkedListTester {

    // test fixture
    private LinkedList<Integer> list1;
    private LinkedList<String>  list2;

    @Before
    public void setup() {
        list1 = new LinkedList<Integer>();
        list2 = new LinkedList<String>();
    }

    @Test
    public void testInitialState() {
        assertTrue(list1.size() == 0);
        assertTrue(list2.size() == 0);
    }

    @Test
    public void testAddTenThings() {
        for (int i=0; i<10; i++)
          list1.insert(i,0);
        for (int i=0; i<10; i++)
          list2.insert(((Integer)i).toString(),0);
        assertTrue(list1.size() == 10);
        assertTrue(list2.size() == 10);
        assertTrue(list1.get(1) == 8);
        assertTrue(list2.get(2).equals("7"));
    }

    @Test
    public void testAddTenThingsThenDeleteSome(){
        for (int i=0; i<10; i++)
            list1.insert(i,0);

        for (int i=0; i<10; i++)
            list2.insert(((Integer)i).toString(),0);

        list1.delete(0);
        list1.delete(2);
        list1.delete(7);
        list2.delete(1);

        assertTrue(list1.size() == 7);
        assertTrue(list1.get(0) == 8);
        assertTrue(list1.get(6) == 1);
        assertTrue(list2.size() == 9);
        assertTrue(list2.get(1).equals("7"));
    }

    @Test
    public void testInitialStateOfIteratorOnEmptyList() {
        LinkedList<Integer>.LinkedListIterator it1 = list1.iterator();
        LinkedList<String>.LinkedListIterator  it2 = list2.iterator();
        assertFalse(it1.hasNext());
        assertFalse(it2.hasNext());
    }


    @Test
    public void simpleIteratorTest() {
        for (int i=0; i<10; i++)
          list1.insert(i,0);
        for (int i=0; i<10; i++)
          list2.insert(((Integer)i).toString(),0);
        LinkedList<Integer>.LinkedListIterator it1 = list1.iterator();
        LinkedList<String>.LinkedListIterator  it2 = list2.iterator();

        int counter = 9;
        while (it1.hasNext()) {
            assertTrue(counter == it1.next());
            counter--;
        }

    }

    @Test
    public void addRemoveIteratorTest(){

        // Make a linked list with 9 to 0 descending
        for (int i=0; i<10; i++)
            list1.insert(i,0);
        LinkedList<Integer>.LinkedListIterator it1 = list1.iterator();
        // Insert 10 at the beginning
        list1.insert(10, it1);
        // 9, then 8 should be next
        assertTrue(9 == it1.next());
        assertTrue(8 == it1.next());

        // delete the last returned value (8)
        list1.delete(it1);

        // see if 7 is still the next value
        assertTrue(7 == it1.next());

        // try to add 11 after 7
        list1.insert(11, it1);
        // Make sure the insertions and deletion worked
        assertTrue(10 == list1.get(0));
        assertTrue(11 == list1.get(3));

        // Original size 10, inserted 2 elements, deleted 1 element
        assertTrue(11 == list1.size());
    }

    @Test
    public void testExceptions(){
        for (int i=0; i<10; i++)
            list1.insert(i,0);
        LinkedList<Integer>.LinkedListIterator it1 = list1.iterator();

        assertTrue(9 == it1.next());
        // Try to delete twice in a row, fail test if exception not thrown
        try{
            list1.delete(it1);
            list1.delete(it1);
            fail();
        }
        catch(IllegalStateException e) {// test passed since exception was thrown
        }

        // Check for NoSuchElementException on a String list
        for (Integer i=0; i<10; i++)
            list2.insert(i.toString(),0);


        LinkedList<String>.LinkedListIterator it2 = list2.iterator();
        try{
            for (Integer j=9; j > -2 ;j--){
                assertEquals(j.toString(), it2.next());
            }
            fail(); // fail if the code gets here (it shouldn't because of the exception)
        }
        catch(NoSuchElementException e){
            //test passed
        }
    }


}

