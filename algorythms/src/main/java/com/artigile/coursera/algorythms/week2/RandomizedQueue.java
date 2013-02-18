package com.artigile.coursera.algorythms.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @author IoaN, 2/18/13 10:10 AM
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static Random random = new Random(System.currentTimeMillis());
    private Item[] mainArray;
    private int size = 0;

    public RandomizedQueue() {
        mainArray = (Item[]) new Object[1];
    }

    /**
     * Rearrange the elements of an int array in random order.
     */
    private static void shuffle(int[] a) {
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
    private static int uniform(int N) {
        return random.nextInt(N);
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    public void enqueue(Item value) {
        checkToAddItem(value);
        if (size == mainArray.length) {
            resizeArrayCapacity(mainArray.length * 2);
        }
        if (size == 0) {
            mainArray = (Item[]) new Object[1];
        }
        mainArray[size++] = value;
    }

    public Item dequeue() {
        checkIfEmptyAndThrow();
        int itemIndexToRemove = random.nextInt(size());
        Item itemToRemove = removeItemInArray(itemIndexToRemove);
        if (size > 0 && size == mainArray.length / 4) {
            resizeArrayCapacity(mainArray.length / 2);
        }
        return itemToRemove;
    }

    public Item sample() {
        checkIfEmptyAndThrow();
        return mainArray[random.nextInt(size())];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private Item removeItemInArray(int itemIndexToRemove) {
        Item itemToRemove = mainArray[itemIndexToRemove];
        int numMoved = size - itemIndexToRemove - 1;
        if (numMoved > 0)
            System.arraycopy(mainArray, itemIndexToRemove + 1, mainArray, itemIndexToRemove, numMoved);
        mainArray[--size] = null;
        return itemToRemove;
    }

    private void resizeArrayCapacity(int arrayCapacity) {
        Item[] newArray = (Item[]) new Object[arrayCapacity];
        System.arraycopy(mainArray, 0, newArray, 0, size());
        mainArray = newArray;
    }

    private void checkIfEmptyAndThrow() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
    }

    private void checkToAddItem(Item item) {
        if (item == null) {
            throw new NullPointerException("Null items can not be inserted");
        }
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
                return mainArray[indexesArray[currentIndex++]];
            }
            return null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
