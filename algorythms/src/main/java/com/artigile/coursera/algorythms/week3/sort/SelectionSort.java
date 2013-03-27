package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 3/24/13 10:18 AM
 */
public class SelectionSort extends AbstractSort {

    public static int exchangeNum=1;
    public static void main(String[] args) {
        Integer[] array = (Integer[]) stringToArray("32 94 16 36 20 98 92 31 51 76");
        SelectionSort selectionSort=new SelectionSort();
        selectionSort.sort(array);
    }

    @Override
    public void sort(Comparable[] array) {
        int N = array.length;
        for (int i = 0; i < N; i++)
        {
            int min = i;
            for (int j = i+1; j < N; j++)
                if (array[j].compareTo(array[min])<0)
                    min = j;
            System.out.println("Exchange: "+ exchangeNum++);
            exch(array, i, min);
            printArray(array);
        }
    }
}
