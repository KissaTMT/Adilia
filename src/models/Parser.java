package models;

import java.util.LinkedList;

public class Parser {
    public static LinkedList<String> getCorrectCorpus(String text) {
        var regex = "_xyi_|,|\\.|\\?|!|\n|\\ufeff|:|-|;|—|~|\"|'";
        var strings = text.replaceAll("\\s", "_xyi_").split(regex);
        var corpus = new LinkedList<String>();

        corpus.add(Model.BEGIN);

        for(int i = 0;i < strings.length; ++i) {
            var window = strings[i];
            if (window.equals("_xyi_") || window.isEmpty() || window == null) continue;
            corpus.add(window);
        }
        corpus.add(Model.END);
        return corpus;
    }
}
