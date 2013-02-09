package com.artigile.coursera.algorythms.unionfind;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 11:00 AM
 */
public class UnionFinderImpl extends AbstractUnionFinder{

    public UnionFinderImpl(int n) {
        super(n);
    }

    public void union(int p, int q) {
        int newLinkValue = idArray[p];
        int rewriteValues = idArray[q];
        for (int i=0;i<idArray.length ;i++) {
            if (rewriteValues == idArray[i]) {
                idArray[i] = newLinkValue;
            }
        }
    }

    public boolean connected(int p, int q) {
        return idArray[p]==idArray[q];
    }
}
