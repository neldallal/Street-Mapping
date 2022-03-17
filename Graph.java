/*
 * Partners: Nadine Eldallal and Jeremy Foss
 *
 * Name: Nadine Eldallal
 * Class: CSC 172
 * Project 3
 * neldalla@u.rochester.edu
 * 12/11/2021
 *
 * Name: Jeremy Foss
 * Class: CSC 172
 * Project 3
 * jfoss3@u.rochester.edu
 * 12/11/2021
 *
 * */

/**
 * The primary functions of the Graph class are the findShortestPath method, getAdjNodes method,
 * and adjacencyList method.
 * The findShortestPath method implements Dijkstra's algorithm to determine
 * the shortest route to each vertex from the specified start vertex.
 * Then, it checks if there was in fact a valid path to the specified end vertex.
 * If there was, the vertexes along that path are added to a finalPath arraylist.
 * The getAdjNodes method gets the arraylist value of adjacent nodes from the adjacency list hashtable,
 * adjList, using a specified vertex key.
 * The adjacencyList method builds an adjacency list hashtable.
 * The hashtable is built, and then populated by all vertices in the vertices hashtable.
 * Then, the edges hashtable is iterated through. For each edge, the start vertex is added
 * to the arraylist value of the end vertex's key, and the end vertex is added to the arraylist
 * value of the start vertex's key.
 * */


import java.util.NoSuchElementException;

public class Graph {
    public MyHashTable<String, Node> v;
    public MyHashTable<String, Edge> e;
    public MyHashTable<String, ArrayList<Node>> adjList = new MyHashTable<>();

    //Constructor takes hashmaps of nodes/vertices and edges
        public Graph(MyHashTable<String, Node> v, MyHashTable<String, Edge> e) {
        this.v = v;
        this.e = e;
        this.adjacencyList();
    }

    //setter for node/vertices
    public void addNode(Node node) {
        v.put(node.getRdOrIntID(), node);
    }

    //getter for node/vertices
    public MyHashTable<String, Node> getNodeMap() {
        return v;
    }

    //setter for edges
    public void addEdge(Edge edge) {
        e.put(edge.getRdOrIntID(), edge);
    }

    //getter for edges
    public MyHashTable<String, Edge> getEdgeMap() {
        return e;
    }


    //Dijkstra's algorithm
    public ArrayList<Node> findShortestPath(String startID, String endID) {
        ArrayList finalPath = new ArrayList();
        adjacencyList();

        // Checking for Errors
        if (startID == endID) {
            System.out.println("Cannot traverse from a point to itself.");
            finalPath.add(v.get(startID));
            return finalPath;
        }
        if (!(v.contains(startID))) {
            throw new NoSuchElementException("Start ID not known.");
        }
        if (!(v.contains(endID))) {
            throw new NoSuchElementException("End ID not known.");
        }

        // Starting Procedures
        MyPriorityQueue<Node> unvisitedQueue = new MyPriorityQueue<>();
        for (String i: v.keys()) {
            (v.get(i)).setDist(999999999);
            (v.get(i)).setParent(null);
            unvisitedQueue.add(v.get(i));
        }

        //Beginning Algorithm at Start Vertex
        unvisitedQueue.remove(v.get(startID));
        v.get(startID).setDist(0);
        unvisitedQueue.add(v.get(startID));
        double distTest;
        Node currentV;
        while (!unvisitedQueue.isEmpty()) {
            currentV = unvisitedQueue.poll();
            for (Node i: this.getAdjNodes(currentV)) {
                distTest = Haversine.calculateDistance(currentV, i);
                distTest = distTest + currentV.getDist();
                if (distTest < i.getDist()) {
                    i.setDist(distTest);
                    i.setParent(currentV);
                    if (unvisitedQueue.remove(i)) {
                        unvisitedQueue.add(i);
                    }
                }
            }
        }

        // Building finalPath
        Node currentN;
        currentN = v.get(endID);
        while (!(currentN.equals(v.get(startID)))) {
            finalPath.add(0, currentN);
            currentN = currentN.getParent();
        }
        finalPath.add(0, v.get(startID));

        return finalPath;
    }

    private ArrayList<Node> getAdjNodes(Node inputNode) {
        return adjList.get(inputNode.getRdOrIntID());
    }

    public void adjacencyList() {
        // Populating Adjacency List Keys with each Node.
        MyHashTable<String, ArrayList<Node>> adjacencyList = new MyHashTable<>();
        for (String i : v.keys()) {
            ArrayList<Node> inputArrayList = new ArrayList();
            adjacencyList.put(i, inputArrayList);
        }

        // Populating Adjacency List Values with Adjacent Nodes' String IDs.
        for (Edge currEdge : e.values()) {
            (adjacencyList.get(currEdge.getStartNode().getRdOrIntID())).add(currEdge.getEndNode());
            (adjacencyList.get(currEdge.getEndNode().getRdOrIntID())).add(currEdge.getStartNode());
        }

        adjList = adjacencyList;
    }


}