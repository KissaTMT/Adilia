import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import models.*;

public class Main {
    public static void main(String[] args) {
        int windowSize = 1;
        Topology topology = new Topology(windowSize);
        Model model = new Model(topology);
        String file = "tmp.txt";
        learnFromFile(model, "corpus/" + file);
        StringBuilder text = model.generate();
        System.out.println(text);
    }

    private static void learnFromFile(Model model, String path) {
        try(var reader = Files.newBufferedReader(Path.of(path))) {
            var line = "";
            while((line = reader.readLine())!=null){
                model.learn(Parser.getCorrectCorpus(line));
            }
        }
        catch (IOException x){
            System.err.format("IOException: %s%n",x);
        }
    }
}
