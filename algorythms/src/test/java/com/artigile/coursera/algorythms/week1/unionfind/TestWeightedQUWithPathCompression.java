package com.artigile.coursera.algorythms.week1.unionfind;

import org.junit.Test;

/**
 * @author IoaN, 2/10/13 6:44 PM
 */
public class TestWeightedQUWithPathCompression extends AbstractUnionTest{
    @Test
    public void testQuickUnionFind() {
        int N = 10;
        testUnion(new WeightedQUWithPathCompression(N),union1);
        testUnion(new WeightedQUWithPathCompression(N),union3);
    }

}
