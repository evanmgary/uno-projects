import java.util.Comparator;

/**
 * Created by emg3 on 3/28/2017.
 */
public class DefaultComparator<T extends Comparable<T>> implements Comparator<T>{

    public int compare(T obj1, T obj2){
        return obj1.compareTo(obj2);
    }
}
