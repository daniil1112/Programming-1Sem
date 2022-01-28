package md2html;

public class Header extends MainElement {
    public Header(String input, int hashCounter) {
        super(input);
        this.setRules("<h"+hashCounter+">","</h"+hashCounter+">");
    }
}
