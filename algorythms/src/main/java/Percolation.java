/**
 * User: ioanbsu
 * Date: 2/13/13
 * Time: 2:15 PM
 */
public class Percolation {


    private boolean[][] percolationGrid;
    private int gridSize;
    private WeightedQuickUnionUF unionFinder;
    private WeightedQuickUnionUF unionFullFinder;
    //private UnionFinder unionFinder;

    /**
     * Creates N*N grid with all cells closed. Initiates linked
     * unionFinder that has last to elements linked to first
     * and last rows respectively
     *
     * @param N grid size (N*N)
     */
    public Percolation(int N) {
        this.gridSize = N;
        percolationGrid = new boolean[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                percolationGrid[row][col] = false;
            }
        }
        unionFinder = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        unionFullFinder = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        for (int row = 0; row < gridSize; row++) {
            unionFinder.union(gridSize * gridSize, row);
            unionFullFinder.union(gridSize * gridSize, row);
            unionFinder.union(gridSize * gridSize + 1, (gridSize - 1) * gridSize + row);
        }
    }

    /**
     * Opens cell [i,j] and also create respective link in unionFinder.
     *
     * @param i row number
     * @param j col number
     */
    public void open(int i, int j) {
        i = i - 1;
        j = j - 1;
        if (!percolationGrid[i][j]) {
            percolationGrid[i][j] = true;
            if (i > 0 && percolationGrid[i - 1][j]) {
                unionFinder.union(i * gridSize + j, (i - 1) * gridSize + j);
                unionFullFinder.union(i * gridSize + j, (i - 1) * gridSize + j);
            }
            if (j > 0 && percolationGrid[i][j - 1]) {
                unionFinder.union(i * gridSize + j, i * gridSize + j - 1);
                unionFullFinder.union(i * gridSize + j, i * gridSize + j - 1);
            }
            if (i < gridSize - 1 && percolationGrid[i + 1][j]) {
                unionFinder.union(i * gridSize + j, (i + 1) * gridSize + j);
                unionFullFinder.union(i * gridSize + j, (i + 1) * gridSize + j);
            }
            if (j < gridSize - 1 && percolationGrid[i][j + 1]) {
                unionFinder.union(i * gridSize + j, i * gridSize + j + 1);
                unionFullFinder.union(i * gridSize + j, i * gridSize + j + 1);
            }
        }
    }

    /**
     * Checks is cell[i,j] is opened
     *
     * @param i row number
     * @param j col number
     * @return true is cell is opened, otherwise returns false.
     */
    public boolean isOpen(int i, int j) {
        i = i - 1;
        j = j - 1;
        return percolationGrid[i][j];
    }

    /**
     * Identifies if there is a path between cell[i,j] and any element at the top row of the grid.
     *
     * @param i row number
     * @param j col number
     * @return true if there is a path otherwiser returns false
     */
    public boolean isFull(int i, int j) {
        if (!isOpen(i, j)) {
            return false;
        }
        i = i - 1;
        j = j - 1;
        return unionFullFinder.connected(gridSize * gridSize, i * gridSize + j);
    }

    /**
     * @return true if the whole grid is percolates, otherwise returns false.
     */
    public boolean percolates() {
        if (gridSize == 1) {
            return isOpen(1, 1);
        }
        return unionFinder.connected(gridSize * gridSize, gridSize * gridSize + 1);
    }
}
