import com.google.common.base.Joiner;
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

    Map<Integer, Set<Integer>> wordBidirectedNetgraph = new HashMap<Integer, Set<Integer>>();
    Map<Integer, Set<String>> synset = new HashMap<Integer, Set<String>>();
    Map<Integer, String> def = new HashMap<Integer, String>();
    Map<String, Integer> nouns = new HashMap<String, Integer>();
    Map<Integer, Set<Integer>> ancestorsMap = new HashMap<Integer, Set<Integer>>();

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        buildHypernyms(hypernyms);
        buildSynsets(synsets);
        System.out.println(distance("1820s", "1750s"));
        System.out.println(distance("1750s", "1820s"));
        System.out.println(distance("1790s", "1850s"));
        System.out.println(distance("1850s", "1790s"));
        System.out.println(sap("1850s", "1790s"));
        System.out.println(sap("1850s", "18-karat_gold"));
        System.out.println(sap("1790s", "18-karat_gold"));
        System.out.println(sap("1820s", "1830s"));
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
        Integer nounAid = nouns.get(nounA);
        Integer nounBid = nouns.get(nounB);

        int[] edgeTo = new int[synset.keySet().size()];
        boolean[] marked = new boolean[synset.keySet().size()];

        Set<Integer> dfsQueuePath = new LinkedHashSet<Integer>();
        dfsQueuePath.add(nounAid);
        marked[nounAid] = true;

        int distance = 0;
        while (!dfsQueuePath.isEmpty()) {
            Integer nextToken = dfsQueuePath.iterator().next();
            dfsQueuePath.remove(nextToken);
            if (nextToken.equals(nounBid)) {
                while (true) {
                    distance++;
                    if (edgeTo[nextToken] == nounAid) {
                        break;
                    }
                    nextToken = edgeTo[nextToken];
                }
                break;
            }
            for (Integer child : wordBidirectedNetgraph.get(nextToken)) {
                if (!marked[child]) {
                    dfsQueuePath.add(child);
                    marked[child] = true;
                    edgeTo[child] = nextToken;
                }
            }
        }
        return distance;
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        LinkedHashSet<Integer> ancesorsA = getAncestors(nouns.get(nounA));
        LinkedHashSet<Integer> ancesorsB = getAncestors(nouns.get(nounB));
        for (Integer ancesorA : ancesorsA) {
            for (Integer ancesorB : ancesorsB) {
                if (ancesorA.equals(ancesorB)) {
                    return Joiner.on(" ").join(synset.get(ancesorA));
                }
            }
        }
        return null;
    }

    private void buildSynsets(String synsets) {
        try {
            List<String> synsetsFileToStringList = Files.readLines(new File(synsets), Charset.defaultCharset());
            for (String configStr : synsetsFileToStringList) {
                String[] values = configStr.split(",");
                int fieldId = convertIntToInteger(values[0]);
                Set<String> foundSynset = new HashSet<String>(Arrays.asList(values[1].split(" ")));
                for (String synonym : foundSynset) {
                    nouns.put(synonym, fieldId);
                }
                synset.put(fieldId, foundSynset);
                def.put(fieldId, values[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildHypernyms(String hypernyms) {
        try {
            List<String> hypernymsFileToStringList = Files.readLines(new File(hypernyms), Charset.defaultCharset());
            for (String configStr : hypernymsFileToStringList) {
                String[] values = configStr.split(",");
                int child = convertIntToInteger(values[0]);
                createMapIfNecessary(child, wordBidirectedNetgraph);
                createMapIfNecessary(child, ancestorsMap);
                if (values.length > 1) {
                    for (int i = 1; i < values.length; i++) {
                        int parent = convertIntToInteger(values[i]);
                        createMapIfNecessary(parent, wordBidirectedNetgraph);
                        createMapIfNecessary(parent, ancestorsMap);
                        wordBidirectedNetgraph.get(parent).add(child);
                        wordBidirectedNetgraph.get(child).add(parent);
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
        mainAnscestorsQueue.add(node);
        LinkedHashSet<Integer> checkQueue = new LinkedHashSet<Integer>();
        for (Integer integer : ancestorsMap.get(node)) {
            if (mainAnscestorsQueue.add(integer)) {
                checkQueue.addAll(ancestorsMap.get(integer));
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
