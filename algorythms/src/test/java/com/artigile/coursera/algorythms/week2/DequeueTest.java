package com.artigile.coursera.algorythms.week2;

import com.artigile.coursera.algorythms.AbstractCourseraTest;
import com.google.common.base.Stopwatch;
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
 * @author IoaN, 2/16/13 9:09 PM
 */
public class DequeueTest extends AbstractCourseraTest {

    @Test
    public void testAllInConsole() throws Exception {
        Deque<String> queue = new Deque<String>();

        List<String> array = new ArrayList<String>();
        for (int RRR = 0; RRR < 1000; RRR++) {

            int size = (int) (Math.random() * 50);
            for (int i = 0; i < size; i++) {
                array.add(i + "");
                if (Math.random() > 0.5) {
                    queue.addFirst("" + i);
                } else {
                    queue.addLast("" + i);
                }
            }
            log(MessageFormat.format("Created. Expected size [{0}], Actual size [{1}]", size, queue.size()));
            log("Printing array");
            for (String arrayElement : queue) {
                array.remove(arrayElement);
                System.out.print(arrayElement + " ");
            }
            assertEquals(0, array.size());
            log("\n=== Now Removing===");
            if (Math.random() > 0.5) {
                for (int i = 0; i < size; i++) {
                    assertEquals(size - i, queue.size());
                    removeRandomly(queue);
                    assertEquals(size - i - 1, queue.size());
                }
            } else {
                while (!queue.isEmpty()) {
                    removeRandomly(queue);
                }
            }
            assertEquals(0, queue.size());
            log(MessageFormat.format("\nData had been randomly removed. Remained size:{0}", queue.size()));


        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyQueueRemoveLast() {
        Deque<String> deque = new Deque<String>();
        assertEquals(0, deque.size());
        assertTrue(deque.isEmpty());
        deque.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyQueueRemoveFirst() {
        Deque<String> deque = new Deque<String>();
        deque.removeFirst();
    }

    @Test(expected = NullPointerException.class)
    public void testAddFirstNullElement() {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("blablabla");
        deque.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void testAddLastNullElement() {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("blablabla");
        deque.addLast(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveOnIterator() {
        Deque<String> deque = new Deque<String>();
        deque.iterator().remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextWhenDoesNotExists() {
        Deque<String> deque = new Deque<String>();
        deque.addFirst("blablabla");
        Iterator<String> iterator = deque.iterator();
        iterator.next();
        iterator.next();
    }

    @Test
    public void testLoitering() {
        Deque<String> deque = new Deque<String>();
        for (int i = 0; i < 100; i++) {
            deque.addFirst("blablabl");
        }
        for (int i = 0; i < 100; i++) {
            deque.removeFirst();

        }
    }

    private void removeRandomly(Deque<String> queue) {
        if (Math.random() > 0.5) {
            System.out.print(queue.removeFirst() + " ");
        } else {
            System.out.print(queue.removeLast() + " ");
        }
    }


}
