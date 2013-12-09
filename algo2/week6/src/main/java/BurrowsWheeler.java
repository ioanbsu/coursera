import java.util.Arrays;
import java.util.Comparator;

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
        StringBuilder str = new StringBuilder();
        int s = BinaryStdIn.readInt(32);
        while (!BinaryStdIn.isEmpty())
            str = str.append(BinaryStdIn.readChar());
        int[] next = argsort(str.toString().toCharArray());
        int idx = 0;
        int pt = next[s];
        while (idx < str.length() - 1) {
            BinaryStdOut.write(str.charAt(pt));
            pt = next[pt];
            idx++;
        }
        BinaryStdOut.write(str.charAt(s));
        // System.out.print(str.charAt(s));
        BinaryStdIn.close();
        BinaryStdOut.close();
    }


    private static int[] argsort(final char[] a) {
        Integer[] indexes = new Integer[a.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        Arrays.sort(indexes, new Comparator<Integer>() {
            @Override
            public int compare(final Integer i1, final Integer i2) {
                return Float.compare(a[i1], a[i2]);
            }
        });
        return asArray(indexes);
    }

    private static <T extends Number> int[] asArray(final T... a) {
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[i].intValue();
        }
        return b;
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
//    java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" BurrowsWheeler - < aesop.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:"  BurrowsWheeler +
//   time  java -Xmx1024M -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" BurrowsWheeler - < aesop.txt | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" MoveToFront - | java -classpath "/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/algs4.jar:/Users/ivanbahdanau/IdeaProjects/git/coursera/libs/stdlib.jar:" Huffman - > mobyDickOutputFileName

}