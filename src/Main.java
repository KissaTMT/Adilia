import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import models.*;

public class Main {
    public static void main(String[] args) {
        var windowSize = Integer.parseInt(args[0]);
        var topology = new Topology(windowSize);
        var model = new Model(topology);
        if (args.length == 1) {
            learnFromDirectory(model);
        } else {
            var file = args[1];
            learnFromFile(model, file);
        }
        var text = model.generate();
        System.out.println(text);
    }

    private static void learnFromDirectory(Model model) {
        var files = new File("corpus/").listFiles();
        for (var path : files) {
            try (var reader = Files.newBufferedReader(Path.of("corpus/" + path.getName()))) {
                var line = "";
                while ((line = reader.readLine()) != null) {
                    model.learn(Parser.getCorrectCorpus(line));
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
    }
    private static void learnFromFile(Model model,String file) {
        try (var reader = Files.newBufferedReader(Path.of("corpus/" + file))) {
            var line = "";
            while ((line = reader.readLine()) != null) {
                model.learn(Parser.getCorrectCorpus(line));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
