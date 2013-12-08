
/**
 * @author ivanbahdanau
 */
public class CircularSuffixArray {

    private int[] indexArray;
    public static int MAX_INSERTION_SORT = 25;

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
     * @param i() the index
     * @return returns index of ith sorted suffix
     */
    public int index(int i) {
        return indexArray[i];
    }


    public static void main(String[] args) {
        //  new CircularSuffixArray( Files.toString(new File("/Users/ivanbahdanau/IdeaProjects/git/coursera/algo2/week6/src/main/resources/aesop.txt"), Charsets.UTF_8));
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


    private static void quicksort(char[] array) {
        quicksort(array, 0, array.length - 1);

    }

    public static void quicksort(char[] data, int start, int end) {
        // nonrecursive, efficient inner loop,
        // limited stack size, OK if list already sorted

        int pivotIndex; // Array index for the pivot element
        int first, last;  // start, end of current part
        int n1, n2; // Number of elements before and after the pivot element
        int[] stack = new int[100]; //Stack to hold index ranges
        // allows 2^50 elements to be sorted!
        int top = -1;

        //Fill stack with initial values in preparation for loop
        if (end - start >= MAX_INSERTION_SORT) {
            stack[++top] = start;
            stack[++top] = end;
        }
        while (top >= 0) {
            //Get segment length & first index values
            last = stack[top--];
            first = stack[top--];

            // Partition the array, and set the pivot index.
            pivotIndex = partition(data, first, last);
//      System.out.println("Pivot index: " + pivotIndex);
//      TestSort.printArray(data);

            // Compute the sizes of the two pieces.
            n1 = pivotIndex - first;
            n2 = last - pivotIndex;

            // Push the n & first values of the array segments before & after
            // the pivotIndex onto the stack.  Make sure the larger of the
            // two segments is pushed first.  Only push a segment if its
            // length is > 1
            if (n2 < n1) {
                if (n1 > MAX_INSERTION_SORT) {
                    stack[++top] = first;
                    stack[++top] = pivotIndex - 1;
                }
                if (n2 > MAX_INSERTION_SORT) {
                    stack[++top] = pivotIndex + 1;
                    stack[++top] = last;
                }
            } else {
                if (n2 > MAX_INSERTION_SORT) {
                    stack[++top] = pivotIndex + 1;
                    stack[++top] = last;
                }
                if (n1 > MAX_INSERTION_SORT) {
                    stack[++top] = first;
                    stack[++top] = pivotIndex - 1;
                }
            } // end push depending on size
        } // end while loop for stack
        if (MAX_INSERTION_SORT > 1)  // test allows a check of qsort part
            insertionsort(data, start, end); //use original ends of region
    }


    private static int partition(char[] data, int first, int last) {
        // This version relies on sentinels to make inner loops faster.
        // Precondition: last>first, and data from index first through last.
        // Postcondition: The method has selected some "pivot value" that occurs
        // in data[first]...data[last]. The elements of data have then been
        // rearranged and the method returns a pivot index so that
        // -- data[pivot index] is equal to the pivot;
        // -- each element before data[pivot index] is <= the pivot;
        // -- each element after data[pivot index] is >= the pivot.
        int iLo = first + 1, iHi = last; //lowest, highest untested indices
        int mid = (first + last) / 2; // take pivot from here
        char pivot = data[mid];        //    so in-order not worst case

        // Almost the body of main while loop follows, except for non-sentinal search
        //Swap the chosen pivot value into beginning
        data[mid] = data[first];
        data[first] = pivot;  //serves as first sentinel for downward sweep

        while (data[iHi] > pivot) // normal downward scan
            iHi--;
        while (iLo <= iHi && data[iLo] < pivot) // no sentinel upward yet
            iLo++;
        if (iLo <= iHi) {
            char temp = data[iLo];
            data[iLo] = data[iHi];
            data[iHi] = temp;
            iHi--;
            iLo++;
        }

        while (iLo <= iHi) { // now have sentinels both ways
            while (data[iHi] > pivot)
                iHi--;
            while (data[iLo] < pivot) // have sentenel swapped in place now
                iLo++;
            if (iLo <= iHi) {
                char temp = data[iLo];
                data[iLo] = data[iHi];
                data[iHi] = temp;
                iHi--;
                iLo++;
            }
        }
        int iPivot = iLo - 1;          // place of last smaller value
        data[first] = data[iPivot];  // swap with pivot
        data[iPivot] = pivot;
        return iPivot;
    }

    public static void insertionsort(char[] data, int start, int end) {
        for (int next = start + 1; next <= end; next++) {
            if (data[next - 1] > data[next]) {
                char val = data[next];
                int gap = next - 1;
                data[next] = data[gap];
                while (gap > start && data[gap - 1] > val) {
                    data[gap] = data[gap - 1];
                    gap--;
                }
                data[gap] = val;
            } // end: if not already in place
        } // end: for each element to be inserted
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