package com.artigile.coursera.algorythms.unionfind;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 2:34 PM
 */
public abstract class AbstractUnionFinder implements UnionFinder {

    protected int[] idArray;

    public AbstractUnionFinder(int n) {
        idArray = new int[n];
        for (int i = 0; i < n; i++) {
            idArray[i] = i;
        }
    }

    public int getRoot(int i){
        return i;
    }

}
