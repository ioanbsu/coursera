package com.artigile.coursera.algorythms.unionfind;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 4:26 PM
 */
public class WeightedQuickUnion extends QuickUnionFinder {

    private int[] weightArray;

    public WeightedQuickUnion(int n) {
        super(n);
        weightArray = new int[n];
        for (int i = 0; i < n; i++) {
            weightArray[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int rootP = getRoot(p);
        int rootQ = getRoot(q);
        if (weightArray[rootP] > weightArray[rootQ]) {
            idArray[rootP] =rootQ;
            weightArray[rootP] += weightArray[rootQ];
        } else {
            idArray[rootQ] =rootP;
            weightArray[rootQ] += weightArray[rootP];
        }
    }

    public int getRoot(int i) {
        while (i != idArray[i]) {
            idArray[i]=idArray[idArray[i]];
            i = idArray[i];
        }
        return i;
    }
}
