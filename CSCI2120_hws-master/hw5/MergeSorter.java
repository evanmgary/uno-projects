import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * Merge sorter class that sorts a list using a recursive merge sort algorithm
 * adapted from the one Deitel's text (to be used with comparators)
 * @author Evan Gary
 * @version 7 April 2017
 */
public class MergeSorter implements Sorter {

    /**
     * {@inheritDoc}
    */
    @Override
    public <E extends Comparable<E>> int sort(List<E> data){
        Comparator comparator = new DefaultComparator();
        return sort(data, comparator);
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public <E> int sort(List<E> data, Comparator<E> comparator){
        return sort(data, 0, data.size()-1, comparator);

    }

    /**
     * Merge sort method (recursive) that is actually performing the sort.
     * @param data The data to be sorted.
     * @param low The low index of the section of the array to sort.
     * @param high The high index of the section of the array to sort.
     * @param comparator The comparator to use.
     * @param <E>
     * @return The number of compare operations.
     */

    private <E> int sort(List<E> data, int low, int high, Comparator<E> comparator){
        int operations = 0;
        if ((high - low) >= 1){
            int mid1 = (high + low) / 2;
            int mid2 = mid1 + 1;
            operations += sort(data, low, mid1, comparator);
            operations += sort(data, mid2, high, comparator);
            operations += merge(data, low, mid1, mid2, high, comparator);
        }
        return operations;
    }

    /**
     * Method to merge the arrays as part of the merge sort.
     * @param data The data to be sorted.
     * @param left The low index of the first section of the list.
     * @param mid1 The high index of the first section of the list.
     * @param mid2 The low index of the second section of the list.
     * @param right The high index of the second section of the list.
     * @param comparator The comparator to use.
     * @param <E>
     * @return The number of compare operations.
     */
    private<E> int merge(List<E> data, int left, int mid1, int mid2, int right, Comparator<E> comparator){
        int operations = 0;
        int leftIndex = left;
        int rightIndex = mid2;
        int combinedIndex = left;
        List<E> combined = new ArrayList<E>(data.size());
        //Make the empty list of the needed size using null elements
        for (int i = 0; i < data.size(); i++){
            combined.add(null);
        }

        while(leftIndex <= mid1 && rightIndex <= right){
            if(comparator.compare(data.get(leftIndex), data.get(rightIndex)) <= 0){
                combined.set(combinedIndex++, data.get(leftIndex++));
            }
            else{
                combined.set(combinedIndex++, data.get(rightIndex++));
            }
            operations++;

        }

        if(leftIndex == mid2){
            while (rightIndex <= right){
                combined.set(combinedIndex++, data.get(rightIndex++));
            }
        }
        else{
            while(leftIndex <= mid1){
                combined.set(combinedIndex++, data.get(leftIndex++));
            }
        }

        for (int i = left; i <= right; i++){
            data.set(i, combined.get(i));
        }
        return operations;
    }
}
