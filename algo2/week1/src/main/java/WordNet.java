import java.io.File;
import java.util.*;

/**
 * User: ioanbsu
 * Date: 11/4/13
 * Time: 8:26 AM
 */
public class WordNet {

    Digraph digraph;
    Map<Integer, Set<String>> synset = new HashMap<Integer, Set<String>>();
    Map<Integer, String> synPureData = new HashMap<Integer, String>();
    Map<String, Set<Integer>> nouns = new HashMap<String, Set<Integer>>();
    Map<Integer, Set<Integer>> ancestorsMap = new HashMap<Integer, Set<Integer>>();
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        int synSize = buildSynsets(synsets);
        buildHypernyms(hypernyms, synSize);
        sap = new SAP(digraph);
    }

    // for unit testing of this class
    public static void main(String[] args) {
        new WordNet(args[0], args[1]);
    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nouns.keySet().contains(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        int ancestorId = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        if (ancestorId != -1) {
            return synPureData.get(ancestorId);
        }
        return null;
    }

    private int buildSynsets(String synsets) {
        In in = new In(new File(synsets));
        while (!in.isEmpty()) {
            String configStr = in.readLine();
            String[] values = configStr.split(",");
            int fieldId = convertIntToInteger(values[0]);
            String synonyms = values[1];
            synPureData.put(fieldId, synonyms);
            Set<String> foundSynset = new HashSet<String>(Arrays.asList(synonyms.split(" ")));
            for (String synonym : foundSynset) {
                if (!nouns.containsKey(synonym)) {
                    nouns.put(synonym, new HashSet<Integer>());
                }
                nouns.get(synonym).add(fieldId);
            }
            synset.put(fieldId, foundSynset);
        }

        return nouns.size();
    }

    private void buildHypernyms(String hypernyms, int synSize) {
        digraph = new Digraph(synSize);
        In in = new In(new File(hypernyms));
        while (!in.isEmpty()) {
            String configStr = in.readLine();
            String[] values = configStr.split(",");
            int child = convertIntToInteger(values[0]);
            createMapIfNecessary(child, ancestorsMap);
            if (values.length > 1) {
                for (int i = 1; i < values.length; i++) {
                    int parent = convertIntToInteger(values[i]);
                    digraph.addEdge(child, parent);
                    createMapIfNecessary(parent, ancestorsMap);
                    ancestorsMap.get(child).add(parent);
                }
            }
        }

    }

    private LinkedHashSet<Integer> getAncestors(int node) {
        LinkedHashSet<Integer> mainAnscestorsQueue = new LinkedHashSet<Integer>();
        if (ancestorsMap.get(node) == null) {
            return mainAnscestorsQueue;
        }

        mainAnscestorsQueue.add(node);
        LinkedHashSet<Integer> checkQueue = new LinkedHashSet<Integer>();
        for (Integer ancestor1 : ancestorsMap.get(node)) {
            if (mainAnscestorsQueue.add(ancestor1)) {
                checkQueue.addAll(ancestorsMap.get(ancestor1));
            }
        }
        for (Integer queueNode : checkQueue) {
            mainAnscestorsQueue.addAll(getAncestors(queueNode));
        }
        return mainAnscestorsQueue;
    }

    private Integer convertIntToInteger(String value) {
        return Integer.valueOf(value.replace(" ", ""));
    }

    private void createMapIfNecessary(int child, Map<Integer, Set<Integer>> graph) {
        if (!graph.containsKey(child)) {
            graph.put(child, new HashSet<Integer>());
        }
    }


}
