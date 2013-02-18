package com.artigile.coursera.algorythms.week2;

import junit.framework.Assert;
import org.junit.Test;

import java.text.MessageFormat;

/**
 * @author IoaN, 2/17/13 7:29 PM
 */
public class RandomizedQueueTest {


    @Test
    public void testRandomAdds() throws Exception {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        for (int RRR = 0; RRR < 1000; RRR++) {

            int size = (int) (Math.random() * 50);
            for (int i = 0; i < size; i++) {
                queue.enqueue(i + "");
            }
            System.out.println(MessageFormat.format("Created. Expected size [{0}], Actual size [{1}]", size, queue.size()));
            System.out.println("Printing array");
            for (String arrayElement : queue) {
                System.out.print(arrayElement + " ");
            }
            System.out.println("\n=== Now Removing===");
            if (Math.random() > 0.5) {
                for (int i = 0; i < size; i++) {
                    Assert.assertEquals(size - i, queue.size());
                    System.out.print(queue.dequeue()+" ");
                    Assert.assertEquals(size - i - 1, queue.size());
                }
            } else {
                while (!queue.isEmpty()) {
                    System.out.print(queue.dequeue()+" ");
                }
            }
            Assert.assertEquals(0, queue.size());
            System.out.println(MessageFormat.format("\nData had been randomly removed. Remained size:{0}", queue.size()));


        }
    }

}
