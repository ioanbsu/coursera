import java.util.HashSet;
import java.util.Set;

/**
 * @author ivanbahdanau
 */
public class BoggleSolver {


    private IoaNFastTrie trieTree;
    private String[] dictionary;
    private Set<String> allValidWords;
    private BoggleBoard board;

    public static void main(String[] args) throws InterruptedException {
//        while (true) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        Stopwatch stopwatch = new Stopwatch();
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        StdOut.println(stopwatch.elapsedTime());
//        }
    }

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     * (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     *
     * @param dictionary set of words that read from dictionary
     */
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new String[dictionary.length];
        System.arraycopy(dictionary, 0, this.dictionary, 0, dictionary.length);
        trieTree = new IoaNFastTrie();
        for (String word : dictionary) {
            trieTree.put(word);
        }
    }


    /**
     * Returns all valid words in the given Boggle board, as an Iterable.
     *
     * @param board the board to be analyzed.
     * @return collection of boards
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        allValidWords = new HashSet<String>();
        this.board = board;
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                boolean[][] visitedArray = new boolean[board.rows()][board.cols()];
                visitedArray[row][col] = true;
                findWords(null, getLetter(board, row, col), row, col, visitedArray);
                visitedArray[row][col] = false;
            }
        }
        return allValidWords;
    }

    /**
     * Returns the score of the given (not necessarily valid) word.
     * (You can assume the word contains only the uppercase letters A through Z.)
     *
     * @param word the word to return score for
     * @return score of the word
     */
    public int scoreOf(String word) {
        if (!trieTree.contains(word)) {
            return 0;
        }
        int wordLength = word.length();
        if (wordLength == 3 || wordLength == 4) {
            return 1;
        } else if (wordLength == 5) {
            return 2;
        } else if (wordLength == 6) {
            return 3;
        } else if (wordLength == 7) {
            return 5;
        } else if (wordLength >= 8) {
            return 11;
        }
        return 0;
    }


    private void findWords(IoaNFastTrie.Node nodeToStart, String word, int row, int col, boolean[][] visitedArray) {
        IoaNFastTrie.Node foundNode = trieTree.getNode(word, nodeToStart);
        if (foundNode != null) {
            if (foundNode.endOfTheWord) {
                allValidWords.add(word);
            }
            branchWord(foundNode, word, row + 1, col, visitedArray);
            branchWord(foundNode, word, row - 1, col, visitedArray);
            branchWord(foundNode, word, row, col + 1, visitedArray);
            branchWord(foundNode, word, row, col - 1, visitedArray);
            branchWord(foundNode, word, row - 1, col - 1, visitedArray);
            branchWord(foundNode, word, row + 1, col - 1, visitedArray);
            branchWord(foundNode, word, row - 1, col + 1, visitedArray);
            branchWord(foundNode, word, row + 1, col + 1, visitedArray);
        }
    }

    private void branchWord(IoaNFastTrie.Node nodeToStart, String word, int row, int col, boolean[][] visitedArray) {
        if (row < board.rows() && row >= 0 && col < board.cols() && col >= 0 && !visitedArray[row][col]) {
            visitedArray[row][col] = true;
            String letter = getLetter(board, row, col);
            findWords(nodeToStart, word + letter, row, col, visitedArray);
            visitedArray[row][col] = false;
        }
    }

    private String getLetter(BoggleBoard board, int row, int col) {
        if (board.getLetter(row, col) == 'Q') {
            return board.getLetter(row, col) + "U";
        }
        return board.getLetter(row, col) + "";
    }


    /**
     * ===============================================================
     */
    private static class IoaNFastTrie {
        private static final char CHAR_START_INDEX = 'A';
        private Node root = new Node();
        public static final int ARRAYS_SIZE = 'Z' - 'A' + 1;

        private static class Node {
            private int letterNumber = -1;
            private boolean endOfTheWord = false;
            private Node[] next = new Node[ARRAYS_SIZE];
        }

        public void put(String word) {
            fastPut(word);
        }

        private void fastPut(String word) {
            Node nodeToSearch = root;
            for (int i = 0; i < word.length(); i++) {
                int letterIndex = word.charAt(i) - CHAR_START_INDEX;
                if (nodeToSearch.next[letterIndex] == null) {
                    Node child = new Node();
                    child.letterNumber = i;
                    nodeToSearch.next[letterIndex] = child;
                    nodeToSearch = child;
                } else {
                    nodeToSearch = nodeToSearch.next[letterIndex];
                }
            }
            nodeToSearch.endOfTheWord = true;
        }

        public boolean contains(String word) {
            return getNode(word, root).endOfTheWord;
        }

        public Node getNode(String word, Node nodeToSearch) {
            if (nodeToSearch == null) {
                nodeToSearch = root;
            }
            for (int i = nodeToSearch.letterNumber + 1; i < word.length(); i++) {
                int letterIndex = word.charAt(i) - CHAR_START_INDEX;
                if (nodeToSearch.next[letterIndex] == null) {
                    return null;
                }
                nodeToSearch = nodeToSearch.next[letterIndex];
            }
            return nodeToSearch;
        }

    }
}
