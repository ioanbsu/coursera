import java.util.Comparator;

/**
 * @author IoaN, 2/23/13 7:01 PM
 */
public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            double slope1 = slopeTo(o1);
            double slope2 = slopeTo(o2);
            if (slope1 == slope2) {
                return 0;
            } else if (slope1 - slope2 > 0) {
                return 1;
            }
            return -1;
        }
    };
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // unit test
    public static void main(String[] args) {
//        StdDraw.setScale(0,10);
//        StdDraw.setPenRadius(0.01);
       /* Point p = new Point(241, 58);
        Point q = new Point(357, 111);
        Point r = new Point(241, 58);
        Point s = new Point(176, 390);*/

        Point p = new Point(2,4);
        Point q = new Point(5,1);
        Point r = new Point(2,4);
        System.out.println(p.SLOPE_ORDER.compare(q,r));
        System.out.println(p.slopeTo(q));
        System.out.println(p.slopeTo(r));
//        System.out.println(p.SLOPE_ORDER.compare(r,s));
//        System.out.println(p.SLOPE_ORDER.compare(q,s));
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if(x==that.x){
            if(y==that.y){
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        if(y==that.y){
            return 0;
        }
        return ((double) (that.y - y)) / (that.x - x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that.y != y) {
            return y - that.y;
        } else {
            return x - that.x;
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}