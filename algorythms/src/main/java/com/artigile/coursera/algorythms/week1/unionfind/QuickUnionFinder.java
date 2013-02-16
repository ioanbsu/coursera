package com.artigile.coursera.algorythms.week1.unionfind;

import com.artigile.coursera.algorythms.StdIn;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 2:34 PM
 */
public class QuickUnionFinder extends AbstractUnionFinder {


    public QuickUnionFinder(int n) {
        super(n);
    }

    public static void main(String[] args) {
        System.out.println("Enter length");
        int N = StdIn.readInt();
        QuickUnionFinder unionFinder = new QuickUnionFinder(N);
        unionFinder.testUnionFind();
    }

    public void union(int rootP, int rootQ) {
        idArray[rootQ] =rootP;
    }

    public boolean connected(int rootP, int rootQ) {
        return rootP == rootQ;
    }

    public int getRoot(int i) {
        while (i != idArray[i]) {
            i = idArray[i];
        }
        return i;
    }
}
