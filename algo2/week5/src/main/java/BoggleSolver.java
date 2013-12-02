import java.util.HashSet;
import java.util.Set;

/**
 * @author ivanbahdanau
 */
public class BoggleSolver {


    private IoaNFastTrie trieTree;
    private String[] dictionary;
    private BoggleBoard board;
    private char[] QU_WORD = new char[2];

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
        QU_WORD[0] = 'Q';
        QU_WORD[1] = 'U';
        this.dictionary = new String[dictionary.length];
        System.arraycopy(dictionary, 0, this.dictionary, 0, dictionary.length);
        trieTree = new IoaNFastTrie();
        for (String word : dictionary) {
            trieTree.put(word.toCharArray());
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
        this.board = board;
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                boolean[][] visitedArray = new boolean[board.rows()][board.cols()];
                visitedArray[row][col] = true;
                findWords(null, getLetter(board, row, col), row, col, visitedArray, allValidWords);
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
        if (!trieTree.contains(word.toCharArray())) {
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


    private void findWords(IoaNFastTrie.Node nodeToStart, char[] word, int row, int col, boolean[][] visitedArray, Set<String> allValidWords) {
        IoaNFastTrie.Node foundNode = trieTree.getNode(word, nodeToStart);
        if (foundNode != null) {
            if (word.length > 2 && foundNode.endOfTheWord) {
                allValidWords.add(new String(word));
            }
            branchWord(foundNode, word, row + 1, col, visitedArray, allValidWords);
            branchWord(foundNode, word, row - 1, col, visitedArray, allValidWords);
            branchWord(foundNode, word, row, col + 1, visitedArray, allValidWords);
            branchWord(foundNode, word, row, col - 1, visitedArray, allValidWords);
            branchWord(foundNode, word, row - 1, col - 1, visitedArray, allValidWords);
            branchWord(foundNode, word, row + 1, col - 1, visitedArray, allValidWords);
            branchWord(foundNode, word, row - 1, col + 1, visitedArray, allValidWords);
            branchWord(foundNode, word, row + 1, col + 1, visitedArray, allValidWords);
        }
    }

    private void branchWord(IoaNFastTrie.Node nodeToStart, char[] word, int row, int col, boolean[][] visitedArray, Set<String> allValidWords) {
        if (row < board.rows() && row >= 0 && col < board.cols() && col >= 0 && !visitedArray[row][col]) {
            visitedArray[row][col] = true;
            char[] letter = getLetter(board, row, col);
            char[] newWord = new char[letter.length + word.length];
            System.arraycopy(word, 0, newWord, 0, word.length);
            System.arraycopy(letter, 0, newWord, word.length, letter.length);
            findWords(nodeToStart, newWord, row, col, visitedArray, allValidWords);
            visitedArray[row][col] = false;
        }
    }

    private char[] getLetter(BoggleBoard board, int row, int col) {
        if (board.getLetter(row, col) == 'Q') {
            return QU_WORD;
        }
        return new char[]{board.getLetter(row, col)};
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

        public void put(char[] word) {
            fastPut(word);
        }

        private void fastPut(char[] word) {
            Node nodeToSearch = root;
            for (int i = 0; i < word.length; i++) {
                int letterIndex = word[i] - CHAR_START_INDEX;
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

        public boolean contains(char[] word) {
            Node node = getNode(word, root);
            return node != null && getNode(word, root).endOfTheWord;
        }

        public Node getNode(char[] word, Node nodeToSearch) {
            if (nodeToSearch == null) {
                nodeToSearch = root;
            }
            for (int i = nodeToSearch.letterNumber + 1; i < word.length; i++) {
                int letterIndex = word[i] - CHAR_START_INDEX;
                if (nodeToSearch.next[letterIndex] == null) {
                    return null;
                }
                nodeToSearch = nodeToSearch.next[letterIndex];
            }
            return nodeToSearch;
        }

    }
}
