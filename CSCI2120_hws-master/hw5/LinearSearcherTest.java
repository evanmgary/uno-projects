import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test class for LinearSearcher
*/
public class LinearSearcherTest{
    private static LinearSearcher searcher = new LinearSearcher();
    
    @Test
    public void testEmptyList(){
        GenericSearcherHelper.testEmptyList(searcher);
    }

    @Test
    public void testList(){
        GenericSearcherHelper.testList(searcher);
    }
}
