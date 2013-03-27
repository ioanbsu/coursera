package com.artigile.coursera.algorythms.week3.sort;

/**
 * @author IoaN, 2/23/13 1:34 PM
 */
public abstract class AbstractSort implements Sort {

    protected static Comparable[] stringToArray(String str) {
        String[] values = str.split(" ");
        Integer[] intValues = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            intValues[i] = Integer.valueOf(values[i]);
        }
        return intValues;
    }

    public boolean isSorted(Comparable[] array) {
        return isSorted(array, 0,array.length);
    }
    public boolean isSorted(Comparable[] array, int beginning, int ending) {
        for (int i = beginning; i < ending - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    public void printArray(Comparable[] array) {
        for (Comparable comparable : array) {
            System.out.print(comparable + " ");
        }
        System.out.println("");
    }



    protected void exch(Comparable[] array, int compareInd1, int compareInd2) {
        Comparable el = array[compareInd1];
        array[compareInd1] = array[compareInd2];
        array[compareInd2] = el;
    }


    public static String[] array1=new String[]{"lazy", "heap", "data", "hash", "leaf", "exch", "rank", "edge", "time",
            "lifo", "cost", "miss", "swap", "type","null", "flip", "path", "sink", "root", "tree", "java", "swim", "link", "node"};

    public static String[] array2=new String[]{"cost", "data", "edge", "exch", "hash", "heap", "lazy", "leaf", "lifo",
            "miss", "rank", "time", "flip", "null", "path", "sink", "swap", "type", "java", "link", "node", "root", "swim", "tree"};

    public static String[] array3=new String[]{"cost", "data", "edge", "exch", "flip", "hash", "heap", "lazy", "leaf",
            "lifo", "miss", "null", "rank", "swap", "time", "type", "path", "sink", "root", "tree", "java", "swim", "link", "node"};

    public static String[] array4=new String[]{"type", "tree", "swap", "time", "swim", "node", "rank", "path", "sink",
            "lifo", "link", "miss", "exch", "data", "null", "flip", "edge", "hash", "root", "leaf", "java", "cost", "heap", "lazy"};

    public static String[] array5=new String[]{ "heap", "data", "hash", "java", "exch", "flip", "edge", "cost", "lazy",
            "lifo", "miss", "swap", "type", "null", "time", "path", "sink", "root", "tree", "rank", "swim", "link", "node", "leaf"};

    public static String[] array6=new String[]{"java", "exch", "cost", "edge", "lazy", "heap", "data", "flip", "leaf",
            "lifo", "link", "hash", "path", "sink", "null", "miss", "swap", "time", "rank", "node", "swim", "type", "root", "tree"};

    public static String[] array7=new String[]{"leaf", "rank", "miss", "hash", "data", "edge", "lazy", "lifo", "time",
            "exch", "cost", "heap", "swap", "type", "null", "flip", "path", "sink", "root", "tree", "java", "swim", "link", "node"};

    public static String[] array8=new String[]{"data", "edge", "exch", "hash", "heap", "lazy", "leaf", "rank", "cost",
            "flip", "lifo", "miss", "null", "swap", "time", "type", "java", "link", "node", "path", "root", "sink", "swim", "tree"};

    public static String[] array9=new String[]{ "cost", "data", "edge", "exch", "flip", "hash", "heap", "java", "lazy",
            "leaf", "lifo", "link", "swap", "type", "null", "time", "path", "sink", "root", "tree", "rank", "swim", "miss", "node"};

    public static String[] array10=new String[]{"cost", "heap", "data", "hash", "java", "exch", "flip", "edge", "lazy",
            "lifo", "time", "miss", "swap", "type", "null", "rank", "path", "sink", "root", "tree", "leaf", "swim", "link", "node"};

    public static String[] array11=new String[]{"cost", "data", "edge", "exch", "flip", "hash", "heap", "java", "lazy",
            "leaf", "lifo", "link", "miss", "node", "null", "path", "rank", "root", "sink", "swap", "swim", "time", "tree", "type"};
    public static String[][] arrays=new String[][]{
            array1,
            array2,
            array3,
            array4,
            array5,
            array6,
            array7,
            array8,
            array9,
            array10,
            array11
    };

}
