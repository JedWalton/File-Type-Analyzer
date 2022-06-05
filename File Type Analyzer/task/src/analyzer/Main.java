package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        String file = args[0];
        String pattern = args[1];
        String res = args[2];

        Path path = Paths.get(file);
        byte[] data = Files.readAllBytes(path);

        String text = new String(data);
        if (text.contains(pattern)){
            System.out.println(res);
        } else {
            System.out.println("Unknown file type");
        }
    }
}
