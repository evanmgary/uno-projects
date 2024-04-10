import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Heap<E>{
    private E[] heapArray; //the heap itself is stored in an array
    private int size;

    public Heap(ArrayList<E> list, Comparator<E> comparator){
        heapArray = (E[]) new  Object[list.size()* 2 + 1];
        size = 0;
        buildHeap(list, comparator);
    }


    /**
     * Sort method. Deletes the minimum repeatedly until there is a sorted list.
     * @param list the list to be sorted
     * @param comparator
     * @return a sorted list
     */
    public ArrayList<?> sort(ArrayList<E> list, Comparator<E> comparator){
        ArrayList<E> returnList = new ArrayList<E>();
        for (int i = 0; i < list.size(); i++){
            returnList.add(deleteMin(comparator));
        }

        return returnList;
    }

    /**
     * BuildHeap method, adds to the heap and percolates up.
     * @param input
     * @param comparator
     */
    private void buildHeap(ArrayList<E> input, Comparator<E> comparator){
        int hole;
        for (hole = 1; hole <= input.size(); hole++){
            int insert = hole;
            E value = input.get(hole - 1);

            //Percolate up
            heapArray[hole] = value;
            while ((insert > 1) && comparator.compare(heapArray[insert], heapArray[insert / 2]) < 0) {
                E temp = heapArray[insert];
                heapArray[insert] = heapArray[insert / 2];
                heapArray[insert / 2] = temp;
                insert = insert/2;
            }
            size++;
        }
    }

    /**
     * DeleteMin method, removes the root and percolates down
     * @param comparator
     * @return The removed element
     */
    private E deleteMin(Comparator<E> comparator){
        int hole = 1;
        E toReturn = heapArray[1];
        E toMove =  heapArray[size];
        heapArray[size] = null;
        heapArray[hole] = toMove;
        boolean done = false;
        while(!done) {

            //Case 1: the "hole" node has no children
            if (heapArray[hole * 2] == null && (heapArray[hole * 2 +1] == null)){
                done = true;
            }

            //Case 2: the "hole" node has only a left child
            if (heapArray[hole * 2] != null && heapArray[hole * 2 + 1] == null) {
                //Switch the parent with the only child if it is greater than the child
                if (comparator.compare(heapArray[hole], heapArray[hole *2]) <= 0){
                    done = true;
                }
                else{
                    E temp = heapArray[hole *2];
                    heapArray[hole * 2] = heapArray[hole];
                    heapArray[hole] = temp;
                    done = true;
                }
            }

            //Case 3: the "hole" node has two children
            if (heapArray[hole * 2] != null && heapArray[hole * 2 + 1] != null) {
                // Switch the parent with the smallest child (if the parent is larger than either)
                if (comparator.compare(heapArray[hole * 2], heapArray[hole * 2 + 1]) < 0) {
                    E temp = heapArray[hole *2];
                    heapArray[hole * 2] = heapArray[hole];
                    heapArray[hole] = temp;
                    hole = hole * 2;
                }
                else if (comparator.compare(heapArray[hole * 2], heapArray[hole * 2 + 1]) >= 0) {
                    E temp = heapArray[hole * 2 +1];
                    heapArray[hole * 2 +1] = heapArray[hole];
                    heapArray[hole] = temp;
                    hole = hole * 2 +1;
                }
            }
        }
        size--;
        return toReturn;
    }

    /**
     * Easy method to sort a list, builds the heap and runs the sort.
     * @param list the list to sort
     * @param comparator the comparator to use
     * @param <E>
     * @return the sorted list
     */
    public static <E> ArrayList<?> heapSort(ArrayList<E> list, Comparator<E> comparator){
        Heap<E> heap = new Heap<>(list, comparator);
        return heap.sort(list, comparator);
    }

}
