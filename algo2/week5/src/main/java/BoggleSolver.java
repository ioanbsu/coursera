import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ivanbahdanau
 */
public class BoggleSolver {


    private TSTFast<Integer> trieTree;
    private String[] dictionary;

    private static int wordsTried = 0;

    public static void main(String[] args) {
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
        trieTree = new TSTFast<Integer>();
        for (int i = 0; i < dictionary.length; i++) {
            trieTree.put(dictionary[i], i);
        }
    }


    /**
     * Returns all valid words in the given Boggle board, as an Iterable.
     *
     * @param board the board to be analyzed.
     * @return collection of boards
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> allValidWords = new HashSet<String>();
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                boolean[][] visitedArray = new boolean[board.rows()][board.cols()];
                visitedArray[row][col] = true;
                findWords(getLetter(board, row, col), board, row, col, allValidWords, visitedArray);
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
        }
        if (wordLength == 5) {
            return 2;
        }
        if (wordLength == 6) {
            return 3;
        }
        if (wordLength == 7) {
            return 5;
        }
        if (wordLength >= 8) {
            return 11;
        }
        return 0;
    }


    private void findWords(String word, BoggleBoard board, int row, int col, Set<String> allValidWords, boolean[][] visitedArray) {
        if (word.length() > 2 && trieTree.contains(word)) {
            allValidWords.add(word);
        }
        if (trieTree.hasWord(word)) {
            branchWord(word, board, row + 1, col, allValidWords, visitedArray);
            branchWord(word, board, row - 1, col, allValidWords, visitedArray);
            branchWord(word, board, row, col + 1, allValidWords, visitedArray);
            branchWord(word, board, row, col - 1, allValidWords, visitedArray);
            branchWord(word, board, row - 1, col - 1, allValidWords, visitedArray);
            branchWord(word, board, row + 1, col - 1, allValidWords, visitedArray);
            branchWord(word, board, row - 1, col + 1, allValidWords, visitedArray);
            branchWord(word, board, row + 1, col + 1, allValidWords, visitedArray);
        }
    }

    private void branchWord(String word, BoggleBoard board, int row, int col, Set<String> allValidWords, boolean[][] visitedArray) {
        if (row < board.rows() && row >= 0 && col < board.cols() && col >= 0 && !visitedArray[row][col]) {
            visitedArray[row][col] = true;
            String letter = getLetter(board, row, col);
            findWords(word + letter, board, row, col, allValidWords, visitedArray);
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
     * ===================================================================
     *
     * @param <Value>
     */
    private class TSTFast<Value> {

        private int N;       // size
        private Node root;   // root of TST

        private class Node {
            private char c;                 // character
            private Node left, mid, right;  // left, middle, and right subtries
            private Value val;              // value associated with string
        }


        // return number of key-value pairs
        public int size() {
            return N;
        }

        /**
         * ***********************************************************
         * Is string key in the symbol table?
         * ************************************************************
         */
        public boolean contains(String key) {
            return get(key) != null;
        }

        public Value get(String key) {
            if (key == null) throw new NullPointerException();
            if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
            Node x = get(root, key, 0);
            if (x == null) return null;
            return x.val;
        }

        // return subtrie corresponding to given key
        private Node get(Node x, String key, int d) {
            if (key == null) throw new NullPointerException();
            if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
            if (x == null) {
                return null;
            }
            char c = key.charAt(d);
            if (c < x.c) {
                return get(x.left, key, d);
            } else if (c > x.c) {
                return get(x.right, key, d);
            } else if (d < key.length() - 1) {
                return get(x.mid, key, d + 1);
            } else {
                return x;
            }
        }


        /**
         * ***********************************************************
         * Insert string s into the symbol table.
         * ************************************************************
         */
        public void put(String s, Value val) {
            if (!contains(s)) N++;
            root = put(root, s, val, 0);
        }

        private Node put(Node x, String s, Value val, int d) {
            char c = s.charAt(d);
            if (x == null) {
                x = new Node();
                x.c = c;
            }
            if (c < x.c) x.left = put(x.left, s, val, d);
            else if (c > x.c) x.right = put(x.right, s, val, d);
            else if (d < s.length() - 1) x.mid = put(x.mid, s, val, d + 1);
            else x.val = val;
            return x;
        }


        // all keys starting with given prefix
        public boolean hasWord(String prefix) {
            Node x = get(root, prefix, 0);
            return x != null && collectFast(x.mid, prefix);
        }

        // all keys in subtrie rooted at x with given prefix
        private boolean collectFast(Node x, String prefix) {
            boolean hasWord;
            if (x == null) {
                return false;
            }
            if (x.val != null) {
                return true;
            }
            hasWord = collectFast(x.left, prefix);
            if (hasWord) {
                return true;
            }
            hasWord = collectFast(x.mid, prefix + x.c);
            if (hasWord) {
                return true;
            }
            hasWord = collectFast(x.right, prefix);
            return hasWord;
        }


        public void collect(Node x, String prefix, int i, String pat, Queue<String> q) {
            if (x == null) return;
            char c = pat.charAt(i);
            if (c == '.' || c < x.c) collect(x.left, prefix, i, pat, q);
            if (c == '.' || c == x.c) {
                if (i == pat.length() - 1 && x.val != null) q.enqueue(prefix + x.c);
                if (i < pat.length() - 1) collect(x.mid, prefix + x.c, i + 1, pat, q);
            }
            if (c == '.' || c > x.c) collect(x.right, prefix, i, pat, q);
        }


    }
}
