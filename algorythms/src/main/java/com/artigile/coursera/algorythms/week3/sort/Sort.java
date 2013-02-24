package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 12:14 PM
 */
public interface Sort {

    void sort(Comparable[] array);

    public boolean isSorted(Comparable[] array, int beginning, int ending);
    public boolean isSorted(Comparable[] array);


}
