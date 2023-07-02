package models;

import java.util.LinkedList;

public class Parser {
    private static String regex =  "_xyi_|,|\\.|\\?|!|\n|\\ufeff|:|-|;|—|~|\"|'|\\)|\\(";

    public static LinkedList<String> getCorrectCorpus(String text){
        return createCorrectCorpus(text);
    }
    private static LinkedList<String> createCorrectCorpus(String text) {
        var strings = text.replaceAll("\\s", "_xyi_").toLowerCase().split(regex);
        var corpus = new LinkedList<String>();

        corpus.addFirst(Model.BEGIN);
        for(int i = 0;i < strings.length; ++i) {
            var window = strings[i];
            if (window.equals("_xyi_") || window.isEmpty() || window == null) continue;
            corpus.add(window);
        }
        corpus.addLast(Model.END);
        return corpus;
    }
    private static LinkedList<String> createCorrectCorpus(String text, int windowSize) {
        var singleWindowSizeCorpus = createCorrectCorpus(text);

        var corpus = new LinkedList<String>();

        for(var i = 0; i<singleWindowSizeCorpus.size()-windowSize+1;i++){
            var window = new StringBuilder(singleWindowSizeCorpus.get(i));
            for(var j = i+1;j<i+windowSize;j++){
                window.append(" " + singleWindowSizeCorpus.get(j));
            }
            corpus.add(window.toString());
        }

        return corpus;
    }
}
