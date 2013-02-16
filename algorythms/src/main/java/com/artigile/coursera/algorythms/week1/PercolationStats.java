package com.artigile.coursera.algorythms.week1;

import com.google.common.base.Stopwatch;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author IoaN, 2/12/13 8:10 PM
 */
public class PercolationStats {
    private double resultsArray[];
    private int gridSize;
    private int computationTimes;
    private double lastCalculatedMean = -1;
    private double lastCalculatedStdDev = -1;

    public PercolationStats(int N, int T) {
        this.gridSize = N;
        this.computationTimes = T;
        resultsArray = new double[computationTimes];
        Stopwatch stopwatch = new Stopwatch().start();
        for (int i = 0; i < computationTimes; i++) {
            resultsArray[i] = getOpenStep(new Percolation(gridSize));
        }
        System.out.println("Initialization time(milliseconds): " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public double mean() {
        if (lastCalculatedMean != -1) {
            return lastCalculatedMean;
        }
        double sum = 0;
        for (double value : resultsArray) {
            sum += value;
        }
        lastCalculatedMean = sum / computationTimes / gridSize / gridSize;
        return lastCalculatedMean;
    }

    public double stddev() {
        if (lastCalculatedStdDev != -1) {
            return lastCalculatedStdDev;
        }
        double mean = mean();
        double sum = 0;
        for (double value : resultsArray) {
            sum += (value / gridSize / gridSize - mean) * (value / gridSize / gridSize - mean);
        }
        lastCalculatedStdDev = Math.sqrt(sum / (computationTimes - 1));
        return lastCalculatedStdDev;
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(computationTimes);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(computationTimes);
    }

    private int getOpenStep(Percolation percolation) {
        Set<Integer> openedItems = new HashSet<Integer>();
        Random randomInteger = new Random();
        int openedBlocks = 0;
        while (!percolation.percolates()) {
            int nextInteger = randomInteger.nextInt(gridSize * gridSize);
            if (!openedItems.contains(nextInteger)) {
                openedItems.add(nextInteger);
                percolation.open(nextInteger / gridSize, nextInteger % gridSize);
                openedBlocks++;
            }
        }
        return openedBlocks;
    }

    ///======================================================================================================
    ///======================================================================================================
    ///=========================================Static functions=============================================
    ///======================================================================================================
    ///======================================================================================================
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                printCalculations(new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1])));
            } catch (Exception e) {
                System.out.println("Looks like there were wrong arguments passed. Please specify two positive integer values");
            }
        } else if (args.length == 0) {
            System.out.println("");
            printCalculations(new PercolationStats(200, 100));
            printCalculations(new PercolationStats(200, 100));
            printCalculations(new PercolationStats(2, 10000));
            printCalculations(new PercolationStats(2, 100000));
        } else {
            System.out.println("Wrong number of arguments passed. Please pass two integer numbers");
        }
    }


    private static void printCalculations(PercolationStats percolationStats) {
        System.out.println("Starting pre-calculations...");
        System.out.println(padEnd("mean", 40, ' ') + "= " + percolationStats.mean());
        System.out.println(padEnd("stddev", 40, ' ') + "= " + percolationStats.stddev());
        System.out.println(padEnd("95% confidence interval", 40, ' ') + "= " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "\n");
    }

    public static String padEnd(String string, int minLength, char padChar) {
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(string);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

}
