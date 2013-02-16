package com.artigile.coursera.algorythms.week2.stackandqueue;

/**
 * @author IoaN, 2/15/13 7:33 PM
 */
public class FixedCapacityStack<T> implements Stack<T>{

    protected T[] s;
    private int N = 0;

    public FixedCapacityStack(int capacity) {
        s = (T[]) new Object[capacity];
    }

    public void push(T element) {
        s[N++] = element;
    }

    public T pop() {
        T returnValue=s[--N];
        s[N]=null;
        return returnValue;
    }

    public boolean isEmpty() {
        return N == 0;
    }
}
