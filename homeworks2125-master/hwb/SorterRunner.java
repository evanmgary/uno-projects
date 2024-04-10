import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Runner class for Bonus Homework.
 * Contains the file reader for the heapsort. Also provides a sample use of the radix sort.
 * Command line arguments: filename
 * @author Evan Gary
 * @version 31 July 2017
 */
public class SorterRunner{
    public static void main(String [] args){

        try(BufferedWriter out = new BufferedWriter(new FileWriter("out.txt"))) {
            List<String> fileList = Files.readAllLines(Paths.get(args[0]));
            ArrayList<?> sortedList = Heap.heapSort((ArrayList<String>)fileList, new StringComparator());

            for (int i = 0; i < sortedList.size(); i++){
                out.write((String) sortedList.get(i));
                out.newLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<>();
        list.add("1234");
        list.add("abcd");
        list.add("QERT");
        list.add("qsdf");
        list.add("bnef");
        list.add("4rg4");

        System.out.println(RadixSorter.sort(list));

        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("asdf");
        list2.add("22");
        list2.add("zsdd");
        list2.add("erere");
        list2.add("5555");
        list2.add("ZSEFS");
        ArrayList<?> sortedList2 = Heap.heapSort(list2, new StringComparator());

        System.out.println(Arrays.toString(sortedList2.toArray()));

    }
}