
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author IoaN, 3/10/13 3:52 PM
 */
public class Solver {
    private boolean solvable = true;
    private SearchNode solutionNode;

    /**
     * find a solutionNode to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        MinPQ<SearchNode> solutionQueue = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinSolutionQueue = new MinPQ<SearchNode>();
        if (initial.isGoal()) {
            solutionNode = new SearchNode(initial, 0, null);
            return;
        }
        try {
            SearchNode searchNode = new SearchNode(initial, 0, null);
            SearchNode twinSearchNode = new SearchNode(initial.twin(), 0, null);
            solutionQueue.insert(searchNode);
            twinSolutionQueue.insert(twinSearchNode);
            solutionNode = getSolution(solutionQueue, twinSolutionQueue);
        } catch (Exception e) {
            solvable = false;
        }
    }

    /**
     * solve a slider puzzle (given below)
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solutionNode to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    /**
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * min number of moves to solve initial board; -1 if no solutionNode
     */
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solutionNode.moves;
    }

    /**
     * sequence of boards in a shortest solutionNode; null if no solutionNode
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        List<Board> solutionSteps = new LinkedList<Board>();
        if (solutionNode != null) {
            SearchNode iterableSolution = solutionNode;
            while (iterableSolution.prevSearchNode != null) {
                solutionSteps.add(iterableSolution.board);
                iterableSolution = iterableSolution.prevSearchNode;
            }
            solutionSteps.add(iterableSolution.board);
        }
        Collections.reverse(solutionSteps);
        return new LinkedHashSet<Board>(solutionSteps);
    }

    private SearchNode getSolution(MinPQ<SearchNode> solutionQueue, MinPQ<SearchNode> twinSolutionQueue) throws Exception {
        while (true) {
            SearchNode minDeleted = solutionQueue.delMin();
            if (minDeleted.board.isGoal()) {
                return minDeleted;
            }
            for (Board boardNeibourhood : minDeleted.board.neighbors()) {
                if (minDeleted.prevSearchNode == null || !boardNeibourhood.equals(minDeleted.prevSearchNode.board)) {
                    SearchNode newSearchNode = new SearchNode(boardNeibourhood, minDeleted.moves + 1, minDeleted);
                    solutionQueue.insert(newSearchNode);
                }
            }
            SearchNode twinMinDeleted = twinSolutionQueue.delMin();
            if (twinMinDeleted.board.isGoal()) {
                throw new Exception("solution does not exist");

            }
            for (Board boardNeibourhood : twinMinDeleted.board.neighbors()) {
                if (twinMinDeleted.prevSearchNode == null || !boardNeibourhood.equals(twinMinDeleted.prevSearchNode.board)) {
                    SearchNode twinNewSearchNode = new SearchNode(boardNeibourhood, twinMinDeleted.moves + 1, twinMinDeleted);
                    twinSolutionQueue.insert(twinNewSearchNode);
                }
            }
        }
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prevSearchNode;
        private int moves = 0;

        public SearchNode(Board board, int moves, SearchNode prevSearchNode) {
            this.board = board;
            this.moves = moves;
            this.prevSearchNode = prevSearchNode;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (o == null) {
                return -1;
            }
            return board.manhattan() + moves - o.board.manhattan() - o.moves;
        }

        @Override
        public boolean equals(Object obj) {
            return compareTo((SearchNode) obj)==0;
        }
    }

}
