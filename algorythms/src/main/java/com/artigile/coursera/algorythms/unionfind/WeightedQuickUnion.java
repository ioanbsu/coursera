package com.artigile.coursera.algorythms.unionfind;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 4:26 PM
 */
public class WeightedQuickUnion extends  QuickUnionFinder{

    private int[] weightArray;

    public WeightedQuickUnion(int n) {
        super(n);
        weightArray=new int[n];
        for (int i = 0; i < n; i++) {
            weightArray[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int rootP=getRoot(p);
        int rootQ=getRoot(q);
        if(weightArray[rootP]>weightArray[rootQ]){
            super.union(p, q);
            weightArray[rootP]=weightArray[rootQ]+weightArray[rootP];
        }else{
            super.union(q, p);
            weightArray[rootQ]=weightArray[rootQ]+weightArray[rootP];
        }
    }
}
