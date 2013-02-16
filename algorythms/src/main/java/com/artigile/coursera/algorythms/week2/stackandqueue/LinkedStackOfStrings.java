package com.artigile.coursera.algorythms.week2.stackandqueue;

/**
 * @author IoaN, 2/15/13 7:18 PM
 */
public class LinkedStackOfStrings {

    private Node first;

    public boolean isEmpty() {
        return first == null;
    }

    public String pop() {
        String valueToReturn = first.item;
        first = first.next;
        return valueToReturn;
    }

    public void push(String value) {
        Node newItem = new Node();
        newItem.item = value;
        newItem.next = first;
        first = newItem;
    }

    private class Node {
        String item;
        Node next;
    }

}
