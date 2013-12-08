/**
 * @author ivanbahdanau
 */
public class MoveToFront {


    private static final int ASCII_SIZE = 256;
    private static char[] ascii = new char[ASCII_SIZE];

    /**
     * apply move-to-front encoding, reading from standard input and writing to standard output
     */
    public static void encode() {
        for (int i = 0; i < ascii.length; i++) {
            ascii[i] = (char) i;
        }
        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            char c = BinaryStdIn.readChar();
            int replaceCharacter = updateArray(c);
            BinaryStdOut.write(replaceCharacter, 8);
        }
        BinaryStdOut.close();
    }

    /**
     * apply move-to-front decoding, reading from standard input and writing to standard output
     */
    public static void decode() {
        for (int i = 0; i < ascii.length; i++) {
            ascii[i] = (char) i;
        }
        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            char c = BinaryStdIn.readChar(8);
            char character = ascii[c];
            BinaryStdOut.write(character, 8);
            updateArray(character);
        }
        BinaryStdOut.close();
    }

    /**
     * if args[0] is '-', apply move-to-front encoding
     * if args[0] is '+', apply move-to-front decoding
     *
     * @param args args
     */
    public static void main(String[] args) {
        String value = args[0];
        if ("-".equals(value)) {
            encode();
        }
        if ("+".equals(value)) {
            decode();
        }
    }


    private static int updateArray(char c) {
        int replaceCharacter = -1;
        for (int searchCharIndex = 0; searchCharIndex < ASCII_SIZE; searchCharIndex++) {
            if (ascii[searchCharIndex] == c) {
                replaceCharacter = searchCharIndex;
                break;
            }
        }
        System.arraycopy(ascii, 0, ascii, 1, replaceCharacter);
        ascii[0] = c;
        return replaceCharacter;
    }
    //java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" MoveToFront - < abra.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" MoveToFront +
    //java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" MoveToFront - < aesop.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" MoveToFront +

}