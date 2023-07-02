package models;

import java.io.IOException;
import java.util.*;

public class Model{
    public static final String BEGIN = "*BEGIN*";
    public static final String END = "*END*";
    private HashMap<String, Node> selection;
    private Topology topology;

    public HashMap<String, Node> getSelection() {
        return selection;
    }
    public Topology getTopology() {return topology; }

    public Model(Topology topology){
        this.topology = topology;
        selection = new HashMap<>();
    }

    public Model(Topology topology, HashMap<String, Node> selection) {
        this.topology = topology;
        this.selection = selection;
    }
    public String generate(){
        return topology.getWindowSize()>1?generateHigher():generateSingle();
    }
    private String generateSingle(){
        var begin = getRandomBeginKey(1);
        var node = selection.get(begin);
        var window = node.getNext();
        var windows = new LinkedList<>(Arrays.asList(window));
        var result = new StringBuilder(window);

        while (true){
            if(node == null) break;

            var next = node.getNext();
            if(next.equals(END)) break;
            windows.add(next);
            result.append(" " + windows.getLast());
            node = selection.get(next);
        }

        return result.toString();
    }
    private String generateHigher(){
        var begin = generateBeginWindow();
        var result = new StringBuilder(begin);
        var windows = new LinkedList<>(Arrays.asList(begin.split(" ")));
        var node = selection.get(begin);

        while (true){
            if(node == null) break;

            var next = node.getNext();
            if (next.equals(END)) {
                break;
            }
            windows.addLast(next);
            result.append(" " + next);
            var key = generateNextKey(windows);
            node = selection.get(key);
        }

        return result.toString();
    }
    private String generateNextKey(LinkedList<String> windows){
        var last = windows.size();
        var first = last - topology.getWindowSize();
        var key = new StringBuilder(windows.get(first));
        for(var i = first+1;i<last;i++){
            key.append(" " + windows.get(i));
        }
        return key.toString();
    }
    private String generateBeginWindow(){
        var begin = selection.get(getRandomBeginKey(topology.getWindowSize()));
        var valuePath = begin.getWindow().substring(BEGIN.length()+1);
        var result = new StringBuilder(valuePath);
        result.append(" " + begin.getNext());
        return result.toString();
    }
    public void learn(LinkedList<String> corpus) throws IOException {
        if (corpus.size() > 2) {
            upgradeSelection(corpus);
        }
    }
    private String getRandomBeginKey(int windowSize){
        if(windowSize == 1) return BEGIN;
        else{
            var keys = new ArrayList<String>();
            for(var k : selection.keySet()){
                if(k.contains(BEGIN)) keys.add(k);
            }
            return keys.get(new Random().nextInt(keys.size()));
        }
    }

    private void upgradeSelection(LinkedList<String> corpus) {
        int length = corpus.size();

        for(int i = 0; i < length - topology.getWindowSize(); i++) {
            var window = corpus.get(i);

            for(var j = i + 1; j< i+topology.getWindowSize();j++){
                window += " " + corpus.get(j);
            }
            var nextWindow = corpus.get(i + topology.getWindowSize());

            if (selection.containsKey(window)) {
                var node = selection.get(window);
                if (node.containsWindow(nextWindow)) {
                    node.increaseNumber(nextWindow);
                }
                else{
                    node.addWindow(nextWindow);
                }
            }
            else {
                selection.put(window, new Node(window, nextWindow));
            }
        }

    }
}
