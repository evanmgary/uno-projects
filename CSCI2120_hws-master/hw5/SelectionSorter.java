import java.util.List;
import java.util.Comparator;
/**
 * Selection sorter class that sorts a list using a selection sort algorithm
 * @author Evan Gary
 * @version 7 April 2017
 */
public class SelectionSorter implements Sorter {
    /**
     * {@inheritDoc}
     */
    @Override
    public <E extends Comparable<E>> int sort(List<E> data) {
        Comparator comparator = new DefaultComparator();
        return sort(data, comparator);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public <E> int sort(List<E> data, Comparator<E> comparator){
        int[] min = {0, 0};
        int operations = 0;
        for (int i = 0; i < data.size(); i++){
            min = findMin(data, i, comparator);
            swap(data, i, min[0]);
            operations = operations + min[1];
        }
        return operations;
    }

    /**
     * Swaps two elements of a list.
     * @param data The list on which to perform the operation.
     * @param first The first element to swap.
     * @param second The second element to swap.
     * @param <E>
     */
    private <E> void swap(List<E> data, int first, int second){
        E temp = data.get(first);
        data.set(first, data.get(second));
        data.set(second, temp);

    }

    /**
     * Find the smallest element of a list.
     * @param data The list on which to perform the operation.
     * @param startIndex The index of the list at which to start searching.
     * @param comparator The comparator used to compare elements.
     * @param <E>
     * @return The index of the smallest element and number of compare operations.
     */
    private <E> int[] findMin(List<E>data , int startIndex, Comparator<E> comparator){
        int[] min = {startIndex, 0};
        for (int i = startIndex; i < data.size(); i++){
            min[1]++;
            if (comparator.compare(data.get(i), data.get(min[0])) < 0){
                min[0] = i;

            }
        }
        return min;
    }
}
