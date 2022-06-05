package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws IOException {
        String flag = args[0];
        String file = args[1];
        String pattern = args[2];
        String res = args[3];

        Path path = Paths.get(file);
        byte[] data = Files.readAllBytes(path);

        String text = new String(data);
        if (flag.equals("--naive")) {
            naive(text, pattern, res);
        } else if (flag.equals("--KMP")) {
            KNP(text, pattern, res);
        }
    }

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

    static void KNP(String text, String pattern, String res) {
        Instant startTimeKMP = Instant.now();
        Instant endTimeKMP = Instant.now();

        if (matchesKNP(pattern, text)) {
            System.out.println(res);
        } else {
            System.out.println("Unknown file type");
        }

        System.out.println(res);
        Duration d = Duration.between(startTimeKMP, endTimeKMP);
        System.out.println("It took " + d.toSeconds() + " seconds");
    }



    static boolean matchesKNP(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();

        // create lps[] that will hold the longest
        // prefix suffix values for pattern
        int lps[] = new int[M];
        int j = 0; // index for pat[]


        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pat, M, lps);

        int i = 0; // index for txt[]
        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
//                System.out.println("Found pattern "
//                        + "at index " + (i - j));
                j = lps[j - 1];
                return true;
            }

            // mismatch after j matches
            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return false;
    }

    static void computeLPSArray(String pat, int M, int lps[]) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

}
