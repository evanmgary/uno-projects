import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test class for LinearSearcher
*/
public class BinarySearcherTest{
    private static BinarySearcher searcher = new BinarySearcher();
    
    @Test
    public void testEmptyList(){
        GenericSearcherHelper.testEmptyList(searcher);
    }

    @Test
    public void testList(){
        GenericSearcherHelper.testList(searcher);
    }
}
