package expression.parser;

public class BaseParser {
    private char END = 0;
    protected CharSource source;
    private char ch;

    public BaseParser(CharSource source) {
        this.source = source;
        take();
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected char getChar() {
        return ch;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        } else {
            return false;
        }
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw source.error(String.format(
                    "Expected '%s', found '%s'",
                    expected, ch
            ));
        }
    }

    protected boolean end() {
        return test(END);
    }

    protected void expect(final String value) {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean testWord(final String value) {
        char chSaved = ch;
        int taked = 0;
        for (char c : value.toCharArray()) {
            if (!take(c)) {
                source.movePosLeft(taked);
                ch = chSaved;
                return false;
            }
            taked++;
        }
        return true;
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected char getCurrentChar() {
        return ch;
    }

    protected void toNext() {
        source.next();
    }
}
