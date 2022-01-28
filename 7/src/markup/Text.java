package markup;

public class Text implements Element{
    private final StringBuilder textValue;
    public Text(String value){
        this.textValue = new StringBuilder(value);
    }

    @Override
    public StringBuilder getTextMarkdown() {
        return textValue;
    }

    @Override
    public StringBuilder getTextHtml() {
        return textValue;
    }

    @Override
    public void setHtmlRules(String start, String end) {}

    @Override
    public void setMarkdownRules(String start, String end) {}
}
