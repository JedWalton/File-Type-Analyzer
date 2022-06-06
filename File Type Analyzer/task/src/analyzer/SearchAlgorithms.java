package analyzer;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class SearchAlgorithms {

    static void naive(String text, String pattern, String res) {
        Instant startTimeNaive = Instant.now();
        Instant endTimeNaive = Instant.now();

        if (text.contains(pattern)) {
            System.out.println(res);
        } else {
            System.out.println("Unknown file type");
        }

        Duration d = Duration.between(startTimeNaive, endTimeNaive);
        System.out.println("It took " + d.toSeconds() + " seconds");
    }
}
