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
 * A node class to be used in constructing the graph that has class variables
 * of Road or Intersection ID, latitude, longitude, distance, and parent.
 * It also implements the comparable interface to compare the current shortest path
 * that it took to get to that node.
 * */

import java.util.Comparator;

public class Node implements Comparable<Node> {
    private String RdOrIntID;
    private double latitude;
    private double longitude;
    private double dist = Double.POSITIVE_INFINITY;
    private Node parent;

    //constructor
    public Node(String ID, double latitude, double longitude) {
        this.RdOrIntID = ID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //getter for ID
    public String getRdOrIntID() {
        return RdOrIntID;
    }

    //getter for latitude
    public double getLat() {
        return latitude;
    }

    //getter for longitude
    public double getLong() {
        return longitude;
    }

    //setter for Distance
    public void setDist(double dist){
        this.dist = dist;
    }

    //getter for Distance
    public double getDist() {
        return dist;
    }

    //setter for parent
    public void setParent(Node n) {
        parent = n;
    }

    //getter for parent
    public Node getParent() {
        return parent;
    }


    @Override
    public int compareTo(Node o) {
        return  Double.compare(dist, o.getDist());
    }

}