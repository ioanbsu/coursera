package com.artigile.coursera.algorythms.week2.stackandqueue;

/**
 * @author IoaN, 2/15/13 8:14 PM
 */
public class LinkedQueueOfStrings<T> implements Queue<String> {

    private Node first;
    private Node last;

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public String dequeue() {
        String valueToReturn = first.value;
        first = first.next;
        if (isEmpty()) {
            last = null;
        }
        return valueToReturn;
    }

    @Override
    public void enqueue(String object) {
        Node newItem = new Node();
        newItem.value = object;
        last.next = newItem;
        if (isEmpty()) {
            first = newItem;
        } else {
            last = newItem;
        }
    }

    private class Node {
        String value;
        Node next;
    }
}
