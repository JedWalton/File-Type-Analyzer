package analyzer;

public class Naive implements SearchStrategy {

    @Override
    public boolean search(String text, String pattern) {
        return text.contains(pattern);
    }
}