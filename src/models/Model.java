package models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Model {
    public static final String BEGIN = "*BEGIN*";
    public static final String END = "*END*";
    private HashMap<String, Node> selection;
    private Topology topology;

    public HashMap<String, Node> getSelection() {
        return selection;
    }

    public Model(Topology topology) {
        this.topology = topology;
        selection = new HashMap();
    }

    public Model(Topology topology, HashMap<String, Node> selection) {
        this.topology = topology;
        this.selection = selection;
    }

    public StringBuilder generate() {
        var begin = selection.get(BEGIN).nextWord();
        var constructor = new LinkedList<>(Arrays.asList(begin));
        var result = new StringBuilder(begin);
        var node = selection.get(begin);

        while(true) {
            var nextWord = node.nextWord();
            if (nextWord.equals(END)) break;

            constructor.addLast(nextWord);
            node = selection.get(nextWord);
            result.append(" " + constructor.get(constructor.size() - 1));
        }
        return result;
    }

    public void learn(LinkedList<String> corpus) {
        if (corpus.size() > 2) {
            upgradeSelection(corpus);
        }
    }

    private void upgradeSelection(LinkedList<String> corpus) {
        int length = corpus.size();

        for(int i = 0; i < length - 1; ++i) {
            var window = corpus.get(i);
            var nextWindow = corpus.get(i + 1);
            if (window.isEmpty() || window.equals(" ")) continue;
            if (nextWindow.isEmpty()) nextWindow = corpus.get((++i) + 1);

            if (selection.containsKey(window)) {
                var node = selection.get(window);
                if (node.containsWord(nextWindow)) {
                    node.increaseNumber(nextWindow);
                }
                else{
                    node.addWord(nextWindow);
                }
            }
            else {
                selection.put(window, new Node(window, nextWindow));
            }
        }

    }
}
