package com.artigile.coursera.algorythms.unionfind;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 2:34 PM
 */
public class QuickUnionFinder extends AbstractUnionFinder {


    public QuickUnionFinder(int n) {
        super(n);
    }

    public void union(int p, int q) {
        idArray[getRoot(q)] = p;
    }

    public boolean connected(int p, int q) {
        return getRoot(p) == getRoot(q);
    }

    private int getRoot(int i) {
        while (i != idArray[i]) {
            i = idArray[i];
        }
        return i;
    }
}
