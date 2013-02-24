package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 11:28 AM
 */
public class MergeBottomUp extends AbstractMergeSort {

    public static void main(String[] args) {
        Integer[] array = (Integer[]) stringToArray("53 14 93 29 68 52 89 24 44 74");
        MergeBottomUp mergeBottomUp = new MergeBottomUp();
        mergeBottomUp.sort(array);
        System.out.println("");
    }

    public void sort(Comparable[] array) {
        Integer[] copiedArray = new Integer[array.length];
        System.arraycopy(array, 0, copiedArray, 0, array.length);

        for (int size = 1; size < array.length; size *= 2) {
            for (int lo = 0; lo < array.length - size; lo += size * 2) {
                merge(array, copiedArray, lo, lo + size - 1, Math.min(lo + size * 2 - 1, array.length - 1));
            }
        }
    }

}
