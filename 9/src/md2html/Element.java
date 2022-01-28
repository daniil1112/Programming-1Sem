package md2html;

public interface Element {

    StringBuilder toHTML();
    void setRules(String start, String end);

}
