import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Graph generator class for Homework 5.
 * Takes a file as input and generates a graph from the file.
 * @author Evan Gary
 * @version 31 July 2017
 */
public class GraphGenerator{

    /**
     * Two parameter method that takes both a .co and .gr file
     * Used when latitude and longitude data are needed
     * @param nodePath The .co file path
     * @param edgePath The .gr file path
     * @return The completed graph (without Dijkstra calculations)
     */
    public static Graph getData(Path nodePath, Path edgePath){
        Graph theGraph = new Graph();

        try{
            List<String> fileNodes = Files.readAllLines(nodePath);
            List<String> fileEdges = Files.readAllLines(edgePath);

            for(int i = 0; i < fileNodes.size(); i++){
                String[] split = fileNodes.get(i).split(" ");
                if (split[0].equals("v")){
                    //System.out.println(Integer.parseInt(split[0]));
                    //System.out.println(Integer.parseInt(split[1]));
                    // System.out.println(Integer.parseInt(split[2]));
                    // System.out.println(Integer.parseInt(split[3]));
                    theGraph.addNode(Integer.parseInt(split[1]), new Integer[]{Integer.parseInt(split[2]),Integer.parseInt(split[3])});
                }
            }

            for (int j = 0; j < fileEdges.size() ; j++){
                String[] split = fileEdges.get(j).split(" ");
                if (split[0].equals("a")){
                    int addStart = Integer.parseInt(split[1]);
                    int addEnd  = Integer.parseInt(split[2]);
                    int addWeight = Integer.parseInt(split[3]);
                    // System.out.println(nodes.get(addStart));
                    theGraph.makeEdge(addStart, addEnd, addWeight);
                    // System.out.println(nodes.get(addStart).edges.size());
                }
            }

        }
        catch(IOException e){e.printStackTrace();}
        return theGraph;
    }

    /**
     * One parameter method that takes a .gr file
     * Used when only edge data and weights are available
     * @param edgePath The .gr file path
     * @return The completed graph (without Dijkstra calculations)
     */
    public static Graph getData(Path edgePath){
        Graph theGraph = new Graph();
        try{
            List<String> fileEdges = Files.readAllLines(edgePath);

            for (int j = 0; j < fileEdges.size() ; j++){
                String[] split = fileEdges.get(j).split(" ");
                if (split[0].equals("a")){
                    int addStart = Integer.parseInt(split[1]);
                    int addEnd  = Integer.parseInt(split[2]);
                    int addWeight = Integer.parseInt(split[3]);

                    if (theGraph.getNode(addStart) == null){
                        theGraph.addNode(addStart, null);
                    }

                    if(theGraph.getNode(addEnd) == null){
                        theGraph.addNode(addEnd, null);
                    }

                    theGraph.makeEdge(addStart, addEnd, addWeight);
                    // System.out.println(nodes.get(addStart).edges.size());
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return theGraph;
    }
}