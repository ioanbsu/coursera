package com.artigile.coursera.algorythms.week2;

/**
 * @author IoaN, 2/17/13 9:02 PM
 */
public class Subset {

    public static void main(String[] args) {
        int subset = 5;
        RandomizedQueue<String> items = new RandomizedQueue<String>();
        items.enqueue("1");
        items.enqueue("2");
        items.enqueue("3");
        items.enqueue("4");
        items.enqueue("5");
        items.enqueue("4");
        items.enqueue("5");
        items.enqueue("6");
        for (int i = 0; i < subset; i++) {
            System.out.println(items.dequeue());
        }
    }
}
