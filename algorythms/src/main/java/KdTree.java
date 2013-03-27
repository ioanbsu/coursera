import java.util.TreeSet;

/**
 * @author IoaN, 3/17/13 10:59 AM
 */
public class KdTree {

    private int size;
    private Node rootNode;

    private static long timing=0;

    /**
     * construct an empty set of points
     */
    public KdTree() {

    }

    /**
     * is the set empty?
     *
     * @return true if set is empty. Otherwise return false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * number of points in the set
     *
     * @return quantity of points
     */
    public int size() {
        return size;
    }

    /**
     * add the point p to the set (if it is not already in the set)
     *
     * @param pointToInsert point to add to the set
     */
    public void insert(Point2D pointToInsert) {
        if (pointToInsert == null) {
            return;
        }
        if (rootNode == null) {
            rootNode = new Node();
            rootNode.point = pointToInsert;
            rootNode.rect = new RectHV(0, 0, 1, 1);
            size++;
            return;
        }
        try {
            searchChildBranch(pointToInsert);
        } catch (ElementDoesNotExistException e) {
            Node foundParentNode = e.foundDeepestNode;
            size++;
            Node newNode = new Node();
            newNode.point = pointToInsert;
            if (foundParentNode.nodeType == NodeType.VERTICAL) {
                if (pointToInsert.x() < foundParentNode.point.x()) {
                    newNode.rect = new RectHV(foundParentNode.rect.xmin(), foundParentNode.rect.ymin(), foundParentNode.point.x(), foundParentNode.rect.ymax());
                    foundParentNode.leftChild = newNode;
                } else {
                    newNode.rect = new RectHV(foundParentNode.point.x(), foundParentNode.rect.ymin(), foundParentNode.rect.xmax(), foundParentNode.rect.ymax());
                    foundParentNode.rightChild = newNode;
                }
                newNode.nodeType = NodeType.HORIZONTAL;
            }
            if (foundParentNode.nodeType == NodeType.HORIZONTAL) {
                if (pointToInsert.y() < foundParentNode.point.y()) {
                    newNode.rect = new RectHV(foundParentNode.rect.xmin(), foundParentNode.rect.ymin(), foundParentNode.rect.xmax(), foundParentNode.point.y());
                    foundParentNode.leftChild = newNode;
                } else {
                    newNode.rect = new RectHV(foundParentNode.rect.xmin(), foundParentNode.point.y(), foundParentNode.rect.xmax(), foundParentNode.rect.ymax());
                    foundParentNode.rightChild = newNode;
                }
                newNode.nodeType = NodeType.VERTICAL;
            }
        }
    }

    /**
     * does the set contain the point p?
     *
     * @param p checks is set contains point.
     * @return true if contains, otherwise returns false.
     */
    public boolean contains(Point2D p) {
        if (isEmpty()) {
            return false;
        }
        try {
            searchChildBranch(p);
            return true;
        } catch (ElementDoesNotExistException e) {
            return false;
        }
    }

    /**
     * draw all of the points to standard draw
     */
    public void draw() {
        drawAll(rootNode);
    }

