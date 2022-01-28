package expression.parser;

public class StringCharSource implements CharSource {
    private final String string;
    private int pos;

    public StringCharSource(String string) {
        this.string = string;
    }

    @Override
    public char next() {
        return string.charAt(pos++);
    }

    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public IllegalArgumentException error(String message) {
        return new IllegalArgumentException(String.format(
                "%d: %s (rest of input %s)",
                pos, message, string.substring(pos)
        ));
    }

    public void movePosLeft(int count) {
        if (pos + count >= 0) {
            pos -= count;
        } else {
            throw new IllegalArgumentException("Moving pos out of range");
        }

    }
}
