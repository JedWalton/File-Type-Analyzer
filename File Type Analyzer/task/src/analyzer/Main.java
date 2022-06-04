package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.print(Files
                .readAllLines(Paths.get(args[0]))
                .stream()
                .anyMatch(s -> s.contains(args[1])) ? args[2] : "Unknown file type");
    }
}
