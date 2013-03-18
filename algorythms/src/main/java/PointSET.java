import java.util.TreeSet;

/**
 * @author IoaN, 3/17/13 9:25 AM
 */
public class PointSET {

    private TreeSet<Point2D> points = new TreeSet<Point2D>();

    /**
     * construct an empty set of points
     */
    public PointSET() {

    }

    /**
     * is the set empty?
     *
     * @return true if set is empty. Otherwise return false.
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * number of points in the set
     *
     * @return quantity of points
     */
    public int size() {
        return points.size();
    }

    /**
     * add the point p to the set (if it is not already in the set)
     *
     * @param p point to add to the set
     */
    public void insert(Point2D p) {
        points.add(p);
    }

    /**
     * does the set contain the point p?
     *
     * @param p checks is set contains point.
     * @return true if contains, otherwise returns false.
     */
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    /**
     * draw all of the points to standard draw
     */
    public void draw() {
        for (Point2D point : points) {
            StdDraw.point(point.x(), point.y());
        }
    }

    /**
     * all points in the set that are inside the rectangle
     *
     * @param rect the area where we're searching points in.
     * @return points that are inside rect
     */
    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> pointsInRect = new TreeSet<Point2D>();
        for (Point2D point : points) {
            if (point.x() > rect.xmin() && point.x() < rect.xmax() && point.y() > rect.ymin() && point.y() < rect.ymax()) {
                pointsInRect.add(point);
            }
        }
        return pointsInRect;
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty
     *
     * @param p finds nearest neibour to the point
     * @return returns nearest neibhour.
     */
    public Point2D nearest(Point2D p) {
        double nearesDistance = Double.MAX_VALUE;
        Point2D closestPoint = null;
        for (Point2D point : points) {
            double distance = p.distanceTo(point);
            if (distance < nearesDistance) {
                nearesDistance = distance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }
}
