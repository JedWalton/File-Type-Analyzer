package analyzer;

public class KMP implements SearchStrategy {

    @Override
    public boolean search(String text, String pattern) {
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
}