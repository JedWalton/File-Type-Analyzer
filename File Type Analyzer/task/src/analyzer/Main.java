package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    static List<String[]> patterns = new ArrayList<>();

    public static boolean kmp(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        if(m > n) {
            return false;
        }

        // prefix function for pattern
        int[] pref = new int[m];
        for(int i = 1; i < m; i++) {
            int j = pref[i - 1];
            while(pattern.charAt(i) != pattern.charAt(j) && j != 0) {
                j = pref[j - 1];
            }
            if(pattern.charAt(i) == pattern.charAt(j)) {
                pref[i] = j + 1;
            }
        }

        // check if occurs
        boolean occurs = false;
        for(int i = 0, j = 0; i < n; i++) {
            while(text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if(i == n || j == m) break;
            }
            if(j == m) {
                occurs = true;
                break;
            }

            if(j != 0) {
                j = pref[j - 1];
                i--;
            }
        }

        return occurs;
    }

    static class Worker extends Thread {
        File file;
        Path path;
        String text;

        Worker(File file) {
            this.file = file;
            try {
                path = Paths.get(file.getPath());
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
                    if(kmp(text, pattern[1])) {
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


    public static void main(String[] args) {
        File root = new File(args[0]);
        File patternsDb = new File(args[1]);

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

//            for(var pattern : patterns) {
//                System.out.println(pattern[0] + " "
//                + pattern[1] + " " + pattern[2]);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File[] files = root.listFiles();

        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        List<Future<?>> futures = new ArrayList<>();

        for(File file : Objects.requireNonNull(files)) {
            futures.add(executor.submit(new Worker(file)));
        }

        for(var future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}