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
        indexArray = new int[stringLength];
        char[] stringArray = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            indexArray[i] = i;
        }
        shellSort(stringArray);
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

    private void sort(char[] array) {
        sort(array, 0, array.length - 1);
    }

    public void shellSort(char[] array) {
        int N = array.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, ...
        while (h >= 1) { // h-sort the array.
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && compareArrays(array, j, j - h) < 0; j -= h) {
                    exch(j, j - h);
                }
            }
            h = h / 3;
        }
    }

    private void sort(char[] array, int compareInd1, int compareInd2) {
        if (compareInd1 >= compareInd2) {
            return;
        }
        int found = sort(array, compareInd1, compareInd2, false);
//        printArray(array);
//        System.out.println(found);
        sort(array, compareInd1, found - 1);
        sort(array, found + 1, compareInd2);
    }

    private int sort(char[] array, int compareInd1, int compareInd2, boolean searchUp) {
        if (compareInd1 >= compareInd2) {
            return compareInd2;
        }
        if (!searchUp) {
            for (int i = compareInd2; i > compareInd1; i--) {
                if (compareArrays(array, i, compareInd1) < 0) {
                    exch(compareInd1, i);
                    return sort(array, compareInd1 + 1, i, true);
                }
            }
            return compareInd1;
        } else {
            for (int i = compareInd1; i < compareInd2; i++) {
                if (compareArrays(array, i, compareInd2) > 0) {
                    exch(compareInd2, i);
                    return sort(array, i, compareInd2 - 1, false);
                }
            }
            return compareInd2;
        }
    }

    private int compareArrays(char[] chars, int i1, int i2) {
        i1 = indexArray[i1];
        i2 = indexArray[i2];
        for (int i = 0; i < chars.length; i++) {

            int index1 = i + i1;
            int index2 = i + i2;
            if (index1 >= chars.length) {
                index1 = index1 - chars.length;
            }
            if (index2 >= chars.length) {
                index2 = index2 - chars.length;
            }
            if (chars[index1] > chars[index2]) {
                return 1;
            } else if (chars[index1] < chars[index2]) {
                return -1;
            }
        }
        return 0;
    }


    private void exch(int compareInd1, int compareInd2) {
        int tmp = indexArray[compareInd1];
        indexArray[compareInd1] = indexArray[compareInd2];
        indexArray[compareInd2] = tmp;
    }


}