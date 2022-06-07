package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Worker extends Thread {

    private final File file;
    private String text;
    private final SearchStrategy strategy;
    private final List<String[]> patterns;

    Worker(File file, SearchStrategy strategy, List<String[]> patterns) {
        this.file = file;
        this.strategy = strategy;
        this.patterns = patterns;
        try {
            Path path = Paths.get(file.getPath());
            text = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String result = String.format("%s: Unknown file type", file.getName());
            for(String[] pattern : patterns) {
                if(strategy.search(text, pattern[1])) {
                    result = String.format("%s: %s", file.getName(), pattern[2]);
                    break;
                }
            }
            System.out.println(result);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}