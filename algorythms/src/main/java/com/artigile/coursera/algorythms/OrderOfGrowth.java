package com.artigile.coursera.algorythms;

/**
 * User: ioanbsu
 * Date: 2/8/13
 * Time: 10:42 AM
 */
public class OrderOfGrowth {

    public static double[][] q1Array = new double[][]{
            {4096, 0.1},
            {16384, 1.96},
            {65536, 37.60},
            {262144, 725.65},
            {1048576, 13932.43}
    };

    public static double[][] q2Array = new double[][]{
            {4096, 0.16},
            {16384, 3.13},
            {65536, 64.73},
            {262144, 1326.51}
    };
    public static double[][] q3Array = new double[][]{
            {3125, 0.1},
            {15625, 3.65},
            {78125, 132.29},
            {390625, 4666.46}
    };


    public static double[][] q4Array = new double[][]{
            {46656, 0.01},
            {279936, 0.17},
            {1679616, 3.43},
            {10077696, 71.08},
            {60466176, 1474.43}
    };


    public static double[][] q5Array = new double[][]{
            {7776, 0.07},
            {46656, 3.4},
            {279936, 162.01},
            {1679616, 7683.22},
    };


    public static void main(String[] args) {
        calculateOrderOfGrowth(q1Array, 4);
        calculateOrderOfGrowth(q2Array, 4);
        calculateOrderOfGrowth(q3Array, 5);
        calculateOrderOfGrowth(q4Array, 6);
        calculateOrderOfGrowth(q5Array, 6);
    }

    private static void calculateOrderOfGrowth(double[][] arrayOfGrowth, int logBase) {
        int quantity = 0;
        double result = 0;
        for (int i = 1; i < arrayOfGrowth.length; i++) {
            double tmpResult = getLogDistance(arrayOfGrowth[i][1], arrayOfGrowth[i - 1][1], logBase) / getLogDistance(arrayOfGrowth[i][0], arrayOfGrowth[i - 1][0], logBase);
            result += tmpResult;
            System.out.println(tmpResult);
            quantity++;
        }
        System.out.println("========");
        System.out.println(result / quantity);
        System.out.println("=============================================");

    }


    public static double getLogDistance(double var1, double var2, int logBase) {
        return (Math.log(var2) - Math.log(var1)) / Math.log(logBase);
    }

    public static void test(int n) {

        System.out.println("Size: " + n);
        for (int i = 1; i < n; i++) {
            boolean printed = false;
            for (int j = i; j * j <= n; j = j * 2) {
                System.out.println(j);
                printed = true;
            }
            if (printed)
                System.out.println("==");
        }
        System.out.println("===========================================");
    }
}
