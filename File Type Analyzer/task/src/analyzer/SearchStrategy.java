package analyzer;

@FunctionalInterface
public interface SearchStrategy {

    public boolean search(String text, String pattern);
}
