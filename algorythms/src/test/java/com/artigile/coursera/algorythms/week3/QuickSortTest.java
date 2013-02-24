package com.artigile.coursera.algorythms.week3;

import com.artigile.coursera.algorythms.week3.sort.QuickSort;
import com.artigile.coursera.algorythms.week3.sort.Sort;
import com.artigile.coursera.algorythms.week3.sort.ThreeWayQuickSort;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author IoaN, 2/23/13 4:34 PM
 */
public class QuickSortTest {

    @Test
    public void testQuickSort(){
        Sort quickSort=new QuickSort();
        testSort(quickSort);
    }
    @Test
    public void testThreeWayQuickSort(){
        Sort quickSort = new ThreeWayQuickSort();
        testSort(quickSort);
    }




    private void testSort(Sort quickSort) {
        for(int i=0;i<10000;i++){
            Double[] array=new Double[(int) (Math.random()*1000)];
            for(int k=0;k<array.length;k++){
                array[k]=Math.random();
            }
            quickSort.sort(array);
            Assert.assertTrue(quickSort.isSorted(array));
        }
    }
}
