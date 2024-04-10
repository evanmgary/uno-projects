import java.util.List;
import java.util.Comparator;
/**
 * Binary search class that sorts a list using a binary search algorithm
 * Must be called on a sorted list.
 * @author Evan Gary
 * @version 7 April 2017
 */
public class BinarySearcher implements Searcher {
    /**
     * {@inheritDoc}
    */
    @Override
    public <E extends Comparable<E>> int[] search(List<? extends E> data, E key) {
        Comparator comparator = new DefaultComparator();
        return search(data, key, comparator);

    }

    /**
     * {@inheritDoc}
    */
    @Override
    public <E> int[] search(List<? extends E> data, E key, Comparator<E> comparator){
        int[] searchIndex = {-1, 0};
        int high = data.size() - 1;
        int low = 0;
        int mid = (low + high) / 2;
        boolean found = false;

        while((low <= high) && found == false) {
            if (comparator.compare(data.get(mid), key) < 0) {

                low = mid + 1;
                mid = (low + high) / 2;
                searchIndex[1]++;

            } else if (comparator.compare(data.get(mid), key) > 0) {

                high = mid - 1;
                mid = (low + high) / 2;
                searchIndex[1]++;

            } else {
                searchIndex[0] = mid;
                searchIndex[1]++;
                found = true;
            }
        }
        return searchIndex;
    }
}
