package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Patterns {

    private final List<String[]> patterns = new ArrayList<>();

    Patterns(File patternsDb) {
        try {
            List<String> lines = Files.readAllLines(patternsDb.toPath());
            for(String pattern : lines) {
                String[] patternArr = pattern.split(";");
                patternArr[1] = patternArr[1].replaceAll("\"", "");
                patternArr[2] = patternArr[2].replaceAll("\"", "");
                patterns.add(patternArr);
            }
            patterns.sort(new Comparator<String[]>() {
                public int compare(String[] first, String[] second) {
                    return second[0].compareTo(first[0]);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<String[]> getPatterns() {
        return patterns;
    }
}