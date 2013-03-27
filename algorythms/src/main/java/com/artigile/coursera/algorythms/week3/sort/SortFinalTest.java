package com.artigile.coursera.algorythms.week3.sort;

import java.util.Arrays;

/**
 * @author IoaN, 3/24/13 10:47 AM
 */
public class SortFinalTest {

    public static void main(String[] args) {
        Sort quickSort=new SelectionSort();
        int i=0;
        for (String[] array: AbstractSort.arrays) {
            quickSort.sort(array);

            if(i<AbstractSort.arrays.length-1/*&&Arrays.equals(array,AbstractSort.arrays[i+1])*/){
                System.out.println(i);
                quickSort.printArray(array);
                quickSort.printArray(AbstractSort.arrays[i + 1]);
            }
            i++;

        }
    }
}
