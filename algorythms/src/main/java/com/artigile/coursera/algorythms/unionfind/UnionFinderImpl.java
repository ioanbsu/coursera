package com.artigile.coursera.algorythms.unionfind;

import com.artigile.coursera.algorythms.StdIn;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 11:00 AM
 */
public class UnionFinderImpl extends AbstractUnionFinder {

    public UnionFinderImpl(int n) {
        super(n);
    }

    public static void main(String[] args) {
        System.out.println("Enter length");
        int N = StdIn.readInt();
        UnionFinderImpl unionFinder = new UnionFinderImpl(N);
        unionFinder.testUnionFind();
    }

    public void union(int p, int q) {
        int newLinkValue = idArray[q];
        int rewriteValues = idArray[p];
        for (int i = 0; i < idArray.length; i++) {
            if (rewriteValues == idArray[i]) {
                idArray[i] = newLinkValue;
            }
        }
    }

    public boolean connected(int p, int q) {
        return idArray[p] == idArray[q];
    }

}
