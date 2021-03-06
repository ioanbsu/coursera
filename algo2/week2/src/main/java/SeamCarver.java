import java.awt.*;

/**
 * Date: 11/10/13
 * Time: 11:40 AM
 *
 * @author ioanbsu
 */

public class SeamCarver {
    private Picture picture;
    private double[][] energyMatrix;
    private int[][] imageMatrix;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        initImageMatrix(picture);
        calculateEnergyMatrix();
    }

    /**
     * @return current picture
     */
    public Picture picture() {
        if (picture.width() != imageMatrix[0].length || picture.height() != imageMatrix.length) {
            Picture newPicture = new Picture(imageMatrix[0].length, imageMatrix.length);
            for (int rowIndex = 0; rowIndex < imageMatrix.length; rowIndex++) {
                for (int colIndex = 0; colIndex < imageMatrix[rowIndex].length; colIndex++) {
                    newPicture.set(colIndex, rowIndex, new Color(imageMatrix[rowIndex][colIndex]));
                }
            }
            picture = newPicture;
        }
        return picture;
    }

    /**
     * @return width of current picture
     */
    public int width() {
        return imageMatrix[0].length;
    }

    /**
     * @return height of current picture
     */
    public int height() {
        return imageMatrix.length;

    }

    /**
     * @param x x coordinate of picture pixel                    represents width
     * @param y y coordinate of picture pixel                    represents height
     * @return energy of pixel at column x and row y
     */
    public double energy(int x, int y) {
        final int RED_COLOR = 16;
        final int GREEN_COLOR = 8;
        final int BLUE_COLOR = 0;
        if (x < 0 || y < 0 || x >= energyMatrix[0].length || y >= energyMatrix.length) {
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
        transformImageMatrix();
        int[] horizontalSeam = findVerticalSeam();
        transformImageMatrix();
        return horizontalSeam;
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
        transformImageMatrix();
        removeVerticalSeam(a);
        transformImageMatrix();

    }

    /**
     * remove vertical seam from picture
     *
     * @param a the vertical
     */
    public void removeVerticalSeam(int[] a) {
        if (a.length != height()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < a.length - 1; i++) {
            if (Math.abs(a[i] - a[i + 1]) > 1) {
                throw new IllegalArgumentException();
            }
        }
        removePathFromArray(energyMatrix, a);
        removePathFromArray(imageMatrix, a);
    }

    private int getHorizontalCentralDiff(int x, int y, int color) {
        return ((imageMatrix[y][x + 1] >> color) & 0xFF) - ((imageMatrix[y][x - 1] >> color) & 0xFF);
    }

    private int getVerticalCentralDiff(int x, int y, int color) {
        return ((imageMatrix[y + 1][x] >> color) & 0xFF) - ((imageMatrix[y - 1][x] >> color) & 0xFF);
    }

    private int[] calculatePath(double[][] energyMatrix) {
        int[] newPath = new int[energyMatrix.length];
        if (energyMatrix[0].length <= 2 || imageMatrix.length <= 2) {
            for (int i = 0; i < energyMatrix.length; i++) {
                newPath[i] = 0;
            }
            return newPath;
        }
        double minFoundDistance = Double.POSITIVE_INFINITY;
        int minFoundDistancePathIndex = -1;
        DirectedEdge[] minEdgeTo = new DirectedEdge[imageMatrix[0].length * imageMatrix.length];

        double[] distTo = new double[imageMatrix[0].length * imageMatrix.length];
        for (int i = 0; i < imageMatrix[0].length * imageMatrix.length; i++) {
            if (i < imageMatrix[0].length) {
                distTo[i] = 0;
            } else {
                distTo[i] = Double.POSITIVE_INFINITY;
            }
        }
        DirectedEdge[] edgeTo = new DirectedEdge[imageMatrix[0].length * imageMatrix.length];

        for (int rowNum = 0; rowNum < imageMatrix.length - 1; rowNum++) {
            for (int colNum = 1; colNum < imageMatrix[0].length - 1; colNum++) {
                int v = getGraphPixelIndex(rowNum, colNum, energyMatrix);
                for (int kid = Math.max(0, colNum - 1); kid <= Math.min(colNum + 1, imageMatrix[0].length); kid++) {
                    int w = getGraphPixelIndex(rowNum + 1, kid, energyMatrix);
                    relax(new DirectedEdge(v, w, energyMatrix[rowNum][colNum]), distTo, edgeTo);
                }
            }
        }
        for (int i = imageMatrix.length * imageMatrix[0].length - imageMatrix[0].length; i < imageMatrix.length * imageMatrix[0].length; i++) {
            if (distTo[i] < minFoundDistance) {
                minFoundDistancePathIndex = i;
                minFoundDistance = distTo[i];
                minEdgeTo = edgeTo;
            }
        }
        int counter = energyMatrix.length - 1;
        DirectedEdge nextEdge = minEdgeTo[minFoundDistancePathIndex];
        newPath[counter--] = nextEdge.to() % energyMatrix[0].length;
        while (nextEdge != null) {
            newPath[counter--] = nextEdge.from() % energyMatrix[0].length;
            nextEdge = minEdgeTo[nextEdge.from()];
        }
        return newPath;
    }

    private void relax(DirectedEdge e, double[] distTo, DirectedEdge[] edgeTo) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    private int getGraphPixelIndex(int i, int j, double[][] energyMatrix) {
        return energyMatrix[0].length * i + j;
    }

    private void removePathFromArray(double[][] energyMatrix, int[] a) {
        double[][] newEnergyMatrix = new double[energyMatrix.length][energyMatrix[0].length - 1];
        for (int rowIndex = 0; rowIndex < energyMatrix.length; rowIndex++) {
            System.arraycopy(energyMatrix[rowIndex], 0, newEnergyMatrix[rowIndex], 0, a[rowIndex]);
            if (a[rowIndex] < newEnergyMatrix[rowIndex].length) {
                System.arraycopy(energyMatrix[rowIndex], a[rowIndex] + 1, newEnergyMatrix[rowIndex], a[rowIndex], energyMatrix[0].length - a[rowIndex] - 1);
            }
        }
        this.energyMatrix = newEnergyMatrix;
    }

    private void removePathFromArray(int[][] imageMatrix, int[] a) {
        int[][] newImageMatrix = new int[imageMatrix.length][imageMatrix[0].length - 1];
        for (int rowIndex = 0; rowIndex < imageMatrix.length; rowIndex++) {
            System.arraycopy(imageMatrix[rowIndex], 0, newImageMatrix[rowIndex], 0, a[rowIndex]);
            if (a[rowIndex] < newImageMatrix[rowIndex].length) {
                System.arraycopy(imageMatrix[rowIndex], a[rowIndex] + 1, newImageMatrix[rowIndex], a[rowIndex], imageMatrix[0].length - a[rowIndex] - 1);
            }
        }
        this.imageMatrix = newImageMatrix;
        for (int rowIndex = 0; rowIndex < imageMatrix.length; rowIndex++) {
            if (a[rowIndex] < energyMatrix[0].length) {
                energyMatrix[rowIndex][a[rowIndex]] = energy(a[rowIndex], rowIndex);
            }
            if (a[rowIndex] > 0) {
                energyMatrix[rowIndex][a[rowIndex] - 1] = energy(a[rowIndex] - 1, rowIndex);
            }
        }
    }

    private void initImageMatrix(Picture picture) {
        imageMatrix = new int[picture.height()][picture.width()];
        for (int heightIndex = 0; heightIndex < picture.height(); heightIndex++) {
            for (int widthIndex = 0; widthIndex < picture.width(); widthIndex++) {
                imageMatrix[heightIndex][widthIndex] = picture.get(widthIndex, heightIndex).getRGB();
            }
        }
    }

    private void transformImageMatrix() {
        int[][] newImageMatrix = new int[imageMatrix[0].length][imageMatrix.length];
        for (int i = 0; i < imageMatrix.length; i++) {
            for (int j = 0; j < imageMatrix[i].length; j++) {
                newImageMatrix[j][i] = imageMatrix[i][j];
            }
        }
        imageMatrix = newImageMatrix;
        calculateEnergyMatrix();
    }

    private void calculateEnergyMatrix() {
        energyMatrix = new double[height()][width()];
        for (int heightIndex = 0; heightIndex < height(); heightIndex++) {
            for (int widthIndex = 0; widthIndex < width(); widthIndex++) {
                double calculatedEnergy = energy(widthIndex, heightIndex);
                energyMatrix[heightIndex][widthIndex] = calculatedEnergy;
            }
        }
    }

}
