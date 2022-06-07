package analyzer;

public class RabinKarp implements SearchStrategy {

    private final long a = 33;
    private final long mod = 1_000_000_000 + 7;

    private long getHash(String text) {
        long hash = 0;
        long multiplier = 1;
        for(int i = 0; i < text.length(); i++) {
            hash = (hash + text.charAt(i) * multiplier) % mod;
            multiplier = (multiplier * a) % mod;
        }
        return hash % mod;
    }

    private boolean rabinKarp(String text, String pattern) {
        int N = text.length();
        int M = pattern.length();
        long patternHash = getHash(pattern);
        long lastPower = 1;
        for(int i = 0; i < M - 1; i++) {
            lastPower = (lastPower * a) % mod;
        }
        long substrHash = getHash(text.substring(N - M, N));
        for(int i = N - M; i >= 0; i--) {
            if(i != N - M) {
                substrHash -= (text.charAt(i + M) * lastPower) % mod;
                substrHash = (substrHash + mod) % mod;
                substrHash *= a;
                substrHash += text.charAt(i) ;
                substrHash %= mod;
            }
            boolean isEqual = false;
            int comparisons = 0;
            if(substrHash == patternHash) {
                for(int j = i; j < i + M; j++) {
                    comparisons++;
                    if(pattern.charAt(j - i) != text.charAt(j)) {
                        break;
                    }
                }
                isEqual = comparisons == M;
            }
            if(isEqual) {
                return true;
            }

        }

        return false;
    }

    @Override
    public boolean search(String text, String pattern) {
        if(text.length() < pattern.length()) {
            return false;
        }
        return rabinKarp(text, pattern);
    }
}