package com.artigile.coursera.algorythms.week2.sort;

/**
 * @author IoaN, 2/16/13 6:52 PM
 */
public class SelectionSort {

    private static final int array1[] = {66, 69, 98, 29, 28, 53, 10, 79, 86, 30};
    private static final int array2[] = {81, 96, 33, 74, 90, 65, 56, 24, 34, 42};
    private static final int array3[] = {33, 83, 99, 40, 92, 86, 82, 85, 70, 10};

    public static void main(String[] args) {
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(array3);
    }

    public void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int min = array[i];
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    minIndex = j;
                }
            }
            exchange(array, i, minIndex);

            System.out.println("iteration " + (i + 1) + ":" + arrayToString(array));
        }
    }

    private String arrayToString(int[] array) {
        StringBuilder arrayStringBuilder = new StringBuilder();
        for (int i : array) {
            arrayStringBuilder.append(i).append(" ");
        }
        return arrayStringBuilder.toString();
    }

    private void exchange(int[] array, int i, int j) {
        int rememberVal = array[i];
        array[i] = array[j];
        array[j] = rememberVal;
    }

}
