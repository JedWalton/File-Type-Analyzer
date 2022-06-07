package analyzer;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        // initialize files
        File root       = new File(args[0]);
        File patternsDb = new File(args[1]);
        File[] filesToBeChecked = root.listFiles();

        // get patterns
        List<String[]> patterns = new Patterns(patternsDb).getPatterns();

        // create strategy
        SearchStrategy strategy = new RabinKarp();

        // create executor
        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        // fill list of futures
        List<Future<?>> futures = new ArrayList<>();
        for(File file : Objects.requireNonNull(filesToBeChecked)) {
            futures.add(executor.submit(new Worker(file, strategy, patterns)));
        }

        // handle list of futures
        for(var future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}