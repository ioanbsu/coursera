import java.util.Arrays;

/**
 * Date: 11/10/13
 * Time: 11:40 AM
 *
 * @author ioanbsu
 */

public class SeamCarver {


    private final int RED_COLOR = 16;
    private final int GREEN_COLOR = 8;
    private final int BLUE_COLOR = 0;
    private Picture picture;
    private double[][] energyMatrix;
    private double[][] transformedEnergyMatrix;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        energyMatrix = new double[height()][width()];
        transformedEnergyMatrix = new double[width()][height()];

        for (int heightIndex = 0; heightIndex < height(); heightIndex++) {
            for (int widthIndex = 0; widthIndex < width(); widthIndex++) {
                double calculatedEnergy = energy(widthIndex, heightIndex);
                energyMatrix[heightIndex][widthIndex] = calculatedEnergy;
                transformedEnergyMatrix[widthIndex][heightIndex] = calculatedEnergy;
            }
        }
    }

    public static void main(String[] args) {

    }

    /**
     * @return current picture
     */
    public Picture picture() {
        return picture;
    }

    /**
     * @return width of current picture
     */
    public int width() {
        return picture.width();
    }

    /**
     * @return height of current picture
     */
    public int height() {
        return picture.height();

    }

    /**
     * @param x x coordinate of picture pixel                    represents width
     * @param y y coordinate of picture pixel                    represents height
     * @return energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x > picture.width() || y > picture.height()) {
            throw new IndexOutOfBoundsException();
        }
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 255D * 255D * 3;
        }
        int rDifX = getHorizontalCentralDiff(x, y, RED_COLOR);
        int gDifX = getHorizontalCentralDiff(x, y, GREEN_COLOR);
        int bDifX = getHorizontalCentralDiff(x, y, BLUE_COLOR);
        int deltaXSquared = rDifX * rDifX + gDifX * gDifX + bDifX * bDifX;

        int rDifY = getVerticalCentralDiff(x, y, RED_COLOR);
        int gDifY = getVerticalCentralDiff(x, y, GREEN_COLOR);
        int bDifY = getVerticalCentralDiff(x, y, BLUE_COLOR);
        int deltaYSquared = rDifY * rDifY + gDifY * gDifY + bDifY * bDifY;
        return deltaYSquared + deltaXSquared;
    }

    /**
     * @return sequence of indices for horizontal seam
     */
    public int[] findHorizontalSeam() {
        return calculatePath(transformedEnergyMatrix);
    }

    /**
     * @return sequence of indices for vertical seam
     */
    public int[] findVerticalSeam() {
        return calculatePath(energyMatrix);

    }

    /**
     * remove horizontal seam from picture
     *
     * @param a the horizontal seam path
     */
    public void removeHorizontalSeam(int[] a) {
        double[][] newEnergyMatrix = new double[energyMatrix.length - 1][energyMatrix[0].length];
        double[][] newTransformedEnergyMatrix = new double[energyMatrix[0].length][energyMatrix.length - 1];

        for (int rowNumber = 0; rowNumber < energyMatrix.length; rowNumber++) {
            for (int colNumber = 0; colNumber < energyMatrix[rowNumber].length; colNumber++) {
                if (colNumber < a[rowNumber]) {
                    newEnergyMatrix[rowNumber][colNumber] = energyMatrix[rowNumber][colNumber];
                    newTransformedEnergyMatrix[colNumber][rowNumber] = energyMatrix[rowNumber][colNumber];
                } else {
                    newEnergyMatrix[rowNumber][colNumber] = energyMatrix[rowNumber][colNumber + 1];
                    newTransformedEnergyMatrix[colNumber][rowNumber] = energyMatrix[rowNumber][colNumber + 1];
                }
            }
        }
    }

    /**
     * remove vertical seam from picture
     *
     * @param a the vertical
     */
    public void removeVerticalSeam(int[] a) {
    }

    private int getHorizontalCentralDiff(int x, int y, int color) {
        return ((picture.get(x + 1, y).getRGB() >> color) & 0xFF) - ((picture.get(x - 1, y).getRGB() >> color) & 0xFF);
    }

    private int getVerticalCentralDiff(int x, int y, int color) {
        return ((picture.get(x, y + 1).getRGB() >> color) & 0xFF) - ((picture.get(x, y - 1).getRGB() >> color) & 0xFF);
    }

    private int[] calculatePath(double[][] energyMatrix) {
        double minPath = Double.POSITIVE_INFINITY;
        int[] path = new int[energyMatrix.length + 2];
        EdgeWeightedDigraph pathsGraph = buildGraph(energyMatrix);

        int startEdge = energyMatrix.length * energyMatrix[0].length;
        int endEdge = energyMatrix.length * energyMatrix[0].length + 1;
        for (int i = 0; i < energyMatrix[0].length - 1; i++) {
            pathsGraph.addEdge(new DirectedEdge(startEdge, i, 0));
            int graphVertexIndex = getGraphPixelIndex(energyMatrix.length - 1, i, energyMatrix);
            pathsGraph.addEdge(new DirectedEdge(graphVertexIndex, endEdge, 0));

        }
        AcyclicSP acyclicSP1 = new AcyclicSP(pathsGraph, startEdge);
        acyclicSP1.pathTo(endEdge);
        int pathCounter = 0;
        DirectedEdge lastDirectedEdge = null;
        for (DirectedEdge directedEdge : acyclicSP1.pathTo(endEdge)) {
            lastDirectedEdge = directedEdge;
            path[pathCounter++] = directedEdge.from() % energyMatrix[0].length;
        }
        if (lastDirectedEdge != null) {
            path[pathCounter] = lastDirectedEdge.from() % energyMatrix[0].length;
        }
        return Arrays.copyOfRange(path, 1, path.length - 1);
    }

    private EdgeWeightedDigraph buildGraph(double[][] energyMatrix) {
        EdgeWeightedDigraph energyWeightedGraphVertical = new EdgeWeightedDigraph(width() * height() + 2);
        for (int heightIndex = 0; heightIndex < energyMatrix.length; heightIndex++) {
            for (int widthIndex = 0; widthIndex < energyMatrix[heightIndex].length; widthIndex++) {
                if (heightIndex > 0) {
                    if (widthIndex > 0) {
                        linkVerticesInGraph(energyWeightedGraphVertical, heightIndex - 1, widthIndex - 1, heightIndex, widthIndex, energyMatrix);
                    }
                    if (widthIndex < energyMatrix[heightIndex].length - 1) {
                        linkVerticesInGraph(energyWeightedGraphVertical, heightIndex - 1, widthIndex + 1, heightIndex, widthIndex, energyMatrix);
                    }
                    linkVerticesInGraph(energyWeightedGraphVertical, heightIndex - 1, widthIndex, heightIndex, widthIndex, energyMatrix);
                }
            }
        }
        return energyWeightedGraphVertical;
    }

    private void linkVerticesInGraph(EdgeWeightedDigraph energyWeightedGraphVertical, int i1, int j1, int i2, int j2, double[][] energyMatrix) {
        int fromPixelGraphIndex = getGraphPixelIndex(i1, j1, energyMatrix);
        int toPixelGraphIndex = getGraphPixelIndex(i2, j2, energyMatrix);
        energyWeightedGraphVertical.addEdge(new DirectedEdge(fromPixelGraphIndex, toPixelGraphIndex, energyMatrix[i1][j1]));
    }

    private int getGraphPixelIndex(int i, int j, double[][] energyMatrix) {
        return energyMatrix[0].length * i + j;
    }


}
