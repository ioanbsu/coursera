/**
 * @author IoaN, 2/17/13 9:20 PM
 */
public class Subset {

    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.print("Please provide size of subset. Pass only one integer parameter.");
            return;
        }
        int subsetSize;
        try {
            subsetSize = Integer.valueOf(args[0]);
        } catch (Exception e) {
            StdOut.print("Provided value for the size of subset is not correct. Please enter integer value.");
            return;
        }
        RandomizedQueue<String> items = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            items.enqueue(StdIn.readString());
        }
        int iterations = Math.min(subsetSize, items.size());
        for (int i = 0; i < iterations; i++) {
            StdOut.println(items.dequeue());
        }
    }
}
