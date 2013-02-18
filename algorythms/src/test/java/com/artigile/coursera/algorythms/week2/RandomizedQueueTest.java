package com.artigile.coursera.algorythms.week2;

import com.artigile.coursera.algorythms.AbstractCourseraTest;
import com.google.common.base.Stopwatch;
import junit.framework.Assert;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * @author IoaN, 2/17/13 7:29 PM
 */
public class RandomizedQueueTest extends AbstractCourseraTest {


    @Test
    public void simpleTest() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        for (int i = 0; i < 10000; i++) {
            queue.enqueue("1");
            queue.enqueue("2");
            queue.enqueue("3");
            queue.enqueue("4");
            queue.dequeue();
            queue.dequeue();
            queue.dequeue();
            queue.dequeue();
        }
    }

    @Test
    public void testRandomAdds() throws Exception {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        for (int RRR = 0; RRR < 1000; RRR++) {

            List<String> array = new ArrayList<String>();
            int size = (int) (Math.random() * 50);
            for (int i = 0; i < size; i++) {
                array.add(i + "");
                queue.enqueue(i + "");
            }
            log(MessageFormat.format("Created. Expected size [{0}], Actual size [{1}]", size, queue.size()));
            log("Printing array");
            for (String arrayElement : queue) {
                log(arrayElement + " ");
                array.remove(arrayElement);
            }
            assertEquals(0, array.size());

            log("\n=== Now Removing===");
            if (!queue.isEmpty()) {
                log(MessageFormat.format("Random number from queue: {0}", queue.sample()));
            }
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

    @Test
    public void simulateRandomCalls() {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int times = 10000;
        queue.enqueue("sdf");
        queue.dequeue();

        for (int i = 0; i < times; i++) {
            if (Math.random() < 0.9) {
                log(i + ": Added");
                queue.enqueue(i + "");
            } else if (!queue.isEmpty()) {
                queue.dequeue();
                log(i + ": Removed");
            }
        }
        Iterator<String> iterator1 = queue.iterator();
        Iterator<String> iterator2 = queue.iterator();

        for (int i = 0; i < queue.size(); i++) {
            System.out.println(MessageFormat.format("{0} {1}", iterator1.next(), iterator2.next()));
        }
    }

    @Test
    public void timingTests() {
        int totalNumberOfTests = 1024;
        for (int j = 0; j < 5; j++) {
            RandomizedQueue<String> queue = new RandomizedQueue<String>();

            System.out.println("Running for size: " + totalNumberOfTests);
            Stopwatch stopwatch = new Stopwatch().start();
            for (int i = 0; i < totalNumberOfTests; i++) {
                queue.enqueue(i + "");
            }
            System.out.println("Enqueue: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.stop();

            System.out.print("Sampling:");
            stopwatch = new Stopwatch().start();
            for (int i = 0; i < totalNumberOfTests; i++) {
                queue.sample();
            }
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.stop();

            System.out.print("Is Empty:");
            stopwatch = new Stopwatch().start();
            for (int i = 0; i < totalNumberOfTests; i++) {
                queue.isEmpty();
            }
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.stop();

            System.out.print("Size:");
            stopwatch = new Stopwatch().start();
            for (int i = 0; i < totalNumberOfTests; i++) {
                queue.size();
            }
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.stop();

            System.out.print("Iterator:");
            stopwatch = new Stopwatch().start();
            Iterator<String> valuesIterator = queue.iterator();
            while (valuesIterator.hasNext()) {
                valuesIterator.next();
            }
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.stop();

            System.out.print("Dequeing:");
            stopwatch = new Stopwatch().start();
            for (int i = 0; i < totalNumberOfTests; i++) {
                queue.dequeue();
            }
            System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.stop();

            System.out.println("======================");
            for (int times = 0; times < 2; times++) {
                totalNumberOfTests *= 2;
            }
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
