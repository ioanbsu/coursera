import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author IoaN, 2/23/13 7:07 PM
 */
public class Brute {

    private static String fileContent = null;

    public static void main(String[] args) throws Exception {
        readFile(args[0]);
        StdDraw.setScale(0, 32768);
        StdDraw.setPenRadius(0.005);
        Point[] points = initiatePointsArray();
        for (int j = 0; j < points.length; j++) {
            points[j].draw();
        }
        Arrays.sort(points);
        for (int i1 = 0; i1 < points.length; i1++) {
            for (int i2 = i1 + 1; i2 < points.length; i2++) {
                for (int i3 = i2 + 1; i3 < points.length; i3++) {
                    for (int i4 = i3 + 1; i4 < points.length; i4++) {
                        if (points[i1].slopeTo(points[i2]) == points[i1].slopeTo(points[i3])
                                && points[i1].slopeTo(points[i2]) == points[i1].slopeTo(points[i4])) {
                            printPoints(points[i1], points[i2], points[i3], points[i4]);
                        }
                    }
                }
            }
        }

    }

    private static void printPoints(Point point1, Point point2, Point point3, Point point4) {
        Point[] pointsToPrint = new Point[4];
        pointsToPrint[0] = point1;
        pointsToPrint[1] = point2;
        pointsToPrint[2] = point3;
        pointsToPrint[3] = point4;
        Arrays.sort(pointsToPrint);
        printPoints(pointsToPrint);
    }

    private static void printPoints(Point[] points) {
        StringBuilder pointsPath = new StringBuilder();
        for (int i = 0; i < points.length; i++) {
            pointsPath.append(points[i]);
            if (i < points.length - 1) {
                pointsPath.append(" -> ");
            }
        }
        points[0].drawTo(points[points.length - 1]);
        if (!pointsPath.toString().trim().isEmpty()) {
            StdOut.println(pointsPath.toString());
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
//        StdOut.println("=====File content====");
//        StdOut.println(fileContent);
//        StdOut.println("=====================");
    }

    private static int getNumberOfPoints() {
        String numOfRowsStr = fileContent.substring(0, fileContent.indexOf("\n"));
        return Integer.valueOf(numOfRowsStr.replaceAll("\\s", ""));
    }


}
