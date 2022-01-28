package md2html;

public class Paragraph extends MainElement {
    public Paragraph(String input) {
        super(input);
        this.setRules("<p>","</p>");
    }

}
