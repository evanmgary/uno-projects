import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test class for MergeSorter
*/
public class MergeSorterTest{
    private static MergeSorter sorter = new MergeSorter();

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
