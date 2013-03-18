package com.artigile.coursera.algorythms.week4;

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author IoaN, 3/9/13 6:55 PM
 */
public class BoardTest {
    private static Random random = new Random(System.currentTimeMillis());
    private static long count = 0;
    private static Set<String> usedBoards = new HashSet<String>();

    /**
     * Rearrange the elements of the subarray a[lo..hi] in random order.
     */
    public static void shuffle(Object[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length)
            throw new RuntimeException("Illegal subarray range");
        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi - i + 1);     // between i and hi
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N - i);     // between i and N-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Return an integer uniformly between 0 (inclusive) and N (exclusive).
     */
    public static int uniform(int N) {
        return random.nextInt(N);
    }

    @Test
    public void testRandomBoards() {
        for (int i = 0; i < 50000; i++) {
            while (true) {
                testBoard();
            }
        }
    }

    @Test
    public void testFindSolution() {
        Board randomBoard = generateRandomBoard();
        findSolution(randomBoard);
    }

    @Test
    public void testManhattanAndHamming() {
        int[][] blocks = new int[2][2];
        blocks[0][0] = 2;
        blocks[0][1] = 3;
        blocks[1][0] = 0;
        blocks[1][1] = 1;
        Board board = new Board(blocks);
        Assert.assertEquals(3, board.hamming());
        Assert.assertEquals(5, board.manhattan());
        blocks[0][0] = 0;
        blocks[0][1] = 1;
        blocks[1][0] = 3;
        blocks[1][1] = 2;
        board = new Board(blocks);
        Assert.assertEquals(2, board.hamming());
        Assert.assertEquals(2, board.manhattan());
        blocks[0][0] = 1;
        blocks[0][1] = 2;
        blocks[1][0] = 3;
        blocks[1][1] = 0;
        board = new Board(blocks);
        Assert.assertEquals(0, board.hamming());
        Assert.assertEquals(0, board.manhattan());
        Assert.assertTrue(board.isGoal());

    }

    private void testBoard() {
        Board board = generateRandomBoard();
        if (board == null) return;
        //  System.out.println("===================");
        printAllBoardData(board);
        for (Board neighbour : board.neighbors()) {
            printAllBoardData(neighbour);
        }
    }

    private Board generateRandomBoard() {
        int size = 3;//(int) (Math.random() * 3);
        if (size == 0) {
            return null;
        }
        int[][] blocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                blocks[i][j] = i * size + j;
            }
            shuffle(blocks[i]);
        }
        shuffle(blocks, 0, blocks.length - 1);
        Board board = new Board(blocks);
        return board;
    }

    private void printAllBoardData(Board board) {
        System.out.println("original: " + board);
        System.out.println("isGoal : " + board.isGoal());
        System.out.println("hamming: " + board.hamming());
        System.out.println("manhattan: " + board.manhattan());
        // System.out.println("twin: " + board.twin());
        System.out.println("====");
    }

    private void findSolution(Board board) {
        count++;
        if (board.isGoal()) {
            printAllBoardData(board);
            System.exit(0);
        } else {
            for (Board board1 : board.neighbors()) {
                if (usedBoards.add(board1.toString())) {
                    printAllBoardData(board);
                    findSolution(board1);
                }
            }
        }
    }
}
