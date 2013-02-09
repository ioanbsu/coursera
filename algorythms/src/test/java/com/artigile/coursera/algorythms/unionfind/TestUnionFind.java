package com.artigile.coursera.algorythms.unionfind;

import com.artigile.coursera.algorythms.StdIn;
import org.junit.Test;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 11:05 AM
 */

public class TestUnionFind {


    public static void main(String[] args) {
        new TestUnionFind().testQuickUnionFind();
    }

    @Test
    public void testUnionFind() {
        System.out.println("Enter length");
        int N = StdIn.readInt();
        UnionFinder uf = new UnionFinderImpl(N);
        int p, q;
        while (!StdIn.isEmpty()) {
            p = StdIn.readInt();
            q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                System.out.println("Connected " + p + " and " + q);
            } else {
                System.out.println(p + " and " + q + " are already connected ");
            }
        }
    }

    @Test
    public void testQuickUnionFind() {
        System.out.println("Enter length");
        int N = StdIn.readInt();
        UnionFinder uf = new QuickUnionFinder(N);
        int p, q;
        while (!StdIn.isEmpty()) {
            p = StdIn.readInt();
            q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                System.out.println("Connected " + p + " and " + q);
            } else {
                System.out.println(p + " and " + q + " are already connected ");
            }
        }
    }


}
