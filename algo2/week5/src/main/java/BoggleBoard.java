/**
 * @author ivanbahdanau
 */

public class BoggleBoard {

    /**
     * Initializes a 4-by-4 Boggle board randomly.
     * (using the Hasbro dice)
     */
    public BoggleBoard() {

    }


    /**
     * Initializes a Boggle board from the specified filename.
     *
     * @param filename the dictionary file name
     */
    public BoggleBoard(String filename) {
    }


    /**
     * Initializes a Boggle board from the 2d char array.
     * (with 'Q' representing the two-letter sequence "Qu")
     *
     * @param a the board representation
     */
    public BoggleBoard(char[][] a) {
    }


    /**
     * Initializes a random M-by-N Boggle board randomly.
     * (using the frequency of letters in the English language)
     *
     * @param M width
     * @param N height
     */
    public BoggleBoard(int M, int N) {
    }


    /**
     * Returns the character in the given row and column.
     * (with 'Q' representing the two-letter sequence "Qu")
     *
     * @param row the col number
     * @param col the row number
     * @return
     */
    public char get(int row, int col) {
        return (char) -1;
    }

    /**
     * @return Returns the number of rows.
     */
    public int rows() {
        return -1;
    }

    /**
     * @return Returns the number of colums.
     */
    public int cols() {
        return -1;
    }


    /**
     * @return string representation of the board.
     */
    public String toString() {
        return "";
    }
}
