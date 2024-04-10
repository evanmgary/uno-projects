import java.util.List;
import java.util.Comparator;
/**
 * Linear search class that sorts a list using a linear search algorithm
 * @author Evan Gary
 * @version 7 April 2017
 */

public class LinearSearcher implements Searcher {
    /**
     * {@inheritDoc}
    */
    @Override
    public <E extends Comparable<E>> int[] search(List<? extends E> data, E key){
        Comparator comparator = new DefaultComparator();
        return search(data, key, comparator);
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public <E> int[] search(List<? extends E> data, E key, Comparator<E> comparator){
        int[] searchIndex = {-1, 0} ;
        for (int i = 0; i < data.size(); i++){
            searchIndex[1]++;
            if (comparator.compare(data.get(i), key) == 0){
                searchIndex[0] = i;
            }
        }
        return searchIndex;
    }
}
