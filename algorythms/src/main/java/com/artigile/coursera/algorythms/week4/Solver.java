package com.artigile.coursera.algorythms.week4;

import java.util.*;

/**
 * @author IoaN, 3/10/13 1:40 PM
 */
public class Solver {
    private static Random random = new Random(System.currentTimeMillis());
    private boolean solvable = true;
    private SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> solutionQueue = new MinPQ<SearchNode>();
        SearchNode searchNode = null;
        try {
            searchNode = new SearchNode(initial, null);
            solutionQueue.insert(searchNode);
            solution = getSolution(solutionQueue, new HashSet<String>());
        } catch (Exception e) {
            solvable = false;
            searchNode = new SearchNode(initial.twin(), null);
            solutionQueue.insert(searchNode);
            solution = getSolution(solutionQueue, new HashSet<String>());
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        Board initial = generateRandomBoard();

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
    }

    private static Board generateRandomBoard() {
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

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * min number of moves to solve initial board; -1 if no solution
     * since board is not allowed to return total moves -  return here hamming which is equal to total moves because
     * when all elements are th the right place then the hamming sum comsists from moves only.
     */
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solution.board.hamming();
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        List<Board> solutionSteps = new LinkedList<Board>();
        if (solution != null) {
            SearchNode iterableSolution = solution;
            while (iterableSolution.prevSearchNode != null) {
                solutionSteps.add(iterableSolution.board);
                iterableSolution = iterableSolution.prevSearchNode;
            }
            solutionSteps.add(iterableSolution.board);
        }
        Collections.reverse(solutionSteps);
        return solutionSteps;
    }

    private SearchNode getSolution(MinPQ<SearchNode> solutionQueue, Set<String> existingBoards) {
        while (true) {
            SearchNode minDeleted = solutionQueue.delMin();
            for (Board boardNeibourhood : minDeleted.board.neighbors()) {
                if (existingBoards.add(boardNeibourhood.toString())) {
                    SearchNode newSearchnode = new SearchNode(boardNeibourhood, minDeleted);
                    solutionQueue.insert(newSearchnode);
                    if (boardNeibourhood.isGoal()) {
                        return newSearchnode;
                    }
                }
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {

        private Board board;
        private SearchNode prevSearchNode;

        public SearchNode(Board board, SearchNode prevSearchNode) {
            this.board = board;
            this.prevSearchNode = prevSearchNode;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (o == null) {
                return -1;
            }
            return board.manhattan() - o.board.manhattan();
        }
    }

}
