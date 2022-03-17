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
 * An edge class to be used in constructing the graph that has
 * class variables for the Road or Intersection ID, start node, end node, and path weight.
 * */

public class Edge {
    private String ID;
    private Node startNode;
    private Node endNode;
    private double weight;

    //constructor
    public Edge(String ID, Node startNode, Node endNode) {
        this.ID = ID;
        this.startNode = startNode;
        this.endNode = endNode;

        //Get the distance between two nodes/vertices using the Haversine function
        weight = Haversine.calculateDistance(startNode,endNode);

    }

    public String getRdOrIntID() {
        return ID;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public double getWeight() {
        return weight;
    }

}
