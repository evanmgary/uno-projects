import static org.junit.Assert.*;
import java.util.ArrayList;

public class GenericSearcherHelper{
    public static void testEmptyList(Searcher theSearcher){
        ArrayList<Integer> list = new ArrayList<Integer>();
        assertTrue(theSearcher.search(list,1)[0] == -1);
        assertTrue(theSearcher.search(list,0)[0] == -1);
    }

    public static void testList(Searcher theSearcher){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 100; i++){
            list.add(i);
        }
        assertTrue(theSearcher.search(list, -1)[0] == -1);
        assertTrue(theSearcher.search(list, 0)[0] == 0);
        assertTrue(theSearcher.search(list, 13)[0] == 13);
        assertTrue(theSearcher.search(list, 99)[0] == 99);
        assertTrue(theSearcher.search(list, 100)[0] == -1);
    }
}
