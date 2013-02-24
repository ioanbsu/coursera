package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 1:14 PM
 */
public class QuickSort extends AbstractSort {

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
//        Integer[] array = (Integer[]) stringToArray("27 81 79 30 38 85 21 22 70 45 66 75");
        String[] array = "B B B A A A A A B A B A".split(" ");
        quickSort.sort(array);
    }

    @Override
    public void sort(Comparable[] array) {
        sort(array, 0, array.length - 1);
        //printArray(array);
    }

    private void sort(Comparable[] array, int compareInd1, int compareInd2) {
        if(compareInd1>=compareInd2){
            return;
        }
        int found=sort(array, compareInd1,compareInd2,false);
//        printArray(array);
//        System.out.println(found);
        sort(array,compareInd1,found-1);
        sort(array,found+1,compareInd2);
    }

    private int sort(Comparable[] array, int compareInd1, int compareInd2, boolean searchUp) {
        if (compareInd1 >= compareInd2) {
            return compareInd2;
        }
        if (!searchUp) {
            for (int i = compareInd2; i > compareInd1 ; i--) {
                if (array[i].compareTo(array[compareInd1]) < 0) {
                    exchange(array, compareInd1, i);
                    return sort(array, compareInd1 + 1, i, true);
                }
            }
            return compareInd1;
        } else {
            for (int i = compareInd1; i < compareInd2; i++) {
                if (array[i].compareTo(array[compareInd2]) > 0) {
                    exchange(array, compareInd2, i);
                   return sort(array, i, compareInd2 - 1, false);
                }
            }
            return compareInd2;
        }
    }


}
