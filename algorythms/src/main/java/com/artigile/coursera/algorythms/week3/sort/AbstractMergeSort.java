package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 11:38 AM
 */
public abstract class AbstractMergeSort extends AbstractSort {

    private static int operation = 1;

    public void merge(Comparable[] array, Comparable[] copiedArray, int beginning, int middle, int ending) {
        assert isSorted(array, beginning, middle);
        assert isSorted(array, middle + 1, ending);
        for (int i = beginning; i <= ending; i++) {
            copiedArray[i] = array[i];
        }

        int i = beginning;
        int j = middle + 1;
        for (int k = beginning; k <= ending; k++) {
            if (i > middle) {
                array[k] = copiedArray[j++];
            } else if (j > ending) {
                array[k] = copiedArray[i++];
            } else if (copiedArray[i].compareTo(copiedArray[j]) < 0) {
                array[k] = copiedArray[i++];
            } else {
                array[k] = copiedArray[j++];
            }
        }
        System.out.println("Merge operation " + (operation++));
        printArray(array);
        assert isSorted(array, beginning, ending);
    }


}
