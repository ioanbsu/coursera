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
        return traverseGraph(a, b, TraversingResultType.LENGHT);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> a, Iterable<Integer> b) {
        return traverseGraph(a, b, TraversingResultType.ANCESTOR);
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


    private int traverseGraph(Iterable<Integer> a, Iterable<Integer> b, TraversingResultType type) {
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
        int bestDistance = Integer.MAX_VALUE;
        int parentNode = -1;

        while (true) {
            boolean counting = false;
            if (!qA.isEmpty()) {
                counting = true;
                int vA = qA.dequeue();
                for (int childA : digraph.adj(vA)) {
                    if (!markedA[childA]) {
                        distToA[childA] = distToA[vA] + 1;
                        markedA[childA] = true;
                        qA.enqueue(childA);
                    }
                    if (markedA[childA] && markedB[childA]) {
                        int distance = distToA[childA] + distToB[childA];
                        if (distance < bestDistance) {
                            bestDistance = distance;
                            parentNode = childA;
                        }
                        if (bestDistance < distToA[childA]) {
                            counting = false;
                        }
                    }
                }
            }
            if (!qB.isEmpty()) {
                counting = true;
                int vB = qB.dequeue();
                for (int childB : digraph.adj(vB)) {
                    if (!markedB[childB]) {
                        distToB[childB] = distToB[vB] + 1;
                        markedB[childB] = true;
                        qB.enqueue(childB);
                    }
                    if (markedA[childB] && markedB[childB]) {
                        int distance = distToA[childB] + distToB[childB];
                        if (distance < bestDistance) {
                            bestDistance = distance;
                            parentNode = childB;
                        }
                        if (bestDistance < distToB[childB]) {
                            counting = false;
                        }
                    }
                }
            }
            if (!counting) {
                break;
            }
        }
        if (type == TraversingResultType.ANCESTOR) {
            return parentNode;
        }
        if (type == TraversingResultType.LENGHT) {
            return bestDistance;
        }
        return -1;
    }

    private enum TraversingResultType {
        LENGHT, ANCESTOR;
    }

}
