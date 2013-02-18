package com.artigile.coursera.algorythms.week2;

import com.artigile.coursera.algorythms.AbstractCourseraTest;
import junit.framework.Assert;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author IoaN, 2/17/13 7:29 PM
 */
public class RandomizedQueueTest extends AbstractCourseraTest {


    @Test
    public void testRandomAdds() throws Exception {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        for (int RRR = 0; RRR < 1000; RRR++) {

            int size = (int) (Math.random() * 50);
            for (int i = 0; i < size; i++) {
                queue.enqueue(i + "");
            }
            log(MessageFormat.format("Created. Expected size [{0}], Actual size [{1}]", size, queue.size()));
            log("Printing array");
            for (String arrayElement : queue) {
                log(arrayElement + " ");
            }

            log("\n=== Now Removing===");
            log(MessageFormat.format("Random number from queue: {0}", queue.sample()));
            StringBuilder removedElements = new StringBuilder();
            if (Math.random() > 0.5) {
                for (int i = 0; i < size; i++) {
                    Assert.assertEquals(size - i, queue.size());
                    removedElements.append(queue.dequeue()).append(" ");
                    Assert.assertEquals(size - i - 1, queue.size());
                }
            } else {
                while (!queue.isEmpty()) {
                    removedElements.append(queue.dequeue()).append(" ");
                }
            }
            log(removedElements.toString());

            Assert.assertEquals(0, queue.size());
            log(MessageFormat.format("\nData had been randomly removed. Remained size:{0}", queue.size()));
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyQueueRemove() {
        RandomizedQueue<String> deque = new RandomizedQueue<String>();
        assertEquals(0, deque.size());
        assertTrue(deque.isEmpty());
        deque.dequeue();
    }

    @Test(expected = NullPointerException.class)
    public void testAddFirstNullElement() {
        RandomizedQueue<String> deque = new RandomizedQueue<String>();
        deque.enqueue("blablabla");
        deque.enqueue(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveOnIterator() {
        RandomizedQueue<String> deque = new RandomizedQueue<String>();
        deque.iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextWhenDoesNotExists() {
        RandomizedQueue<String> deque = new RandomizedQueue<String>();
        deque.enqueue("blablabla");
        Iterator<String> iterator = deque.iterator();
        iterator.next();
        iterator.next();
    }
}
