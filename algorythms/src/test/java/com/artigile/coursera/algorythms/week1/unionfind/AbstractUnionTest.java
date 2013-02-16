package com.artigile.coursera.algorythms.week1.unionfind;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author IoaN, 2/10/13 1:21 PM
 */
public abstract class AbstractUnionTest {

    protected int[][] union1=new int[][]{
            {1,2},
            {1,3},
            {6,0},
            {0,8},
            {0,9},
            {4,5},
            {7,4},
            {7,9},
            {3,0}
    };

    protected int[][] union2=new int[][]{
            {4,3},
            {3,8},
            {6,5},
            {4,9},
            {2,1},
            {5,0},
            {7,2},
            {6,1},
            {7,3}
    };

    protected int[][] union3=new int[][]{
            {5,3},
            {8,7},
            {5,4},
            {0,2},
            {1,4},
            {0,7},
            {9,3},
            {0,1},
            {7,6}
    };


    public void testUnion(UnionFinder uf, int[][] unionsArray) {
        for (int[] unions : unionsArray) {
            int rootP = uf.getRoot(unions[0]);
            int rootQ = uf.getRoot(unions[1]);
            assertFalse(uf.connected(rootP, rootQ));
            uf.union(rootP, rootQ);
            assertTrue(uf.connected( uf.getRoot(rootP),  uf.getRoot(rootQ)));
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(uf.connected(uf.getRoot(i), uf.getRoot(j)));
            }
        }
    }
}
