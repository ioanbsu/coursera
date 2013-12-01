import java.util.HashSet;
import java.util.Set;

/**
 * @author ivanbahdanau
 */
public class BoggleSolver {


    private TST<Integer> trieTree;
    private String[] dictionary;

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
//        Stopwatch stopwatch = new Stopwatch();
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
//        StdOut.println(stopwatch.elapsedTime());

    }

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     * (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
     *
     * @param dictionary set of words that read from dictionary
     */
    public BoggleSolver(String[] dictionary) {
        this.dictionary = dictionary;
        trieTree = new TST<Integer>();
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
        Integer foundWordIndex;
        if (word.length() > 2) {
            foundWordIndex = trieTree.get(word);
            if (foundWordIndex != null) {
                allValidWords.add(dictionary[foundWordIndex]);
            }
        }

        if (trieTree.prefixMatch(word).iterator().hasNext()) {
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
        String letter = board.getLetter(row, col) + "";
        if (letter.equals("Q")) {
            letter = letter + "U";
        }
        return letter;
    }

}
