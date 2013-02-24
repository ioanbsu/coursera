import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * @author IoaN, 2/23/13 10:29 PM
 */
public class Fast {
    public static final int COLINEAR_COUNT = 3;
    private static String fileContent = null;

    public static void main(String[] args) throws Exception {
        readFile(args[0]);
        StdDraw.setScale(0, 32768);
        StdDraw.setPenRadius(0.005);
        Point[] points = initiatePointsArray();
        Set<TreeSet<Point>> listOfColinearPoints = new HashSet<TreeSet<Point>>();
        for (int i = 0; i < points.length; i++) {
            points[i].draw();
            Arrays.sort(points, points[i].SLOPE_ORDER);
            TreeSet<Point> pointsWithSameSlope = new TreeSet<Point>();
            for (int j = 0; j < points.length - 1; j++) {
                if (i == j || i == j + 1) {
                    continue;
                }
                if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[j + 1])) {
                    pointsWithSameSlope.add(points[j]);
                } else {
                    pointsWithSameSlope.add(points[j]);
                    if (pointsWithSameSlope.size() >= COLINEAR_COUNT) {
                        pointsWithSameSlope.add(points[i]);
                        addPointsSetToFoundList(listOfColinearPoints, pointsWithSameSlope);
                    }
                    pointsWithSameSlope = new TreeSet<Point>();
                }
            }
            if (pointsWithSameSlope.size() >= COLINEAR_COUNT) {
                pointsWithSameSlope.add(points[i]);
                addPointsSetToFoundList(listOfColinearPoints, pointsWithSameSlope);
            }
        }
        for (Set<Point> listOfColinearPoint : listOfColinearPoints) {
            Object[] pointsToPrint = listOfColinearPoint.toArray();
            Arrays.sort(pointsToPrint);
            printPoints(pointsToPrint);
        }
    }

    private static void addPointsSetToFoundList(Set<TreeSet<Point>> listOfColinearPoints, TreeSet<Point> pointsWithSameSlope) {
        double insertSetSlope = getSetSlope(pointsWithSameSlope);
        for (TreeSet<Point> listOfColinearPoint : listOfColinearPoints) {
            if (insertSetSlope == getSetSlope(listOfColinearPoint)) {
                return;
            }
        }
        listOfColinearPoints.add(pointsWithSameSlope);
    }

    private static double getSetSlope(TreeSet<Point> pointsWithSameSlope) {
        Iterator<Point> pointsIterator = pointsWithSameSlope.iterator();
        return pointsIterator.next().slopeTo(pointsIterator.next());
    }

    private static void printPoints(Object[] points) {
        for (int i = 0; i < points.length; i++) {
            StdOut.print(points[i]);
            if (i < points.length - 1) {
                StdOut.print(" -> ");
                ((Point) points[i]).drawTo((Point) points[i + 1]);
            }
        }
        StdOut.println();
    }

    private static void readFile(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(ClassLoader.getSystemResource(fileName).getFile()));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        fileContent = stringBuilder.toString();
        if (fileContent == null || fileContent.isEmpty()) {
            throw new Exception("Empty file was provided. Please provide file with correct architecture.");
        }
    }


//    ===============================reading from UnsatisfiedLinkError functions===========================

    protected static Point[] initiatePointsArray() {
        int pointsSize = getNumberOfPoints();
        Point[] points = new Point[pointsSize];
        for (int i = 0; i < pointsSize; i++) {
            points[i] = getPoint(i);
        }
        return points;
    }

    private static Point getPoint(int i) {
        String[] strPointRepresentations = fileContent.split("\r\n");
        String pointStr = strPointRepresentations[i + 1];
        StringTokenizer numbersTokenizer = new StringTokenizer(pointStr, " ");
        return new Point(Integer.valueOf(numbersTokenizer.nextToken()), Integer.valueOf(numbersTokenizer.nextToken()));
    }

    private static int getNumberOfPoints() {
        String numOfRowsStr = fileContent.substring(0, fileContent.indexOf("\r\n"));
        return Integer.valueOf(numOfRowsStr);
    }

    private static class MyHashSet extends HashSet<Set<Point>> {
        @Override
        public boolean equals(Object o) {
            Set<Point> thatPoint = (Set<Point>) o;
            return thatPoint != null && !thatPoint.isEmpty() && !isEmpty() && thatPoint.iterator().next() == iterator().next();
        }

        @Override
        public int hashCode() {
            return iterator().next().hashCode();
        }
    }

}
