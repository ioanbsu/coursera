import java.util.*;

/**
 * @author ivanbahdanau
 */
public class BurrowsWheeler {
    /**
     * apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
     */
    public static void encode() {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            char c = BinaryStdIn.readChar();
            stringBuilder.append(c);
        }

        String word = stringBuilder.toString();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(word);
        for (int i = 0; i < word.length(); i++) {
            if (circularSuffixArray.index(i) == 0) {
                BinaryStdOut.write(i, 32);
                break;
            }
        }
        for (int i = 0; i < word.length(); i++) {
            int index = circularSuffixArray.index(i) - 1;
            if (index < 0) {
                index = word.length() - 1;
            }
            BinaryStdOut.write(word.charAt(index), 8);
        }
        BinaryStdOut.close();
    }

    /**
     * apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
     */
    public static void decode() {
        int fisrtCharacter = BinaryStdIn.readInt(32);

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; !BinaryStdIn.isEmpty(); i++) {
            strBuilder.append(BinaryStdIn.readChar(8));
        }
        String achievedStr = strBuilder.toString();


//        int fisrtCharacter = 3;
//        String achievedStr = "ARD!RCAAAABB";

        char[] t = achievedStr.toCharArray();
        char[] first = achievedStr.toCharArray();
        Arrays.sort(first);
        int[] next = new int[t.length];
        boolean[] processed = new boolean[t.length];

        for (int frstIndex = 0; frstIndex < first.length; frstIndex++) {
            char searchChar = first[frstIndex];
            for (int tIndx = 0; tIndx < t.length; tIndx++) {
                if (!processed[tIndx]&&searchChar == t[tIndx]) {
                    next[frstIndex] = tIndx;
                    processed[tIndx]=true;
                    break;
                }
            }
        }

        int nextIndex = fisrtCharacter;
        for (int i = 0; i < t.length; i++) {
            BinaryStdOut.write(first[nextIndex], 8);
            nextIndex = next[nextIndex];
        }
        BinaryStdOut.close();
    }

    /**
     * if args[0] is '-', apply Burrows-Wheeler encoding
     * if args[0] is '+', apply Burrows-Wheeler decoding
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

//    java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" BurrowsWheeler - < abra.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" HexDump 16
//    java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" BurrowsWheeler - < abra.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:"  BurrowsWheeler +
//   time  java -Xmx1024M -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" BurrowsWheeler - < aesop.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" MoveToFront - | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" Huffman - > mobyDickOutputFileName

}