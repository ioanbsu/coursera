import java.util.Arrays;
import java.util.Date;

/**
 * @author ivanbahdanau
 */
public class BurrowsWheeler {
    /**
     * apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
     */
    public static void encode() {
        String word = BinaryStdIn.readString();
//        String word =  Files.toString(new File("/Users/ivanbahdanau/IdeaProjects/git/coursera/algo2/week6/src/main/resources/aesop.txt"), Charsets.UTF_8);
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(word);
        char[] wordCharacters = new char[word.length()];
        int initIndex = 0;
        for (int i = 0; i < word.length(); i++) {
            if (circularSuffixArray.index(i) == 0) {
                initIndex = i;
            }
            int index = circularSuffixArray.index(i) - 1;
            if (index < 0) {
                index = word.length() - 1;
            }
            wordCharacters[i] = word.charAt(index);
        }
        BinaryStdOut.write(initIndex, 32);
        BinaryStdOut.write(new String(wordCharacters), 8);
        BinaryStdOut.close();
    }

    /**
     * apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
     */
    public static void decode() {
        int fisrtCharacter = BinaryStdIn.readInt(32);
//        BinaryStdOut.write("Started decoding  " + new Date());
        String achievedStr = BinaryStdIn.readString();
        char[] t = achievedStr.toCharArray();
        char[] first = achievedStr.toCharArray();
        Arrays.sort(first);
        int[] next = new int[t.length];
        boolean[] processed = new boolean[t.length];

        for (int frstIndex = 0; frstIndex < first.length; frstIndex++) {
            char searchChar = first[frstIndex];
            for (int tIndx = 0; tIndx < t.length; tIndx++) {
                if (!processed[tIndx] && searchChar == t[tIndx]) {
                    next[frstIndex] = tIndx;
                    processed[tIndx] = true;
                    break;
                }
            }
        }
        int nextIndex = fisrtCharacter;
        for (int i = 0; i < t.length; i++) {
            BinaryStdOut.write(first[nextIndex], 8);
            nextIndex = next[nextIndex];
        }
//        BinaryStdOut.write("Done decoding  " + new Date());
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
//    java -Xmx1024M -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" BurrowsWheeler - < aesop.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:"  BurrowsWheeler +
//   time  java -Xmx1024M -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" BurrowsWheeler - < aesop.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" MoveToFront - | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" Huffman - > mobyDickOutputFileName

}