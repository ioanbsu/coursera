/**
 * @author ivanbahdanau
 */
public class CircularSuffixArray {

    private int[] indexArray;

    /**
     * circular suffix array of s
     *
     * @param s str
     */
    public CircularSuffixArray(String s) {
        int stringLength = s.length();
        char[] charArray = s.toCharArray();
        indexArray = new int[stringLength];
        SuffixArray[] sortedSuffixes = new SuffixArray[stringLength];
        for (int i = 0; i < s.length(); i++) {
            char[] sortedArray = new char[stringLength];
            System.arraycopy(charArray, i, sortedArray, 0, stringLength - i);
            System.arraycopy(charArray, 0, sortedArray, stringLength - i, i);
            sortedSuffixes[i] = new SuffixArray(sortedArray);
            indexArray[i] = i;

        }
        sort(sortedSuffixes);
//        System.out.println("");
    }

    /**
     * @return length of s
     */
    public int length() {
        return indexArray.length;
    }

    /**
     * @param i the index
     * @return returns index of ith sorted suffix
     */
    public int index(int i) {
        return indexArray[i];
    }


    public static void main(String[] args) {
        new CircularSuffixArray("ABRACADABRA!");
    }


    private void sort(Comparable[] array) {
        sort(array, 0, array.length - 1);
        //printArray(array);
    }

    private void sort(Comparable[] array, int compareInd1, int compareInd2) {
        if (compareInd1 >= compareInd2) {
            return;
        }
        int found = sort(array, compareInd1, compareInd2, false);
//        printArray(array);
//        System.out.println(found);
        sort(array, compareInd1, found - 1);
        sort(array, found + 1, compareInd2);
    }

    private int sort(Comparable[] array, int compareInd1, int compareInd2, boolean searchUp) {
        if (compareInd1 >= compareInd2) {
            return compareInd2;
        }
        if (!searchUp) {
            for (int i = compareInd2; i > compareInd1; i--) {
                if (array[i].compareTo(array[compareInd1]) < 0) {
                    exch(array, compareInd1, i);
                    return sort(array, compareInd1 + 1, i, true);
                }
            }
            return compareInd1;
        } else {
            for (int i = compareInd1; i < compareInd2; i++) {
                if (array[i].compareTo(array[compareInd2]) > 0) {
                    exch(array, compareInd2, i);
                    return sort(array, i, compareInd2 - 1, false);
                }
            }
            return compareInd2;
        }
    }


    private void exch(Comparable[] array, int compareInd1, int compareInd2) {
        Comparable el = array[compareInd1];
        array[compareInd1] = array[compareInd2];
        array[compareInd2] = el;
        int tmp = indexArray[compareInd1];

        indexArray[compareInd1] = indexArray[compareInd2];
        indexArray[compareInd2] = tmp;


    }

    private class SuffixArray implements Comparable<SuffixArray> {

        private char[] array;

        private SuffixArray(char[] array) {
            this.array = array;
        }

        @Override
        public int compareTo(SuffixArray otherArray) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] > otherArray.array[i]) {
                    return 1;
                } else if (array[i] < otherArray.array[i]) {
                    return -1;
                }
            }
            return 0;
        }
    }
}