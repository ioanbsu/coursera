package com.artigile.coursera.algorythms.unionfind;

import org.junit.Test;

/**
 * @author IoaN, 2/10/13 12:54 PM
 */
public class TextUnionFind extends AbstractUnionTest {

    @Test
    public void testQuickUnionFind() {
        int N = 10;
        testUnion(new UnionFinderImpl(N),union1);
        testUnion(new UnionFinderImpl(N),union2);
    }
}
