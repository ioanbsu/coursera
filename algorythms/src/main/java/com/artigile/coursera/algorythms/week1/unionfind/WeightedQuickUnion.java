package com.artigile.coursera.algorythms.week1.unionfind;

import com.artigile.coursera.algorythms.StdIn;

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

    public static void main(String[] args) {
        System.out.println("Enter length");
        int N = StdIn.readInt();
        WeightedQuickUnion unionFinder = new WeightedQuickUnion(N);
        unionFinder.testUnionFind();
    }

    @Override
    public void union(int p, int q) {
        int rootP = getRoot(p);
        int rootQ = getRoot(q);
        if (weightArray[rootP] >= weightArray[rootQ]) {
            idArray[rootQ] = rootP;
            weightArray[rootP] += weightArray[rootQ];
        } else {
            idArray[rootP] = rootQ;
            weightArray[rootQ] += weightArray[rootP];
        }
    }

    public int getRoot(int i) {
        while (i != idArray[i]) {
            i = idArray[i];
        }
        return i;
    }
}
