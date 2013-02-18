import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author IoaN, 2/17/13 9:07 PM
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size = 0;

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        checkToAddItem(item);
        Node newNode = new Node();
        newNode.next = first;
        newNode.item = item;
        if (size() != 0) {
            first.prev = newNode;
        }
        first = newNode;
        if (size() == 0) {
            last = first;
        }
        size++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        checkToAddItem(item);
        if (size() == 0) {
            addFirst(item);
        } else {
            Node newNode = new Node();
            newNode.item = item;
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
            size++;
        }
    }

    // delete and return the item at the front
    public Item removeFirst() {
        checkToRemove();
        Item removedItem = first.item;
        first = first.next;
        if (first != null) {
            first.prev.next = null;
            first.prev = null;
        }
        if (isEmpty()) {
            last = null;
        }
        size--;
        return removedItem;
    }

    // delete and return the item at the end
    public Item removeLast() {
        checkToRemove();
        if (size() == 1) {
            return removeFirst();
        } else {
            last = last.prev;
            Item returnValue = last.next.item;
            last.next.prev = null;
            last.next = null;
            size--;
            return returnValue;
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node node = first;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (node != null) {
                    Item itemToReturn = node.item;
                    node = node.next;
                    return itemToReturn;
                }
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void checkToAddItem(Item item) {
        if (item == null) {
            throw new NullPointerException("Null items can not be inserted");
        }
    }

    private void checkToRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty.");
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

}