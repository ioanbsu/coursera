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
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        LinkedHashSet<Integer> ancesorsA = getAncestors(v);
        LinkedHashSet<Integer> ancesorsB = getAncestors(w);
        int ancACounter = 0;
        int ansBCounter = 0;
        for (Integer ancesorA : ancesorsA) {
            ansBCounter = 0;
            for (Integer ancesorB : ancesorsB) {
                if (ancesorA.equals(ancesorB)) {
                    return ancACounter + ansBCounter;
                }
                ansBCounter++;
            }
            ancACounter++;
        }
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        LinkedHashSet<Integer> ancesorsA = getAncestors(v);
        LinkedHashSet<Integer> ancesorsB = getAncestors(w);
        for (Integer ancesorA : ancesorsA) {
            for (Integer ancesorB : ancesorsB) {
                if (ancesorA.equals(ancesorB)) {
                    return ancesorB;
                }
            }
        }
        return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths breadthFirstDirectedPaths = new BreadthFirstDirectedPaths(digraph, v);
        int length = Integer.MAX_VALUE;
        for (Integer node : w) {
            if (breadthFirstDirectedPaths.hasPathTo(node)) {
                int newLength = breadthFirstDirectedPaths.distTo(node);
                if (newLength < length) {
                    length = newLength;
                }
            }
        }
        if (length == Integer.MAX_VALUE) {
            return -1;
        }
        return length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return -1;
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
