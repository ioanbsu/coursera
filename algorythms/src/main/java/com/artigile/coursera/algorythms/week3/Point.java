package com.artigile.coursera.algorythms.week3;

/**
 * @author IoaN, 2/23/13 6:50 PM
 */
public class Point implements Comparable<Point>, IPoint {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {

    }

    public void drawTo(Point that) {

    }

    @Override
    public int compareTo(Point comparingObject) {
        if (comparingObject == null) {
            return 1;
        }
        if (comparingObject.y != y) {
            return y - comparingObject.y;
        } else {
            return x - comparingObject.x;
        }

    }

    public double slopeTo(Point that) {
        if (that.x == x) {
            return Double.MAX_VALUE;
        }
        return ((double) (that.y - y)) / (that.x - x);
    }

    public String toString() {
        return super.toString();
    }
}
