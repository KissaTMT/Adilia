
package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Node {
    private String window;
    private String nextWindow;
    private HashMap<String, Double> numbers;

    public String getWindow() {
        return window;
    }

    public boolean containsWindow(String window) {
        return numbers.containsKey(window);
    }

    public HashMap<String, Double> getNumbers() {
        return numbers;
    }

    public Node(String window, String nextWindow) {
        this.window = window;
        numbers = new HashMap();
        this.nextWindow = nextWindow;
        numbers.put(nextWindow, 1.0);
    }

    public String getNext() {
        if(numbers.size()==1) return nextWindow;
        var windowsRandom = new ArrayList<String>();
        var windowWithMaxWeight = "";
        var maxWeight = Double.MIN_VALUE;

        for(var item : numbers.keySet()) {
            if (getWeight(numbers.get(item)) > new Random().nextDouble()) {
                windowsRandom.add(item);
            }
            if (numbers.get(item) > maxWeight) {
                maxWeight = numbers.get(item);
                windowWithMaxWeight = item;
            }
        }

        return windowsRandom.size() == 0 ? windowWithMaxWeight : windowsRandom.get(new Random().nextInt(windowsRandom.size()));
    }

    public void addWindow(String word) {numbers.put(word, 1.0);}

    public void increaseNumber(String window) {
        var number = numbers.get(window);
        numbers.put(window, number + 1.0);
    }

    public String toString() {
        return window;
    }

    private Double getWeight(Double number) {
        return number / numbers.values().size();
    }
}
