package com.artigile.coursera.algorythms.unionfind;

import com.artigile.coursera.algorythms.StdIn;

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

    public int getRoot(int i) {
        return i;
    }

    public void testUnionFind() {
        int p, q, rootP, rootQ;
        while (!StdIn.isEmpty()) {
            p = StdIn.readInt();
            q = StdIn.readInt();
            rootP = getRoot(p);
            rootQ = getRoot(q);
            if (!connected(rootP, rootQ)) {
                union(rootP, rootQ);
                System.out.println("Connected " + p + " and " + q);
            } else {
                System.out.println(p + " and " + q + " are already connected ");
            }
        }
    }

}
