package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 6:35 PM
 */
public class CourseraQuickSort extends AbstractSort {


    public static void main(String[] args) {
        CourseraQuickSort quickSort = new CourseraQuickSort();
//        Integer[] array = (Integer[]) stringToArray("73 48 25 53 17 37 42 76 96 63 71 66");
        String[] array = "A B A B A A A A B A A A".split(" ");
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
            exchange(array, i, j);
        }
        exchange(array, lo, j);
        return j;

    }
}
