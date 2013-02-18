package com.artigile.coursera.algorythms.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @author IoaN, 2/16/13 8:29 PM
 */
public class RandomizedQueueLinked<Item> implements Iterable<Item> {
    private static Random random = new Random(System.currentTimeMillis());
    private Node first;
    private Node last;
    private int size = 0;


    // construct an empty randomized queue
    public RandomizedQueueLinked() {

    }


    // is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        checkToAddItem(item);
        Node newNode = new Node();
        newNode.next = first;
        newNode.item = item;
        if (size() > 0) {
            first.prev = newNode;
        }
        first = newNode;
        size++;
        if (size() == 1) {
            last = first;
        }
    }

    // delete and return a random item
    public Item dequeue() {
//        System.out.println("------------------------------------------------");
//        Stopwatch measure=new Stopwatch().start();
        checkIfEmptyAndThrow();
        if (size() == 1) {
            size--;
            first.next = null;
            first.prev = null;
            last.prev = null;
            last.next = null;
            Item itemToReturn = first.item;
            last = null;
            first = null;
            return itemToReturn;
        }
//        System.out.println("First: "+measure.elapsed(TimeUnit.MICROSECONDS));
//        measure.stop();
//        measure=new Stopwatch().start();
        int nodeToRemoveIndex = random.nextInt(size());
//        System.out.println("Generating random number: "+measure.elapsed(TimeUnit.MICROSECONDS));
//        measure.stop();
//        measure=new Stopwatch().start();
        Node nodeToRemove = getNode(nodeToRemoveIndex);
//        System.out.println("Getting node: "+measure.elapsed(TimeUnit.MICROSECONDS));
//        measure.stop();
//        measure=new Stopwatch().start();
        if (nodeToRemove.next != null && nodeToRemove.prev != null) {
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
        } else if (nodeToRemove.next == null) {
            nodeToRemove.prev.next = null;
            last = nodeToRemove.prev;
        } else {
            first = nodeToRemove.next;
            first.prev = null;
        }
        nodeToRemove.next = null;
        nodeToRemove.prev = null;
        size--;
//        System.out.println("RemoveFinished: "+measure.elapsed(TimeUnit.MICROSECONDS));
//        System.out.println("------------------------------------------------");
//        measure.stop();
        return nodeToRemove.item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        checkIfEmptyAndThrow();
        return getNode(random.nextInt(size())).item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private void checkToAddItem(Item item) {
        if (item == null) {
            throw new NullPointerException("Null items can not be inserted");
        }
    }

    private void checkIfEmptyAndThrow() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
    }

    private Node getNode(int number) {
        Node foundNode;
        if (number <= size() / 2) {
            foundNode = first;
            for (int i = 0; i < number; i++) {
                foundNode = foundNode.next;
            }
        } else {
            foundNode = last;
            for (int i = size() - 1; i > number; i--) {
                foundNode = foundNode.prev;
            }
        }
        return foundNode;
    }

    private class RandomIterator implements Iterator<Item> {

        private int[] indexesArray;
        private int currentIndex = 0;

        private RandomIterator() {
            indexesArray = new int[size()];
            for (int i = 0; i < indexesArray.length; i++) {
                indexesArray[i] = i;
            }
            shuffle(indexesArray);
        }

        @Override
        public boolean hasNext() {
            return currentIndex < indexesArray.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (!isEmpty()) {
                return getNode(indexesArray[currentIndex++]).item;
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }



    /**
     * Rearrange the elements of an int array in random order.
     */
    public static void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N - i);     // between i and N-1
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * Return an integer uniformly between 0 (inclusive) and N (exclusive).
     */
    public static int uniform(int N) {
        return random.nextInt(N);
    }

}
