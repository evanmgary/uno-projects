import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;
/**
 * Runner class for Homework 5.
 * Command line arguments: filename source destination outputFileName
 * @author Evan Gary
 * @version 31 July 2017
 */
public class FindShortestRoadPath{
    public static void main(String [] args){
        Graph theGraph = GraphGenerator.getData(Paths.get(args[0]));

        theGraph.generateDijkstra(Integer.parseInt(args[1]));
        ArrayList<Integer> pathList = theGraph.findShortestPath(Integer.parseInt(args[2]));

        try(BufferedWriter out = new BufferedWriter(new FileWriter(args[3]))){
            out.write(pathList.get(0).toString());
            out.newLine();

            for (int i = pathList.size() - 1 ; i > 0 ; i--){
                out.write(pathList.get(i).toString());
                out.newLine();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        /*ArrayList<String> list = new ArrayList<String>();
        list.add("6");
        list.add("5");
        list.add("4");
        list.add("3");
        list.add("2");
        list.add("1");
        ArrayList<?> sortedList = Heap.heapSort(list, new StringComparator());

        System.out.println(Arrays.toString(sortedList.toArray()));
        */
    }
}