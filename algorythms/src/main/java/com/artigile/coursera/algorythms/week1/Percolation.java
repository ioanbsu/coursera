package com.artigile.coursera.algorythms.week1;

import com.artigile.coursera.algorythms.unionfind.UnionFinder;
import com.artigile.coursera.algorythms.unionfind.WeightedQUWithPathCompression;

/**
 * @author IoaN, 2/12/13 8:10 PM
 */
public class Percolation {

    private boolean[][] percolationGrid;
    private int gridSize;
    private UnionFinder unionFinder;

    public Percolation(int N) {
        this.gridSize = N;
        percolationGrid = new boolean[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                percolationGrid[row][col] = false;
            }
        }
        unionFinder = new WeightedQUWithPathCompression(gridSize * gridSize + 2);
        for (int row = 0; row < gridSize; row++) {
            unionFinder.union(gridSize * gridSize, row);
            unionFinder.union(gridSize * gridSize + 1, (gridSize - 1) * gridSize + row);
        }
    }

    public void open(int i, int j) {
        percolationGrid[i][j] = true;
        if (i > 0 && percolationGrid[i - 1][j]) {
            unionFinder.union(i * gridSize + j, (i - 1) * gridSize + j);
        }
        if(j>0&& percolationGrid[i][j-1]){
            unionFinder.union(i * gridSize + j, i * gridSize + j - 1);
        }
        if(i<gridSize-1&& percolationGrid[i + 1][j]){
            unionFinder.union(i * gridSize + j, (i + 1) * gridSize + j);
        }
        if(j<gridSize-1&& percolationGrid[i][j+1]){
            unionFinder.union(i * gridSize + j, i * gridSize + j + 1);
        }
    }

    public boolean isOpen(int i, int j) {
        return percolationGrid[i][j];
    }

    public boolean isFull(int i, int j) {
        return !isOpen(i, j);
    }

    public boolean percolates() {
        return unionFinder.connected(unionFinder.getRoot(gridSize * gridSize), unionFinder.getRoot(gridSize * gridSize + 1));
    }

}
