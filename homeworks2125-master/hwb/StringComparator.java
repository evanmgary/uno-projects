import java.util.Comparator;

public class StringComparator implements Comparator<String> {

    public int compare(String first, String second){
        first = first.toLowerCase();
        second = second.toLowerCase();
        return first.compareToIgnoreCase(second);
    }

}