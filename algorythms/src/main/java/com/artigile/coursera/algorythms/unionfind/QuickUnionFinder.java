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

    public void union(int rootP, int rootQ) {
        idArray[rootQ] =getRoot(rootP);
    }

    public boolean connected(int rootP, int rootQ) {
        return rootP==rootQ;
    }

    public int getRoot(int i) {
        while (i != idArray[i]) {
            i = idArray[i];
        }
        return i;
    }
}
