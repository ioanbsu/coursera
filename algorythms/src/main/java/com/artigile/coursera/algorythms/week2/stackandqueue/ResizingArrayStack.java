package com.artigile.coursera.algorythms.week2.stackandqueue;

/**
 * @author IoaN, 2/15/13 7:57 PM
 */
public class ResizingArrayStack<T> implements Stack<T> {

    protected T[] s;
    int N = 0;

    public ResizingArrayStack() {
        s = (T[]) new Object[1];
    }

    public void push(T value) {
        if (N == s.length) {
            resizeArrayCapacity(s.length * 2);
        }
        s[N++] = value;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    public T pop() {
        T valueToReturn = s[--N];
        s[N] = null;
        if (N > 0 && N == s.length / 4) {
            resizeArrayCapacity(s.length / 2);
        }
        return valueToReturn;
    }

    private void resizeArrayCapacity(int arrayCapacity) {
        T[] newArray = (T[]) new Object[arrayCapacity];
        for (int i=0;i<Math.min(s.length,arrayCapacity);i++) {
            newArray[i]=s[i];
        }
        s = newArray;
    }

}
