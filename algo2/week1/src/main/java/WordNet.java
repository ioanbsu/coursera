import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * User: ioanbsu
 * Date: 11/4/13
 * Time: 8:26 AM
 */
public class WordNet {

    Graph graph;
    Map<Integer, Set<String>> synset = new HashMap<Integer, Set<String>>();
    //    Map<Integer, String> glosses = new HashMap<Integer, String>();
    Map<String, Set<Integer>> nouns = new HashMap<String, Set<Integer>>();
    Map<Integer, Set<Integer>> ancestorsMap = new HashMap<Integer, Set<Integer>>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        int synSize = buildSynsets(synsets);
        buildHypernyms(hypernyms, synSize);
        com.google.common.base.Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        System.out.println(distance("George_Bush", "Jack_Kennedy"));
        System.out.println(distance("George_Bush", "Eric_Blair"));
        System.out.println(distance("Minsk", "Homyel"));
        System.out.println(distance("Moscow", "Homyel"));
        System.out.println(distance("Moscow", "Vladimir_Putin"));
        System.out.println(distance("Apostelic_Father", "Vladimir_Putin"));
        System.out.println(stopwatch);
        System.out.println(sap("George_Bush", "Jack_Kennedy"));
        System.out.println(sap("George_Bush", "Eric_Blair"));
        System.out.println(sap("Minsk", "Homyel"));
        System.out.println(sap("Moscow", "Homyel"));
        System.out.println(sap("Moscow", "Vladimir_Putin"));
        System.out.println(sap("Apostelic_Father", "Vladimir_Putin"));
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
        //do bfs here
        Set<Integer> nounAids = nouns.get(nounA);
        Set<Integer> nounBids = nouns.get(nounB);
        if (nounAids.isEmpty() || nounBids.isEmpty()) {
            throw new IllegalArgumentException();
        }

        int minDistance = Integer.MAX_VALUE;
        for (Integer nounBid : nounBids) {
            for (Integer nounAid : nounAids) {
                BreadthFirstPaths breadthFirstDirectedPaths = new BreadthFirstPaths(graph, nounAid);
                int distance = breadthFirstDirectedPaths.distTo(nounBid);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        return minDistance;

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        Set<Integer> nounAids = nouns.get(nounA);
        Set<Integer> nounBids = nouns.get(nounB);

        if (nounAids.isEmpty() || nounBids.isEmpty()) {
            throw new IllegalArgumentException();
        }
        int minDistance = Integer.MAX_VALUE;
        int minNounA = -1;
        int minNounB = -1;
        for (Integer nounBid : nounBids) {
            for (Integer nounAid : nounAids) {
                BreadthFirstPaths breadthFirstDirectedPaths = new BreadthFirstPaths(graph, nounAid);
                int distance = breadthFirstDirectedPaths.distTo(nounBid);
                if (distance < minDistance) {
                    minDistance = distance;
                    minNounA = nounAid;
                    minNounB = nounBid;
                }
            }
        }
        LinkedHashSet<Integer> ancesorsA = getAncestors(minNounA);
        LinkedHashSet<Integer> ancesorsB = getAncestors(minNounB);
        for (Integer ancesorA : ancesorsA) {
            for (Integer ancesorB : ancesorsB) {
                if (ancesorA.equals(ancesorB)) {
                    return Joiner.on(" ").join(synset.get(ancesorA));
                }
            }
        }
        return null;
    }

    private int buildSynsets(String synsets) {
        try {
            List<String> synsetsFileToStringList = Files.readLines(new File(synsets), Charset.defaultCharset());
            for (String configStr : synsetsFileToStringList) {
                String[] values = configStr.split(",");
                int fieldId = convertIntToInteger(values[0]);
                Set<String> foundSynset = new HashSet<String>(Arrays.asList(values[1].split(" ")));
                for (String synonym : foundSynset) {
                    if (!nouns.containsKey(synonym)) {
                        nouns.put(synonym, new HashSet<Integer>());
                    }
                    nouns.get(synonym).add(fieldId);
                }
                synset.put(fieldId, foundSynset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nouns.size();
    }

    private void buildHypernyms(String hypernyms, int synSize) {
        try {
            List<String> hypernymsFileToStringList = Files.readLines(new File(hypernyms), Charset.defaultCharset());
            graph = new Graph(synSize);
            for (String configStr : hypernymsFileToStringList) {
                String[] values = configStr.split(",");
                int child = convertIntToInteger(values[0]);
                createMapIfNecessary(child, ancestorsMap);
                if (values.length > 1) {
                    for (int i = 1; i < values.length; i++) {
                        int parent = convertIntToInteger(values[i]);
                        graph.addEdge(parent, child);
                        createMapIfNecessary(parent, ancestorsMap);
                        ancestorsMap.get(child).add(parent);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
