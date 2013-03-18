import java.util.TreeSet;

/**
 * @author IoaN, 3/17/13 10:59 AM
 */
public class KdTree {

    private int size;
    private Node rootNode;

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
        if (rootNode == null) {
            rootNode = new Node();
            rootNode.point = pointToInsert;
            rootNode.rect = new RectHV(0, 0, 1, 1);
            return;
        }
        Node foundParentNode = searchChildBranch(pointToInsert);
        if (foundParentNode.point.equals(pointToInsert)) {
            return;
        }
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
        size++;

    }

    /**
     * does the set contain the point p?
     *
     * @param p checks is set contains point.
     * @return true if contains, otherwise returns false.
     */
    public boolean contains(Point2D p) {
        Node foundNode = searchChildBranch(p);
        return foundNode.point.equals(p);
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
        Node nodeToSearch = rootNode;
        TreeSet<Point2D> intersectedNodes = new TreeSet<Point2D>();
        searchIntersected(nodeToSearch, intersectedNodes, rect);
        return intersectedNodes;
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty
     *
     * @param p finds nearest neibour to the point
     * @return returns nearest neibhour.
     */
    public Point2D nearest(Point2D p) {
        Node leftBranch = rootNode.leftChild;
        Node rightBranch = rootNode.rightChild;
        Point2D nearestPoint2D1 = findNeareast(leftBranch, rootNode.point, p.distanceSquaredTo(rootNode.point), p);
        Point2D nearestPoint2D2 = findNeareast(rightBranch, rootNode.point, p.distanceSquaredTo(rootNode.point), p);
        if (nearestPoint2D1.distanceSquaredTo(p) > nearestPoint2D2.distanceSquaredTo(p)) {
            return nearestPoint2D2;
        } else {
            return nearestPoint2D1;
        }
    }

    private Point2D findNeareast(Node nodeToSearch, Point2D nearestPoint, double nearestDistance, Point2D queryPoint) {
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
                nearestPoint2 = findNeareast(nodeToSearch.leftChild, nearestPoint, nearestDistance, queryPoint);
            }
            if (nodeToSearch.rightChild != null && nodeToSearch.rightChild.rect.distanceSquaredTo(queryPoint) <= calculatedNearestDistanceSquared) {
                nearestPoint = findNeareast(nodeToSearch.rightChild, nearestPoint, nearestDistance, queryPoint);
            }
        } else {
            if (nodeToSearch.rightChild != null && nodeToSearch.rightChild.rect.distanceSquaredTo(queryPoint) <= calculatedNearestDistanceSquared) {
                nearestPoint = findNeareast(nodeToSearch.rightChild, nearestPoint, nearestDistance, queryPoint);
            }
            if (nodeToSearch.leftChild != null && nodeToSearch.leftChild.rect.distanceSquaredTo(queryPoint) <= calculatedNearestDistanceSquared) {
                nearestPoint2 = findNeareast(nodeToSearch.leftChild, nearestPoint, nearestDistance, queryPoint);
            }
        }

        if (nearestPoint2.distanceSquaredTo(queryPoint) > nearestPoint.distanceSquaredTo(queryPoint)) {
            return nearestPoint;
        }
        return nearestPoint2;
    }

    private boolean isGoLeftFirst(Node nodeToSearch, Point2D queryPoint) {
        if (nodeToSearch.nodeType == NodeType.VERTICAL) {
            return queryPoint.x() < nodeToSearch.point.x();
        } else {
            return queryPoint.y() < nodeToSearch.point.y();
        }

    }

    private void searchIntersected(Node nodeToAnalyze, TreeSet<Point2D> intersectedNodes, RectHV rect) {
        if (nodeToAnalyze == null) {
            return;
        }
        if (nodeToAnalyze.nodeType == NodeType.VERTICAL) {
            if (nodeToAnalyze.point.x() < rect.xmax()) {
                searchIntersected(nodeToAnalyze.rightChild, intersectedNodes, rect);
            }
            if (nodeToAnalyze.point.x() > rect.xmin()) {
                searchIntersected(nodeToAnalyze.leftChild, intersectedNodes, rect);
            }
        } else if (nodeToAnalyze.nodeType == NodeType.HORIZONTAL) {
            if (nodeToAnalyze.point.y() < rect.ymax()) {
                searchIntersected(nodeToAnalyze.rightChild, intersectedNodes, rect);
            }
            if (nodeToAnalyze.point.y() > rect.ymin()) {
                searchIntersected(nodeToAnalyze.leftChild, intersectedNodes, rect);
            }
        }
        if (rect.contains(nodeToAnalyze.point)) {
            intersectedNodes.add(nodeToAnalyze.point);
        }
    }

    private Node searchChildBranch(Point2D searchPoint) {
        Node searchNode = rootNode;
        while (searchNode != null) {
            Node nextBranch = null;
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
                return searchNode;
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
        return;
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

}
