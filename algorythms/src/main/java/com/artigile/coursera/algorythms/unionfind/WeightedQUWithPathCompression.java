package com.artigile.coursera.algorythms.unionfind;

/**
 * @author IoaN, 2/10/13 6:43 PM
 */
public class WeightedQUWithPathCompression extends WeightedQuickUnion {

    public WeightedQUWithPathCompression(int n) {
        super(n);
    }

    public int getRoot(int i) {
        while (i != idArray[i]) {
            idArray[i] = idArray[idArray[i]];
            i = idArray[i];
        }
        return i;
    }
}
