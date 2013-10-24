package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 3/24/13 12:45 PM
 */
public class Shellsort extends AbstractSort {

    private static int sortingPhase = 1;

    public static void main(String[] args) {
        Integer[] array = (Integer[]) stringToArray("51 27 66 53 37 70 54 64 13 48");

        Shellsort shellsort = new Shellsort();
        shellsort.sort(array);
        shellsort.printArray(array);
    }

    @Override
    public void sort(Comparable[] array) {
        int N = array.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, ...
        while (h >= 1) { // h-sort the array.
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && array[j].compareTo(array[j - h]) < 0; j -= h) {
                    exch(array, j, j - h);
                    System.out.println("Sorting Phase :" + sortingPhase++);
                    printArray(array);
                }
            }
            h = h / 3;
        }
    }
}
