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
 * This is the main class by which the program runs.
 * This driver class extends JPanel in order to draw the map.
 * The main method reads in the command line arguments and then reads the input file,
 * adding nodes and edges to two separate hash tables. If the user specifies directions,
 * a graph is constructed from the nodes and edges and the path is found
 * from the start point to the end point specified by the user.
 * The path and the total distance in miles is printed to the console.
 *
 *
 * If the user specifies that the map be displayed, the main method will first build
 * the "canvas" upon which the map will be drawn, and names it "map".
 * The canvas is titled according to what text file was used. Then, in order to draw on map,
 * the main method adds a new iteration of the "Intersections" class to map.
 * A different constructor of "Intersections" is used depending on whether or not
 * the user specified directions.
 *
 * */


import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StreetMapping extends JPanel {

    static String input;
    static double minLat = Double.MAX_VALUE;
    static double maxLat = -1 * Double.MAX_VALUE;
    static double minLong = Double.MAX_VALUE;
    static double maxLong = -1 * Double.MAX_VALUE;
    static double maxDist = -1 * Double.MAX_VALUE;
    static MyHashTable<String, Node> v = new MyHashTable<>();
    static MyHashTable<String, Edge> e = new MyHashTable<>();
    static boolean displayDirections = false;
    static boolean displayMap = false;
    static String startID = null;
    static String endID = null;
    static ArrayList<Node> path;
    static JPanel Intersections = new JPanel();

    //Read the input file
    //initialize the graph object using the input stream
    //use Graph(In in) constructor.
    private static void readFile() {
        BufferedReader buffR = null;
        try {
            String current;
            buffR = new BufferedReader(new FileReader(input));

            while ((current = buffR.readLine()) != null) {
                String[] arr1 = current.split("\t");
                String r_or_i = arr1[0];

                if (r_or_i.equals("i")) {
                    Node node = new Node(arr1[1], Double.valueOf(arr1[2]), Double.valueOf(arr1[3]));
                    if (node.getLat() < minLat)
                        minLat = node.getLat();

                    if (node.getLat() > maxLat)
                        maxLat = node.getLat();

                    if (node.getLong() < minLong)
                        minLong = node.getLong();

                    if (node.getLong() > maxLong)
                        maxLong = node.getLong();

                    v.put(node.getRdOrIntID(),node);
                }
                else {
                    Edge edge = new Edge(arr1[1], v.get(arr1[2]), v.get(arr1[3]));

                    if (edge.getWeight() > maxDist) {
                        maxDist = edge.getWeight();
                    }

                    e.put(edge.getRdOrIntID(), edge);
                }
            } //end of while loop

            if (displayDirections == true) {
                Graph graph = new Graph(v, e);
                path = graph.findShortestPath(startID, endID);
                printPath();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printPath() {
        double pathDist = 0;
        for (int i=0; i<path.size() - 1; i++) {
            System.out.println(path.get(i).getRdOrIntID());
            pathDist += Haversine.calculateDistance(path.get(i), path.get(i+1));
        }

        System.out.println(path.get(path.size() - 1).getRdOrIntID());
        System.out.println("Distance of shortest path: ");
        double miles = pathDist * 0.621371;
        double roundMiles = Math.floor(miles * 1000) / 1000;

        System.out.print(roundMiles);
        System.out.println(" miles.");
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("No command line arguments entered");
            return;
        }

        input = args[0];

        for (int i = 1; i< args.length; i++) {
            if (args[i].equals("--show")) {
                displayMap = true;
            }
            else if(args[i].equals("--directions")) {
                //System.out.println(args[i]);
                displayDirections = true;
                //to make sure we have both arguments
                if (args.length == i+1 || args.length == i+2) {
                    System.err.println("Not enough arguments");
                    return;
                }
                startID = args[i+1];
                endID = args[i+2];
            }
        }

        if (!displayMap && !displayDirections) {
            System.err.println("You need to specify to show directions via" +
                    " '--directions startID endID' or to show the map via '--show'");
        }

        readFile();

        if (displayMap == true) {
            // Building Map Frame
            int mapHeight = 600;
            int mapWidth = 800;
            JFrame map = new JFrame();
            map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            if (args[0].contains("ur")) {
                map.setTitle("Map of UoR Campus");
            }
            else if (args[0].contains("nys")) {
                map.setTitle("Map of New York State");
            }
            else if (args[0].contains("monroe")) {
                map.setTitle("Map of Monroe County");
            }
            else {
                map.setTitle("Map of somewhere or other");
            }

            // Drawing Intersections
            if (displayDirections == true) {
                Intersections draw = new Intersections(v, e, path);
                map.add(draw);
            }
            else {
                Intersections draw = new Intersections(v, e);
                map.add(draw);
            }
            map.setSize(mapWidth, mapHeight);
            map.setVisible(true);

        }

    }

}
