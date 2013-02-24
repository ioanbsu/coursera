package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 6:08 PM
 */
public class ThreeWayQuickSort extends AbstractSort {


    public static void main(String[] args) {
        ThreeWayQuickSort threeWaySort = new ThreeWayQuickSort();
        Integer[] array = (Integer[]) stringToArray("53 38 22 31 33 55 53 67 53 53");
        threeWaySort.sort(array);
        threeWaySort.printArray(array);
    }

    @Override
    public void sort(Comparable[] array) {
        sort(array, 0, array.length - 1);
    }

    private void sort(Comparable[] array, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int lt = lo, gt = hi;
        Comparable v = array[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = array[i].compareTo(v);
            if (cmp < 0) {
                exchange(array, lt++, i++);
            } else if (cmp > 0) {
                exchange(array, i, gt--);
            } else {
                i++;
            }
        }
//        printArray(array);
        sort(array, lo, lt - 1);
        sort(array, gt + 1, hi);
    }
}
