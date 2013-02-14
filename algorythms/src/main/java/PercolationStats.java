/**
 * Run this application as specified in specification for this assignment, for example:
 * java PercolationStats 200 100
 * Make sure you specify the classpath for algs4.jar and strlib.jar correctly.
 * User: ioanbsu
 * Date: 2/13/13
 * Time: 2:16 PM
 */
public class PercolationStats {

    private double resultsArray[];
    private int gridSize;
    private int computationTimes;
    private double lastCalculatedMean = -1;
    private double lastCalculatedStdDev = -1;

    private double magicNumber = 1.96; //not too much explanation was given in lectures about what this number is about...

    /**
     * Constructor runs T times percolation tests and stores the results in resultsArray.
     *
     * @param N grid size
     * @param T number of tests.
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("The size of array and quantity of tests should be positive integer numbers.");
        }
        this.gridSize = N;
        this.computationTimes = T;
        resultsArray = new double[computationTimes];
        //   Stopwatch stopwatch = new Stopwatch();
        for (int i = 0; i < computationTimes; i++) {
            resultsArray[i] = ((double) getOpenStep(new Percolation(gridSize))) / gridSize / gridSize;
        }
        //  System.out.println("Initialization in(seconds): " + stopwatch.elapsedTime());
    }

    /**
     * Calculates mean for the results array. Cached last found mean value.
     *
     * @return mean value.
     */
    public double mean() {
        if (lastCalculatedMean != -1) {
            return lastCalculatedMean;
        }
        lastCalculatedMean = StdStats.mean(resultsArray);
        return lastCalculatedMean;
    }

    /**
     * sample standard deviation of percolation threshold. Caches last calculated value.
     *
     * @return standard deviation.
     */
    public double stddev() {
        if (lastCalculatedStdDev != -1) {
            return lastCalculatedStdDev;
        }
        lastCalculatedStdDev = StdStats.stddev(resultsArray);
        return lastCalculatedStdDev;
    }

    /**
     * @return lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - magicNumber * stddev() / Math.sqrt(computationTimes);
    }

    /**
     * @return higher bound of the 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + magicNumber * stddev() / Math.sqrt(computationTimes);
    }


    /**
     * Performs random cells enabling in the grid until it percolates.
     *
     * @param percolation the percolation grid instance.
     * @return step number at what the grid was percolated.
     */
    private int getOpenStep(Percolation percolation) {
        int[] valuesArray = new int[gridSize * gridSize];
        int arrayStartValue = 0;
        for (int i = 0; i < gridSize * gridSize; i++) {
            valuesArray[i] = arrayStartValue++;
        }
        StdRandom.shuffle(valuesArray);
        int openedBlocks = 0;
        for (int value : valuesArray) {
            percolation.open(value / gridSize + 1, value % gridSize + 1);
            openedBlocks++;
            if (percolation.percolates()) {
                break;
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
            printCalculations(new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1])));
        } else if (args.length == 0) {
            System.out.println("No input parameters passed, executing default set testings.");
            printCalculations(new PercolationStats(200, 100));
            printCalculations(new PercolationStats(200, 100));
            printCalculations(new PercolationStats(2, 10000));
            printCalculations(new PercolationStats(2, 100000));
        } else {
            System.out.println("Wrong number of arguments passed. Please pass two integer numbers");
        }
    }

    private static void printCalculations(PercolationStats percolationStats) {
        //   System.out.println("Starting calculations...");
        //   Stopwatch stopwatch = new Stopwatch();
        System.out.println(padEnd("\nmean", 40, ' ') + "= " + percolationStats.mean());
        System.out.println(padEnd("stddev", 40, ' ') + "= " + percolationStats.stddev());
        System.out.println(padEnd("95% confidence interval", 40, ' ') + "= " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());
        // System.out.println(padEnd("Calculations time(in seconds): ", 40, ' ') + stopwatch.elapsedTime() + "\n");
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
