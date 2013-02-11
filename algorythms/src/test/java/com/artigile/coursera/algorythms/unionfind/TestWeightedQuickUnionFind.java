package com.artigile.coursera.algorythms.unionfind;

import org.junit.Test;

/**
 * @author IoaN, 2/10/13 12:54 PM
 */
public class TestWeightedQuickUnionFind extends AbstractUnionTest {

    @Test
    public void testQuickUnionFind() {
        int N = 10;
        testUnion(new WeightedQuickUnion(N),union1);
        testUnion(new WeightedQuickUnion(N),union2);
        testUnion(new WeightedQuickUnion(N),union3);
    }

}
