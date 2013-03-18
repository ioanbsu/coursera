import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * @author IoaN, 2/23/13 10:29 PM
 */
public class Fast {
    private static final int COLINEAR_COUNT = 3;
    private static String fileContent = null;

    public static void main(String[] args) throws Exception {
        readFile(args[0]);
        StdDraw.setScale(0, 32768);
        StdDraw.setPenRadius(0.002);
        Point[] points = initiatePointsArray();
        Set<TreeSet<Point>> listOfColinearPoints = new HashSet<TreeSet<Point>>();
        for (int j = 0; j < points.length; j++) {
            points[j].draw();
        }
        for (int i = 0; i < points.length; i++) {
            Point[] copiedArray = new Point[points.length];
            System.arraycopy(points, 0, copiedArray, 0, copiedArray.length);
            Arrays.sort(copiedArray, points[i].SLOPE_ORDER);
            TreeSet<Point> pointsWithSameSlope = new TreeSet<Point>();
            for (int j = 0; j < copiedArray.length - 1; j++) {
                if (points[i] == copiedArray[j] || points[i] == copiedArray[j + 1]) {
                    continue;
                }
                if (points[i].slopeTo(copiedArray[j]) == points[i].slopeTo(copiedArray[j + 1])) {
                    pointsWithSameSlope.add(copiedArray[j]);
                } else {
                    pointsWithSameSlope.add(copiedArray[j]);
                    addPointToList(points, listOfColinearPoints, i, pointsWithSameSlope);
                    pointsWithSameSlope = new TreeSet<Point>();
                }
            }
            pointsWithSameSlope.add(copiedArray[copiedArray.length - 1]);
            addPointToList(points, listOfColinearPoints, i, pointsWithSameSlope);
        }
        for (Set<Point> listOfColinearPoint : listOfColinearPoints) {
            Object[] pointsToPrint = listOfColinearPoint.toArray();
            Arrays.sort(pointsToPrint);
            printPoints(pointsToPrint);
        }
    }

    private static void addPointToList(Point[] points, Set<TreeSet<Point>> listOfColinearPoints, int i, TreeSet<Point> pointsWithSameSlope) {
        if (pointsWithSameSlope.size() >= COLINEAR_COUNT) {
            pointsWithSameSlope.add(points[i]);
            listOfColinearPoints.add(pointsWithSameSlope);
        }
    }

    private static void printPoints(Object[] points) {
        StringBuilder pointsPath = new StringBuilder();
        for (int i = 0; i < points.length; i++) {
            pointsPath.append(points[i]);
            if (i < points.length - 1) {
                pointsPath.append(" -> ");

            }
        }
        ((Point) points[0]).drawTo((Point) points[points.length - 1]);
        StdOut.println(pointsPath.toString());
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
        fileContent = fileContent.replaceAll("\r\n\r\n", "\r\n").replaceAll("\n\n", "\n");
        if (fileContent == null || fileContent.isEmpty()) {
            throw new Exception("Empty file was provided. Please provide file with correct architecture.");
        }
    }

//    ===============================reading from UnsatisfiedLinkError functions===========================

    private static Point[] initiatePointsArray() {
        int pointsSize = getNumberOfPoints();
        Point[] points = new Point[pointsSize];
        for (int i = 0; i < pointsSize; i++) {
            points[i] = getPoint(i);
        }
        return points;
    }

    private static Point getPoint(int i) {
        String[] strPointRepresentations = fileContent.split("\n");
        String pointStr = strPointRepresentations[i + 1];
        StringTokenizer numbersTokenizer = new StringTokenizer(pointStr, " ");
        return new Point(Integer.valueOf(numbersTokenizer.nextToken().replaceAll("\\s", "")),
                Integer.valueOf(numbersTokenizer.nextToken().replaceAll("\\s", "")));
    }

    private static int getNumberOfPoints() {
        String numOfRowsStr = fileContent.substring(0, fileContent.indexOf("\n"));
        return Integer.valueOf(numOfRowsStr.replaceAll("\\s", ""));
    }
}
