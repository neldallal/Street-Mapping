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
 * This class contains the Haversine function implementation to calculate the weights
 * (distances) between two nodes on a sphere (Earth).
 *
 * The Haversine formula calculates the shortest distance between
 * two points on a sphere using their latitudes and longitudes measured along the surface.
 *
 */


public class Haversine {

    public static double calculateDistance(Node startNode, Node endNode) {
        final double r = 6371;
        double diffLat = Math.toRadians(endNode.getLat() - startNode.getLat());
        double diffLong = Math.toRadians(endNode.getLong() - startNode.getLong());

        //convert to radians
        double startLat = Math.toRadians(startNode.getLat());
        double endLat = Math.toRadians(endNode.getLat());

        //apply formulae
        double a = Math.pow(Math.sin(diffLat / 2),2) +
                    Math.pow(Math.sin(diffLong / 2), 2) *
                    Math.cos(startLat) *
                    Math.cos(endLat);

        double c = 2 * Math.asin(Math.sqrt(a));
        return r * c;
    }

}
