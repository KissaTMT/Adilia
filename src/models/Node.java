//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Node {
    private String word;
    private HashMap<String, Double> numbers;

    public String getWord() {
        return this.word;
    }

    public boolean containsWord(String word) {
        return this.numbers.containsKey(word);
    }

    public HashMap<String, Double> getNumbers() {
        return this.numbers;
    }

    public Node(String word) {
        this.word = word;
        numbers = new HashMap();
    }

    public Node(String word, String nextWord) {
        this.word = word;
        numbers = new HashMap();
        numbers.put(nextWord, 1.0);
    }

    public String nextWord() {
        var wordsRandom = new ArrayList<String>();
        var wordWithMaxWeight = "";
        var maxWeight = Double.MIN_VALUE;
        var iterator = numbers.keySet().iterator();

        while(iterator.hasNext()) {
            var i = (String)iterator.next();
            if (this.getWeight(numbers.get(i)) > (new Random()).nextDouble()) {
                wordsRandom.add(i);
                break;
            }

            if (numbers.get(i) > maxWeight) {
                maxWeight = numbers.get(i);
                wordWithMaxWeight = i;
            }
        }

        return wordsRandom.size() == 0 ? wordWithMaxWeight : wordsRandom.get(new Random().nextInt(wordsRandom.size()));
    }

    public void addWord(String word) {
        this.numbers.put(word, 1.0);
    }

    public void increaseNumber(String word) {
        var number = numbers.get(word);
        numbers.put(word, number + 1.0);
    }

    public String toString() {
        return word;
    }

    private Double getWeight(Double number) {
        return number / numbers.values().size();
    }
}
