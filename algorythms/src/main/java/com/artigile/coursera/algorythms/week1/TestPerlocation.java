package com.artigile.coursera.algorythms.week1;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @author IoaN, 2/12/13 8:38 PM
 */
public class TestPerlocation {
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            runTest();
        }

    }

    private static void runTest() {
        int size = 10000;
        double probability = 0.5;
        Percolation percolation = new Percolation(size);
        Stopwatch stopwatch=new Stopwatch().start();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getBoolean(probability)) {
                    percolation.open(i, j);
                }
            }
        }
        System.out.println("To build the array(milliseconds): "+ stopwatch.elapsed(TimeUnit.SECONDS));
     //   printGrid(percolation, size);
        stopwatch.stop();
        stopwatch=new Stopwatch().start();
        System.out.println(percolation.percolates() + "");
        System.out.println("To check if there is path(microseconds):"+stopwatch.elapsed(TimeUnit.MICROSECONDS));
        stopwatch.stop();
    }

    public static boolean getBoolean(double probability) {
        return Math.random() < probability;
    }

    public static void printGrid(Percolation percolation, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (percolation.isOpen(i, j)) {
                    System.out.print("[ ]");
                } else {
                    System.out.print("[X]");
                }
            }
            System.out.println("");
        }
    }
}
