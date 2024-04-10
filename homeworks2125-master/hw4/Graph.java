import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Graph class for Homework 5. Contains the graph as well as the Dijkstra's algorithm methods.
 * @author Evan Gary
 * @version 31 July 2017
 */
public class Graph{

    private Map<Integer, Node> nodes;
    private ArrayList<Node> toFindMin;

    public Graph(){
        nodes = new HashMap<Integer, Node>();
        toFindMin = new ArrayList<Node>();
    }

    /**
     * Generates the dijkstra table by filling the node instance variables distance and previous node.
     * @param startingNodeid the source node's id
     */
  public void generateDijkstra(int startingNodeid){
    Node startingNode = nodes.get(startingNodeid);
    // Can't work on an empty graph
    if (nodes.isEmpty()){
      throw new IllegalStateException("Cannot generate if there is no graph.");
    }

    // Start by setting the distance on the initial node to zero and the path to itself
    startingNode.distance = 0;
    startingNode.known = false;
    startingNode.pathNode = startingNode;
    toFindMin.add(startingNode);

    //Iterate N times, N is the number of nodes
    for (int i = 0; i < nodes.size() - 1; i++){

        //Find the lowest distane unknown node and set it to true
      Node workNode = findMinDistanceNode();
      workNode.known = true;
      //Once a node is known it cannot be used in the next pass
      toFindMin.remove(workNode);

      //Go through the edges and assign distances to the adjaent nodes
      for (int j = 0 ; j < workNode.edges.size(); j++){
        int distanceToNext = workNode.distance + workNode.edges.get(j).weight;
        //Do not change distance if it is not less than an already determined distance
        if (distanceToNext < workNode.edges.get(j).end.distance) {
          workNode.edges.get(j).end.distance = distanceToNext;
          workNode.edges.get(j).end.pathNode = workNode;
          //Once a distance has been added to a node, it is a candidate to be used in the next pass
            //Add it to the ArrayList to be used for this purpose
          toFindMin.add(workNode.edges.get(j).end);
        }
      }

    }
  }

    /**
     * Find the lowest distance unknown node.
     * @return the lowest distance node to use for Dijkstra
     */
  private Node findMinDistanceNode(){
    int currentMin = Integer.MAX_VALUE;
    Node currentMinNode = null;
    // The usable nodes are stored in an ArrayList. Iterate through the list until the lowest distance is found.
    for (int i = 0; i <= toFindMin.size()-1 ; i++){
      if (toFindMin.get(i).distance < currentMin){
        currentMin = toFindMin.get(i).distance;
        currentMinNode = toFindMin.get(i);
      }
    }
    return currentMinNode;
  }

    /**
     * Finds the shortest path to a vertex based on the already calculated Dijkstra data.
     * @param destId The destination node
     * @return An ArrayList containing the IDs of the path.
     */
  public ArrayList<Integer> findShortestPath(int destId){
    Node destNode = nodes.get(destId);
    ArrayList<Integer> pathList = new ArrayList<>();
    pathList.add(destNode.distance);
    pathList.add(destId);
    //Starting at the destination node, work backward to the source node
    while(destNode.pathNode != destNode){
        pathList.add(destNode.pathNode.id);
        destNode = destNode.pathNode;
    }

    return pathList;
  }

    /**
     * Adds a node to the graph
     * @param id The node's id
     * @param data Any object that can contain some data, such as latitude and longitude
     */
  public void addNode(int id, Object data){
    nodes.put(id, new Node(id, data));

  }

  public Node getNode(int id){
    try{
      return nodes.get(id);
    }
    catch(IndexOutOfBoundsException e ){
      return null;
    }

  }

    /**
     * Adds an edge to the graph.
     * @param start The starting node (not strictly necessary)
     * @param end The destination node
     * @param weight The edge's weight
     */
    public void makeEdge(int start, int end, int weight){
        nodes.get(start).edges.add(new Edge(start, end, weight));
    }

    /**
     * Node class containing the node's id, edges, and Dijkstra data.
     */
    private class Node{
        private int id;
        private Object data;
        private ArrayList<Edge> edges;
        private boolean known;
        private Integer distance;
        private Node pathNode;



        public Node(Integer id, Object object){
            this.id = id;
            this.data = object;
            edges = new ArrayList<Edge>();
            known = false;
            distance = Integer.MAX_VALUE;
            pathNode = null;
        }

        public String toString(){
            return (id + " " + distance);
        }
    }

    private class Edge{
        private Node start;
        private Node end;
        private int weight;

        private Edge(int start, int end, int weight){
            this.start = nodes.get(start);
            this.end = nodes.get(end);
            this.weight = weight;
        }
    }

}