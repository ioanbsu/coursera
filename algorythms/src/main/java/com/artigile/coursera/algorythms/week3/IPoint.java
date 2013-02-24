package com.artigile.coursera.algorythms.week3;

/**
 * @author IoaN, 2/23/13 6:51 PM
 */
public interface IPoint {

    // draw this point
    public void draw();

    // draw the line segment from this point to that point
    public void drawTo(Point that);

    // string representation
    public String toString();

    // is this point lexicographically smaller than that point?
    public int compareTo(Point that);

    // the slope between this point and that point
    public double slopeTo(Point that);


}
