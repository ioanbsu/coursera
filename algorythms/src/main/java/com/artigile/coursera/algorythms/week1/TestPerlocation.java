package com.artigile.coursera.algorythms.week1;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @author IoaN, 2/12/13 8:38 PM
 */
public class TestPerlocation {
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(0,1);
        percolation.open(0,2);
        percolation.open(2,1);
        printGrid(percolation, 3);
        System.out.println(percolation.percolates());

        for (int i = 0; i < 50; i++) {
            runTest();
        }

    }

    private static void runTest() {
        int size = 1000;
        double probability = 0.5;
        Percolation percolation = new Percolation(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (getBoolean(probability)) {
                    percolation.open(i, j);
                }
            }
        }
       // printGrid(percolation, size);
        Stopwatch stopwatch=new Stopwatch().start();
     //   printGrid(percolation, size);
        System.out.println(percolation.percolates() + "");
        System.out.println(stopwatch.elapsed(TimeUnit.MICROSECONDS));
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
