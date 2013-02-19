import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @author IoaN, 2/17/13 9:20 PM
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static Random random = new Random(System.currentTimeMillis());
    private Item[] mainArray;
    private int size = 0;

    public RandomizedQueue() {
        mainArray = (Item[]) new Object[1];
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
        final int itemIndexToRemove = random.nextInt(size());
        Item itemToRemove = mainArray[itemIndexToRemove];
        mainArray[itemIndexToRemove] = mainArray[size() - 1];
        mainArray[size() - 1] = null;
        size--;
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
            StdRandom.shuffle(indexesArray);
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

