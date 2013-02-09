package com.artigile.coursera.algorythms.unionfind;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 11:02 AM
 */
public interface UnionFinder {

    void union(int p, int q);

    boolean connected(int p, int q);

    int getRoot(int i);
}
