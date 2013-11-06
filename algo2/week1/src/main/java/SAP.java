import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * User: ioanbsu
 * Date: 11/4/13
 * Time: 2:02 PM
 */
public class SAP {
    private Digraph digraph;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = new Digraph(G);
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            Stopwatch stopwatch = new Stopwatch();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            StdOut.printf("length = %d, ancestor = %d\n", sap.length(Arrays.asList(v), Arrays.asList(w)), ancestor);
            System.out.println(stopwatch.elapsedTime());
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int a, int b) {
        return length(Arrays.asList(a), Arrays.asList(b));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int a, int b) {
        return ancestor(Arrays.asList(a), Arrays.asList(b));

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> a, Iterable<Integer> b) {
        boolean markedA[] = new boolean[digraph.V()];
        boolean markedB[] = new boolean[digraph.V()];
        int distToA[] = new int[digraph.V()];
        int distToB[] = new int[digraph.V()];

        Queue<Integer> qA = new Queue<Integer>();
        for (Integer nodeA : a) {
            markedA[nodeA] = true;
            distToA[nodeA] = 0;
            qA.enqueue(nodeA);
        }
        Queue<Integer> qB = new Queue<Integer>();
        for (Integer nodeB : b) {
            markedB[nodeB] = true;
            distToB[nodeB] = 0;
            qB.enqueue(nodeB);
            if (markedA[nodeB] && markedB[nodeB]) {
                return 0;
            }
        }
        int length = -1;
        while (true) {
            boolean counting = false;
            if (!qA.isEmpty()) {
                counting = true;
                int vA = qA.dequeue();
                for (int w : digraph.adj(vA)) {
                    if (!markedA[w]) {
                        distToA[w] = distToA[vA] + 1;
                        markedA[w] = true;
                        qA.enqueue(w);
                    }
                    if (markedA[w] && markedB[w]) {
                        length = distToA[w] + distToB[w];
                        break;
                    }
                }
            }
            if (!qB.isEmpty()) {
                counting = true;
                int vB = qB.dequeue();
                for (int w : digraph.adj(vB)) {
                    if (!markedB[w]) {
                        distToB[w] = distToB[vB] + 1;
                        markedB[w] = true;
                        qB.enqueue(w);
                    }
                    if (markedA[w] && markedB[w]) {
                        length = distToA[w] + distToB[w];
                        break;
                    }
                }
            }
            if (!counting || length != -1) {
                break;
            }
        }
        return length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> a, Iterable<Integer> b) {
        boolean markedA[] = new boolean[digraph.V()];
        boolean markedB[] = new boolean[digraph.V()];
        int distToA[] = new int[digraph.V()];
        int distToB[] = new int[digraph.V()];

        Queue<Integer> qA = new Queue<Integer>();
        for (Integer nodeA : a) {
            markedA[nodeA] = true;
            distToA[nodeA] = 0;
            qA.enqueue(nodeA);
        }
        Queue<Integer> qB = new Queue<Integer>();
        for (Integer nodeB : b) {
            markedB[nodeB] = true;
            distToB[nodeB] = 0;
            qB.enqueue(nodeB);
            if (markedA[nodeB] && markedB[nodeB]) {
                return nodeB;
            }
        }
        int parentNode = -1;
        while (true) {
            boolean counting = false;
            if (!qA.isEmpty()) {
                counting = true;
                int vA = qA.dequeue();
                for (int w : digraph.adj(vA)) {
                    if (!markedA[w]) {
                        distToA[w] = distToA[vA] + 1;
                        markedA[w] = true;
                        qA.enqueue(w);
                    }
                    if (markedA[w] && markedB[w]) {
                        parentNode = w;
                        break;
                    }
                }
            }
            if (!qB.isEmpty()) {
                counting = true;
                int vB = qB.dequeue();
                for (int w : digraph.adj(vB)) {
                    if (!markedB[w]) {
                        distToB[w] = distToB[vB] + 1;
                        markedB[w] = true;
                        qB.enqueue(w);
                    }
                    if (markedA[w] && markedB[w]) {
                        parentNode = w;
                        break;
                    }
                }
            }
            if (!counting || parentNode != -1) {
                break;
            }
        }
        return parentNode;
    }

    private LinkedHashSet<Integer> getAncestors(int node) {
        LinkedHashSet<Integer> mainAnscestorsQueue = new LinkedHashSet<Integer>();
        mainAnscestorsQueue.add(node);
        LinkedHashSet<Integer> checkQueue = new LinkedHashSet<Integer>();
        for (Integer ancestor1 : digraph.adj(node)) {
            if (mainAnscestorsQueue.add(ancestor1)) {
                for (Integer ancestor2 : digraph.adj(ancestor1)) {
                    checkQueue.add(ancestor2);
                }
            }
        }
        for (Integer queueNode : checkQueue) {
            mainAnscestorsQueue.addAll(getAncestors(queueNode));
        }
        return mainAnscestorsQueue;
    }


}
