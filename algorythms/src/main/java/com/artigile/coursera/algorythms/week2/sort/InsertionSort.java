package com.artigile.coursera.algorythms.week2.sort;

/**
 * @author IoaN, 2/16/13 7:06 PM
 */
public class InsertionSort {

    private static int exchangeNum=1;

    private static final int array1[] = {66, 69, 98, 29, 28, 53, 10, 79, 86, 30};
    private static final int array2[] = {81, 96, 33, 74, 90, 65, 56, 24, 34, 42};
    private static final int array3[] = {22 ,29 ,40 ,55 ,75 ,26 ,21 ,12 ,72 ,67 };

    public static void main(String[] args) {
        InsertionSort insertionSort = new InsertionSort();
        insertionSort.sort(array3);
    }

    public void sort(int[] array) {
        int N = array.length;
        for (int i = 0; i < N; i++)
            for (int j = i; j > 0; j--)
                if (array[j]<array[j-1])
                    exchange(array, j, j - 1);
                else break;
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
        System.out.println("exchanged "+exchangeNum++ +": ");
        System.out.println(arrayToString(array));
    }
}
