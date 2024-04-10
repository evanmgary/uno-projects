import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test class for InsertionSorter
*/
public class InsertionSorterTest{
    private static InsertionSorter sorter = new InsertionSorter();

    @BeforeClass
    public static void initialize(){
        GenericSorterHelper.initializeLists();
    }
    
    @Test
    public void testEmptyList(){
        GenericSorterHelper.testEmptyLists(sorter);
    }

    @Test
    public void testIntegerLists(){
        GenericSorterHelper.testIntegerLists(sorter);
    }

    @Test
    public void testStringLists(){
        GenericSorterHelper.testStringLists(sorter);
    }

    @Test
    public void testStringListsByLengthThenNatural(){
        GenericSorterHelper.testStringListsByLengthThenNatural(sorter);
    }
}
