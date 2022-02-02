package expression.parser;

public interface CharSource {
    char next();

    boolean hasNext();

    void movePosLeft(int count);

    IllegalArgumentException error(String message);
}