    /**
     * all points in the set that are inside the rectangle
     *
     * @param rect the area where we're searching points in.
     * @return points that are inside rect
     */
    public Iterable<Point2D> range(RectHV rect) {
        TreeSet<Point2D> intersectedNodes = new TreeSet<Point2D>();
        searchIntersected(rootNode, intersectedNodes, rect);
        return intersectedNodes;
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty
     *
     * @param p finds nearest neibour to the point
     * @return returns nearest neibhour.
     */
    public Point2D nearest(Point2D p) {
      //  long startTime=System.nanoTime();
        try {
            if (isEmpty()) {
                return null;
            }
            Node leftBranch = rootNode.leftChild;
            Node rightBranch = rootNode.rightChild;
            Point2D nearestPoint2D1 = findNearest(leftBranch, rootNode.point, p.distanceSquaredTo(rootNode.point), p);
            Point2D nearestPoint2D2 = findNearest(rightBranch, rootNode.point, p.distanceSquaredTo(rootNode.point), p);
         //   timing+=System.nanoTime()-startTime;
            if (nearestPoint2D1.distanceSquaredTo(p) > nearestPoint2D2.distanceSquaredTo(p)) {
                return nearestPoint2D2;
            } else {
                return nearestPoint2D1;
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        return null;
    }

    private Point2D findNearest(Node nodeToSearch, Point2D nearestPoint, double nearestDistance, Point2D queryPoint) {
        if (nodeToSearch == null) {
            return nearestPoint;
        }
        double calculatedNearestDistanceSquared = queryPoint.distanceSquaredTo(nodeToSearch.point);
        if (calculatedNearestDistanceSquared <= nearestDistance) {
            nearestPoint = nodeToSearch.point;
            nearestDistance = calculatedNearestDistanceSquared;
        }
        // if the closest point discovered so far is closer than the distance between the query point and the
        // rectangle corresponding to a node, there is no need to explore that node (or its subtrees)
        boolean goLeftFirst = isGoLeftFirst(nodeToSearch, queryPoint);
        Point2D nearestPoint2 = nearestPoint;
        if (goLeftFirst) {
            if (nodeToSearch.leftChild != null && nodeToSearch.leftChild.rect.distanceSquaredTo(queryPoint) <= calculatedNearestDistanceSquared) {
                nearestPoint2 = findNearest(nodeToSearch.leftChild, nearestPoint, nearestDistance, queryPoint);
            }
            calculatedNearestDistanceSquared = queryPoint.distanceSquaredTo(nearestPoint2);
            if (calculatedNearestDistanceSquared <= nearestDistance) {
                nearestDistance = calculatedNearestDistanceSquared;
            }
            if (nodeToSearch.rightChild != null && nodeToSearch.rightChild.rect.distanceSquaredTo(queryPoint) <= calculatedNearestDistanceSquared) {
                nearestPoint = findNearest(nodeToSearch.rightChild, nearestPoint, nearestDistance, queryPoint);
            }
        } else {
            if (nodeToSearch.rightChild != null && nodeToSearch.rightChild.rect.distanceSquaredTo(queryPoint) <= calculatedNearestDistanceSquared) {
                nearestPoint = findNearest(nodeToSearch.rightChild, nearestPoint, nearestDistance, queryPoint);
            }
            calculatedNearestDistanceSquared = queryPoint.distanceSquaredTo(nearestPoint);
            if (calculatedNearestDistanceSquared <= nearestDistance) {
                nearestDistance = calculatedNearestDistanceSquared;
            }
            if (nodeToSearch.leftChild != null && nodeToSearch.leftChild.rect.distanceSquaredTo(queryPoint) <= calculatedNearestDistanceSquared) {
                nearestPoint2 = findNearest(nodeToSearch.leftChild, nearestPoint, nearestDistance, queryPoint);
            }
        }

        if (nearestPoint2.distanceSquaredTo(queryPoint) > nearestPoint.distanceSquaredTo(queryPoint)) {
            return nearestPoint;
        }
        return nearestPoint2;
    }

    private boolean isGoLeftFirst(Node nodeToSearch, Point2D queryPoint) {
        if (nodeToSearch.rightChild == null) {
            return true;
        } else if (nodeToSearch.leftChild == null) {
            return false;
        }
//        if (nodeToSearch.nodeType == NodeType.VERTICAL) {
            return nodeToSearch.leftChild.rect.distanceSquaredTo(queryPoint)<nodeToSearch.rightChild.rect.distanceSquaredTo(queryPoint);
//        } else {
//            return queryPoint.y() < nodeToSearch.point.y();
//        }

    }

    private void searchIntersected(Node nodeToAnalyze, TreeSet<Point2D> intersectedNodes, RectHV rect) {
        if (nodeToAnalyze == null) {
            return;
        }
        if (rect.contains(nodeToAnalyze.point)) {
            intersectedNodes.add(nodeToAnalyze.point);
        }
        if (nodeToAnalyze.rightChild != null && nodeToAnalyze.rightChild.rect.intersects(rect)) {
            searchIntersected(nodeToAnalyze.rightChild, intersectedNodes, rect);
        }
        if (nodeToAnalyze.leftChild != null && nodeToAnalyze.leftChild.rect.intersects(rect)) {
            searchIntersected(nodeToAnalyze.leftChild, intersectedNodes, rect);
        }

    }

    private Node searchChildBranch(Point2D searchPoint) throws ElementDoesNotExistException {
        Node searchNode = rootNode;
        while (searchNode != null) {
            Node nextBranch = null;
            if (searchNode.point.equals(searchPoint)) {
                return searchNode;
            }
            if (searchNode.nodeType == NodeType.VERTICAL) {
                if (searchPoint.x() < searchNode.point.x()) {
                    nextBranch = searchNode.leftChild;
                } else {
                    nextBranch = searchNode.rightChild;
                }
            }
            if (searchNode.nodeType == NodeType.HORIZONTAL) {
                if (searchPoint.y() < searchNode.point.y()) {
                    nextBranch = searchNode.leftChild;
                } else {
                    nextBranch = searchNode.rightChild;
                }
            }
            if (nextBranch == null) {
                throw new ElementDoesNotExistException(searchNode);
            } else {
                searchNode = nextBranch;
            }
        }
        return null;
    }

    private void drawAll(Node rootNode) {
        if (rootNode != null) {
            StdDraw.point(rootNode.point.x(), rootNode.point.y());
            drawAll(rootNode.leftChild);
            drawAll(rootNode.rightChild);
        }
    }


    private enum NodeType {
        VERTICAL, HORIZONTAL;
    }

    private class Node {
        private NodeType nodeType = NodeType.VERTICAL;
        private Point2D point;
        private RectHV rect;
        private Node leftChild;
        private Node rightChild;

    }

    private class ElementDoesNotExistException extends Exception {
        private Node foundDeepestNode;

        private ElementDoesNotExistException(Node foundDeepestNode) {
            this.foundDeepestNode = foundDeepestNode;
        }
    }

}
