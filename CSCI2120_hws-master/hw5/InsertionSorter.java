import java.util.List;
import java.util.Comparator;
/**
 * Insertion sorter class that sorts a list using an insertion sort algorithm
 * @author Evan Gary
 * @version 7 April 2017
 */

public class InsertionSorter implements Sorter {
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
        int operations = 0;
        //If the list is size 1, it is already sorted (no compare operations needed)
        if (data.size() == 1){
            return 0;
        }

        //Perform insertion operation for each element
        for (int i = 1; i < data.size(); i++){
                operations += insert(data, i, comparator);
        }

        return operations;
    }

    /**
     * Helper method to perform the insertion operation in the sort.
     * @param data The array to sort.
     * @param index The index to start the insertion operation.
     * @param comparator The comparator to perform the sort.
     * @param <E>
     * @return The number of compare operations
     */
    private <E> int insert(List<E> data, int index, Comparator<E> comparator) {
        int stepIndex = index;
        int operations = 0;
        boolean sorted = false;

        //Step back from the index, swapping values one at a time. Once a value less than the stepIndex
        //value is found, stop swapping.
        while (!sorted && (stepIndex > 0)) {

            if (comparator.compare(data.get(stepIndex), data.get(stepIndex - 1)) < 0){
                swap(data, stepIndex, stepIndex - 1);
                stepIndex--;
                operations++;
            }
            else{
                operations++;
                sorted = true;
            }

        }
        return operations;
    }

    /**
     * Helper method to swap two elements of the list.
     * @param data The list that is being sorted.
     * @param first The first element to swap.
     * @param second The second element to swap.
     * @param <E>
     */
    private <E> void swap(List<E> data, int first, int second){
        E temp = data.get(first);
        data.set(first, data.get(second));
        data.set(second, temp);

    }



} //end InsertionSorter
