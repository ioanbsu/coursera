package com.artigile.coursera.algorythms.unionfind;

import org.junit.Test;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 11:05 AM
 */

public class TestQuickUnionFind extends AbstractUnionTest {

    @Test
    public void testQuickUnionFind() {
        int N = 10;
        testUnion(new QuickUnionFinder(N),union1);
        testUnion(new QuickUnionFinder(N),union2);
    }


}
