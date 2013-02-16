package com.artigile.coursera.algorythms.week2.stackandqueue;

/**
 * @author IoaN, 2/15/13 8:13 PM
 */
public interface Queue<T> {

    boolean isEmpty();

    T dequeue();

    void enqueue(T object);
}
