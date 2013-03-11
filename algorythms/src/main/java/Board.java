import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author IoaN, 3/10/13 3:51 PM
 */
public class Board {

    private int[][] blocks;
    private int x, y;
    private int EMPTY_CELL_NUM = 0;


    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, blocks.length);
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
    }

    // board dimension N
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != EMPTY_CELL_NUM) {
                    if (blocks[i][j] != blocks.length * i + j + 1) {
                        distance++;
                    }
                }
            }
        }
        return distance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != EMPTY_CELL_NUM) {
                    distance += Math.abs(j - getExpectedXCoordinate(blocks[i][j])) + Math.abs(i - getExpectedYCoordinate(blocks[i][j]));
                }
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0 && hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        if (blocks.length < 2) {
            return new Board(blocks);
        }
        int[][] twinBlocks = createBlocksCopy();
        int randomRow = (int) (Math.random() * blocks.length);
        int middle = Math.max(0, blocks.length - 1) / 2;
        if (twinBlocks[randomRow][middle] == EMPTY_CELL_NUM || twinBlocks[randomRow][middle + 1] == EMPTY_CELL_NUM) {
            if (randomRow < blocks.length - 1) {
                randomRow++;
            } else if (randomRow > 0) {
                randomRow--;
            }
        }
        twinBlocks[randomRow][middle] = blocks[randomRow][middle + 1];
        twinBlocks[randomRow][middle + 1] = blocks[randomRow][middle];
        return new Board(twinBlocks);
    }

    // does this board equal y?
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof Board) {
            return Arrays.deepEquals(blocks,((Board)anObject).blocks);
        }
        return false;
    }



    // all neighboring boards
    public Iterable<Board> neighbors() {
        Set<Board> neighbours = new HashSet<Board>();
        if (x > 0) {
            Board newBoard = new Board(moveLeft(x, y));
            neighbours.add(newBoard);
        }
        if (x < blocks.length - 1) {
            Board newBoard = new Board(moveRight(x, y));
            neighbours.add(newBoard);
        }
        if (y < blocks.length - 1) {
            Board newBoard = new Board(moveDown(x, y));
            neighbours.add(newBoard);
        }
        if (y > 0) {
            Board newBoard = new Board(moveUp(x, y));
            neighbours.add(newBoard);
        }
        return neighbours;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int getExpectedXCoordinate(int value) {
        return (value - 1) % blocks.length;
    }

    private int getExpectedYCoordinate(int value) {
        return (value - 1) / blocks.length;
    }

    private int[][] moveLeft(int x, int y) {
        int[][] movedLeftBlocks = createBlocksCopy();
        movedLeftBlocks[x][y] = blocks[x - 1][y];
        movedLeftBlocks[x - 1][y] = blocks[x][y];
        return movedLeftBlocks;
    }

    private int[][] moveRight(int x, int y) {
        int[][] movedLeftBlocks = createBlocksCopy();
        movedLeftBlocks[x][y] = blocks[x + 1][y];
        movedLeftBlocks[x + 1][y] = blocks[x][y];
        return movedLeftBlocks;
    }

    private int[][] moveDown(int x, int y) {
        int[][] movedLeftBlocks = createBlocksCopy();
        movedLeftBlocks[x][y] = blocks[x][y + 1];
        movedLeftBlocks[x][y + 1] = blocks[x][y];
        return movedLeftBlocks;
    }

    private int[][] moveUp(int x, int y) {
        int[][] movedLeftBlocks = createBlocksCopy();
        movedLeftBlocks[x][y] = blocks[x][y - 1];
        movedLeftBlocks[x][y - 1] = blocks[x][y];
        return movedLeftBlocks;
    }

    private int[][] createBlocksCopy() {
        int[][] arrayCopy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            System.arraycopy(blocks[i], 0, arrayCopy[i], 0, blocks.length);
        }
        return arrayCopy;
    }

}