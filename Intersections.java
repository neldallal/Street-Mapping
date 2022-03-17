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
 * The intersections class has two important functions.
 * Firstly, the constructor, and secondly, the paint component.
 * The first constructor passes the vertices and edges hashtables
 * into the class and then proceeds to the paint component.
 * The second constructor passes both hashtables, and then proceeds to use the finalpath
 * arraylist of vertices in order to build a path arraylist containing all edges that
 * would be traveled through to reach the vertices specified in the finalpath arraylist.
 * It then proceeds to the paint component.
 * The paint component determines the range of longitudes and latitudes included
 * in the vertices hashtable. Then, it iterates through the edges to be drawn and
 * performs calculations on the longitudes and latitudes to determine where the points
 * should be placed on the map, drawing as it goes. If directions were specified, it will check
 * if the edge currently being calculated is present on the path arraylist of edges and
 * change the color and width with which it is drawn.
 * */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Color;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;

public class Intersections extends JPanel{
    public MyHashTable<String, Node> v;
    public MyHashTable<String, Edge> e;
    boolean highlightPath = false;
    ArrayList<String> pathList = new ArrayList<String>();

    public Intersections(MyHashTable<String, Node> v, MyHashTable<String, Edge> e) {
        this.v = v;
        this.e = e;
    }

    public Intersections(MyHashTable<String, Node> v, MyHashTable<String, Edge> e, ArrayList<Node> finalPath) {
        this.v = v;
        this.e = e;
        this.highlightPath = true;
        for (Edge currEdge: e.values()) {
            if ((finalPath.contains(currEdge.getStartNode())) && (finalPath.contains(currEdge.getEndNode()))) {
                this.pathList.add(currEdge.getRdOrIntID());
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        // Getting Map Dimensions
        int mapHeight = this.getHeight();
        int mapWidth = this.getWidth();


        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);

        // Finding min/max values for Lat/Long
        double minLat = 999999999;
        double minLong = 999999999;
        double maxLat = -999999999;
        double maxLong = -999999999;
        for (String i: v.keys()) {
            if ((v.get(i).getLat()) < minLat) {
                minLat = v.get(i).getLat();
            }
            if ((v.get(i).getLat()) > maxLat) {
                maxLat = v.get(i).getLat();
            }
            if ((v.get(i).getLong()) < minLong) {
                minLong = v.get(i).getLong();
            }
            if ((v.get(i).getLong()) > maxLong) {
                maxLong = v.get(i).getLong();
            }
        }

        // Drawing routes
        for (String i : e.keys()) {
            double getX1 = e.get(i).getStartNode().getLong();
            double getY1 = e.get(i).getStartNode().getLat();
            double getX2 = e.get(i).getEndNode().getLong();
            double getY2 = e.get(i).getEndNode().getLat();
            double xConstant = 0.9 * (mapWidth / (maxLong - minLong));
            double yConstant = 0.9 * (mapHeight / (maxLat - minLat));
            double calcX1 = (getX1 - minLong) * xConstant;
            double calcY1 = (getY1 - minLat) * yConstant;
            double calcX2 = (getX2 - minLong) * xConstant;
            double calcY2 = (getY2 - minLat) * yConstant;
            int startX = (int) Math.round(calcX1);
            int startY = (int) Math.round(mapHeight - calcY1);
            int endX = (int) Math.round(calcX2);
            int endY = (int) Math.round(mapHeight - calcY2);
            if (highlightPath == true) {
                if (pathList.contains(i)) {
                    g2D.setColor(Color.RED);
                    g2D.setStroke(new BasicStroke(5));
                    g2D.drawLine(startX, startY, endX, endY);
                    g2D.setColor(Color.BLACK);
                    g2D.setStroke(new BasicStroke());
                }
                else {
                    g2D.drawLine(startX, startY, endX, endY);
                }
            }
            else {
                g2D.drawLine(startX, startY, endX, endY);
            }
        }

    }

}
