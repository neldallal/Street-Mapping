import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Mapping extends JPanel {

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
    static double width = 420;
    static double height = 400;

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
            Node n1 = (Node) path.get(i);
            System.out.println(n1.getRdOrIntID());
            pathDist += Haversine.calculateDistance((Node)path.get(i), (Node)path.get(i+1));
        }

        Node n2 = (Node) path.get(path.size() - 1);
        System.out.println(n2.getRdOrIntID());
        System.out.println("Distance of shortest path: ");
        double miles = pathDist * 0.621371;
        double roundMiles = Math.floor(miles * 1000) / 1000;

        System.out.print(roundMiles);
        System.out.println(" miles.");
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double latitudeScale = this.getHeight()/Math.abs(maxLat - minLat);
        double longitudeScale = this.getWidth()/Math.abs(maxLong - minLong);
        /*e.forEach((key,value) -> {
            Edge edge = e.get(key);
            int startX = (int) ((edge.getStartNode().getLong() - minLong) * longitudeScale);
            int startY = (int) (this.getHeight() - ((edge.getStartNode().getLat() - minLat) * latitudeScale));

            int endX = (int) ((edge.getEndNode().getLong() - minLong) * longitudeScale);
            int endY = (int) (this.getHeight() - (edge.getEndNode().getLat() - minLat) * latitudeScale);

            g2D.drawLine(startX, startY, endX, endY);
        });*/

        if (displayDirections) {
            g2D.setColor(Color.RED);
            g2D.setStroke(new BasicStroke(5));

            for (int i=0; i<path.size() - 1; i++) {
                int startX = (int) ((path.get(i).getLong() - minLong) * longitudeScale);
                int startY = (int) (this.getHeight() - ((path.get(i).getLat() - minLat) * latitudeScale));

                int endX = (int) ((path.get(i+1).getLong() - minLong) * longitudeScale);
                int endY = (int) (this.getHeight() - ((path.get(i+1).getLat() - minLat) * latitudeScale));

                g2D.drawLine(startX, startY, endX, endY);
            }
        }

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
                System.out.println(args[i]);
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
            //TODO: paint component
            JFrame frame = new JFrame();
            frame.setSize(400,420);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Mapping mapping = new Mapping();
            frame.setContentPane(mapping);

            frame.setVisible(true);
            frame.invalidate();

        }

    }

}

/*    public void forEach(BiConsumer<? super Key, ? super Value> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<Key, Value> entry : entrySet()) {
            Key k;
            Value v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }*/
