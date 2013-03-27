package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 6:35 PM
 */
public class CourseraQuickSort extends AbstractSort {


    public static void main(String[] args) {
        CourseraQuickSort quickSort = new CourseraQuickSort();
        Integer[] array = (Integer[]) stringToArray("70 84 29 83 39 72 13 67 43 25 31 69");
//        String[] array = "A B A B A A A A B A A A".split(" ");
        quickSort.sort(array);
        quickSort.printArray(array);
    }

    @Override
    public void sort(Comparable[] array) {
         sort(array,0,array.length-1) ;
    }

    private int sort(Comparable[] array, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (array[++i].compareTo(array[lo]) < 0) {
                if (i == hi) {
                    break;
                }
            }
            while (array[lo].compareTo(array[--j]) < 0) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(array, i, j);
        }
        exch(array, lo, j);
        return j;

    }
}
