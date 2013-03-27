package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 10:30 AM
 */
public class MergeSort extends AbstractMergeSort {


    public static void main(String[] args) {
        MergeSort mergeSort = new MergeSort();
        Integer[] array = (Integer[]) stringToArray("45 30 83 58 64 46 27 33 52 96");
        Integer[] copiedArray = new Integer[array.length];
        System.arraycopy(array, 0, copiedArray, 0, array.length);
        mergeSort.sort(array);
        System.out.println("sdfsdf");
    }

    public void sort(Comparable[] array, Comparable[] copiedArray, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = (lo + hi) / 2;
        sort(array, copiedArray, lo, mid);
        sort(array, copiedArray, mid + 1, hi);
        merge(array, copiedArray, lo, mid, hi);
    }

    @Override
    public void sort(Comparable[] array) {
        Integer[] copiedArray = new Integer[array.length];
        System.arraycopy(array, 0, copiedArray, 0, array.length);
        sort(array, copiedArray, 0, array.length - 1);
    }
}
