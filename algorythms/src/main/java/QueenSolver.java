/**
 * Date: 10/23/13
 * Time: 10:23 PM
 *
 * @author ioanbsu
 */

public class QueenSolver {


    public static final int BOARD_SIZE = 30;

    public static void main(String[] args) {
        QueenSolver queenSolver = new QueenSolver();
        boolean board[][] = new boolean[BOARD_SIZE][BOARD_SIZE];
        if (queenSolver.findNext(0, board)) {
            queenSolver.printBoard(board);
        } else {
            System.out.println("No Solution");
        }
    }


    public boolean findNext(int row, boolean[][] board) {
        if (row == BOARD_SIZE) {
            return true;
        }
        for (int col = 0; col < BOARD_SIZE; col++) {
            board[row][col] = true;
            if (checkBoard(board) && findNext(row + 1, board)) {
                return true;
            } else {
                board[row][col] = false;
            }
        }
        return false;
    }

    public boolean checkBoard(boolean[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j]
                        && (!checkHorizontal(i, j, board)
                        || !checkVertical(i, j, board)
                        || !checkDiagonals(i, j, board))) {
//                    System.out.println("Bad Board");
//                    printBoard(board);
                    return false;
                }
            }
        }
        printBoard(board);
        return true;
    }

    private boolean checkHorizontal(int i, int j, boolean[][] board) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (col != j && board[i][col]) {
                return false;
            }
        }
        return true;//true means the row is GOOD;
    }

    private boolean checkVertical(int i, int j, boolean[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (row != i && board[row][j]) {
                return false;
            }
        }
        return true;//true means the row is GOOD;
    }

    private boolean checkDiagonals(int i, int j, boolean[][] board) {
        int iterateI = i;
        int iterateJ = j;

        for (; ; ) {
            iterateI++;
            iterateJ++;
            if (iterateI > BOARD_SIZE - 1 || iterateJ > BOARD_SIZE - 1) {
                break;
            }
            if (board[iterateI][iterateJ]) {
                return false;
            }
        }
        iterateI = i;
        iterateJ = j;
        for (; ; ) {
            iterateI--;
            iterateJ--;
            if (iterateI < 0 || iterateJ < 0) {
                break;
            }
            if (board[iterateI][iterateJ]) {
                return false;
            }
        }
        iterateI = i;
        iterateJ = j;
        for (; ; ) {
            iterateI++;
            iterateJ--;
            if (iterateI > BOARD_SIZE - 1 || iterateJ < 0) {
                break;
            }
            if (board[iterateI][iterateJ]) {
                return false;
            }
        }
        iterateI = i;
        iterateJ = j;
        for (; ; ) {
            iterateI--;
            iterateJ++;
            if (iterateI < 0 || iterateJ > BOARD_SIZE - 1) {
                break;
            }
            if (board[iterateI][iterateJ]) {
                return false;
            }
        }
        return true;//true means the row is GOOD;
    }

    public void printBoard(boolean board[][]) {
        for (boolean[] booleans : board) {
            for (boolean aBoolean : booleans) {
                System.out.print((aBoolean ? "*" : "_") + " ");
            }
            System.out.println("");
        }
        System.out.println("==============");
    }
}
