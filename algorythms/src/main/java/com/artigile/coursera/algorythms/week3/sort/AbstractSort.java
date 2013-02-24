package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 1:34 PM
 */
public abstract class AbstractSort implements Sort {


    protected static Comparable[] stringToArray(String str) {
        String[] values = str.split(" ");
        Integer[] intValues = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            intValues[i] = Integer.valueOf(values[i]);
        }
        return intValues;
    }

    public boolean isSorted(Comparable[] array) {
        return isSorted(array, 0,array.length);
    }
    public boolean isSorted(Comparable[] array, int beginning, int ending) {
        for (int i = beginning; i < ending - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    protected void printArray(Comparable[] array) {
        for (Comparable comparable : array) {
            System.out.print(comparable + " ");
        }
        System.out.println("");
    }



    protected void exchange(Comparable[] array, int compareInd1, int compareInd2) {
        Comparable el = array[compareInd1];
        array[compareInd1] = array[compareInd2];
        array[compareInd2] = el;
    }

}
